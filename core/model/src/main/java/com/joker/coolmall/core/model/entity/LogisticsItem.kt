package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 物流轨迹项实体类
 *
 * @param time 时间
 * @param status 状态描述
 * @author Joker.X
 */
@Serializable
data class LogisticsItem(
    /**
     * 时间
     */
    val time: String? = null,

    /**
     * 状态描述
     */
    val status: String? = null
)