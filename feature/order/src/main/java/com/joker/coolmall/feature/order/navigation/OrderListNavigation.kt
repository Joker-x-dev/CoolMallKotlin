package com.joker.coolmall.feature.order.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.order.view.OrderListRoute
import com.joker.coolmall.navigation.routes.OrderRoutes

/**
 * 订单列表页面导航
 */
fun NavGraphBuilder.orderListScreen() {
    composable(route = OrderRoutes.ORDER_LIST) {
        OrderListRoute()
    }
} 