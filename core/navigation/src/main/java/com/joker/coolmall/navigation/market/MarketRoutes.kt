package com.joker.coolmall.navigation.market

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * 营销模块路由
 *
 * @author Joker.X
 */
object MarketRoutes {
    /**
     * 优惠券管理路由
     *
     * @author Joker.X
     */
    @Serializable
    data object Coupon : NavKey
}
