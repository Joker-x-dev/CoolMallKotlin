package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 轮播图模型
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