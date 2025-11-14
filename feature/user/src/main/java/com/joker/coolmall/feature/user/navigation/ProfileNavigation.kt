package com.joker.coolmall.feature.user.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.user.view.ProfileRoute
import com.joker.coolmall.navigation.routes.UserRoutes

/**
 * 个人中心页面导航
 *
 * @param sharedTransitionScope 共享转换作用域
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.profileScreen(sharedTransitionScope: SharedTransitionScope) {
    composable<UserRoutes.Profile> {
        ProfileRoute(sharedTransitionScope, this@composable)
    }
}
