package com.joker.coolmall.feature.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.common.view.AboutRoute
import com.joker.coolmall.navigation.routes.CommonRoutes

/**
 * 关于我们页面导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.aboutScreen() {
    composable<CommonRoutes.About> {
        AboutRoute()
    }
}
