package com.joker.coolmall.navigation

import com.joker.coolmall.navigation.routes.*
import kotlin.reflect.KClass

/**
 * 路由拦截器（类型安全版本）
 *
 * 负责管理需要登录的页面配置和路由拦截逻辑
 * 使用类型安全的方式处理路由拦截
 *
 * @author Joker.X
 */
class RouteInterceptor {

    /**
     * 需要登录的路由类型集合
     * 在这里配置所有需要登录才能访问的页面类型
     */
    private val loginRequiredRouteTypes = setOf(
        // 用户模块 - 需要登录的页面
        UserRoutes.Profile::class,
        UserRoutes.AddressList::class,
        UserRoutes.AddressDetail::class,
        UserRoutes.Footprint::class,

        // 订单模块 - 需要登录的页面
        OrderRoutes.List::class,
        OrderRoutes.Confirm::class,
        OrderRoutes.Detail::class,
        OrderRoutes.Pay::class,
        OrderRoutes.Refund::class,
        OrderRoutes.Comment::class,
        OrderRoutes.Logistics::class,

        // 客服模块 - 需要登录的页面
        CsRoutes.Chat::class,

        // 营销模块 - 需要登录的页面
        MarketRoutes.Coupon::class,

        // 反馈模块 - 需要登录的页面
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
    fun requiresLogin(route: Any): Boolean {
        // 获取路由对象的类型
        val routeClass = route::class

        // 检查是否在需要登录的路由类型集合中
        return loginRequiredRouteTypes.contains(routeClass)
    }

    /**
     * 获取登录页面路由对象
     *
     * @return 登录页面的路由对象
     * @author Joker.X
     */
    fun getLoginRoute(): Any {
        return AuthRoutes.Login
    }

    /**
     * 添加需要登录的路由类型
     *
     * @param routeClass 需要登录的路由类型
     * @author Joker.X
     */
    fun addLoginRequiredRoute(routeClass: KClass<*>) {
        (loginRequiredRouteTypes as MutableSet).add(routeClass)
    }

    /**
     * 移除需要登录的路由类型
     *
     * @param routeClass 不再需要登录的路由类型
     * @author Joker.X
     */
    fun removeLoginRequiredRoute(routeClass: KClass<*>) {
        (loginRequiredRouteTypes as MutableSet).remove(routeClass)
    }

    /**
     * 获取所有需要登录的路由类型
     *
     * @return 需要登录的路由类型集合
     * @author Joker.X
     */
    fun getLoginRequiredRoutes(): Set<KClass<*>> {
        return loginRequiredRouteTypes.toSet()
    }
}
