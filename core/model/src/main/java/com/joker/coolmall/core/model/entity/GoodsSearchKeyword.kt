package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 搜索关键词
 */
@Serializable
data class GoodsSearchKeyword(
    /**
     * ID
     */
    val id: Long = 0,

    /**
     * 名称
     */
    val name: String = "",

    /**
     * 排序
     */
    val sortNum: Int = 0,

    /**
     * 创建时间
     */
    val createTime: String? = null,

    /**
     * 更新时间
     */
    val updateTime: String? = null
)
