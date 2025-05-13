package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 购物车
 */
@Serializable
class Cart {

    /**
     * 商品 id
     */
    var goodsId: Long = 0

    /**
     * 商品名称
     */
    var goodsName: String = ""

    /**
     * 商品主图
     */
    var goodsMainPic: String = ""

    /**
     * 规格
     */
    var spec: List<CartGoodsSpec> = listOf()
}