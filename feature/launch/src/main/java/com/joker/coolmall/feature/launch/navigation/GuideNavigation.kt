/**
 * 引导页导航
 *
 * @author Joker.X
 */
package com.joker.coolmall.feature.launch.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.launch.view.GuideRoute
import com.joker.coolmall.navigation.routes.LaunchRoutes

/**
 * 引导页面导航
 */
fun NavGraphBuilder.guideScreen() {
    composable(route = LaunchRoutes.GUIDE) {
        GuideRoute()
    }
}