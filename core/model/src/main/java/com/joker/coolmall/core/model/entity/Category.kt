package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 商品类型
 *
 * @param id ID
 * @param name 名称
 * @param parentId 父ID
 * @param sortNum 排序
 * @param pic 图片
 * @param status 状态 0-禁用 1-启用
 * @param createTime 创建时间
 * @param updateTime 更新时间
 * @author Joker.X
 */
@Serializable
data class Category(
    /**
     * ID
     */
    val id: Long = 0,

    /**
     * 名称
     */
    val name: String = "",

    /**
     * 父ID
     */
    val parentId: Int? = null,

    /**
     * 排序
     */
    val sortNum: Int = 0,

    /**
     * 图片
     */
    val pic: String? = null,

    /**
     * 状态 0-禁用 1-启用
     */
    val status: Int = 1,

    /**
     * 创建时间
     */
    val createTime: String? = null,

    /**
     * 更新时间
     */
    val updateTime: String? = null
)
