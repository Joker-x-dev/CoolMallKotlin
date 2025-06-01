package com.joker.coolmall.navigation.routes

/**
 * 商品模块路由常量
 */
object GoodsRoutes {
    /**
     * 商品根路由
     */
    private const val GOODS_ROUTE = "goods"
    
    /**
     * 商品详情路由
     */
    const val DETAIL = "${GOODS_ROUTE}/detail"
    
    /**
     * 商品搜索路由
     */
    const val SEARCH = "${GOODS_ROUTE}/search"
    
    /**
     * 商品分类页面路由
     */
    const val CATEGORY = "${GOODS_ROUTE}/category"
    
    /**
     * 商品评价路由
     */
    const val COMMENT = "${GOODS_ROUTE}/comment"
}