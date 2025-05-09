package com.joker.coolmall.feature.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.main.view.MainRoute
import com.joker.coolmall.navigation.routes.MainRoutes

/**
 * 注册主页面路由
 */
fun NavGraphBuilder.mainScreen() {
    composable(MainRoutes.MAIN) {
        MainRoute()
    }
}