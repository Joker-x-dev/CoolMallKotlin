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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
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
import com.joker.coolmall.core.ui.component.dialog.WeDialog
import com.joker.coolmall.core.ui.component.goods.OrderGoodsCard
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.modal.DictSelectModal
import com.joker.coolmall.core.ui.component.modal.OrderGoodsModal
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
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
 * @param navController 导航控制器
 * @author Joker.X
 */
@Composable
internal fun OrderDetailRoute(
    viewModel: OrderDetailViewModel = hiltViewModel(),
    navController: NavController
) {
    // UI状态
    val uiState by viewModel.uiState.collectAsState()
    // 转换后的购物车列表
    val cartList by viewModel.cartList.collectAsState()
    // 取消订单弹窗显示状态
    val cancelModalVisible by viewModel.cancelModalVisible.collectAsState()
    // 取消原因弹出层UI状态
    val cancelReasonsModalUiState by viewModel.cancelReasonsModalUiState.collectAsState()
    // 选中的取消原因
    val selectedCancelReason by viewModel.selectedCancelReason.collectAsState()
    // 确认收货弹窗显示状态
    val showConfirmDialog by viewModel.showConfirmDialog.collectAsState()
    // 再次购买弹窗显示状态
    val rebuyModalVisible by viewModel.rebuyModalVisible.collectAsState()
    // 商品评论弹窗显示状态
    val commentModalVisible by viewModel.commentModalVisible.collectAsState()
    // 注册页面刷新监听
    val backStackEntry = navController.currentBackStackEntry

    OrderDetailScreen(
        uiState = uiState,
        cartList = cartList,
        cancelModalVisible = cancelModalVisible,
        cancelReasonsModalUiState = cancelReasonsModalUiState,
        selectedCancelReason = selectedCancelReason,
        showConfirmDialog = showConfirmDialog,
        rebuyModalVisible = rebuyModalVisible,
        commentModalVisible = commentModalVisible,
        onBackClick = viewModel::handleBackClick,
        onRetry = viewModel::retryRequest,
        onCancelClick = viewModel::cancelOrder,
        onPayClick = viewModel::navigateToPayment,
        onRefundClick = viewModel::toOrderRefund,
        onConfirmClick = viewModel::confirmOrder,
        onLogisticsClick = viewModel::toOrderLogistics,
        onCommentClick = viewModel::toOrderComment,
        onRebuyClick = viewModel::handleRebuy,
        onRebuyModalDismiss = viewModel::hideRebuyModal,
        onRebuyGoodsClick = viewModel::toGoodsDetail,
        onCommentModalDismiss = viewModel::hideCommentModal,
        onCommentGoodsClick = viewModel::toOrderCommentForGoods,
        onCancelModalDismiss = viewModel::hideCancelModal,
        onCancelModalExpanded = viewModel::onCancelModalExpanded,
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
 * @param cancelModalVisible 取消订单弹窗显示状态
 * @param cancelReasonsModalUiState 取消原因弹出层UI状态
 * @param selectedCancelReason 选中的取消原因
 * @param showConfirmDialog 确认收货弹窗显示状态
 * @param rebuyModalVisible 再次购买弹窗显示状态
 * @param commentModalVisible 商品评论弹窗显示状态
 * @param onBackClick 返回回调
 * @param onRetry 重试请求回调
 * @param onCancelClick 取消订单回调
 * @param onPayClick 支付回调
 * @param onRefundClick 退款回调
 * @param onConfirmClick 确认收货回调
 * @param onLogisticsClick 查看物流回调
 * @param onCommentClick 评价回调
 * @param onRebuyClick 再次购买回调
 * @param onRebuyModalDismiss 再次购买弹窗关闭回调
 * @param onRebuyGoodsClick 再次购买商品点击回调
 * @param onCommentModalDismiss 商品评论弹窗关闭回调
 * @param onCommentGoodsClick 商品评论商品点击回调
 * @param onCancelModalDismiss 取消订单弹窗关闭回调
 * @param onCancelModalExpanded 取消订单弹窗展开完成回调
 * @param onCancelReasonSelected 取消原因选中回调
 * @param onCancelConfirm 确认取消订单回调
 * @param onCancelRetry 取消原因重试回调
 * @param onConfirmDialogDismiss 确认收货弹窗关闭回调
 * @param onConfirmReceive 确认收货确认回调
 * @author Joker.X
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
    rebuyModalVisible: Boolean = false,
    commentModalVisible: Boolean = false,
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {},
    onCancelClick: () -> Unit = {},
    onPayClick: () -> Unit = {},
    onRefundClick: () -> Unit = {},
    onConfirmClick: () -> Unit = {},
    onLogisticsClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onRebuyClick: () -> Unit = {},
    onRebuyModalDismiss: () -> Unit = {},
    onRebuyGoodsClick: (Long) -> Unit = {},
    onCommentModalDismiss: () -> Unit = {},
    onCommentGoodsClick: (Long) -> Unit = {},
    onCancelModalDismiss: () -> Unit = {},
    onCancelModalExpanded: () -> Unit = {},
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
                            onRebuyClick = onRebuyClick,
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
        title = R.string.select_cancel_reason,
        uiState = cancelReasonsModalUiState,
        selectedItem = selectedCancelReason,
        onItemSelected = onCancelReasonSelected,
        onConfirm = { onCancelConfirm() },
        onRetry = onCancelRetry,
        onExpanded = onCancelModalExpanded
    )

    // 确认收货弹窗
    if (showConfirmDialog) {
        WeDialog(
            title = stringResource(R.string.confirm_receipt),
            content = stringResource(R.string.confirm_receipt_message),
            okText = stringResource(R.string.confirm),
            cancelText = stringResource(R.string.cancel),
            onOk = onConfirmReceive,
            onCancel = onConfirmDialogDismiss,
            onDismiss = onConfirmDialogDismiss
        )
    }

    // 再次购买弹窗
    OrderGoodsModal(
        visible = rebuyModalVisible,
        title = stringResource(R.string.select_goods_to_buy),
        buttonText = stringResource(R.string.rebuy),
        cartList = cartList,
        onDismiss = onRebuyModalDismiss,
        onItemButtonClick = onRebuyGoodsClick
    )

    // 商品评论弹窗
    OrderGoodsModal(
        visible = commentModalVisible,
        title = stringResource(R.string.select_goods_to_comment),
        buttonText = stringResource(R.string.go_to_comment),
        cartList = cartList,
        onDismiss = onCommentModalDismiss,
        onItemButtonClick = onCommentGoodsClick
    )
}

/**
 * 订单详情内容视图
 *
 * @param data 订单数据
 * @param cartList 购物车列表
 * @author Joker.X
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
                    TitleWithLine(text = stringResource(R.string.price_detail))
                }
            )

            AppListItem(
                title = stringResource(R.string.goods_total_price),
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
                title = stringResource(R.string.discount_amount),
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
                title = stringResource(R.string.actual_payment),
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
                        TitleWithLine(text = stringResource(R.string.refund_info))
                    }
                )

                AppListItem(
                    title = stringResource(R.string.refund_amount),
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
                    title = stringResource(R.string.refund_status),
                    showArrow = false,
                    trailingText = when (refund.status) {
                        0 -> stringResource(R.string.refund_status_applying)
                        1 -> stringResource(R.string.refund_status_refunded)
                        2 -> stringResource(R.string.refund_status_rejected)
                        else -> stringResource(R.string.refund_status_unknown)
                    }
                )

                AppListItem(
                    title = stringResource(R.string.refund_reason),
                    showArrow = false,
                    trailingText = refund.reason ?: stringResource(R.string.none)
                )

                if (refund.status == 2) {
                    AppListItem(
                        title = stringResource(R.string.refund_reject_reason),
                        showArrow = false,
                        trailingText = refund.refuseReason ?: stringResource(R.string.none),
                        showDivider = false
                    )
                } else {
                    AppListItem(
                        title = stringResource(R.string.refund_apply_time),
                        showArrow = false,
                        trailingText = refund.applyTime ?: stringResource(R.string.none),
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
                    TitleWithLine(text = stringResource(R.string.order_info))
                }
            )

            AppListItem(
                title = stringResource(R.string.order_number),
                trailingContent = {
                    AppText(
                        text = data.orderNum,
                        type = TextType.SECONDARY,
                        size = TextSize.BODY_MEDIUM
                    )
                    SpaceHorizontalSmall()

                    AppText(
                        text = stringResource(R.string.copy),
                        type = TextType.LINK,
                        size = TextSize.BODY_MEDIUM,
                        onClick = { /* 复制订单号 */ }
                    )
                    SpaceHorizontalXSmall()
                },
                showArrow = false
            )

            AppListItem(
                title = stringResource(R.string.payment_method),
                showArrow = false,
                trailingText = stringResource(R.string.wechat)
            )

            AppListItem(
                title = stringResource(R.string.payment_time),
                showArrow = false,
                trailingText = data.payTime ?: stringResource(R.string.unpaid)
            )

            AppListItem(
                title = stringResource(R.string.order_time),
                showArrow = false,
                trailingText = data.createTime
            )

            AppListItem(
                title = stringResource(R.string.order_remark),
                showArrow = false,
                trailingText = data.remark ?: stringResource(R.string.none)
            )
        }

    }
}

/**
 * 订单详情界面浅色主题预览
 *
 * @author Joker.X
 */
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

/**
 * 订单详情界面深色主题预览
 *
 * @author Joker.X
 */
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