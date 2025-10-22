package com.joker.coolmall.navigation

import androidx.navigation.NavOptions

/**
 * 导航事件
 * 定义所有可能的导航操作类型
 *
 * @author Joker.X
 */
sealed class NavigationEvent {
    /**
     * 导航到指定路由
     *
     * @param route 目标路由
     * @param navOptions 导航选项
     * @author Joker.X
     */
    data class NavigateTo(
        val route: String,
        val navOptions: NavOptions? = null
    ) : NavigationEvent()

    /**
     * 返回上一页
     *
     * @param result 返回结果，可用于传递数据给上一页
     * @author Joker.X
     */
    data class NavigateBack(
        val result: Map<String, Any>? = null
    ) : NavigationEvent()

    /**
     * 返回到指定路由
     *
     * @param route 目标路由
     * @param inclusive 是否包含目标路由本身
     * @author Joker.X
     */
    data class NavigateBackTo(
        val route: String,
        val inclusive: Boolean = false
    ) : NavigationEvent()
}