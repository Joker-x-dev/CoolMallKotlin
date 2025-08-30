package com.joker.coolmall.core.model.request

import kotlinx.serialization.Serializable

/**
 * 取消订单请求模型
 */
@Serializable
data class CancelOrderRequest(
    /**
     * 订单ID
     */
    val orderId: Long,

    /**
     * 取消原因
     */
    val remark: String
)