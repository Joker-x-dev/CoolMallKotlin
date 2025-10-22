package com.joker.coolmall.navigation.routes

/**
 * 订单模块路由常量
 *
 * @author Joker.X
 */
object OrderRoutes {
    /**
     * 订单模块根路由
     */
    private const val ORDER_ROUTE = "order"

    /**
     * 订单列表路由
     */
    const val LIST = "$ORDER_ROUTE/list"

    /**
     * 确认订单路由
     */
    const val CONFIRM = "$ORDER_ROUTE/confirm"

    /**
     * 订单详情路由
     */
    const val DETAIL = "$ORDER_ROUTE/detail"

    /**
     * 订单支付路由
     */
    const val PAY = "$ORDER_ROUTE/pay"

    /**
     * 退款申请路由
     */
    const val REFUND = "$ORDER_ROUTE/refund"

    /**
     * 订单评价路由
     */
    const val COMMENT = "$ORDER_ROUTE/comment"

    /**
     * 订单物流路由
     */
    const val LOGISTICS = "$ORDER_ROUTE/logistics"
}