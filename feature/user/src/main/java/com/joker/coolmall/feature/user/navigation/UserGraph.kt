package com.joker.coolmall.feature.user.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * 用户模块导航图
 *
 * 整合用户模块下所有页面的导航
 */
fun NavGraphBuilder.userGraph(navController: NavHostController) {
    profileScreen()
    settingsScreen()
    addressListScreen()
    addressDetailScreen()
    footprintScreen()
}
