package com.joker.coolmall.feature.cs.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.feature.cs.viewmodel.ChatViewModel

/**
 * 客服聊天路由
 *
 * @param viewModel 客服聊天 ViewModel
 */
@Composable
internal fun ChatRoute(
    viewModel: ChatViewModel = hiltViewModel()
) {
    ChatScreen(
        onBackClick = viewModel::navigateBack,
        onRetry = {}
    )
}

/**
 * 客服聊天界面
 *
 * @param uiState UI状态
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatScreen(
    uiState: BaseNetWorkUiState<Any> = BaseNetWorkUiState.Loading,
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    AppScaffold(
        onBackClick = onBackClick
    ) {
        BaseNetWorkView(
            uiState = uiState,
            onRetry = onRetry
        ) {
            ChatContentView()
        }
    }
}

/**
 * 客服聊天内容视图
 */
@Composable
private fun ChatContentView() {
    AppText(
        text = "客服聊天",
        size = TextSize.TITLE_MEDIUM
    )
}

/**
 * 客服聊天界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun ChatScreenPreview() {
    AppTheme {
        ChatScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}

/**
 * 客服聊天界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun ChatScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        ChatScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
} 