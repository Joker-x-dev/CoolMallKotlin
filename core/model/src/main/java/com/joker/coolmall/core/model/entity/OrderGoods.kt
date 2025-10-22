package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 订单商品
 *
 * @param id ID
 * @param orderId 订单ID
 * @param goodsId 商品ID
 * @param price 价格
 * @param discountPrice 优惠金额
 * @param count 数量
 * @param remark 其他信息
 * @param goodsInfo 商品信息
 * @param spec 规格
 * @param isComment 是否评价 0-否 1-是
 * @param createTime 创建时间
 * @param updateTime 更新时间
 * @author Joker.X
 */
@Serializable
data class OrderGoods(

    /**
     * ID
     */
    val id: Long = 0,

    /**
     * 订单ID
     */
    val orderId: Long = 0,

    /**
     * 商品ID
     */
    val goodsId: Long = 0,

    /**
     * 价格
     */
    val price: Double = 0.0,

    /**
     * 优惠金额
     */
    val discountPrice: Double = 0.0,

    /**
     * 数量
     */
    val count: Int = 0,

    /**
     * 其他信息
     */
    val remark: String? = null,

    /**
     * 商品信息
     */
    val goodsInfo: Goods? = null,

    /**
     * 规格
     */
    val spec: GoodsSpec? = null,

    /**
     * 是否评价 0-否 1-是
     */
    val isComment: Int = 0,

    /**
     * 创建时间
     */
    val createTime: String? = null,

    /**
     * 更新时间
     */
    val updateTime: String? = null
) 