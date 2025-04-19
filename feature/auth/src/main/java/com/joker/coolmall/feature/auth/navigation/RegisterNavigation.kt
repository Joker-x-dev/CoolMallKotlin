package com.joker.coolmall.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.auth.view.RegisterRoute
import com.joker.coolmall.navigation.routes.AuthRoutes

/**
 * 注册页面导航
 */
fun NavGraphBuilder.registerScreen() {
    composable(route = AuthRoutes.REGISTER) {
        RegisterRoute()
    }
} 