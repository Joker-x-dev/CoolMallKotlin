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
import com.joker.coolmall.feature.order.viewmodel.OrderListViewModel

/**
 * 订单列表路由
 *
 * @param viewModel 订单列表ViewModel
 */
@Composable
internal fun OrderListRoute(
    viewModel: OrderListViewModel = hiltViewModel()
) {
    OrderListScreen(
        onBackClick = viewModel::navigateBack,
        onRetry = {}
    )
}

/**
 * 订单列表页面
 *
 * @param uiState UI状态
 * @param onBackClick 返回回调
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrderListScreen(
    uiState: BaseNetWorkUiState<Any> = BaseNetWorkUiState.Loading,
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    AppScaffold {
        BaseNetWorkView(
            uiState = uiState,
            onRetry = onRetry
        ) {
            OrderListContentView()
        }
    }
}

/**
 * 订单列表内容视图
 */
@Composable
private fun OrderListContentView() {
    AppText(
        text = "订单列表",
        size = TextSize.TITLE_MEDIUM
    )
}

@Preview(showBackground = true)
@Composable
internal fun OrderListScreenPreview() {
    AppTheme {
        OrderListScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun OrderListScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        OrderListScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
} 