package com.joker.coolmall.feature.order.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.component.EndRow
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.model.entity.Cart
import com.joker.coolmall.core.model.entity.DictItem
import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.model.preview.previewCartList
import com.joker.coolmall.core.model.preview.previewOrder
import com.joker.coolmall.core.ui.component.button.AppButton
import com.joker.coolmall.core.ui.component.goods.OrderGoodsCard
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.modal.DictSelectModal
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.PriceText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.title.TitleWithLine
import com.joker.coolmall.feature.order.R
import com.joker.coolmall.feature.order.viewmodel.OrderRefundViewModel

/**
 * 退款申请路由
 *
 * @param viewModel 退款申请 ViewModel
 * @author Joker.X
 */
@Composable
internal fun OrderRefundRoute(
    viewModel: OrderRefundViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState() // 订单数据状态
    val cartList by viewModel.cartList.collectAsState() // 购物车列表
    val refundModalVisible by viewModel.refundModalVisible.collectAsState() // 退款原因弹窗显示状态
    val refundReasonsModalUiState by viewModel.refundReasonsModalUiState.collectAsState() // 退款原因弹窗UI状态
    val selectedRefundReason by viewModel.selectedRefundReason.collectAsState() // 选中的退款原因

    OrderRefundScreen(
        uiState = uiState,
        cartList = cartList,
        refundModalVisible = refundModalVisible,
        refundReasonsModalUiState = refundReasonsModalUiState,
        selectedRefundReason = selectedRefundReason,
        onBackClick = viewModel::navigateBack,
        onRetry = viewModel::retryRequest,
        onReasonClick = viewModel::showRefundModal,
        onRefundModalDismiss = viewModel::hideRefundModal,
        onRefundModalExpanded = viewModel::onRefundModalExpanded,
        onReasonSelected = viewModel::selectRefundReason,
        onReasonConfirm = viewModel::selectRefundReason,
        onReasonRetry = viewModel::loadRefundReasons,
        onSubmitClick = viewModel::submitRefundApplication
    )
}

/**
 * 退款申请界面
 *
 * @param uiState UI状态
 * @param cartList 转换后的购物车列表
 * @param refundModalVisible 退款原因弹窗是否可见
 * @param refundReasonsModalUiState 退款原因弹窗UI状态
 * @param selectedRefundReason 选中的退款原因
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 * @param onReasonClick 选择退款原因回调
 * @param onRefundModalDismiss 关闭退款原因弹窗回调
 * @param onRefundModalExpanded 退款弹窗展开完成回调
 * @param onReasonSelected 退款原因选择回调
 * @param onReasonConfirm 退款原因确认回调
 * @param onReasonRetry 重试加载退款原因回调
 * @param onSubmitClick 提交退款申请回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrderRefundScreen(
    uiState: BaseNetWorkUiState<Order> = BaseNetWorkUiState.Loading,
    cartList: List<Cart> = emptyList(),
    refundModalVisible: Boolean = false,
    refundReasonsModalUiState: BaseNetWorkUiState<List<DictItem>> = BaseNetWorkUiState.Loading,
    selectedRefundReason: DictItem? = null,
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {},
    onReasonClick: () -> Unit = {},
    onRefundModalDismiss: () -> Unit = {},
    onRefundModalExpanded: () -> Unit = {},
    onReasonSelected: (DictItem) -> Unit = {},
    onReasonConfirm: (DictItem?) -> Unit = {},
    onReasonRetry: () -> Unit = {},
    onSubmitClick: () -> Unit = {}
) {
    AppScaffold(
        titleText = stringResource(R.string.order_refund),
        onBackClick = onBackClick,
        useLargeTopBar = true,
        bottomBar = {
            if (uiState is BaseNetWorkUiState.Success) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shadowElevation = 4.dp,
                ) {
                    EndRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(SpacePaddingMedium)
                            .navigationBarsPadding()
                    ) {
                        AppButton(
                            text = stringResource(R.string.submit),
                            modifier = Modifier.fillMaxWidth(),
                            enabled = selectedRefundReason != null,
                            onClick = onSubmitClick
                        )
                    }
                }
            }
        }
    ) {
        BaseNetWorkView(
            uiState = uiState,
            onRetry = onRetry
        ) { order ->
            OrderRefundContentView(
                data = order,
                cartList = cartList,
                selectedRefundReason = selectedRefundReason,
                onReasonClick = onReasonClick
            )
        }
    }

    // 退款原因选择弹窗
    DictSelectModal(
        visible = refundModalVisible,
        onDismiss = onRefundModalDismiss,
        title = R.string.select_cancel_reason,
        uiState = refundReasonsModalUiState,
        selectedItem = selectedRefundReason,
        onConfirm = onReasonConfirm,
        onRetry = onReasonRetry,
        onExpanded = onRefundModalExpanded
    )
}

/**
 * 退款申请内容视图
 *
 * @param data 订单数据
 * @param cartList 转换后的购物车列表
 * @param selectedRefundReason 选中的退款原因
 * @param onReasonClick 选择退款原因回调
 * @author Joker.X
 */
@Composable
private fun OrderRefundContentView(
    data: Order,
    cartList: List<Cart> = emptyList(),
    selectedRefundReason: DictItem? = null,
    onReasonClick: () -> Unit = {}
) {
    VerticalList(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {

        // 退款金额卡片
        Card {
            AppListItem(
                title = "",
                showArrow = false,
                leadingContent = {
                    TitleWithLine(text = stringResource(R.string.refund_info))
                }
            )

            AppListItem(
                title = stringResource(R.string.refund_reason),
                trailingText = selectedRefundReason?.name ?: stringResource(R.string.please_select),
                showArrow = true,

                onClick = onReasonClick
            )

            AppListItem(
                title = stringResource(R.string.refund_amount),
                showDivider = false,
                showArrow = false,
                trailingContent = {
                    PriceText(
                        data.realPrice,
                        integerTextSize = TextSize.BODY_LARGE,
                        decimalTextSize = TextSize.BODY_SMALL,
                        symbolTextSize = TextSize.BODY_SMALL,
                    )
                }
            )
        }

        // 订单商品卡片
        cartList.forEach { cart ->
            OrderGoodsCard(
                data = cart,
                enableQuantityStepper = false,
            )
        }
    }
}

/**
 * 退款申请界面浅色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun OrderRefundScreenPreview() {
    AppTheme {
        OrderRefundScreen(
            cartList = previewCartList,
            uiState = BaseNetWorkUiState.Success(
                data = previewOrder
            )
        )
    }
}

/**
 * 退款申请界面深色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun OrderRefundScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        OrderRefundScreen(
            cartList = previewCartList,
            uiState = BaseNetWorkUiState.Success(
                data = previewOrder
            )
        )
    }
}