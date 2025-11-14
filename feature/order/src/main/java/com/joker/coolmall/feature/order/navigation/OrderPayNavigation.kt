package com.joker.coolmall.feature.order.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.order.view.OrderPayRoute
import com.joker.coolmall.navigation.routes.OrderRoutes

/**
 * 订单支付页面导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.orderPayScreen() {
    composable<OrderRoutes.Pay> {
        OrderPayRoute()
    }
}
