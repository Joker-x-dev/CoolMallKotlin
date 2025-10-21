package com.joker.coolmall.feature.launch.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.joker.coolmall.feature.launch.view.GuideRoute
import com.joker.coolmall.navigation.routes.LaunchRoutes

/**
 * 引导页路由常量
 *
 * @author Joker.X
 */
object GuideRoutes {
    const val FROM_SETTINGS_ARG = "from_settings"

    /**
     * 带参数的路由模式
     */
    const val GUIDE_PATTERN = "${LaunchRoutes.GUIDE}?${FROM_SETTINGS_ARG}={${FROM_SETTINGS_ARG}}"
}

/**
 * 引导页面导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.guideScreen() {
    composable(
        route = GuideRoutes.GUIDE_PATTERN,
        arguments = listOf(
            navArgument(GuideRoutes.FROM_SETTINGS_ARG) {
                type = NavType.BoolType
                defaultValue = false
            }
        )
    ) {
        GuideRoute()
    }
}