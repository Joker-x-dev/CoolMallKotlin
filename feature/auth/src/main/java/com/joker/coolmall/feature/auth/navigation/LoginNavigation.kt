package com.joker.coolmall.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.auth.view.LoginRoute
import com.joker.coolmall.navigation.routes.AuthRoutes

/**
 * 登录主页导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.loginScreen() {
    composable<AuthRoutes.Login> {
        LoginRoute()
    }
}
