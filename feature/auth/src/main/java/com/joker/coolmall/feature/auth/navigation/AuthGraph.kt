package com.joker.coolmall.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * 认证模块导航图
 *
 * 整合认证模块下所有页面的导航
 * @author Joker.X
 */
fun NavGraphBuilder.authGraph(navController: NavHostController) {
    loginScreen()
    accountLoginScreen()
    smsLoginScreen()
    registerScreen()
    resetPasswordScreen()
} 