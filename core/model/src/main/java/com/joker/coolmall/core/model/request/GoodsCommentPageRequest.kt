package com.joker.coolmall.core.model.request

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.Serializable

/**
 * 商品评论分页查询请求模型
 *
 * @param goodsId 商品ID
 * @param page 页码
 * @param size 每页大小
 * @author Joker.X
 */
@Serializable
data class GoodsCommentPageRequest(
    /**
     * 商品ID
     */
    val goodsId: String,

    /**
     * 页码
     */
    @EncodeDefault
    var page: Int = 1,

    /**
     * 每页大小
     */
    @EncodeDefault
    var size: Int = 20
)