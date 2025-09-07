package com.joker.coolmall.feature.launch.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * 商品模块导航图
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.launchGraph(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope
) {
    splashScreen(sharedTransitionScope)
    guideScreen(sharedTransitionScope)
}