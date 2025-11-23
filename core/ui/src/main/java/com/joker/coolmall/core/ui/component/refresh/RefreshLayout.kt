package com.joker.coolmall.core.ui.component.refresh

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.common.base.state.LoadMoreState
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

/**
 * 支持下拉刷新和上拉加载更多的布局组件
 *
 * 基于 Compose 自定义 Layout 和 NestedScrollConnection 实现。
 * 替换了官方的 PullToRefreshBox
 *
 * @param modifier 修饰符
 * @param isGrid 是否为网格模式，默认为 false（列表模式）
 * @param listState 列表状态，如果为 null 则创建新（列表模式时使用）
 * @param gridState 网格状态，如果为 null 则创建新（网格模式时使用）
 * @param isRefreshing 是否正在刷新
 * @param loadMoreState 加载更多状态
 * @param scrollBehavior 顶部导航栏滚动行为，用于实现滑动折叠效果
 * @param onRefresh 刷新回调
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 判断是否应该触发加载更多的函数
 * @param gridContent 网格内容构建器（网格模式时使用）
 * @param content 列表内容构建器（列表模式时使用）
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefreshLayout(
    modifier: Modifier = Modifier,
    isGrid: Boolean = false,
    listState: LazyListState? = null,
    gridState: LazyStaggeredGridState? = null,
    isRefreshing: Boolean = false,
    loadMoreState: LoadMoreState = LoadMoreState.PullToLoad,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean = { _, _ -> false },
    gridContent: LazyStaggeredGridScope.() -> Unit = {},
    content: LazyListScope.() -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    val refreshState = rememberRefreshState(coroutineScope)

    // 同步刷新状态
    LaunchedEffect(isRefreshing) {
        if (!isRefreshing && refreshState.isRefreshing) {
            // 从刷新状态变为非刷新状态时，如果还有偏移量，说明需要进入结束动画
            // 立即设置 isFinishing 为 true，防止 UI 闪烁（Loading 动画重置）
            if (refreshState.indicatorOffset > 0) {
                refreshState.isFinishing = true
            }
        }
        refreshState.isRefreshing = isRefreshing
    }

    // 监听刷新状态变化
    LaunchedEffect(refreshState.isRefreshing) {
        if (refreshState.isRefreshing) {
            refreshState.animateIsOver = false
            refreshState.isFinishing = false
            onRefresh()
        } else {
            // 刷新结束，如果有偏移量（说明是刚刷新完），则显示完成状态并延迟收起
            if (refreshState.indicatorOffset > 0) {
                refreshState.isFinishing = true
                delay(800)
                refreshState.isFinishing = false
                refreshState.animateOffsetTo(0f)
            } else {
                refreshState.animateOffsetTo(0f)
            }
        }
    }

    // 使用 Layout 实现自定义布局
    Layout(
        modifier = modifier
            .clipToBounds()
            .fillMaxSize()
            .nestedScroll(refreshState.connection)
            .let { mod ->
                if (scrollBehavior != null) {
                    mod.nestedScroll(scrollBehavior.nestedScrollConnection)
                } else {
                    mod
                }
            },
        content = {
            // 内容区域
            RefreshContent(
                isGrid = isGrid,
                listState = listState,
                gridState = gridState,
                loadMoreState = loadMoreState,
                onLoadMore = onLoadMore,
                shouldTriggerLoadMore = shouldTriggerLoadMore,
                gridContent = gridContent,
                content = content
            )

            // 刷新指示器
            RefreshHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp), // 调整高度为标准高度，避免遮挡
                state = refreshState
            )
        }
    ) { measurables, constraints ->
        val contentPlaceable = measurables[0].measure(constraints)
        val headerPlaceable = measurables.getOrNull(1)?.measure(
            constraints.copy(minHeight = 0, maxHeight = constraints.maxHeight)
        )

        // 设定触发刷新的阈值高度
        // 使用测量的高度作为阈值，确保完全显示
        refreshState.headerHeight = headerPlaceable?.height?.toFloat() ?: 0f

        layout(constraints.maxWidth, constraints.maxHeight) {
            // 内容随刷新offset移动
            contentPlaceable.placeRelative(0, refreshState.indicatorOffset.roundToInt())

            // 刷新指示器
            // 它的位置需要精心计算以保持"拉伸"的视觉中心感
            // 这里我们将它固定在顶部区域，通过 offset 移动
            headerPlaceable?.placeRelative(
                0,
                -headerPlaceable.height + refreshState.indicatorOffset.roundToInt()
            )
        }
    }
}