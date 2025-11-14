package com.joker.coolmall.feature.user.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.user.view.AddressListRoute
import com.joker.coolmall.navigation.routes.UserRoutes

/**
 * 收货地址列表页面导航
 *
 * @param navController 导航控制器
 * @author Joker.X
 */
fun NavGraphBuilder.addressListScreen(navController: NavHostController) {
    composable<UserRoutes.AddressList> {
        AddressListRoute(navController = navController)
    }
}
