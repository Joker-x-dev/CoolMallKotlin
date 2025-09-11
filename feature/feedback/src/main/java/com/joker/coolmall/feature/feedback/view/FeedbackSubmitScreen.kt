package com.joker.coolmall.feature.feedback.view

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
import com.joker.coolmall.feature.feedback.viewmodel.FeedbackSubmitViewModel

/**
 * 提交反馈路由
 *
 * @param viewModel 提交反馈 ViewModel
 * @author Joker.X
 */
@Composable
internal fun FeedbackSubmitRoute(
    viewModel: FeedbackSubmitViewModel = hiltViewModel()
) {
    FeedbackSubmitScreen(
        onBackClick = viewModel::navigateBack,
        onRetry = {}
    )
}

/**
 * 提交反馈界面
 *
 * @param uiState UI状态
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FeedbackSubmitScreen(
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
            FeedbackSubmitContentView()
        }
    }
}

/**
 * 提交反馈内容视图
 */
@Composable
private fun FeedbackSubmitContentView() {
    AppText(
        text = "提交反馈",
        size = TextSize.TITLE_MEDIUM
    )
}

/**
 * 提交反馈界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun FeedbackSubmitScreenPreview() {
    AppTheme {
        FeedbackSubmitScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}

/**
 * 提交反馈界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun FeedbackSubmitScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        FeedbackSubmitScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}