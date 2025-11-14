package com.joker.coolmall.feature.order.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.order.view.OrderRefundRoute
import com.joker.coolmall.navigation.routes.OrderRoutes

/**
 * 退款申请导航
 *
 * @param navController 导航控制器
 * @author Joker.X
 */
fun NavGraphBuilder.orderRefundScreen(navController: NavHostController) {
    composable<OrderRoutes.Refund> {
        OrderRefundRoute()
    }
}
