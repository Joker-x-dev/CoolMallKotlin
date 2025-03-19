package com.joker.coolmall.feature.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.main.MainRoute

/**
 * 主页面路由
 */
const val MAIN_ROUTE = "main"

/**
 * 导航到主页面的NavController扩展函数
 */
fun NavController.navigateToMain() {
    navigate(MAIN_ROUTE) {
        launchSingleTop = true
        popUpTo(MAIN_ROUTE)
    }
}

/**
 * 注册主页面路由
 */
fun NavGraphBuilder.mainScreen() {
    composable(MAIN_ROUTE) {
        MainRoute()
    }
}