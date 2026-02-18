package com.joker.coolmall.navigation.main

import androidx.navigation3.runtime.NavKey
import com.joker.coolmall.navigation.navigate
import com.joker.coolmall.navigation.navigateAndCloseCurrent

/**
 * 主模块导航封装
 *
 * @author Joker.X
 */
object MainNavigator {

    /**
     * 跳转到主框架页
     *
     * @author Joker.X
     */
    fun toMain() {
        navigate(MainRoutes.Main)
    }

    /**
     * 关闭当前页并跳转到主框架页
     *
     * @param currentRoute 当前路由
     * @author Joker.X
     */
    fun toMainAndCloseCurrent(currentRoute: NavKey) {
        navigateAndCloseCurrent(
            route = MainRoutes.Main,
            currentRoute = currentRoute,
        )
    }

    /**
     * 跳转到首页
     *
     * @author Joker.X
     */
    fun toHome() {
        navigate(MainRoutes.Home)
    }

    /**
     * 跳转到分类页
     *
     * @author Joker.X
     */
    fun toCategory() {
        navigate(MainRoutes.Category)
    }

    /**
     * 跳转到购物车页
     *
     * @param showBackIcon 是否显示返回按钮
     * @author Joker.X
     */
    fun toCart(showBackIcon: Boolean = false) {
        navigate(MainRoutes.Cart(showBackIcon = showBackIcon))
    }

    /**
     * 跳转到我的页
     *
     * @author Joker.X
     */
    fun toMine() {
        navigate(MainRoutes.Mine)
    }
}
