package com.joker.coolmall.feature.order.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.joker.coolmall.feature.order.view.OrderRefundRoute
import com.joker.coolmall.navigation.routes.OrderRoutes

/**
 * 退款申请页面路由常量
 *
 * @author Joker.X
 */
object OrderRefundRoutes {
    const val ORDER_ID_ARG = "order_id"

    /**
     * 带参数的路由模式
     */
    const val REFUND_PATTERN = "${OrderRoutes.REFUND}/{$ORDER_ID_ARG}"
}

/**
 * 退款申请导航
 *
 * @param navController 导航控制器
 * @author Joker.X
 */
fun NavGraphBuilder.orderRefundScreen(navController: NavHostController) {
    composable(
        route = OrderRefundRoutes.REFUND_PATTERN,
        arguments = listOf(navArgument(OrderRefundRoutes.ORDER_ID_ARG) {
            type = NavType.LongType
        })
    ) {
        OrderRefundRoute()
    }
}