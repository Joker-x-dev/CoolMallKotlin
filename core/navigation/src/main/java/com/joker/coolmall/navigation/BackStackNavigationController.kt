package com.joker.coolmall.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

/**
 * 创建 BackStack 导航控制器
 *
 * @param backStack 回退栈
 * @param navigator 导航管理器
 * @return BackStack 导航控制器
 * @author Joker.X
 */
fun createBackStackNavigationController(
    backStack: NavBackStack<NavKey>,
    navigator: AppNavigator,
): NavigationController {
    return BackStackNavigationController(backStack = backStack, navigator = navigator)
}

/**
 * 基于 BackStack 的导航控制器实现
 *
 * @author Joker.X
 */
private class BackStackNavigationController(
    /**
     * 回退栈
     */
    private val backStack: NavBackStack<NavKey>,
    /**
     * 导航管理器
     */
    private val navigator: AppNavigator,
) : NavigationController {

    /**
     * 导航到目标页面
     *
     * @param route 目标路由
     * @param navOptions 导航选项
     * @author Joker.X
     */
    override fun navigateTo(route: NavKey, navOptions: NavigationOptions?) {
        val popUpToRoute = navOptions?.popUpToRoute
        if (popUpToRoute != null) {
            backStack.popUpTo(
                route = popUpToRoute,
                inclusive = navOptions.inclusive,
                allowPopToEmpty = navOptions.allowPopToEmpty,
            )
        }
        backStack.add(route)
    }

    /**
     * 返回上一页
     *
     * @author Joker.X
     */
    override fun navigateBack() {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }

    /**
     * 返回到指定页面
     *
     * @param route 目标路由
     * @param inclusive 是否包含目标路由
     * @author Joker.X
     */
    override fun navigateBackTo(route: NavKey, inclusive: Boolean) {
        backStack.popUpTo(route = route, inclusive = inclusive)
    }

    /**
     * 回退并携带结果
     *
     * @param key 结果 Key
     * @param result 回传结果
     * @author Joker.X
     */
    override fun <T> popBackStackWithResult(key: NavigationResultKey<T>, result: T) {
        navigator.dispatchResult(key = key, result = result)
        navigateBack()
    }
}

/**
 * BackStack 按路由弹栈
 *
 * @param route 目标路由
 * @param inclusive 是否包含目标路由
 * @param allowPopToEmpty 当目标路由是栈底时，是否允许清空整个返回栈
 * @author Joker.X
 */
private fun NavBackStack<NavKey>.popUpTo(
    route: NavKey,
    inclusive: Boolean,
    allowPopToEmpty: Boolean = false,
) {
    val targetIndex = indexOfLast { it == route }
    if (targetIndex == -1) return

    val removeFromIndex = if (inclusive) targetIndex else targetIndex + 1
    if (removeFromIndex >= size) return

    if (removeFromIndex == 0) {
        if (allowPopToEmpty) {
            clear()
        } else if (size > 1) {
            subList(1, size).clear()
        }
        return
    }

    subList(removeFromIndex, size).clear()
}
