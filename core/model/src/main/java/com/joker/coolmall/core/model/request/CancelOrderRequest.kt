package com.joker.coolmall.core.model.request

import kotlinx.serialization.Serializable

/**
 * 取消订单请求模型
 *
 * @param orderId 订单ID
 * @param remark 取消原因
 * @author Joker.X
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