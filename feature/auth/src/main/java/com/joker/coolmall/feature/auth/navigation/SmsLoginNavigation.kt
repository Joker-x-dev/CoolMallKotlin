package com.joker.coolmall.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.auth.view.SmsLoginRoute
import com.joker.coolmall.navigation.routes.AuthRoutes

/**
 * 短信登录页面导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.smsLoginScreen() {
    composable<AuthRoutes.SmsLogin> {
        SmsLoginRoute()
    }
}
