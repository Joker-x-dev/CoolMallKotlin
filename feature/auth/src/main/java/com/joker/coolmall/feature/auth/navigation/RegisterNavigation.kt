package com.joker.coolmall.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.auth.view.RegisterRoute
import com.joker.coolmall.navigation.routes.AuthRoutes

/**
 * 注册页面导航
 *
 * @param navController 导航控制器
 */
fun NavGraphBuilder.registerScreen(navController: NavController) {
    composable(route = AuthRoutes.REGISTER) {
        RegisterRoute(navController = navController)
    }
} 