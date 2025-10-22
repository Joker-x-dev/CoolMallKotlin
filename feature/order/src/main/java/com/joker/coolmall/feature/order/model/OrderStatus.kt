package com.joker.coolmall.feature.order.model

import androidx.annotation.StringRes
import com.joker.coolmall.feature.order.R

/**
 * 订单状态枚举
 *
 * @param labelRes 状态显示文本资源ID
 * @author Joker.X
 */
enum class OrderStatus(@param:StringRes val labelRes: Int) {
    ALL(R.string.order_status_all),
    UNPAID(R.string.order_status_unpaid),
    UNSHIPPED(R.string.order_status_unshipped),
    UNRECEIVED(R.string.order_status_unreceived),
    AFTER_SALE(R.string.order_status_after_sale),
    UNEVALUATED(R.string.order_status_unevaluated),
    COMPLETED(R.string.order_status_completed_tab),
} 