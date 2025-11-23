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
 * 刷新内容区域组件
 *
 * 根据 [isGrid] 参数切换显示列表或网格布局，并支持布局切换动画。
 *
 * @param isGrid 是否为网格模式
 * @param listState 列表状态
 * @param gridState 网格状态
 * @param loadMoreState 加载更多状态
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 判断是否应该触发加载更多的函数
 * @param gridContent 网格内容构建器
 * @param content 列表内容构建器
 * @author Joker.X
 */
@Composable
fun RefreshContent(
    isGrid: Boolean,
    listState: LazyListState?,
    gridState: LazyStaggeredGridState?,
    loadMoreState: LoadMoreState,
    onLoadMore: () -> Unit,
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean,
    gridContent: LazyStaggeredGridScope.() -> Unit,
    content: LazyListScope.() -> Unit
) {
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
            RefreshGridContent(
                gridState = gridState,
                loadMoreState = loadMoreState,
                onLoadMore = onLoadMore,
                shouldTriggerLoadMore = shouldTriggerLoadMore,
                content = gridContent
            )
        } else {
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

/**
 * 列表刷新内容组件
 *
 * @param listState 列表状态
 * @param loadMoreState 加载更多状态
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 判断是否应该触发加载更多的函数
 * @param content 列表内容构建器
 * @author Joker.X
 */
@Composable
private fun RefreshListContent(
    listState: LazyListState?,
    loadMoreState: LoadMoreState,
    onLoadMore: () -> Unit,
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean,
    content: LazyListScope.() -> Unit
) {
    val actualListState = listState ?: rememberLazyListState()
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

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            onLoadMore()
        }
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(SpaceVerticalMedium),
        state = actualListState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = SpaceHorizontalMedium)
    ) {
        item { Spacer(modifier = Modifier) }
        content()
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
 * 网格刷新内容组件
 *
 * @param gridState 网格状态
 * @param loadMoreState 加载更多状态
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 判断是否应该触发加载更多的函数
 * @param content 网格内容构建器
 * @author Joker.X
 */
@Composable
private fun RefreshGridContent(
    gridState: LazyStaggeredGridState? = null,
    loadMoreState: LoadMoreState,
    onLoadMore: () -> Unit,
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean,
    content: LazyStaggeredGridScope.() -> Unit
) {
    val actualGridState = gridState ?: rememberLazyStaggeredGridState()
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

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            onLoadMore()
        }
    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(SpacePaddingMedium),
        horizontalArrangement = Arrangement.spacedBy(SpacePaddingMedium),
        verticalItemSpacing = SpacePaddingMedium,
        state = actualGridState
    ) {
        content()
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
