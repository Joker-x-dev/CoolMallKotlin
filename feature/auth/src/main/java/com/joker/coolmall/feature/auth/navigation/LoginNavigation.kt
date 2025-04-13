package com.joker.coolmall.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.auth.view.LoginRoute
import com.joker.coolmall.navigation.routes.AuthRoutes

/**
 * 账号密码登录页面导航
 *
 * @param navController 导航控制器
 */
fun NavGraphBuilder.loginScreen(navController: NavController) {
    composable(route = AuthRoutes.LOGIN) {
        LoginRoute(navController = navController)
    }
} 