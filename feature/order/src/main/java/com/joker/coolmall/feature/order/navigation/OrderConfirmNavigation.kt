package com.joker.coolmall.feature.order.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.order.view.OrderConfirmRoute
import com.joker.coolmall.navigation.routes.OrderRoutes

/**
 * 确认订单页面导航
 *
 * @param navController 导航控制器
 * @author Joker.X
 */
fun NavGraphBuilder.orderConfirmScreen(navController: NavHostController) {
    composable<OrderRoutes.Confirm> {
        OrderConfirmRoute(navController = navController)
    }
}