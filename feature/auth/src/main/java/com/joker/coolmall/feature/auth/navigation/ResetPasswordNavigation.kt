package com.joker.coolmall.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.auth.view.ResetPasswordRoute
import com.joker.coolmall.navigation.routes.AuthRoutes

/**
 * 找回密码页面导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.resetPasswordScreen() {
    composable<AuthRoutes.ResetPassword> {
        ResetPasswordRoute()
    }
}
