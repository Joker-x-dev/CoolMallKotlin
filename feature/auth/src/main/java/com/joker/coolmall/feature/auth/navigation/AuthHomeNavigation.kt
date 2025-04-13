package com.joker.coolmall.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.auth.view.AuthHomeRoute
import com.joker.coolmall.navigation.routes.AuthRoutes

/**
 * 登录主页导航
 *
 * @param navController 导航控制器
 */
fun NavGraphBuilder.authHomeScreen(navController: NavController) {
    composable(route = AuthRoutes.HOME) {
        AuthHomeRoute(navController = navController)
    }
} 