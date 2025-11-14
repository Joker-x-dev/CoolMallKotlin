package com.joker.coolmall.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.auth.view.RegisterRoute
import com.joker.coolmall.navigation.routes.AuthRoutes

/**
 * 注册页面导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.registerScreen() {
    composable<AuthRoutes.Register> {
        RegisterRoute()
    }
}
