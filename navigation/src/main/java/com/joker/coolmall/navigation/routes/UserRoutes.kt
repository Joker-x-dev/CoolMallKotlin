package com.joker.coolmall.navigation.routes

/**
 * 用户模块路由常量
 *
 * @author Joker.X
 */
object UserRoutes {
    /**
     * 用户模块根路由
     */
    private const val USER_ROUTE = "user"

    /**
     * 个人中心路由
     */
    const val PROFILE = "$USER_ROUTE/profile"

    /**
     * 收货地址列表路由
     */
    const val ADDRESS_LIST = "$USER_ROUTE/address-list"

    /**
     * 收货地址详情路由
     */
    const val ADDRESS_DETAIL = "$USER_ROUTE/address-detail"

    /**
     * 用户足迹路由
     */
    const val FOOTPRINT = "$USER_ROUTE/footprint"
}