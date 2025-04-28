package com.joker.coolmall.feature.user.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.joker.coolmall.feature.user.view.AddressDetailRoute
import com.joker.coolmall.navigation.routes.UserRoutes

/**
 * 收货地址详情页面路由常量
 */
object AddressDetailRoutes {
    const val ADDRESS_ID_ARG = "address_id"
    const val IS_EDIT_MODE_ARG = "is_edit_mode"

    /**
     * 带参数的路由模式
     */
    const val ADDRESS_DETAIL_PATTERN = "${UserRoutes.ADDRESS_DETAIL}?${IS_EDIT_MODE_ARG}={${IS_EDIT_MODE_ARG}}&${ADDRESS_ID_ARG}={${ADDRESS_ID_ARG}}"
}

/**
 * 收货地址详情页面导航
 */
fun NavGraphBuilder.addressDetailScreen() {
    composable(
        route = AddressDetailRoutes.ADDRESS_DETAIL_PATTERN,
        arguments = listOf(
            navArgument(AddressDetailRoutes.IS_EDIT_MODE_ARG) {
                type = NavType.BoolType
                defaultValue = false
            },
            navArgument(AddressDetailRoutes.ADDRESS_ID_ARG) {
                type = NavType.LongType
                defaultValue = 0L
            }
        )
    ) {
        AddressDetailRoute()
    }
} 