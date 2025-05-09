package com.joker.coolmall.feature.order.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.order.view.OrderDetailRoute
import com.joker.coolmall.navigation.routes.OrderRoutes

/**
 * 订单详情导航
 */
fun NavGraphBuilder.orderDetailScreen() {
    composable(route = OrderRoutes.ORDER_DETAIL) {
        OrderDetailRoute()
    }
} 