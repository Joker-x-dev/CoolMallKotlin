package com.joker.coolmall.feature.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.common.view.WebRoute
import com.joker.coolmall.navigation.routes.CommonRoutes

/**
 * 网页页面导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.webScreen() {
    composable<CommonRoutes.Web> {
        WebRoute()
    }
}
