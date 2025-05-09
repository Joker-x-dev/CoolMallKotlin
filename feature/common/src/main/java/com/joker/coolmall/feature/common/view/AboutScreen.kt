package com.joker.coolmall.feature.common.view

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
import com.joker.coolmall.feature.common.viewmodel.AboutViewModel

/**
 * 关于我们路由
 *
 * @param viewModel 关于我们 ViewModel
 */
@Composable
internal fun AboutRoute(
    viewModel: AboutViewModel = hiltViewModel()
) {
    AboutScreen(
        onBackClick = viewModel::navigateBack,
        onRetry = {}
    )
}

/**
 * 关于我们界面
 *
 * @param uiState UI状态
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AboutScreen(
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
            AboutContentView()
        }
    }
}

/**
 * 关于我们内容视图
 */
@Composable
private fun AboutContentView() {
    AppText(
        text = "关于我们",
        size = TextSize.TITLE_MEDIUM
    )
}

/**
 * 关于我们界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun AboutScreenPreview() {
    AppTheme {
        AboutScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}

/**
 * 关于我们界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun AboutScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        AboutScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
} 