/**
 * 订单评价界面
 *
 * @author Joker.X
 */
package com.joker.coolmall.feature.order.view

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
import com.joker.coolmall.feature.order.viewmodel.OrderCommentViewModel

/**
 * 订单评价路由
 *
 * @param viewModel 订单评价 ViewModel
 */
@Composable
internal fun OrderCommentRoute(
    viewModel: OrderCommentViewModel = hiltViewModel()
) {
    OrderCommentScreen(
        onBackClick = viewModel::navigateBack,
        onRetry = {}
    )
}

/**
 * 订单评价界面
 *
 * @param uiState UI状态
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrderCommentScreen(
    uiState: BaseNetWorkUiState<Any> = BaseNetWorkUiState.Loading,
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    AppScaffold(
        titleText = "订单评价",
        onBackClick = onBackClick
    ) {
        BaseNetWorkView(
            uiState = uiState,
            onRetry = onRetry
        ) {
            OrderCommentContentView()
        }
    }
}

/**
 * 订单评价内容视图
 */
@Composable
private fun OrderCommentContentView() {
    AppText(
        text = "订单评价",
        size = TextSize.TITLE_MEDIUM
    )
}

/**
 * 订单评价界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun OrderCommentScreenPreview() {
    AppTheme {
        OrderCommentScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}

/**
 * 订单评价界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun OrderCommentScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        OrderCommentScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}