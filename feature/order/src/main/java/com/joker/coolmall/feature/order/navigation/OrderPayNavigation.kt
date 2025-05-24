package com.joker.coolmall.feature.order.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.joker.coolmall.feature.order.view.OrderPayRoute
import com.joker.coolmall.navigation.routes.OrderRoutes

/**
 * 订单支付页面路由常量
 */
object OrderPayRoutes {
    const val ORDER_ID_ARG = "order_id"
    const val PRICE_ARG = "price"

    /**
     * 带参数的路由模式
     */
    const val ORDER_PAY_PATTERN = "${OrderRoutes.PAY}/{$ORDER_ID_ARG}/{$PRICE_ARG}"
}

/**
 * 订单支付页面导航
 */
fun NavGraphBuilder.orderPayScreen() {
    composable(
        route = OrderPayRoutes.ORDER_PAY_PATTERN,
        arguments = listOf(
            navArgument(OrderPayRoutes.ORDER_ID_ARG) {
                type = NavType.LongType
            },
            navArgument(OrderPayRoutes.PRICE_ARG) {
                type = NavType.IntType
            }
        )
    ) {
        OrderPayRoute()
    }
}