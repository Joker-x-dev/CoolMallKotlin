package com.joker.coolmall.feature.order.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * 订单模块导航图
 *
 * 整合订单模块下所有页面的导航
 */
fun NavGraphBuilder.orderGraph(navController: NavHostController) {
    // 订单列表页面
    orderListScreen(navController)

    // 订单确认页面
    orderConfirmScreen(navController)

    // 订单详情页面
    orderDetailScreen(navController)

    // 订单支付页面
    orderPayScreen()
}
