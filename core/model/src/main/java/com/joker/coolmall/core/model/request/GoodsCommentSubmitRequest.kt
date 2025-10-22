package com.joker.coolmall.core.model.request

import com.joker.coolmall.core.model.entity.Comment
import kotlinx.serialization.Serializable

/**
 * 商品评论提交请求模型
 *
 * @param orderId 订单ID
 * @param data 评论数据
 * @author Joker.X
 */
@Serializable
data class GoodsCommentSubmitRequest(
    /**
     * 订单ID
     */
    val orderId: Long,

    /**
     * 评论数据
     */
    val data: Comment
)