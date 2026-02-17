package com.joker.coolmall.core.navigation.market

import com.joker.coolmall.core.navigation.navigate

/**
 * 营销模块导航封装
 *
 * @author Joker.X
 */
object MarketNavigator {

    /**
     * 跳转到优惠券页
     *
     * @author Joker.X
     */
    fun toCoupon() {
        navigate(MarketRoutes.Coupon)
    }
}
