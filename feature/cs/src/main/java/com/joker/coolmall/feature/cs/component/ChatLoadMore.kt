package com.joker.coolmall.feature.cs.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.ui.component.divider.WeDivider
import com.joker.coolmall.core.ui.component.loading.WeLoading
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.feature.cs.R

/**
 * 聊天消息加载更多组件
 *
 * 专门用于聊天界面的历史消息加载，支持以下几种状态：
 * 1. 可加载：显示加载更多历史消息提示
 * 2. 加载中：显示加载动画和提示文本
 * 3. 加载成功：显示成功提示
 * 4. 加载失败：显示错误提示，支持点击重试
 * 5. 没有更多数据：显示分割线和圆点
 *
 * @param modifier 组件修饰符
 * @param state 当前加载状态，默认为可上拉加载状态
 * @param listState LazyList的状态，用于自动滚动，可为空
 * @param onRetry 加载失败时的重试回调，为空时不可点击重试
 * @author Joker.X
 */
@Composable
fun ChatLoadMore(
    modifier: Modifier = Modifier,
    state: LoadMoreState = LoadMoreState.PullToLoad,
    listState: LazyListState? = null,
    onRetry: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (state) {
            // 可加载更多状态：显示提示文本
            LoadMoreState.PullToLoad -> {
                WeDivider(modifier = Modifier.weight(1f))
                AppText(
                    text = stringResource(R.string.load_more_history),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    type = TextType.SECONDARY
                )
                WeDivider(modifier = Modifier.weight(1f))
            }

            // 加载中状态：显示加载动画和提示文本
            LoadMoreState.Loading -> {
                WeLoading() // 显示加载动画
                Spacer(modifier = Modifier.width(8.dp))
                AppText(text = stringResource(R.string.loading_history))
                // 如果提供了列表状态，在聊天场景中不需要自动滚动
                // 因为用户可能正在查看历史消息
            }

            // 加载成功状态：显示加载成功提示
            LoadMoreState.Success -> {
                WeDivider(modifier = Modifier.weight(1f))
                AppText(
                    text = stringResource(R.string.load_success),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                WeDivider(modifier = Modifier.weight(1f))
            }

            // 加载失败状态：显示错误提示和分割线
            LoadMoreState.Error -> {
                WeDivider(modifier = Modifier.weight(1f))
                if (onRetry != null) {
                    AppText(
                        text = stringResource(R.string.load_failed_retry),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clip(MaterialTheme.shapes.small)
                            .clickable { onRetry() }
                            .padding(vertical = 4.dp),
                        type = TextType.ERROR
                    )
                } else {
                    AppText(
                        text = stringResource(R.string.load_failed),
                        modifier = Modifier.padding(horizontal = 8.dp),
                        type = TextType.ERROR
                    )
                }
                WeDivider(modifier = Modifier.weight(1f))
            }

            // 没有更多数据状态：显示分割线和提示文字
            LoadMoreState.NoMore -> {
                WeDivider(modifier = Modifier.weight(1f))
                AppText(
                    text = stringResource(R.string.no_more_messages),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    type = TextType.SECONDARY
                )
                WeDivider(modifier = Modifier.weight(1f))
            }
        }
    }
}

/**
 * ChatLoadMore 组件预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun ChatLoadMorePreview() {
    AppTheme {
        Column {
            // 可加载更多状态预览
            ChatLoadMore(state = LoadMoreState.PullToLoad)
            Spacer(modifier = Modifier.height(8.dp))

            // 加载中状态预览
            ChatLoadMore(state = LoadMoreState.Loading)
            Spacer(modifier = Modifier.height(8.dp))

            // 加载成功状态预览
            ChatLoadMore(state = LoadMoreState.Success)
            Spacer(modifier = Modifier.height(8.dp))

            // 加载失败状态预览（带重试回调）
            ChatLoadMore(
                state = LoadMoreState.Error,
                onRetry = {}
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 加载失败状态预览（不带重试回调）
            ChatLoadMore(state = LoadMoreState.Error)
            Spacer(modifier = Modifier.height(8.dp))

            // 没有更多数据状态预览
            ChatLoadMore(state = LoadMoreState.NoMore)
        }
    }
}

/**
 * ChatLoadMore 组件深色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun ChatLoadMorePreviewDark() {
    AppTheme(darkTheme = true) {
        Column {
            // 可加载更多状态预览
            ChatLoadMore(state = LoadMoreState.PullToLoad)
            Spacer(modifier = Modifier.height(8.dp))

            // 加载中状态预览
            ChatLoadMore(state = LoadMoreState.Loading)
            Spacer(modifier = Modifier.height(8.dp))

            // 加载成功状态预览
            ChatLoadMore(state = LoadMoreState.Success)
            Spacer(modifier = Modifier.height(8.dp))

            // 加载失败状态预览（带重试回调）
            ChatLoadMore(
                state = LoadMoreState.Error,
                onRetry = {}
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 加载失败状态预览（不带重试回调）
            ChatLoadMore(state = LoadMoreState.Error)
            Spacer(modifier = Modifier.height(8.dp))

            // 没有更多数据状态预览
            ChatLoadMore(state = LoadMoreState.NoMore)
        }
    }
}