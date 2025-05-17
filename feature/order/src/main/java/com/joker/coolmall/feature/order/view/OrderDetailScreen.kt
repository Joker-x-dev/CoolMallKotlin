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
import com.joker.coolmall.feature.order.R
import com.joker.coolmall.feature.order.viewmodel.OrderDetailViewModel

/**
 * 订单详情路由
 *
 * @param viewModel 订单详情ViewModel
 */
@Composable
internal fun OrderDetailRoute(
    viewModel: OrderDetailViewModel = hiltViewModel()
) {
    OrderDetailScreen(
        onBackClick = viewModel::navigateBack,
        onRetry = {}
    )
}

/**
 * 订单详情页面
 *
 * @param uiState UI状态
 * @param onBackClick 返回回调
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrderDetailScreen(
    uiState: BaseNetWorkUiState<Any> = BaseNetWorkUiState.Loading,
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    AppScaffold(
        title = R.string.order_detail,
        onBackClick = onBackClick
    ) {
        BaseNetWorkView(
            uiState = uiState,
            onRetry = onRetry
        ) {
            OrderDetailContentView()
        }
    }
}

/**
 * 订单详情内容视图
 */
@Composable
private fun OrderDetailContentView() {
    AppText(
        text = "订单详情",
        size = TextSize.TITLE_MEDIUM
    )
}

@Preview(showBackground = true)
@Composable
internal fun OrderDetailScreenPreview() {
    AppTheme {
        OrderDetailScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun OrderDetailScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        OrderDetailScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
} 