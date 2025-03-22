package com.joker.coolmall.core.model.response

import kotlinx.serialization.Serializable

/**
 * 分页模型
 */
@Serializable
data class NetworkPageMeta(
    /**
     * 总条数
     */
    val total: Int? = null,

    /**
     * 每页显示条数
     */
    val size: Int? = null,

    /**
     * 当前页码
     */
    val page: Int? = null,
)