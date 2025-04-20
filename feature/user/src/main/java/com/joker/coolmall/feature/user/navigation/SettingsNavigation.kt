package com.joker.coolmall.feature.user.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.user.view.SettingsRoute
import com.joker.coolmall.navigation.routes.UserRoutes

/**
 * 设置页面导航
 */
fun NavGraphBuilder.settingsScreen() {
    composable(route = UserRoutes.SETTINGS) {
        SettingsRoute()
    }
} 