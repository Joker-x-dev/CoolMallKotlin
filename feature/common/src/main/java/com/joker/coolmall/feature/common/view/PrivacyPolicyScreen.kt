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
import com.joker.coolmall.feature.common.viewmodel.PrivacyPolicyViewModel

/**
 * 隐私政策路由
 *
 * @param viewModel 隐私政策 ViewModel
 */
@Composable
internal fun PrivacyPolicyRoute(
    viewModel: PrivacyPolicyViewModel = hiltViewModel()
) {
    PrivacyPolicyScreen(
        onBackClick = viewModel::navigateBack,
        onRetry = {}
    )
}

/**
 * 隐私政策界面
 *
 * @param uiState UI状态
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PrivacyPolicyScreen(
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
            PrivacyPolicyContentView()
        }
    }
}

/**
 * 隐私政策内容视图
 */
@Composable
private fun PrivacyPolicyContentView() {
    AppText(
        text = "隐私政策",
        size = TextSize.TITLE_MEDIUM
    )
}

/**
 * 隐私政策界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun PrivacyPolicyScreenPreview() {
    AppTheme {
        PrivacyPolicyScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}

/**
 * 隐私政策界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun PrivacyPolicyScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        PrivacyPolicyScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}