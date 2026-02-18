package com.joker.coolmall.navigation

import androidx.navigation3.runtime.NavKey

/**
 * 导航控制器接口
 *
 * 由 AppNavHost 提供 BackStack 实现。
 *
 * @author Joker.X
 */
interface NavigationController {
    /**
     * 导航到目标路由
     *
     * @param route 目标路由
     * @param navOptions 导航选项
     * @author Joker.X
     */
    fun navigateTo(route: NavKey, navOptions: NavigationOptions?)

    /**
     * 返回上一页
     *
     * @author Joker.X
     */
    fun navigateBack()

    /**
     * 回退到指定路由
     *
     * @param route 目标路由
     * @param inclusive 是否包含目标路由
     * @author Joker.X
     */
    fun navigateBackTo(route: NavKey, inclusive: Boolean)

    /**
     * 回退并携带结果
     *
     * @param key 结果 Key
     * @param result 结果值
     * @author Joker.X
     */
    fun <T> popBackStackWithResult(key: NavigationResultKey<T>, result: T)
}
