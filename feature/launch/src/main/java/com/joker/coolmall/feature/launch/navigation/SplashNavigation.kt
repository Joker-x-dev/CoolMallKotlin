/**
 * 启动页导航
 *
 * @author Joker.X
 */
package com.joker.coolmall.feature.launch.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.launch.view.SplashRoute
import com.joker.coolmall.navigation.routes.LaunchRoutes

/**
 * 启动页面导航
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.splashScreen(sharedTransitionScope: SharedTransitionScope) {
    composable(route = LaunchRoutes.SPLASH) {
        SplashRoute(sharedTransitionScope, this@composable)
    }
}