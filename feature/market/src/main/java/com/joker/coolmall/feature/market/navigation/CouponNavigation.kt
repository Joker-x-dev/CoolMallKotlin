package com.joker.coolmall.feature.market.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.market.view.CouponRoute
import com.joker.coolmall.navigation.routes.MarketRoutes

/**
 * 我的优惠券页面导航
 */
fun NavGraphBuilder.couponScreen() {
    composable(route = MarketRoutes.COUPON) {
        CouponRoute()
    }
}