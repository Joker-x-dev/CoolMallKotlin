package com.joker.coolmall.feature.order.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * 订单模块导航图
 *
 * 整合订单模块下所有页面的导航
 *
 * @param navController 导航控制器
 * @author Joker.X
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

    // 订单物流页面
    orderLogisticsScreen(navController)

    // 订单退款页面
    orderRefundScreen(navController)

    // 订单评论页面
    orderCommentScreen(navController)
}
