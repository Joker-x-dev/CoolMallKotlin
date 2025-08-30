package com.joker.coolmall.core.model.request

import kotlinx.serialization.Serializable

/**
 * 退款请求实体类
 *
 * @author Joker.X
 */
@Serializable
data class RefundOrderRequest(
    /**
     * 订单ID
     */
    val orderId: Long,

    /**
     * 退款原因
     */
    val reason: String
)