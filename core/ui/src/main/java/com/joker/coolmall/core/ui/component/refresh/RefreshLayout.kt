package com.joker.coolmall.core.ui.component.refresh

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXXLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.ui.component.loading.LoadMore

/**
 * 支持下拉刷新和上拉加载更多的布局组件
 *
 * 基于 Compose 原生组件实现的可刷新列表布局，包含：
 * 1. 下拉刷新功能 - 使用 PullToRefreshBox
 * 2. 自动检测加载更多 - 通过监听列表滚动位置
 * 3. 列表状态管理 - 维护 LazyListState
 * 4. 加载更多组件 - 显示不同的加载状态
 *
 * @param modifier 修饰符
 * @param listState 列表状态，如果为 null 则创建新
 * @param isRefreshing 是否正在刷新
 * @param loadMoreState 加载更多状态
 * @param onRefresh 刷新回调
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 判断是否应该触发加载更多的函数
 * @param content 列表内容构建器，完全控制列表内容（包括头部、项目等）
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefreshLayout(
    modifier: Modifier = Modifier,
    listState: LazyListState? = null,
    isRefreshing: Boolean = false,
    loadMoreState: LoadMoreState = LoadMoreState.PullToLoad,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean = { _, _ -> false },
    content: LazyListScope.() -> Unit
) {
    // 如果未提供列表状态，则创建一个
    val innerListState = listState ?: rememberLazyListState()

    // 监听是否需要加载更多
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = innerListState.layoutInfo.visibleItemsInfo.lastOrNull()
            if (lastVisibleItem != null) {
                shouldTriggerLoadMore(
                    lastVisibleItem.index,
                    innerListState.layoutInfo.totalItemsCount
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

    // 下拉刷新容器
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier
    ) {
        // 列表内容
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(SpaceVerticalMedium),
            state = innerListState,
            modifier = Modifier.fillMaxSize()
        ) {
            // 列表内容
            content()

            // 添加加载更多组件
            item {
                LoadMore(
                    modifier = Modifier.padding(horizontal = SpaceHorizontalXXLarge),
                    state = loadMoreState,
                    listState = if (loadMoreState == LoadMoreState.Loading) innerListState else null,
                    onRetry = onLoadMore
                )
            }
        }
    }
} 