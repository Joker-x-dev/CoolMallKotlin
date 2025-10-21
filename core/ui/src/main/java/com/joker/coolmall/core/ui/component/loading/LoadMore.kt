package com.joker.coolmall.core.ui.component.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.ui.R
import com.joker.coolmall.core.ui.component.divider.WeDivider
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextType

/**
 * 订单列表加载更多组件
 *
 * 用于显示列表底部的加载状态，支持以下几种状态：
 * 1. 可上拉加载：显示上拉加载更多提示
 * 2. 加载中：显示加载动画和提示文本
 * 3. 加载成功：显示成功提示
 * 4. 加载失败：显示错误提示，支持点击重试
 * 5. 没有更多数据：显示分割线和圆点
 *
 * @param modifier 组件修饰符
 * @param state 当前加载状态，默认为可上拉加载状态
 * @param listState LazyList的状态，用于自动滚动到底部，可为空
 * @param onRetry 加载失败时的重试回调，为空时不可点击重试
 * @author Joker.X
 */
@Composable
fun LoadMore(
    modifier: Modifier = Modifier,
    state: LoadMoreState = LoadMoreState.PullToLoad,
    listState: LazyListState? = null,
    onRetry: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (state) {
            // 可上拉加载更多状态：显示提示文本
            LoadMoreState.PullToLoad -> {
                WeDivider(modifier = Modifier.weight(1f))
                AppText(
                    text = stringResource(R.string.load_more_pull),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    type = TextType.SECONDARY
                )
                WeDivider(modifier = Modifier.weight(1f))
            }

            // 加载中状态：显示加载动画和提示文本
            LoadMoreState.Loading -> {
                WeLoading() // 显示加载动画
                Spacer(modifier = Modifier.width(8.dp))
                AppText(text = stringResource(R.string.load_more_loading))
                // 如果提供了列表状态，自动滚动到底部
                if (listState != null) {
                    LaunchedEffect(Unit) {
                        listState.scrollToItem(listState.layoutInfo.totalItemsCount)
                    }
                }
            }

            // 加载成功状态：显示加载成功提示
            LoadMoreState.Success -> {
                WeDivider(modifier = Modifier.weight(1f))
                AppText(
                    text = stringResource(R.string.load_more_success),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                WeDivider(modifier = Modifier.weight(1f))
            }

            // 加载失败状态：显示错误提示和分割线
            LoadMoreState.Error -> {
                WeDivider(modifier = Modifier.weight(1f))
                if (onRetry != null) {
                    AppText(
                        text = stringResource(R.string.load_more_error_retry),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clip(MaterialTheme.shapes.small)
                            .clickable { onRetry() }
                            .padding(vertical = 4.dp),
                        type = TextType.ERROR
                    )
                } else {
                    AppText(
                        text = stringResource(R.string.load_more_error),
                        modifier = Modifier.padding(horizontal = 8.dp),
                        type = TextType.ERROR
                    )
                }
                WeDivider(modifier = Modifier.weight(1f))
            }

            // 没有更多数据状态：显示分割线和中间的圆点
            LoadMoreState.NoMore -> {
                // 左侧分割线
                WeDivider(
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.8f)
                )
                // 中间圆点
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(4.dp)
                        .background(
                            MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.8f),
                            CircleShape
                        )
                )
                // 右侧分割线
                WeDivider(
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.8f)
                )
            }
        }
    }
}

/**
 * OrderLoadMore 组件预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun OrderLoadMorePreview() {
    AppTheme {
        Column {
            // 可上拉加载更多状态预览
            LoadMore(state = LoadMoreState.PullToLoad)
            Spacer(modifier = Modifier.height(8.dp))

            // 加载中状态预览
            LoadMore(state = LoadMoreState.Loading)
            Spacer(modifier = Modifier.height(8.dp))

            // 加载成功状态预览
            LoadMore(state = LoadMoreState.Success)
            Spacer(modifier = Modifier.height(8.dp))

            // 加载失败状态预览（带重试回调）
            LoadMore(
                state = LoadMoreState.Error,
                onRetry = {}
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 加载失败状态预览（不带重试回调）
            LoadMore(state = LoadMoreState.Error)
            Spacer(modifier = Modifier.height(8.dp))

            // 没有更多数据状态预览
            LoadMore(state = LoadMoreState.NoMore)
        }
    }
}

/**
 * OrderLoadMore 组件深色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun OrderLoadMorePreviewDark() {
    AppTheme(darkTheme = true) {
        Column {
            // 可上拉加载更多状态预览
            LoadMore(state = LoadMoreState.PullToLoad)
            Spacer(modifier = Modifier.height(8.dp))

            // 加载中状态预览
            LoadMore(state = LoadMoreState.Loading)
            Spacer(modifier = Modifier.height(8.dp))

            // 加载成功状态预览
            LoadMore(state = LoadMoreState.Success)
            Spacer(modifier = Modifier.height(8.dp))

            // 加载失败状态预览（带重试回调）
            LoadMore(
                state = LoadMoreState.Error,
                onRetry = {}
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 加载失败状态预览（不带重试回调）
            LoadMore(state = LoadMoreState.Error)
            Spacer(modifier = Modifier.height(8.dp))

            // 没有更多数据状态预览
            LoadMore(state = LoadMoreState.NoMore)
        }
    }
} 