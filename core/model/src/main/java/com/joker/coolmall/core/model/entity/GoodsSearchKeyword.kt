package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 搜索关键词
 *
 * @param id ID
 * @param name 名称
 * @param sortNum 排序
 * @param createTime 创建时间
 * @param updateTime 更新时间
 * @author Joker.X
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
