package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 购物车
 *
 * @param goodsId 商品 id
 * @param goodsName 商品名称
 * @param goodsMainPic 商品主图
 * @param spec 规格列表
 * @author Joker.X
 */
@Serializable
data class Cart(
    /**
     * 商品 id
     */
    var goodsId: Long = 0,

    /**
     * 商品名称
     */
    var goodsName: String = "",

    /**
     * 商品主图
     */
    var goodsMainPic: String = "",

    /**
     * 规格
     */
    var spec: List<CartGoodsSpec> = listOf()
)