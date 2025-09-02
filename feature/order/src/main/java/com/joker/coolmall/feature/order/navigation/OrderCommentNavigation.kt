/**
 * 订单评价页面导航
 *
 * @author Joker.X
 */
package com.joker.coolmall.feature.order.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.joker.coolmall.feature.order.view.OrderCommentRoute
import com.joker.coolmall.navigation.routes.OrderRoutes

/**
 * 订单评价页面路由常量
 */
object OrderCommentRoutes {
    const val ORDER_ID_ARG = "order_id"
    const val GOODS_ID_ARG = "goods_id"

    /**
     * 带参数的路由模式
     */
    const val COMMENT_PATTERN = "${OrderRoutes.COMMENT}/{$ORDER_ID_ARG}/{$GOODS_ID_ARG}"
}

/**
 * 订单评价导航
 */
fun NavGraphBuilder.orderCommentScreen(navController: NavHostController) {
    composable(
        route = OrderCommentRoutes.COMMENT_PATTERN,
        arguments = listOf(
            navArgument(OrderCommentRoutes.ORDER_ID_ARG) {
                type = NavType.LongType
            },
            navArgument(OrderCommentRoutes.GOODS_ID_ARG) {
                type = NavType.LongType
            }
        )
    ) {
        OrderCommentRoute()
    }
}