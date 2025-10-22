package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 优惠券条件
 *
 * @param fullAmount 满多少金额
 * @author Joker.X
 */
@Serializable
data class Condition(
    /**
     * 满多少金额
     */
    val fullAmount: Double = 0.0
)