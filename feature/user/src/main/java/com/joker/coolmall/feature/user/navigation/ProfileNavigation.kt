package com.joker.coolmall.feature.user.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.user.view.ProfileRoute
import com.joker.coolmall.navigation.routes.UserRoutes

/**
 * 个人中心页面导航
 */
fun NavGraphBuilder.profileScreen() {
    composable(route = UserRoutes.PROFILE) {
        ProfileRoute()
    }
} 