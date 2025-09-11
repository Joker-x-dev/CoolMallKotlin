/**
 * 意见反馈模型
 *
 * @author Joker.X
 */
package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 意见反馈模型
 */
@Serializable
data class Feedback(

    /**
     * ID
     */
    val id: Long = 0,

    /**
     * 用户ID
     */
    val userId: Long = 0,

    /**
     * 联系方式
     */
    val contact: String = "",

    /**
     * 类型
     */
    val type: Int = 0,

    /**
     * 内容
     */
    val content: String = "",

    /**
     * 图片
     */
    val images: List<String>? = null,

    /**
     * 状态 0-未处理 1-已处理
     */
    val status: Int = 0,

    /**
     * 处理人ID
     */
    val handlerId: Long? = null,

    /**
     * 备注
     */
    val remark: String? = null,

    /**
     * 创建时间
     */
    val createTime: String? = null,

    /**
     * 更新时间
     */
    val updateTime: String? = null
)