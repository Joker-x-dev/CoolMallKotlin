package com.joker.coolmall.core.model.request

import kotlinx.serialization.Serializable

/**
 * 领取优惠券请求参数
 */
@Serializable
data class ReceiveCouponRequest(
    /**
     * 优惠券ID
     */
    val couponId: Long
)