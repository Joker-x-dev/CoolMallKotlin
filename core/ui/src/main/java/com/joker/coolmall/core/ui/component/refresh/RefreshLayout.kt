package com.joker.coolmall.core.ui.component.refresh

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXXLarge
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.ui.component.loading.LoadMore

/**
 * 支持下拉刷新和上拉加载更多的布局组件
 * 支持列表和网格两种模式
 *
 * 基于 Compose 原生组件实现的可刷新布局，包含：
 * 1. 下拉刷新功能 - 使用 PullToRefreshBox
 * 2. 自动检测加载更多 - 通过监听滚动位置
 * 3. 状态管理 - 维护 LazyListState 或 LazyGridState
 * 4. 加载更多组件 - 显示不同的加载状态
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
    // 下拉刷新容器
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier.let { mod ->
            if (scrollBehavior != null) {
                mod.nestedScroll(scrollBehavior.nestedScrollConnection)
            } else {
                mod
            }
        }
    ) {
        // 添加布局切换动画
        AnimatedContent(
            targetState = isGrid,
            transitionSpec = {
                (fadeIn(animationSpec = tween(300, easing = LinearEasing)) +
                        scaleIn(
                            initialScale = 0.92f,
                            animationSpec = tween(300, easing = LinearEasing)
                        ))
                    .togetherWith(
                        fadeOut(animationSpec = tween(300, easing = LinearEasing)) +
                                scaleOut(
                                    targetScale = 0.92f,
                                    animationSpec = tween(300, easing = LinearEasing)
                                )
                    )
            },
            label = "layout_switch_animation"
        ) { targetIsGrid ->
            if (targetIsGrid) {
                // 网格模式
                RefreshGridContent(
                    gridState = gridState,
                    loadMoreState = loadMoreState,
                    onLoadMore = onLoadMore,
                    shouldTriggerLoadMore = shouldTriggerLoadMore,
                    content = gridContent
                )
            } else {
                // 列表模式
                RefreshListContent(
                    listState = listState,
                    loadMoreState = loadMoreState,
                    onLoadMore = onLoadMore,
                    shouldTriggerLoadMore = shouldTriggerLoadMore,
                    content = content
                )
            }
        }
    }
}

/**
 * 列表内容组件
 * 
 * @param listState 列表状态，如果为 null 则创建新的状态
 * @param loadMoreState 加载更多状态
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 判断是否应该触发加载更多的函数
 * @param content 列表内容构建器
 */
@Composable
private fun RefreshListContent(
    listState: LazyListState?,
    loadMoreState: LoadMoreState,
    onLoadMore: () -> Unit,
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean,
    content: LazyListScope.() -> Unit
) {
    // 如果未提供列表状态，则创建一个
    val actualListState = listState ?: rememberLazyListState()

    // 监听是否需要加载更多
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = actualListState.layoutInfo.visibleItemsInfo.lastOrNull()
            if (lastVisibleItem != null) {
                shouldTriggerLoadMore(
                    lastVisibleItem.index,
                    actualListState.layoutInfo.totalItemsCount
                )
            } else false
        }
    }

    // 触发加载更多
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            onLoadMore()
        }
    }

    // 列表模式
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(SpaceVerticalMedium),
        state = actualListState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = SpaceHorizontalMedium)
    ) {

        // 顶部占高
        item {
            Spacer(modifier = Modifier)
        }

        // 列表内容
        content()

        // 添加加载更多组件
        item {
            LoadMore(
                modifier = Modifier.padding(horizontal = SpaceHorizontalXXLarge),
                state = loadMoreState,
                listState = if (loadMoreState == LoadMoreState.Loading) actualListState else null,
                onRetry = onLoadMore
            )
        }
    }
}

/**
 * 网格内容组件（已改为交错瀑布流 StaggeredGrid）
 * 
 * @param gridState 网格状态，如果为 null 则创建新的状态
 * @param loadMoreState 加载更多状态
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 判断是否应该触发加载更多的函数
 * @param content 网格内容构建器
 */
@Composable
private fun RefreshGridContent(
    gridState: LazyStaggeredGridState? = null,
    loadMoreState: LoadMoreState,
    onLoadMore: () -> Unit,
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean,
    content: LazyStaggeredGridScope.() -> Unit
) {

    // 如果未提供列表状态，则创建一个
    val actualGridState = gridState ?: rememberLazyStaggeredGridState()

    // 监听是否需要加载更多
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = actualGridState.layoutInfo.visibleItemsInfo.lastOrNull()
            if (lastVisibleItem != null) {
                shouldTriggerLoadMore(
                    lastVisibleItem.index,
                    actualGridState.layoutInfo.totalItemsCount
                )
            } else false
        }
    }

    // 触发加载更多
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            onLoadMore()
        }
    }

    // 网格模式
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(SpacePaddingMedium),
        horizontalArrangement = Arrangement.spacedBy(SpacePaddingMedium),
        verticalItemSpacing = SpacePaddingMedium,
        state = actualGridState
    ) {

        // 内容
        content()

        // 添加加载更多组件
        item(span = StaggeredGridItemSpan.FullLine) {
            LoadMore(
                modifier = Modifier.padding(horizontal = SpaceHorizontalXXLarge),
                state = loadMoreState,
                listState = null,
                onRetry = onLoadMore
            )
        }
    }
}