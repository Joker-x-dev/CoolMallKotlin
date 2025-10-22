package com.joker.coolmall.core.model.request

import kotlinx.serialization.Serializable

/**
 * 提交意见反馈请求模型
 *
 * @param contact 联系方式
 * @param type 类型
 * @param content 内容
 * @param images 图片
 * @author Joker.X
 */
@Serializable
data class FeedbackSubmitRequest(

    /**
     * 联系方式
     */
    val contact: String? = "",

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