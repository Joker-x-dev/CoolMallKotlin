package com.joker.coolmall.navigation.routes

/**
 * 订单模块路由常量
 */
object OrderRoutes {
    /**
     * 订单模块根路由
     */
    private const val ORDER_ROUTE = "order"

    /**
     * 订单列表路由
     */
    const val ORDER_LIST = "$ORDER_ROUTE/list"

    /**
     * 确认订单路由
     */
    const val ORDER_CONFIRM = "$ORDER_ROUTE/confirm"

    /**
     * 订单详情路由
     */
    const val ORDER_DETAIL = "$ORDER_ROUTE/detail"
}