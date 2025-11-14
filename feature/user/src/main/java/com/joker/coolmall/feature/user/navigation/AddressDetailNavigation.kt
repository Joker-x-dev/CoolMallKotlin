package com.joker.coolmall.feature.user.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.user.view.AddressDetailRoute
import com.joker.coolmall.navigation.routes.UserRoutes

/**
 * 收货地址详情页面导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.addressDetailScreen() {
    composable<UserRoutes.AddressDetail> {
        AddressDetailRoute()
    }
}
