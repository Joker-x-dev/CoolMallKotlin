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
    const val FROM_ARG = "from"

    // 来源类型常量
    const val FROM_ORDER_CONFIRM = "confirm" // 从确认订单页面来

    /**
     * 带参数的路由模式 - 基本路径
     */
    const val ORDER_PAY_PATTERN = "${OrderRoutes.PAY}/{$ORDER_ID_ARG}/{$PRICE_ARG}"

    /**
     * 带参数的路由模式 - 包含可选的来源参数
     */
    const val ORDER_PAY_WITH_FROM_PATTERN = "$ORDER_PAY_PATTERN?$FROM_ARG={$FROM_ARG}"
}

/**
 * 订单支付页面导航
 */
fun NavGraphBuilder.orderPayScreen() {
    composable(
        // 使用带可选参数的路由模式
        route = OrderPayRoutes.ORDER_PAY_WITH_FROM_PATTERN,
        arguments = listOf(
            navArgument(OrderPayRoutes.ORDER_ID_ARG) {
                type = NavType.LongType
            },
            navArgument(OrderPayRoutes.PRICE_ARG) {
                type = NavType.IntType
            },
            navArgument(OrderPayRoutes.FROM_ARG) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    ) {
        OrderPayRoute()
    }
}