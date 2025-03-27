package com.joker.coolmall.core.model

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
     * 限时商品
     */
    val flashSale: List<Goods>? = null,

    /**
     * 商品
     */
    val goods: List<Goods>? = null,
)