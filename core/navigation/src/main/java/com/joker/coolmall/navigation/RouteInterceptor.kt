package com.joker.coolmall.navigation

import androidx.navigation3.runtime.NavKey
import com.joker.coolmall.core.navigation.user.UserRoutes
import com.joker.coolmall.navigation.auth.AuthRoutes
import com.joker.coolmall.navigation.cs.CsRoutes
import com.joker.coolmall.navigation.feedback.FeedbackRoutes
import com.joker.coolmall.navigation.market.MarketRoutes
import com.joker.coolmall.navigation.order.OrderRoutes
import kotlin.reflect.KClass

/**
 * 路由拦截器
 *
 * 负责管理需要登录的页面配置和路由拦截逻辑
 * 使用类型安全的方式处理路由拦截
 *
 * @author Joker.X
 */
class RouteInterceptor {

    /**
     * 需要登录的路由类型集合
     *
     * 在这里统一声明所有需要登录才能访问的页面类型。
     *
     * @author Joker.X
     */
    private val loginRequiredRouteTypes: Set<KClass<out NavKey>> = setOf(
        UserRoutes.Profile::class,
        UserRoutes.AddressList::class,
        UserRoutes.AddressDetail::class,
        OrderRoutes.List::class,
        OrderRoutes.Confirm::class,
        OrderRoutes.Detail::class,
        OrderRoutes.Pay::class,
        OrderRoutes.Refund::class,
        OrderRoutes.Comment::class,
        OrderRoutes.Logistics::class,
        CsRoutes.Chat::class,
        MarketRoutes.Coupon::class,
        FeedbackRoutes.List::class,
        FeedbackRoutes.Submit::class,
    )

    /**
     * 检查指定路由对象是否需要登录
     *
     * @param route 要检查的路由对象（类型安全）
     * @return true表示需要登录，false表示不需要登录
     * @author Joker.X
     */
    fun requiresLogin(route: NavKey): Boolean {
        val routeClass = route::class
        return loginRequiredRouteTypes.contains(routeClass)
    }

    /**
     * 获取登录页面路由对象
     *
     * @return 登录页面的路由对象
     * @author Joker.X
     */
    fun getLoginRoute(): NavKey = AuthRoutes.Login
}
