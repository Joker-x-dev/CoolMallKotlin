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
     * @param route 类型安全的路由对象（必须是 @Serializable）
     * @param navOptions 导航选项
     * @author Joker.X
     */
    data class NavigateTo(
        val route: Any,
        val navOptions: NavOptions? = null
    ) : NavigationEvent()

    /**
     * 返回上一页（简单返回，无结果）
     *
     * @author Joker.X
     */
    data object NavigateUp : NavigationEvent()

    /**
     * 返回上一页并携带类型安全的结果（使用 NavigationResultKey）
     *
     * @param key 类型安全的结果 Key
     * @param result 返回结果
     * @author Joker.X
     */
    data class PopBackStackWithResult<T>(
        val key: NavigationResultKey<T>,
        val result: T
    ) : NavigationEvent()

    /**
     * 返回到指定路由
     *
     * @param route 类型安全的路由对象（必须是 @Serializable）
     * @param inclusive 是否包含目标路由本身
     * @author Joker.X
     */
    data class NavigateBackTo(
        val route: Any,
        val inclusive: Boolean = false
    ) : NavigationEvent()
}
