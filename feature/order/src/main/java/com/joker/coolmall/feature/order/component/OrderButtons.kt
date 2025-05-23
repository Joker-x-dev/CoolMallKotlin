package com.joker.coolmall.feature.order.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.ui.component.button.AppButtonBordered
import com.joker.coolmall.core.ui.component.button.ButtonType

/**
 * 订单按钮组件，用于显示订单相关的操作按钮
 *
 * @param order 订单对象，包含订单状态等信息
 * @param onCancelClick 取消订单按钮点击回调，在待付款状态(status=0)显示
 * @param onPayClick 去支付按钮点击回调，在待付款状态(status=0)显示
 * @param onRefundClick 售后/退款按钮点击回调，在待发货和待收货状态(status=1,2)显示
 * @param onConfirmClick 确认收货按钮点击回调，在待收货状态(status=2)显示
 * @param onLogisticsClick 查看物流按钮点击回调，在待收货、待评价、已完成、退款中、已退款状态(status=2,3,4,5,6)显示
 * @param onCommentClick 去评价按钮点击回调，在待评价和已完成状态(status=3,4)显示
 * @param onRebuyClick 再次购买按钮点击回调，在非待付款状态显示
 * @param modifier Compose修饰符
 */
@Composable
fun OrderButtons(
    order: Order,
    onCancelClick: () -> Unit = {},
    onPayClick: () -> Unit = {},
    onRefundClick: () -> Unit = {},
    onConfirmClick: () -> Unit = {},
    onLogisticsClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onRebuyClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    // 待付款状态显示取消订单和立即支付
    if (order.status == 0) {
        OrderButton(
            text = "取消订单",
            onClick = onCancelClick
        )
        SpaceHorizontalSmall()
        OrderButton(
            text = "去支付",
            onClick = onPayClick,
            isPrimary = true
        )
    }

    // 待发货和待收货状态显示售后/退款
    if (order.status == 1 || order.status == 2) {
        OrderButton(
            text = "售后/退款",
            onClick = onRefundClick
        )
        SpaceHorizontalSmall()
    }

    // 待收货状态显示确认收货
    if (order.status == 2) {
        OrderButton(
            text = "确认收货",
            onClick = onConfirmClick
        )
        SpaceHorizontalSmall()
    }

    // 待收货、待评价、已完成、退款中、已退款状态显示查看物流
    if (listOf(2, 3, 4, 5, 6).contains(order.status)) {
        OrderButton(
            text = "查看物流",
            onClick = onLogisticsClick
        )
        SpaceHorizontalSmall()
    }

    // 待评价和已完成状态显示评价按钮
    if (order.status == 3 || order.status == 4) {
        OrderButton(
            text = "去评价",
            onClick = onCommentClick
        )
        SpaceHorizontalSmall()
    }

    if (order.status != 0) {
        OrderButton(
            text = "再次购买",
            onClick = onRebuyClick,
            isPrimary = true
        )
    }
}

/**
 * 订单按钮组件
 */
@Composable
private fun OrderButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.DEFAULT,
    isPrimary: Boolean = false
) {
    AppButtonBordered(
        text = text,
        onClick = onClick,
        modifier = modifier,
        type = if (isPrimary) type else ButtonType.DEFAULT,
        color = if (!isPrimary) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f) else null,
        height = 30.dp,
        textStyle = MaterialTheme.typography.bodySmall
    )
}