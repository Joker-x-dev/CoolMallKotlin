package com.joker.coolmall.core.ui.component.network

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
 * @param content 成功状态下显示的内容，接收数据参数
 */
@Composable
fun <T : Any> BaseNetWorkView(
    uiState: BaseNetWorkUiState<T>,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(),
    onRetry: () -> Unit = {},
    content: @Composable (data: T) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        when (uiState) {
            is BaseNetWorkUiState.Loading -> PageLoading()
            is BaseNetWorkUiState.Error -> EmptyNetwork(onRetryClick = onRetry)
            is BaseNetWorkUiState.Success -> content(uiState.data)
        }
    }
} 