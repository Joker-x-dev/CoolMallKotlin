package com.joker.coolmall.feature.user.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.joker.coolmall.feature.user.view.ProfileRoute
import com.joker.coolmall.navigation.routes.UserRoutes

/**
 * 个人中心页面路由常量
 */
object ProfileRoutes {
    const val AVATAR_URL = "avatar_url"

    /**
     * 带参数的路由模式（URL参数可选）
     */
    const val PROFILE_PATTERN = "${UserRoutes.PROFILE}?${AVATAR_URL}={${AVATAR_URL}}"
}

/**
 * 个人中心页面导航
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.profileScreen(sharedTransitionScope: SharedTransitionScope) {
    composable(
        route = ProfileRoutes.PROFILE_PATTERN,
        arguments = listOf(navArgument(ProfileRoutes.AVATAR_URL) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        ProfileRoute(sharedTransitionScope, this@composable)
    }
}