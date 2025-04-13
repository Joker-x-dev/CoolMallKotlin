package com.joker.coolmall.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.joker.coolmall.navigation.routes.AuthRoutes

/**
 * 认证模块导航入口
 * 
 * 使用 navigation 构建认证模块的完整导航图
 *
 * @param navController 导航控制器
 */
fun NavGraphBuilder.authNavigation(navController: NavHostController) {
    navigation(
        startDestination = AuthRoutes.HOME,
        route = AuthRoutes.HOME
    ) {
        authGraph(navController)
    }
} 