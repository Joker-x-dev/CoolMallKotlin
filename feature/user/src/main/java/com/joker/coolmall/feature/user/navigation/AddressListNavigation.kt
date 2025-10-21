package com.joker.coolmall.feature.user.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.joker.coolmall.feature.user.view.AddressListRoute
import com.joker.coolmall.navigation.routes.UserRoutes

/**
 * 收货地址列表页面路由常量
 *
 * @author Joker.X
 */
object AddressListRoutes {
    const val IS_SELECT_MODE_ARG = "is_select_mode"

    /**
     * 带参数的路由模式
     */
    const val ADDRESS_LIST_WITH_MODE_PATTERN =
        "${UserRoutes.ADDRESS_LIST}?${IS_SELECT_MODE_ARG}={${IS_SELECT_MODE_ARG}}"
}

/**
 * 收货地址列表页面导航
 *
 * @param navController 导航控制器
 * @author Joker.X
 */
fun NavGraphBuilder.addressListScreen(navController: NavHostController) {
    composable(
        route = AddressListRoutes.ADDRESS_LIST_WITH_MODE_PATTERN,
        arguments = listOf(
            navArgument(AddressListRoutes.IS_SELECT_MODE_ARG) {
                type = NavType.BoolType
                defaultValue = false
            }
        )
    ) {
        AddressListRoute(navController = navController)
    }
}