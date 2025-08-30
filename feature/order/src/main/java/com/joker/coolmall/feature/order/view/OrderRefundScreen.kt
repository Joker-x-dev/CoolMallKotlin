/**
 * 退款申请界面
 *
 * @author Joker.X
 */
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
    val uiState by viewModel.uiState.collectAsState()
    val cartList by viewModel.cartList.collectAsState()
    val refundModalVisible by viewModel.refundModalVisible.collectAsState()
    val refundReasonsModalUiState by viewModel.refundReasonsModalUiState.collectAsState()
    val selectedRefundReason by viewModel.selectedRefundReason.collectAsState()

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
 * @param onReasonSelected 退款原因选择回调
 * @param onReasonConfirm 退款原因确认回调
 * @param onReasonRetry 重试加载退款原因回调
 * @param onSubmitClick 提交退款申请回调
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
    onReasonSelected: (DictItem) -> Unit = {},
    onReasonConfirm: (DictItem?) -> Unit = {},
    onReasonRetry: () -> Unit = {},
    onSubmitClick: () -> Unit = {}
) {
    AppScaffold(
        titleText = "退款申请",
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
                            text = "提交",
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
        title = "请选择退款原因（必选）",
        uiState = refundReasonsModalUiState,
        selectedItem = selectedRefundReason,
        onConfirm = onReasonConfirm,
        onRetry = onReasonRetry
    )
}

/**
 * 退款申请内容视图
 *
 * @param data 订单数据
 * @param cartList 转换后的购物车列表
 * @param selectedRefundReason 选中的退款原因
 * @param onReasonClick 选择退款原因回调
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
                    TitleWithLine(text = "退款信息")
                }
            )

            AppListItem(
                title = "申请原因",
                trailingText = selectedRefundReason?.name ?: "请选择",
                showArrow = true,

                onClick = onReasonClick
            )

            AppListItem(
                title = "退款金额",
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