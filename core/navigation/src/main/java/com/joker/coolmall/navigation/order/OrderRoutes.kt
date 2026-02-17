package com.joker.coolmall.core.navigation.order

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * 订单模块路由
 *
 * @author Joker.X
 */
object OrderRoutes {
    /**
     * 订单列表路由
     *
     * @param tab Tab标签（可选）
     * @author Joker.X
     */
    @Serializable
    data class List(val tab: String? = null) : NavKey

    /**
     * 确认订单路由
     *
     * @author Joker.X
     */
    @Serializable
    data object Confirm : NavKey

    /**
     * 订单详情路由
     *
     * @param orderId 订单ID
     * @author Joker.X
     */
    @Serializable
    data class Detail(val orderId: Long) : NavKey

    /**
     * 订单支付路由
     *
     * @param orderId 订单ID
     * @param price 支付价格
     * @param from 来源（可选）
     * @author Joker.X
     */
    @Serializable
    data class Pay(
        val orderId: Long,
        val price: Int,
        val from: String? = null
    ) : NavKey

    /**
     * 退款申请路由
     *
     * @param orderId 订单ID
     * @author Joker.X
     */
    @Serializable
    data class Refund(val orderId: Long) : NavKey

    /**
     * 订单评价路由
     *
     * @param orderId 订单ID
     * @param goodsId 商品ID
     * @author Joker.X
     */
    @Serializable
    data class Comment(val orderId: Long, val goodsId: Long) : NavKey

    /**
     * 订单物流路由
     *
     * @param orderId 订单ID
     * @author Joker.X
     */
    @Serializable
    data class Logistics(val orderId: Long) : NavKey
}
