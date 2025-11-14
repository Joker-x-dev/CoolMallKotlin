package com.joker.coolmall.feature.user.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.user.view.FootprintRoute
import com.joker.coolmall.navigation.routes.UserRoutes

/**
 * 用户足迹页面导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.footprintScreen() {
    composable<UserRoutes.Footprint> {
        FootprintRoute()
    }
}
