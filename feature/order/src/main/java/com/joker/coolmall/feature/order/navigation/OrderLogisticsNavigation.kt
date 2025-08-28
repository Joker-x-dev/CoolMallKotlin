/**
 * 订单物流页面导航
 *
 * @author Joker.X
 */
package com.joker.coolmall.feature.order.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.joker.coolmall.feature.order.view.OrderLogisticsRoute
import com.joker.coolmall.navigation.routes.OrderRoutes

/**
 * 订单物流页面路由常量
 */
object OrderLogisticsRoutes {
    const val ORDER_ID_ARG = "order_id"

    /**
     * 带参数的路由模式
     */
    const val LOGISTICS_PATTERN = "${OrderRoutes.LOGISTICS}/{$ORDER_ID_ARG}"
}

/**
 * 订单物流导航
 */
fun NavGraphBuilder.orderLogisticsScreen(navController: NavHostController) {
    composable(
        route = OrderLogisticsRoutes.LOGISTICS_PATTERN,
        arguments = listOf(navArgument(OrderLogisticsRoutes.ORDER_ID_ARG) {
            type = NavType.LongType
        })
    ) {
        OrderLogisticsRoute()
    }
}