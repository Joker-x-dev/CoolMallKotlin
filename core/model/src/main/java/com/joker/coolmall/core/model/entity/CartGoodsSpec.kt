package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 购物车商品规格
 * 用于购物车中展示商品的规格信息(不来自服务端)
 *
 * @param id ID
 * @param goodsId 商品ID
 * @param name 名称
 * @param price 价格
 * @param stock 库存
 * @param count 购买数量
 * @param images 图片
 * @author Joker.X
 */
@Serializable
data class CartGoodsSpec(
    /**
     * ID
     */
    val id: Long = 0,

    /**
     * 商品ID
     */
    val goodsId: Long = 0,

    /**
     * 名称
     */
    val name: String = "",

    /**
     * 价格
     */
    val price: Int = 0,

    /**
     * 库存
     */
    val stock: Int = 0,

    /**
     * 购买数量
     */
    val count: Int = 0,

    /**
     * 图片
     */
    val images: List<String>? = null
)
