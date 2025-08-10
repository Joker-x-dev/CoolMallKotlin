package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 优惠券信息
 */
@Serializable
data class Coupon(

    /**
     * ID
     */
    val id: Long = 0,

    /**
     * 标题
     */
    val title: String = "",

    /**
     * 描述
     */
    val description: String = "",

    /**
     * 类型 0-满减
     */
    val type: Int = 0,

    /**
     * 金额
     */
    val amount: Double = 0.0,

    /**
     * 数量
     */
    val num: Int = 0,

    /**
     * 已领取
     */
    val receivedNum: Int = 0,

    /**
     * 开始时间
     */
    val startTime: String? = null,

    /**
     * 结束时间
     */
    val endTime: String? = null,

    /**
     * 状态 0-禁用 1-启用
     */
    val status: Int = 0,

    /**
     * 条件
     */
    val condition: Condition? = null,

    /**
     * 创建时间
     */
    val createTime: String? = null,

    /**
     * 更新时间
     */
    val updateTime: String? = null
)

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