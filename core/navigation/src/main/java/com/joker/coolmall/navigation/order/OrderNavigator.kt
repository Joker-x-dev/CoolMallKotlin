package com.joker.coolmall.core.navigation.order

import com.joker.coolmall.core.navigation.navigate

/**
 * 订单模块导航封装
 *
 * @author Joker.X
 */
object OrderNavigator {

    /**
     * 跳转到订单列表页
     *
     * @param tab Tab标签
     * @author Joker.X
     */
    fun toList(tab: String? = null) {
        navigate(OrderRoutes.List(tab = tab))
    }

    /**
     * 跳转到确认订单页
     *
     * @author Joker.X
     */
    fun toConfirm() {
        navigate(OrderRoutes.Confirm)
    }

    /**
     * 跳转到订单详情页
     *
     * @param orderId 订单ID
     * @author Joker.X
     */
    fun toDetail(orderId: Long) {
        navigate(OrderRoutes.Detail(orderId = orderId))
    }

    /**
     * 跳转到订单支付页
     *
     * @param orderId 订单ID
     * @param price 支付价格
     * @param from 来源
     * @author Joker.X
     */
    fun toPay(orderId: Long, price: Int, from: String? = null) {
        navigate(
            OrderRoutes.Pay(
                orderId = orderId,
                price = price,
                from = from,
            )
        )
    }

    /**
     * 跳转到退款申请页
     *
     * @param orderId 订单ID
     * @author Joker.X
     */
    fun toRefund(orderId: Long) {
        navigate(OrderRoutes.Refund(orderId = orderId))
    }

    /**
     * 跳转到订单评价页
     *
     * @param orderId 订单ID
     * @param goodsId 商品ID
     * @author Joker.X
     */
    fun toComment(orderId: Long, goodsId: Long) {
        navigate(
            OrderRoutes.Comment(
                orderId = orderId,
                goodsId = goodsId,
            )
        )
    }

    /**
     * 跳转到订单物流页
     *
     * @param orderId 订单ID
     * @author Joker.X
     */
    fun toLogistics(orderId: Long) {
        navigate(OrderRoutes.Logistics(orderId = orderId))
    }
}
