package com.joker.coolmall.feature.user.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.user.view.ProfileRoute
import com.joker.coolmall.navigation.routes.UserRoutes

/**
 * 个人中心页面导航
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.profileScreen(sharedTransitionScope: SharedTransitionScope) {
    composable(route = UserRoutes.PROFILE) {
        ProfileRoute(sharedTransitionScope, this@composable)
    }
}