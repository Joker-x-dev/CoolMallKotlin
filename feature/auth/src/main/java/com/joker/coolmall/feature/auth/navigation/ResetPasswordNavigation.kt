package com.joker.coolmall.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.auth.view.ResetPasswordRoute
import com.joker.coolmall.navigation.routes.AuthRoutes

/**
 * 找回密码页面导航
 *
 * @param navController 导航控制器
 */
fun NavGraphBuilder.resetPasswordScreen(navController: NavController) {
    composable(route = AuthRoutes.RESET_PASSWORD) {
        ResetPasswordRoute(navController = navController)
    }
} 