package com.joker.coolmall.feature.common.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.feature.common.viewmodel.UserAgreementViewModel

/**
 * 用户协议路由
 *
 * @param viewModel 用户协议 ViewModel
 */
@Composable
internal fun UserAgreementRoute(
    viewModel: UserAgreementViewModel = hiltViewModel()
) {
    UserAgreementScreen(
        onBackClick = viewModel::navigateBack,
        onRetry = {}
    )
}

/**
 * 用户协议界面
 *
 * @param uiState UI状态
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UserAgreementScreen(
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
            UserAgreementContentView()
        }
    }
}

/**
 * 用户协议内容视图
 */
@Composable
private fun UserAgreementContentView() {
    AppText(
        text = "用户协议",
        size = TextSize.TITLE_MEDIUM
    )
}

/**
 * 用户协议界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun UserAgreementScreenPreview() {
    AppTheme {
        UserAgreementScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}

/**
 * 用户协议界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun UserAgreementScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        UserAgreementScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}