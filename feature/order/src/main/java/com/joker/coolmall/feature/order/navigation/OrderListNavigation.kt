package com.joker.coolmall.feature.order.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.order.view.OrderListRoute
import com.joker.coolmall.navigation.routes.OrderRoutes

/**
 * 订单列表页面导航
 */
fun NavGraphBuilder.orderListScreen(navController: NavHostController) {
    // 添加支持URL参数的路由，允许传入tab参数
    composable(
        route = "${OrderRoutes.LIST}?tab={tab}",
        arguments = emptyList()
    ) {
        OrderListRoute(navController = navController)
    }
} 