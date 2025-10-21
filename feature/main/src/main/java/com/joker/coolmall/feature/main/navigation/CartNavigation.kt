package com.joker.coolmall.feature.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.joker.coolmall.feature.main.view.CartRoute
import com.joker.coolmall.navigation.routes.MainRoutes

/**
 * 购物车路由常量
 *
 * @author Joker.X
 */
object CartRoutes {
    const val SHOW_BACK_ICON_ARG = "show_back_icon"

    /**
     * 带参数的路由模式
     */
    const val CART_PATTERN = "${MainRoutes.CART}?${SHOW_BACK_ICON_ARG}={${SHOW_BACK_ICON_ARG}}"
}

/**
 * 购物车页面导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.cartScreen() {
    composable(
        route = CartRoutes.CART_PATTERN,
        arguments = listOf(
            navArgument(CartRoutes.SHOW_BACK_ICON_ARG) {
                type = NavType.BoolType
                defaultValue = false
            }
        )
    ) {
        CartRoute()
    }
}