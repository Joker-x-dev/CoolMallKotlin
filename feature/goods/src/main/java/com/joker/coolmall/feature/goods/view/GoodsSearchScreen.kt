package com.joker.coolmall.feature.goods.view

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
import com.joker.coolmall.feature.goods.viewmodel.GoodsSearchViewModel

/**
 * 商品搜索路由
 *
 * @param viewModel 商品搜索 ViewModel
 */
@Composable
internal fun GoodsSearchRoute(
    viewModel: GoodsSearchViewModel = hiltViewModel()
) {
    GoodsSearchScreen(
        onBackClick = viewModel::navigateBack,
        onRetry = {}
    )
}

/**
 * 商品搜索界面
 *
 * @param uiState UI状态
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GoodsSearchScreen(
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
            GoodsSearchContentView()
        }
    }
}

/**
 * 商品搜索内容视图
 */
@Composable
private fun GoodsSearchContentView() {
    AppText(
        text = "商品搜索",
        size = TextSize.TITLE_MEDIUM
    )
}

/**
 * 商品搜索界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun GoodsSearchScreenPreview() {
    AppTheme {
        GoodsSearchScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}

/**
 * 商品搜索界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun GoodsSearchScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        GoodsSearchScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}