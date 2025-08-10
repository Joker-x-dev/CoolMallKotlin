package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 优惠券条件
 */
@Serializable
data class Condition(
    /**
     * 满多少金额
     */
    val fullAmount: Double = 0.0
)