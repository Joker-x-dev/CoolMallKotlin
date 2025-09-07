package com.joker.coolmall.feature.user.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * 用户模块导航图
 *
 * 整合用户模块下所有页面的导航
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
