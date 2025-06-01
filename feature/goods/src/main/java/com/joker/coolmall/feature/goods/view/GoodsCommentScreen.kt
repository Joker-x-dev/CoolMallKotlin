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
import com.joker.coolmall.feature.goods.viewmodel.GoodsCommentViewModel

/**
 * 商品评论路由
 *
 * @param viewModel 商品评论 ViewModel
 */
@Composable
internal fun GoodsCommentRoute(
    viewModel: GoodsCommentViewModel = hiltViewModel()
) {
    GoodsCommentScreen(
        onBackClick = viewModel::navigateBack,
        onRetry = {}
    )
}

/**
 * 商品评论界面
 *
 * @param uiState UI状态
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GoodsCommentScreen(
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
            GoodsCommentContentView()
        }
    }
}

/**
 * 商品评论内容视图
 */
@Composable
private fun GoodsCommentContentView() {
    AppText(
        text = "商品评论",
        size = TextSize.TITLE_MEDIUM
    )
}

/**
 * 商品评论界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun GoodsCommentScreenPreview() {
    AppTheme {
        GoodsCommentScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}

/**
 * 商品评论界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun GoodsCommentScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        GoodsCommentScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}