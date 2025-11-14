package com.joker.coolmall.feature.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.common.view.ContributorsRoute
import com.joker.coolmall.navigation.routes.CommonRoutes

/**
 * 贡献者列表页面导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.contributorsScreen() {
    composable<CommonRoutes.Contributors> {
        ContributorsRoute()
    }
}
