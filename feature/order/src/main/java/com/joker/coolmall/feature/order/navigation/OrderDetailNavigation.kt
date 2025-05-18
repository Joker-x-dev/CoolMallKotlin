package com.joker.coolmall.feature.order.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.joker.coolmall.feature.order.view.OrderDetailRoute
import com.joker.coolmall.navigation.routes.OrderRoutes

/**
 * 订单详情页面路由常量
 */
object OrderDetailRoutes {
    const val ORDER_ID_ARG = "order_id"

    /**
     * 带参数的路由模式
     */
    const val ORDER_DETAIL_PATTERN = "${OrderRoutes.DETAIL}/{$ORDER_ID_ARG}"
}

/**
 * 订单详情导航
 */
fun NavGraphBuilder.orderDetailScreen() {
    composable(
        route = OrderDetailRoutes.ORDER_DETAIL_PATTERN,
        arguments = listOf(navArgument(OrderDetailRoutes.ORDER_ID_ARG) {
            type = NavType.LongType
        })
    ) {
        OrderDetailRoute()
    }
} 