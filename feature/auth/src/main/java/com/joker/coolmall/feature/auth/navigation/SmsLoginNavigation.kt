package com.joker.coolmall.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.auth.view.SmsLoginRoute
import com.joker.coolmall.navigation.routes.AuthRoutes

/**
 * 短信登录页面导航
 *
 * @param navController 导航控制器
 */
fun NavGraphBuilder.smsLoginScreen(navController: NavController) {
    composable(route = AuthRoutes.SMS_LOGIN) {
        SmsLoginRoute(navController = navController)
    }
} 