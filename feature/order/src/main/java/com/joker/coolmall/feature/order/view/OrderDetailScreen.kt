package com.joker.coolmall.feature.order.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.component.EndRow
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.model.entity.Cart
import com.joker.coolmall.core.model.entity.DictItem
import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.model.preview.previewOrder
import com.joker.coolmall.core.ui.component.address.AddressCard
import com.joker.coolmall.core.ui.component.goods.OrderGoodsCard
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.modal.DictSelectModal
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.dialog.WeDialog
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.PriceText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.core.ui.component.title.TitleWithLine
import com.joker.coolmall.feature.order.R
import com.joker.coolmall.feature.order.component.OrderButtons
import com.joker.coolmall.feature.order.viewmodel.OrderDetailViewModel
import com.joker.coolmall.core.ui.R as CoreUiR

/**
 * 订单详情路由
 *
 * @param viewModel 订单详情ViewModel
 */
@Composable
internal fun OrderDetailRoute(
    viewModel: OrderDetailViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    val cartList by viewModel.cartList.collectAsState()
    val cancelModalVisible by viewModel.cancelModalVisible.collectAsState()
    val cancelReasonsModalUiState by viewModel.cancelReasonsModalUiState.collectAsState()
    val selectedCancelReason by viewModel.selectedCancelReason.collectAsState()
    val showConfirmDialog by viewModel.showConfirmDialog.collectAsState()
    // 注册页面刷新监听
    val backStackEntry = navController.currentBackStackEntry

    OrderDetailScreen(
        uiState = uiState,
        cartList = cartList,
        cancelModalVisible = cancelModalVisible,
        cancelReasonsModalUiState = cancelReasonsModalUiState,
        selectedCancelReason = selectedCancelReason,
        showConfirmDialog = showConfirmDialog,
        onBackClick = viewModel::handleBackClick,
        onRetry = viewModel::retryRequest,
        onCancelClick = viewModel::cancelOrder,
        onPayClick = viewModel::navigateToPayment,
        onRefundClick = viewModel::toOrderRefund,
        onConfirmClick = viewModel::confirmOrder,
        onLogisticsClick = viewModel::toOrderLogistics,
        onCommentClick = viewModel::toOrderComment,
        onRebuyClick = viewModel::toGoodsDetail,
        onCancelModalDismiss = viewModel::hideCancelModal,
        onCancelReasonSelected = viewModel::selectCancelReason,
        onCancelConfirm = viewModel::confirmCancelOrder,
        onCancelRetry = viewModel::loadCancelReasons,
        onConfirmDialogDismiss = viewModel::hideConfirmReceiveDialog,
        onConfirmReceive = viewModel::confirmReceiveOrder
    )

    // 只要backStackEntry不为null就注册监听
    LaunchedEffect(backStackEntry) {
        viewModel.observeRefreshState(backStackEntry)
    }

    // 拦截系统返回按钮，使用自定义返回逻辑
    BackHandler {
        viewModel.handleBackClick()
    }
}

/**
 * 订单详情页面
 *
 * @param uiState UI状态
 * @param cartList 转换后的购物车列表
 * @param onBackClick 返回回调
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrderDetailScreen(
    uiState: BaseNetWorkUiState<Order> = BaseNetWorkUiState.Loading,
    cartList: List<Cart> = emptyList(),
    cancelModalVisible: Boolean = false,
    cancelReasonsModalUiState: BaseNetWorkUiState<List<DictItem>> = BaseNetWorkUiState.Loading,
    selectedCancelReason: DictItem? = null,
    showConfirmDialog: Boolean = false,
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {},
    onCancelClick: () -> Unit = {},
    onPayClick: () -> Unit = {},
    onRefundClick: () -> Unit = {},
    onConfirmClick: () -> Unit = {},
    onLogisticsClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onRebuyClick: (Long) -> Unit = {},
    onCancelModalDismiss: () -> Unit = {},
    onCancelReasonSelected: (DictItem) -> Unit = {},
    onCancelConfirm: () -> Unit = {},
    onCancelRetry: () -> Unit = {},
    onConfirmDialogDismiss: () -> Unit = {},
    onConfirmReceive: () -> Unit = {}
) {
    // 根据订单状态获取对应的标题资源ID
    val titleResId = if (uiState is BaseNetWorkUiState.Success) {
        when (uiState.data.status) {
            0 -> R.string.order_status_pending_payment
            1 -> R.string.order_status_pending_shipment
            2 -> R.string.order_status_pending_receipt
            3 -> R.string.order_status_pending_comment
            4 -> R.string.order_status_completed
            5 -> R.string.order_status_refunding
            6 -> R.string.order_status_refunded
            7 -> R.string.order_status_closed
            else -> R.string.order_detail
        }
    } else {
        R.string.order_detail
    }

    AppScaffold(
        useLargeTopBar = true,
        title = if (uiState is BaseNetWorkUiState.Success) {
            titleResId
        } else {
            null
        },
        onBackClick = onBackClick,
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
                        OrderButtons(
                            order = uiState.data,
                            onCancelClick = onCancelClick,
                            onPayClick = onPayClick,
                            onRefundClick = onRefundClick,
                            onConfirmClick = onConfirmClick,
                            onLogisticsClick = onLogisticsClick,
                            onCommentClick = onCommentClick,
                            onRebuyClick = { onRebuyClick(uiState.data.goodsList?.firstOrNull()?.goodsId ?: 0L) },
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
            OrderDetailContentView(
                data = order,
                cartList = cartList
            )
        }
    }

    // 取消订单原因选择弹窗
    DictSelectModal(
        visible = cancelModalVisible,
        onDismiss = onCancelModalDismiss,
        title = "请选择取消订单原因（必选）",
        uiState = cancelReasonsModalUiState,
        selectedItem = selectedCancelReason,
        onItemSelected = onCancelReasonSelected,
        onConfirm = { onCancelConfirm() },
        onRetry = onCancelRetry
    )
    
    // 确认收货弹窗
    if (showConfirmDialog) {
        WeDialog(
            title = "确认收货",
            content = "确认收货后无法发起退款等售后申请，请谨慎操作",
            okText = "确认",
            cancelText = "取消",
            onOk = onConfirmReceive,
            onCancel = onConfirmDialogDismiss,
            onDismiss = onConfirmDialogDismiss
        )
    }
}

/**
 * 订单详情内容视图
 */
@Composable
private fun OrderDetailContentView(
    data: Order,
    cartList: List<Cart> = emptyList()
) {
    VerticalList(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        AddressCard(
            address = data.address!!,
            onClick = { /* 地址点击回调 */ }
        )

        // 订单商品卡片
        cartList.forEach { cart ->
            OrderGoodsCard(
                data = cart,
                enableQuantityStepper = false, // 订单详情页面不需要调整数量
                onGoodsClick = { /* 商品点击事件 */ },
                onSpecClick = { /* 规格点击事件 */ }
            )
        }

        // 价格明细卡片
        Card {
            AppListItem(
                title = "",
                showArrow = false,
                leadingContent = {
                    TitleWithLine(text = "价格明细")
                }
            )

            AppListItem(
                title = "商品总价",
                leadingIcon = R.drawable.ic_shop,
                trailingContent = {
                    PriceText(
                        data.price, integerTextSize = TextSize.BODY_LARGE,
                        decimalTextSize = TextSize.BODY_SMALL,
                        symbolTextSize = TextSize.BODY_SMALL,
                        type = TextType.PRIMARY
                    )
                },
                showArrow = false
            )

            AppListItem(
                title = "优惠金额",
                leadingIcon = CoreUiR.drawable.ic_coupon,
                description = data.discountSource?.info?.title ?: "",
                showArrow = false,
                trailingContent = {
                    PriceText(
                        data.discountPrice, integerTextSize = TextSize.BODY_LARGE,
                        decimalTextSize = TextSize.BODY_SMALL,
                        symbolTextSize = TextSize.BODY_SMALL
                    )
                },
                onClick = { /* 选择优惠券 */ }
            )

            AppListItem(
                title = "实付金额",
                leadingIcon = R.drawable.ic_money,
                trailingContent = {
                    PriceText(
                        data.price - data.discountPrice, integerTextSize = TextSize.BODY_LARGE,
                        decimalTextSize = TextSize.BODY_SMALL,
                        symbolTextSize = TextSize.BODY_SMALL,
                        type = TextType.PRIMARY
                    )
                },
                showArrow = false,
                showDivider = false
            )
        }

        // 如果有退款信息，显示退款卡片
        data.refund?.let { refund ->
            Card {
                AppListItem(
                    title = "",
                    showArrow = false,
                    leadingContent = {
                        TitleWithLine(text = "售后/退款")
                    }
                )

                AppListItem(
                    title = "退款金额",
                    trailingContent = {
                        PriceText(
                            refund.amount?.toInt() ?: 0,
                            integerTextSize = TextSize.BODY_LARGE,
                            decimalTextSize = TextSize.BODY_SMALL,
                            symbolTextSize = TextSize.BODY_SMALL,
                            type = TextType.PRIMARY
                        )
                    },
                    showArrow = false
                )

                AppListItem(
                    title = "退款状态",
                    showArrow = false,
                    trailingText = when (refund.status) {
                        0 -> "申请中"
                        1 -> "已退款"
                        2 -> "拒绝退款"
                        else -> "未知状态"
                    }
                )

                AppListItem(
                    title = "申请原因",
                    showArrow = false,
                    trailingText = refund.reason ?: "无"
                )

                if (refund.status == 2) {
                    AppListItem(
                        title = "拒绝原因",
                        showArrow = false,
                        trailingText = refund.refuseReason ?: "无",
                        showDivider = false
                    )
                } else {
                    AppListItem(
                        title = "申请时间",
                        showArrow = false,
                        trailingText = refund.applyTime ?: "无",
                        showDivider = false
                    )
                }
            }
        }

        // 订单信息卡片
        Card {
            AppListItem(
                title = "",
                showArrow = false,
                leadingContent = {
                    TitleWithLine(text = "订单信息")
                }
            )

            AppListItem(
                title = "订单编号",
                trailingContent = {
                    AppText(
                        text = data.orderNum,
                        type = TextType.SECONDARY,
                        size = TextSize.BODY_MEDIUM
                    )
                    SpaceHorizontalSmall()

                    AppText(
                        text = "复制",
                        type = TextType.LINK,
                        size = TextSize.BODY_MEDIUM,
                        onClick = { /* 复制订单号 */ }
                    )
                    SpaceHorizontalXSmall()
                },
                showArrow = false
            )

            AppListItem(
                title = "支付方式",
                showArrow = false,
                trailingText = "微信"
            )

            AppListItem(
                title = "支付时间",
                showArrow = false,
                trailingText = data.payTime ?: "未支付"
            )

            AppListItem(
                title = "下单时间",
                showArrow = false,
                trailingText = data.createTime
            )

            AppListItem(
                title = "订单备注",
                showArrow = false,
                trailingText = data.remark ?: "无"
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
internal fun OrderDetailScreenPreview() {
    AppTheme {
        OrderDetailScreen(
            uiState = BaseNetWorkUiState.Success(
                data = previewOrder
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
                data = previewOrder
            )
        )
    }
}