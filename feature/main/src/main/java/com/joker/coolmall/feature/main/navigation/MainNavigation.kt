package com.joker.coolmall.feature.main.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.main.view.MainRoute
import com.joker.coolmall.navigation.routes.MainRoutes

/**
 * 注册主页面路由
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.mainScreen(sharedTransitionScope: SharedTransitionScope) {
    composable(MainRoutes.MAIN) {
        MainRoute(sharedTransitionScope, this@composable)
    }
}