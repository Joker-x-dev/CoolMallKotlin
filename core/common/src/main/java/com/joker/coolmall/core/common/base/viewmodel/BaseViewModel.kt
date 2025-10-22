package com.joker.coolmall.core.common.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.RouteInterceptor
import kotlinx.coroutines.launch

/**
 * 基础ViewModel
 *
 * 提供所有ViewModel通用的功能：
 * 1. 导航
 * 2. 路由拦截
 *
 * @param navigator 导航控制器
 * @param appState 应用状态
 * @param routeInterceptor 路由拦截器
 * @author Joker.X
 */
abstract class BaseViewModel(
    protected val navigator: AppNavigator,
    protected val appState: AppState,
    protected val routeInterceptor: RouteInterceptor = RouteInterceptor()
) : ViewModel() {

    /**
     * 导航回上一页
     *
     * @author Joker.X
     */
    fun navigateBack() {
        viewModelScope.launch {
            navigator.navigateBack()
        }
    }

    /**
     * 导航回上一页并携带结果
     *
     * @param result 返回结果数据
     * @author Joker.X
     */
    fun navigateBack(result: Map<String, Any>) {
        viewModelScope.launch {
            navigator.navigateBack(result)
        }
    }

    /**
     * 导航到指定路由
     * 自动处理登录拦截逻辑
     *
     * @param route 目标路由
     * @author Joker.X
     */
    fun toPage(route: String) {
        viewModelScope.launch {
            val targetRoute = checkRouteInterception(route)
            navigator.navigateTo(targetRoute)
        }
    }

    /**
     * 关闭当前页面并导航到指定路由
     * 自动处理登录拦截逻辑
     *
     * @param route 目标路由
     * @param currentRoute 当前页面路由，将被关闭
     * @author Joker.X
     */
    fun toPageAndCloseCurrent(route: String, currentRoute: String) {
        viewModelScope.launch {
            val targetRoute = checkRouteInterception(route)
            val navOptions = NavOptions.Builder()
                .setPopUpTo(
                    route = currentRoute,
                    inclusive = true,  // 设为true表示当前页面也会被弹出
                    saveState = false  // 不保存状态
                )
                .build()
            navigator.navigateTo(targetRoute, navOptions)
        }
    }

    /**
     * 携带ID参数导航到指定路由
     * 自动处理登录拦截逻辑
     *
     * @param route 基础路由
     * @param id ID参数值
     * @author Joker.X
     */
    fun toPage(route: String, id: Long) {
        toPage("${route}/$id")
    }

    /**
     * 携带ID参数导航到指定路由并关闭当前页面
     * 自动处理登录拦截逻辑
     *
     * @param route 基础路由
     * @param id ID参数值
     * @param currentRoute 当前页面路由，将被关闭
     * @author Joker.X
     */
    fun toPageAndCloseCurrent(route: String, id: Long, currentRoute: String) {
        toPageAndCloseCurrent("${route}/$id", currentRoute)
    }

    /**
     * 携带参数导航到指定路由
     * 自动处理登录拦截逻辑
     *
     * @param route 基础路由
     * @param args 参数Map
     * @author Joker.X
     */
    fun toPage(route: String, args: Map<String, Any>) {
        viewModelScope.launch {
            val fullRoute = buildRouteWithArgs(route, args)
            val targetRoute = checkRouteInterception(fullRoute)
            navigator.navigateTo(targetRoute)
        }
    }

    /**
     * 携带参数导航到指定路由并关闭当前页面
     * 自动处理登录拦截逻辑
     *
     * @param route 基础路由
     * @param args 参数Map
     * @param currentRoute 当前页面路由，将被关闭
     * @author Joker.X
     */
    fun toPageAndCloseCurrent(route: String, args: Map<String, Any>, currentRoute: String) {
        viewModelScope.launch {
            val fullRoute = buildRouteWithArgs(route, args)
            val targetRoute = checkRouteInterception(fullRoute)
            val navOptions = NavOptions.Builder()
                .setPopUpTo(
                    route = currentRoute,
                    inclusive = true,  // 设为true表示当前页面也会被弹出
                    saveState = false  // 不保存状态
                )
                .build()
            navigator.navigateTo(targetRoute, navOptions)
        }
    }

    /**
     * 检查路由是否需要登录拦截
     *
     * @param route 目标路由
     * @return 如果需要拦截返回登录页面路由，否则返回原路由
     * @author Joker.X
     */
    private fun checkRouteInterception(route: String): String {
        return if (routeInterceptor.requiresLogin(route) && !appState.isLoggedIn.value) {
            // 需要登录但未登录，跳转到登录页面
            routeInterceptor.getLoginRoute()
        } else {
            // 不需要登录或已登录，正常跳转
            route
        }
    }

    /**
     * 构建带参数的路由
     *
     * @param baseRoute 基础路由
     * @param args 参数Map
     * @return 完整路由字符串
     * @author Joker.X
     */
    private fun buildRouteWithArgs(baseRoute: String, args: Map<String, Any>): String {
        if (args.isEmpty()) return baseRoute

        val argString = args.entries.joinToString("&") { (key, value) ->
            "$key=${value.toString().replace(" ", "%20")}"
        }

        return if (baseRoute.contains("?")) {
            // 路由已经有参数
            "$baseRoute&$argString"
        } else {
            // 路由没有参数
            "$baseRoute?$argString"
        }
    }
}