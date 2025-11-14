package com.joker.coolmall.feature.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.main.view.CartRoute
import com.joker.coolmall.navigation.routes.MainRoutes

/**
 * 购物车页面导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.cartScreen() {
    composable<MainRoutes.Cart> {
        CartRoute()
    }
}
