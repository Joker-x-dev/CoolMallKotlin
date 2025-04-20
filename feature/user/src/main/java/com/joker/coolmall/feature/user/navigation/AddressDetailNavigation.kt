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

    /**
     * 带参数的路由模式
     */
    const val ADDRESS_DETAIL_PATTERN = "${UserRoutes.ADDRESS_DETAIL}/{$ADDRESS_ID_ARG}"
}

/**
 * 收货地址详情页面导航
 */
fun NavGraphBuilder.addressDetailScreen() {
    composable(
        route = AddressDetailRoutes.ADDRESS_DETAIL_PATTERN,
        arguments = listOf(navArgument(AddressDetailRoutes.ADDRESS_ID_ARG) {
            type = NavType.LongType
        })
    ) {
        AddressDetailRoute()
    }
} 