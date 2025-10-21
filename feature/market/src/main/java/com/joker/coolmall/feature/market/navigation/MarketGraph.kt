package com.joker.coolmall.feature.market.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * 营销模块导航图
 *
 * @param navController 导航控制器
 * @author Joker.X
 */
fun NavGraphBuilder.marketGraph(navController: NavHostController) {
    couponScreen()
}