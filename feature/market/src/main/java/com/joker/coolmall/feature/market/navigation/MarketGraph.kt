package com.joker.coolmall.feature.market.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.joker.coolmall.navigation.market.MarketRoutes
import com.joker.coolmall.feature.market.view.CouponRoute

/**
 * 营销模块导航图
 *
 * @author Joker.X
 */
fun EntryProviderScope<NavKey>.marketGraph() {
    entry<MarketRoutes.Coupon> {
        CouponRoute()
    }
}
