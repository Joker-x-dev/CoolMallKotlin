package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 用户足迹模型
 */
@Serializable
data class Footprint(
    /**
     * 商品ID
     */
    var goodsId: Long = 0,

    /**
     * 商品名称
     */
    var goodsName: String = "",

    /**
     * 商品副标题
     */
    var goodsSubTitle: String? = null,

    /**
     * 商品主图
     */
    var goodsMainPic: String = "",

    /**
     * 商品价格
     */
    var goodsPrice: Int = 0,

    /**
     * 已售数量
     */
    var goodsSold: Int = 0,

    /**
     * 浏览时间戳
     */
    var viewTime: Long = System.currentTimeMillis()
)