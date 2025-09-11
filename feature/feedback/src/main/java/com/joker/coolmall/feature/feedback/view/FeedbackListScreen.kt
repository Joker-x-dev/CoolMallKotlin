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
import com.joker.coolmall.feature.feedback.viewmodel.FeedbackListViewModel

/**
 * 反馈列表路由
 *
 * @param viewModel 反馈列表 ViewModel
 * @author Joker.X
 */
@Composable
internal fun FeedbackListRoute(
    viewModel: FeedbackListViewModel = hiltViewModel()
) {
    FeedbackListScreen(
        onBackClick = viewModel::navigateBack,
        onRetry = {}
    )
}

/**
 * 反馈列表界面
 *
 * @param uiState UI状态
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FeedbackListScreen(
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
            FeedbackListContentView()
        }
    }
}

/**
 * 反馈列表内容视图
 */
@Composable
private fun FeedbackListContentView() {
    AppText(
        text = "反馈列表",
        size = TextSize.TITLE_MEDIUM
    )
}

/**
 * 反馈列表界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun FeedbackListScreenPreview() {
    AppTheme {
        FeedbackListScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}

/**
 * 反馈列表界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun FeedbackListScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        FeedbackListScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}