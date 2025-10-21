package com.joker.coolmall.feature.launch.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * 启动模块导航图
 *
 * @param navController 导航控制器
 * @param sharedTransitionScope 共享转换作用域
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.launchGraph(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope
) {
    splashScreen(sharedTransitionScope)
    guideScreen()
}