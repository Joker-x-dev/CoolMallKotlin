package com.joker.coolmall.feature.order.model

/**
 * 订单状态
 */
enum class OrderStatus(val label: String) {
    ALL("全部"),
    UNPAID("待付款"),
    UNSHIPPED("待发货"),
    UNRECEIVED("待收货"),
    AFTER_SALE("售后/退款"),
    UNEVALUATED("待评价"),
    COMPLETED("已完成"),
} 