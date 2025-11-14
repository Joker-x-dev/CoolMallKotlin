package com.joker.coolmall.feature.launch.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.launch.view.GuideRoute
import com.joker.coolmall.navigation.routes.LaunchRoutes

/**
 * 引导页面导航（类型安全版本）
 *
 * @author Joker.X
 */
fun NavGraphBuilder.guideScreen() {
    composable<LaunchRoutes.Guide> {
        GuideRoute()
    }
}
