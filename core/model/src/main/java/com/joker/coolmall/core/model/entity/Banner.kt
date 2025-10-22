package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 轮播图模型
 *
 * @param id ID
 * @param description 描述
 * @param path 跳转路径
 * @param pic 图片
 * @param sortNum 排序
 * @param status 状态 1:启用 2:禁用
 * @param createTime 创建时间
 * @param updateTime 更新时间
 * @author Joker.X
 */
@Serializable
data class Banner(

    /**
     * ID
     */
    val id: Long = 0,

    /**
     * 描述
     */
    val description: String = "",

    /**
     * 跳转路径
     */
    val path: String = "",

    /**
     * 图片
     */
    val pic: String = "",

    /**
     * 排序
     */
    val sortNum: Int = 0,

    /**
     * 状态 1:启用 2:禁用
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