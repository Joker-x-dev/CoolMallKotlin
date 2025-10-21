package com.joker.coolmall.core.ui.component.network

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.ui.component.empty.EmptyData
import com.joker.coolmall.core.ui.component.empty.EmptyNetwork
import com.joker.coolmall.core.ui.component.loading.PageLoading

/**
 * 基础网络列表视图组件
 *
 * 用于处理列表页的四种状态：加载中、错误、空数据和成功
 *
 * @param uiState 列表页UI状态
 * @param modifier 修饰符
 * @param padding 内边距
 * @param onRetry 重试回调
 * @param customLoading 自定义加载组件
 * @param customError 自定义错误组件
 * @param customEmpty 自定义空数据组件
 * @param content 成功状态下显示的内容
 * @author Joker.X
 */
@Composable
fun BaseNetWorkListView(
    uiState: BaseNetWorkListUiState,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(),
    onRetry: () -> Unit = {},
    customLoading: @Composable (() -> Unit)? = null,
    customError: @Composable (() -> Unit)? = null,
    customEmpty: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .padding(padding)
    ) {
        AnimatedContent(
            targetState = uiState,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith
                        fadeOut(animationSpec = tween(300))
            },
            label = "ListStateAnimation"
        ) { state ->
            when (state) {
                is BaseNetWorkListUiState.Loading -> {
                    if (customLoading != null) {
                        customLoading()
                    } else {
                        PageLoading()
                    }
                }

                is BaseNetWorkListUiState.Error -> {
                    if (customError != null) {
                        customError()
                    } else {
                        EmptyNetwork(onRetryClick = onRetry)
                    }
                }

                is BaseNetWorkListUiState.Empty -> {
                    if (customEmpty != null) {
                        customEmpty()
                    } else {
                        EmptyData(onRetryClick = onRetry)
                    }
                }

                is BaseNetWorkListUiState.Success -> content()
            }
        }
    }
} 