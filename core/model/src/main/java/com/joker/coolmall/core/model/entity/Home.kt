package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 首页模型
 */
@Serializable
data class Home(

    /**
     * 轮播图
     */
    val banner: List<Banner>? = null,

    /**
     * 分类
     */
    val category: List<Category>? = null,

    /**
     * 全部分类
     */
    val categoryAll: List<Category>? = null,

    /**
     * 限时精选商品
     */
    val flashSale: List<Goods>? = null,

    /**
     * 推荐商品
     */
    val recommend: List<Goods>? = null,

    /**
     * 优惠券
     */
    val coupon: List<Coupon>? = null,

    /**
     * 第一页全部商品
     */
    val goods: List<Goods>? = null,
)