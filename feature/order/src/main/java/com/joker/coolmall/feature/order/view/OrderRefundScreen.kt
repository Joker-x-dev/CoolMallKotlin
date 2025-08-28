/**
 * 退款申请界面
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
import com.joker.coolmall.feature.order.viewmodel.OrderRefundViewModel

/**
 * 退款申请路由
 *
 * @param viewModel 退款申请 ViewModel
 */
@Composable
internal fun OrderRefundRoute(
    viewModel: OrderRefundViewModel = hiltViewModel()
) {
    OrderRefundScreen(
        onBackClick = viewModel::navigateBack,
        onRetry = {}
    )
}

/**
 * 退款申请界面
 *
 * @param uiState UI状态
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrderRefundScreen(
    uiState: BaseNetWorkUiState<Any> = BaseNetWorkUiState.Loading,
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    AppScaffold(
        titleText = "退款申请",
        onBackClick = onBackClick
    ) {
        BaseNetWorkView(
            uiState = uiState,
            onRetry = onRetry
        ) {
            OrderRefundContentView()
        }
    }
}

/**
 * 退款申请内容视图
 */
@Composable
private fun OrderRefundContentView() {
    AppText(
        text = "退款申请",
        size = TextSize.TITLE_MEDIUM
    )
}

/**
 * 退款申请界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun OrderRefundScreenPreview() {
    AppTheme {
        OrderRefundScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}

/**
 * 退款申请界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun OrderRefundScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        OrderRefundScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}