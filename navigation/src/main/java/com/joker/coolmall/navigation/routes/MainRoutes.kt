package com.joker.coolmall.navigation.routes

import kotlinx.serialization.Serializable

/**
 * 主模块路由
 *
 * @author Joker.X
 */
object MainRoutes {
    /**
     * 主框架路由
     *
     * 应用的主框架，包含底部导航栏
     *
     * @author Joker.X
     */
    @Serializable
    data object Main

    /**
     * 首页路由
     *
     * 应用首页，展示推荐商品和营销活动
     *
     * @author Joker.X
     */
    @Serializable
    data object Home

    /**
     * 分类页面路由
     *
     * 商品分类浏览页面
     *
     * @author Joker.X
     */
    @Serializable
    data object Category

    /**
     * 购物车页面路由
     *
     * 购物车列表页面
     *
     * @param showBackIcon 是否显示返回按钮，默认为 false
     * @author Joker.X
     */
    @Serializable
    data class Cart(val showBackIcon: Boolean = false)

    /**
     * 我的页面路由
     *
     * 个人中心页面
     *
     * @author Joker.X
     */
    @Serializable
    data object Mine
}
