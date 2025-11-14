package com.joker.coolmall.feature.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.common.view.SettingsRoute
import com.joker.coolmall.navigation.routes.CommonRoutes

/**
 * 设置页面导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.settingsScreen() {
    composable<CommonRoutes.Settings> {
        SettingsRoute()
    }
}
