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
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.ui.component.empty.EmptyNetwork
import com.joker.coolmall.core.ui.component.loading.PageLoading

/**
 * 基础网络视图组件，用于处理网络请求的三种状态：加载中、错误和成功
 * 简化页面开发，避免重复编写状态处理代码
 *
 * @param T 数据类型
 * @param uiState 当前UI状态
 * @param modifier 可选修饰符
 * @param padding 内边距值，通常来自Scaffold
 * @param onRetry 错误状态下重试点击回调
 * @param customLoading 自定义加载组件，为null时使用默认组件
 * @param customError 自定义错误组件，为null时使用默认组件
 * @param content 成功状态下显示的内容，接收数据参数
 * @author Joker.X
 */
@Composable
fun <T> BaseNetWorkView(
    uiState: BaseNetWorkUiState<T>,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(),
    onRetry: () -> Unit = {},
    customLoading: @Composable (() -> Unit)? = null,
    customError: @Composable (() -> Unit)? = null,
    content: @Composable (data: T) -> Unit
) {
    Box(
        modifier = modifier
            .padding(padding),
    ) {
        AnimatedContent(
            targetState = uiState,
            transitionSpec = {
                // 定义进入和退出动画
                fadeIn(animationSpec = tween(300)) togetherWith
                        fadeOut(animationSpec = tween(300))
            },
            label = "NetworkStateAnimation"
        ) { state ->
            when (state) {
                is BaseNetWorkUiState.Loading -> {
                    if (customLoading != null) {
                        customLoading()
                    } else {
                        PageLoading()
                    }
                }

                is BaseNetWorkUiState.Error -> {
                    if (customError != null) {
                        customError()
                    } else {
                        EmptyNetwork(onRetryClick = onRetry)
                    }
                }

                is BaseNetWorkUiState.Success -> content(state.data)
            }
        }
    }
}