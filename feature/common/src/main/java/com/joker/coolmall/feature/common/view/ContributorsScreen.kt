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
import com.joker.coolmall.feature.common.viewmodel.ContributorsViewModel

/**
 * 贡献者列表路由
 *
 * @param viewModel 贡献者列表 ViewModel
 */
@Composable
internal fun ContributorsRoute(
    viewModel: ContributorsViewModel = hiltViewModel()
) {
    ContributorsScreen(
        onBackClick = viewModel::navigateBack,
        onRetry = {}
    )
}

/**
 * 贡献者列表界面
 *
 * @param uiState UI状态
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ContributorsScreen(
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
            ContributorsContentView()
        }
    }
}

/**
 * 贡献者列表内容视图
 */
@Composable
private fun ContributorsContentView() {
    AppText(
        text = "贡献者列表",
        size = TextSize.TITLE_MEDIUM
    )
}

/**
 * 贡献者列表界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun ContributorsScreenPreview() {
    AppTheme {
        ContributorsScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}

/**
 * 贡献者列表界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun ContributorsScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        ContributorsScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}