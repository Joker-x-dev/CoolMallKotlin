package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 商品详情信息
 *
 * @param goodsInfo 商品信息
 * @param coupon 优惠券列表
 * @param comment 评论列表
 * @author Joker.X
 */
@Serializable
data class GoodsDetail(

    /**
     * 商品信息
     */
    val goodsInfo: Goods,

    /**
     * 优惠券列表
     */
    val coupon: List<Coupon> = emptyList(),

    /**
     * 评论列表
     */
    val comment: List<Comment> = emptyList()
)