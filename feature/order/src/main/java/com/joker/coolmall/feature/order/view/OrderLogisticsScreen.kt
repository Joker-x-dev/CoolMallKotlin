/**
 * 订单物流界面
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
import com.joker.coolmall.feature.order.viewmodel.OrderLogisticsViewModel

/**
 * 订单物流路由
 *
 * @param viewModel 订单物流 ViewModel
 */
@Composable
internal fun OrderLogisticsRoute(
    viewModel: OrderLogisticsViewModel = hiltViewModel()
) {
    OrderLogisticsScreen(
        onBackClick = viewModel::navigateBack,
        onRetry = {}
    )
}

/**
 * 订单物流界面
 *
 * @param uiState UI状态
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrderLogisticsScreen(
    uiState: BaseNetWorkUiState<Any> = BaseNetWorkUiState.Loading,
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    AppScaffold(
        titleText = "订单物流",
        onBackClick = onBackClick
    ) {
        BaseNetWorkView(
            uiState = uiState,
            onRetry = onRetry
        ) {
            OrderLogisticsContentView()
        }
    }
}

/**
 * 订单物流内容视图
 */
@Composable
private fun OrderLogisticsContentView() {
    AppText(
        text = "订单物流",
        size = TextSize.TITLE_MEDIUM
    )
}

/**
 * 订单物流界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun OrderLogisticsScreenPreview() {
    AppTheme {
        OrderLogisticsScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}

/**
 * 订单物流界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun OrderLogisticsScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        OrderLogisticsScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}