package com.joker.coolmall.navigation.routes

import kotlinx.serialization.Serializable

/**
 * 商品模块路由
 *
 * @author Joker.X
 */
object GoodsRoutes {
    /**
     * 商品详情路由
     *
     * @param goodsId 商品ID
     * @author Joker.X
     */
    @Serializable
    data class Detail(val goodsId: Long)

    /**
     * 商品搜索路由
     *
     * @author Joker.X
     */
    @Serializable
    data object Search

    /**
     * 商品分类页面路由
     *
     * @param typeId 类型 ID 列表（逗号分隔，可选）
     * @param featured 是否精选
     * @param recommend 是否推荐
     * @param keyword 关键词（可选）
     * @param minPrice 最小金额（可选，用于优惠券跳转）
     * @author Joker.X
     */
    @Serializable
    data class Category(
        val typeId: String? = null,
        val featured: Boolean = false,
        val recommend: Boolean = false,
        val keyword: String? = null,
        val minPrice: String? = null
    )

    /**
     * 商品评价路由
     *
     * @param goodsId 商品ID
     * @author Joker.X
     */
    @Serializable
    data class Comment(val goodsId: Long)
}
