package com.joker.coolmall.feature.user.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * 用户模块导航图
 *
 * 整合用户模块下所有页面的导航
 *
 * @param navController 导航控制器
 * @param sharedTransitionScope 共享转换作用域
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.userGraph(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope
) {
    profileScreen(sharedTransitionScope)
    addressListScreen(navController)
    addressDetailScreen()
    footprintScreen()
}
