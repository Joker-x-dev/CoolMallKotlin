package com.joker.coolmall.core.model.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 用户订单统计
 *
 * @param pendingPayment 待付款订单数量
 * @param pendingShipment 待发货订单数量
 * @param pendingReceive 待收货订单数量
 * @param pendingReview 待评价订单数量
 * @param refunding 退款中订单数量
 * @param refunded 已退款订单数量
 * @author Joker.X
 */
@Serializable
data class OrderCount(

    /**
     * 待付款订单数量
     */
    @SerialName("待付款")
    val pendingPayment: Int = 0,

    /**
     * 待发货订单数量
     */
    @SerialName("待发货")
    val pendingShipment: Int = 0,

    /**
     * 待收货订单数量
     */
    @SerialName("待收货")
    val pendingReceive: Int = 0,

    /**
     * 待评价订单数量
     */
    @SerialName("待评价")
    val pendingReview: Int = 0,

    /**
     * 退款中订单数量
     */
    @SerialName("退款中")
    val refunding: Int = 0,

    /**
     * 已退款订单数量
     */
    @SerialName("已退款")
    val refunded: Int = 0
)