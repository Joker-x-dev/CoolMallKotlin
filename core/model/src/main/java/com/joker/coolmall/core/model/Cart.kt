package com.joker.coolmall.core.model

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
    var goodsName: String? = null

    /**
     * 商品主图
     */
    var goodsMainPic: String? = null

    /**
     * 规格
     */
    var spec: List<GoodsSpec>? = null
}