/**
 * 提交意见反馈请求模型
 *
 * @author Joker.X
 */
package com.joker.coolmall.core.model.request

import kotlinx.serialization.Serializable

/**
 * 提交意见反馈请求模型
 */
@Serializable
data class FeedbackSubmitRequest(

    /**
     * 联系方式
     */
    val contact: String,

    /**
     * 类型
     */
    val type: Int,

    /**
     * 内容
     */
    val content: String,

    /**
     * 图片
     */
    val images: List<String>? = null
)