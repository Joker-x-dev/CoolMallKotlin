package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 确认订单页面数据
 *
 * @param defaultAddress 默认收货地址
 * @param userCoupon 用户拥有的优惠券
 * @author Joker.X
 */
@Serializable
data class ConfirmOrder(

    /**
     * 默认收货地址
     */
    val defaultAddress: Address? = null,

    /**
     * 用户拥有的优惠券
     */
    val userCoupon: List<Coupon>? = emptyList()
)