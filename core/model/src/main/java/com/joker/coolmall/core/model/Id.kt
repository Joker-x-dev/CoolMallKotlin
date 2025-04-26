package com.joker.coolmall.core.model

import kotlinx.serialization.Serializable

/**
 * ID模型
 * 用于处理只返回ID的接口响应
 */
@Serializable
data class Id(
    /**
     * ID值
     */
    val id: Long = 0
)