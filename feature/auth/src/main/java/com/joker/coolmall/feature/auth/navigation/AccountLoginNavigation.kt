package com.joker.coolmall.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.auth.view.AccountLoginRoute
import com.joker.coolmall.navigation.routes.AuthRoutes

/**
 * 账号登录页面导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.accountLoginScreen() {
    composable<AuthRoutes.AccountLogin> {
        AccountLoginRoute()
    }
}
