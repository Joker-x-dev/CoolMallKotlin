package com.joker.coolmall.navigation

import com.joker.coolmall.navigation.routes.AuthRoutes
import com.joker.coolmall.navigation.routes.CsRoutes
import com.joker.coolmall.navigation.routes.FeedbackRoutes
import com.joker.coolmall.navigation.routes.MarketRoutes
import com.joker.coolmall.navigation.routes.OrderRoutes
import com.joker.coolmall.navigation.routes.UserRoutes

/**
 * 路由拦截器
 * 负责管理需要登录的页面配置和路由拦截逻辑
 *
 * @author Joker.X
 */
class RouteInterceptor {

    /**
     * 需要登录的页面路由集合
     * 在这里配置所有需要登录才能访问的页面
     */
    private val loginRequiredRoutes = setOf(
        // 用户模块 - 需要登录的页面
        UserRoutes.PROFILE,
        UserRoutes.ADDRESS_LIST,
        UserRoutes.ADDRESS_DETAIL,

        // 订单模块 - 需要登录的页面
        OrderRoutes.LIST,
        OrderRoutes.CONFIRM,
        OrderRoutes.DETAIL,
        OrderRoutes.PAY,
        OrderRoutes.REFUND,
        OrderRoutes.COMMENT,
        OrderRoutes.LOGISTICS,

        // 客服模块 - 需要登录的页面
        CsRoutes.CHAT,

        // 营销模块 - 需要登录的页面
        MarketRoutes.COUPON,

        // 反馈模块 - 需要登录的页面
        FeedbackRoutes.LIST,
        FeedbackRoutes.SUBMIT
    )

    /**
     * 检查指定路由是否需要登录
     *
     * @param route 要检查的路由
     * @return true表示需要登录，false表示不需要登录
     * @author Joker.X
     */
    fun requiresLogin(route: String): Boolean {
        // 提取基础路由（去除参数部分）
        val baseRoute = extractBaseRoute(route)

        // 检查是否在需要登录的路由集合中
        return loginRequiredRoutes.any { requiredRoute ->
            baseRoute.startsWith(requiredRoute)
        }
    }

    /**
     * 获取登录页面路由
     *
     * @return 登录页面的路由
     * @author Joker.X
     */
    fun getLoginRoute(): String {
        return AuthRoutes.HOME
    }

    /**
     * 提取基础路由（去除参数和查询字符串）
     *
     * @param route 完整路由
     * @return 基础路由
     * @author Joker.X
     */
    private fun extractBaseRoute(route: String): String {
        // 去除查询参数
        val routeWithoutQuery = route.split("?")[0]

        // 对于带ID参数的路由，需要特殊处理
        // 例如：user/address-detail/123 -> user/address-detail
        return when {
            routeWithoutQuery.matches(Regex(".*/\\d+$")) -> {
                // 如果路由以数字结尾，去除最后的数字部分
                routeWithoutQuery.substringBeforeLast("/")
            }

            else -> routeWithoutQuery
        }
    }

    /**
     * 添加需要登录的路由
     *
     * @param route 需要登录的路由
     * @author Joker.X
     */
    fun addLoginRequiredRoute(route: String) {
        (loginRequiredRoutes as MutableSet).add(route)
    }

    /**
     * 移除需要登录的路由
     *
     * @param route 不再需要登录的路由
     * @author Joker.X
     */
    fun removeLoginRequiredRoute(route: String) {
        (loginRequiredRoutes as MutableSet).remove(route)
    }

    /**
     * 获取所有需要登录的路由
     *
     * @return 需要登录的路由集合
     * @author Joker.X
     */
    fun getLoginRequiredRoutes(): Set<String> {
        return loginRequiredRoutes.toSet()
    }
}