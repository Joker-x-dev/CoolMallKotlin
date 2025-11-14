package com.joker.coolmall.feature.order.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.order.view.OrderLogisticsRoute
import com.joker.coolmall.navigation.routes.OrderRoutes

/**
 * 订单物流导航
 *
 * @param navController 导航控制器
 * @author Joker.X
 */
fun NavGraphBuilder.orderLogisticsScreen(navController: NavHostController) {
    composable<OrderRoutes.Logistics> {
        OrderLogisticsRoute()
    }
}
