package com.joker.coolmall.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * 认证模块导航图
 * 
 * 整合认证模块下所有页面的导航
 * 
 * @param navController 导航控制器
 */
fun NavGraphBuilder.authGraph(navController: NavHostController) {
    authHomeScreen(navController)
    loginScreen(navController)
    smsLoginScreen(navController)
    registerScreen(navController)
    resetPasswordScreen(navController)
} 