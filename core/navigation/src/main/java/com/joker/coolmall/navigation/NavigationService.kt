package com.joker.coolmall.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.Flow

/**
 * 全局导航服务
 *
 * 统一维护当前可用导航器，并对外提供通用导航能力。
 * 业务层只需调用本文件提供的简写函数，无需关注底层绑定细节。
 *
 * @author Joker.X
 */
object NavigationService {
    /**
     * 当前导航器实例
     */
    @Volatile
    private var navigator: AppNavigator? = null

    /**
     * 绑定导航器
     *
     * @param appNavigator 待绑定的导航器
     * @author Joker.X
     */
    fun bind(appNavigator: AppNavigator) {
        navigator = appNavigator
    }

    /**
     * 解绑导航器
     *
     * @param appNavigator 待解绑的导航器
     * @author Joker.X
     */
    fun unbind(appNavigator: AppNavigator) {
        if (navigator === appNavigator) {
            navigator = null
        }
    }

    /**
     * 获取当前导航器
     *
     * @return 当前导航器实例
     * @throws IllegalStateException 当导航器未绑定时抛出异常
     * @author Joker.X
     */
    fun requireNavigator(): AppNavigator {
        return navigator ?: error("AppNavigator is not bound")
    }

    /**
     * 跳转到目标路由
     *
     * @param route 目标路由
     * @param navOptions 导航选项
     * @author Joker.X
     */
    fun navigate(route: NavKey, navOptions: NavigationOptions? = null) {
        requireNavigator().navigateTo(route = route, navOptions = navOptions)
    }

    /**
     * 跳转到目标路由并关闭当前页面
     *
     * 用于“当前页完成后彻底退出当前流程”的场景（例如 Splash/Guide 完成后进入主页）。
     * 即使当前页是栈底，也会被移除，返回时不会再回到该页面。
     *
     * @param route 目标路由
     * @param currentRoute 当前路由（将被关闭）
     * @author Joker.X
     */
    fun navigateAndCloseCurrent(route: NavKey, currentRoute: NavKey) {
        val navOptions = NavigationOptions(
            popUpToRoute = currentRoute,
            inclusive = true,
            allowPopToEmpty = true,
        )
        requireNavigator().navigateTo(route = route, navOptions = navOptions)
    }

    /**
     * 跳转到目标路由并按条件清理回退栈
     *
     * @param route 目标路由
     * @param popUpToRoute 回退栈清理到的目标路由
     * @param inclusive 是否包含 [popUpToRoute]
     * @author Joker.X
     */
    fun navigateWithPopUpTo(route: NavKey, popUpToRoute: NavKey, inclusive: Boolean = false) {
        val navOptions = NavigationOptions(
            popUpToRoute = popUpToRoute,
            inclusive = inclusive,
        )
        requireNavigator().navigateTo(route = route, navOptions = navOptions)
    }

    /**
     * 返回上一页
     *
     * @author Joker.X
     */
    fun navigateBack() {
        requireNavigator().navigateBack()
    }

    /**
     * 返回到指定路由
     *
     * @param route 目标路由
     * @param inclusive 是否包含目标路由
     * @author Joker.X
     */
    fun navigateBackTo(route: NavKey, inclusive: Boolean = false) {
        requireNavigator().navigateBackTo(route = route, inclusive = inclusive)
    }

    /**
     * 返回上一页并携带结果
     *
     * @param key 结果 Key
     * @param result 返回结果
     * @author Joker.X
     */
    fun <T> popBackStackWithResult(key: NavigationResultKey<T>, result: T) {
        requireNavigator().popBackStackWithResult(key = key, result = result)
    }

    /**
     * 返回上一页并携带结果（语义化别名）
     *
     * @param key 结果 Key
     * @param result 返回结果
     * @author Joker.X
     */
    fun <T> navigateBackWithResult(key: NavigationResultKey<T>, result: T) {
        popBackStackWithResult(key = key, result = result)
    }

    /**
     * 监听指定结果 Key 的结果流
     *
     * @param key 结果 Key
     * @return 对应结果流
     * @author Joker.X
     */
    fun <T> resultEvents(key: NavigationResultKey<T>): Flow<T> {
        return requireNavigator().resultEvents(key)
    }
}

/**
 * 跳转到目标路由
 *
 * @param route 目标路由
 * @param navOptions 导航选项
 * @author Joker.X
 */
fun navigate(route: NavKey, navOptions: NavigationOptions? = null) {
    NavigationService.navigate(route = route, navOptions = navOptions)
}

/**
 * 跳转到目标路由并关闭当前页面
 *
 * @param route 目标路由
 * @param currentRoute 当前路由（将被关闭）
 * @author Joker.X
 */
fun navigateAndCloseCurrent(route: NavKey, currentRoute: NavKey) {
    NavigationService.navigateAndCloseCurrent(route = route, currentRoute = currentRoute)
}

/**
 * 跳转到目标路由并按条件清理回退栈
 *
 * @param route 目标路由
 * @param popUpToRoute 回退栈清理到的目标路由
 * @param inclusive 是否包含 [popUpToRoute]
 * @author Joker.X
 */
fun navigateWithPopUpTo(route: NavKey, popUpToRoute: NavKey, inclusive: Boolean = false) {
    NavigationService.navigateWithPopUpTo(
        route = route,
        popUpToRoute = popUpToRoute,
        inclusive = inclusive,
    )
}

/**
 * 返回上一页
 *
 * @author Joker.X
 */
fun navigateBack() {
    NavigationService.navigateBack()
}

/**
 * 返回到指定路由
 *
 * @param route 目标路由
 * @param inclusive 是否包含目标路由
 * @author Joker.X
 */
fun navigateBackTo(route: NavKey, inclusive: Boolean = false) {
    NavigationService.navigateBackTo(route = route, inclusive = inclusive)
}

/**
 * 返回上一页并携带结果
 *
 * @param key 结果 Key
 * @param result 返回结果
 * @author Joker.X
 */
fun <T> popBackStackWithResult(key: NavigationResultKey<T>, result: T) {
    NavigationService.popBackStackWithResult(key = key, result = result)
}

/**
 * 返回上一页并携带结果（语义化别名）
 *
 * @param key 结果 Key
 * @param result 返回结果
 * @author Joker.X
 */
fun <T> navigateBackWithResult(key: NavigationResultKey<T>, result: T) {
    NavigationService.navigateBackWithResult(key = key, result = result)
}

/**
 * 监听指定结果 Key 的结果流
 *
 * @param key 结果 Key
 * @return 对应结果流
 * @author Joker.X
 */
fun <T> resultEvents(key: NavigationResultKey<T>): Flow<T> {
    return NavigationService.resultEvents(key)
}
