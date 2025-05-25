package com.joker.coolmall.core.common.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.joker.coolmall.navigation.AppNavigator
import kotlinx.coroutines.launch

/**
 * 基础ViewModel
 *
 * 提供所有ViewModel通用的功能：
 * 1. 导航
 */
abstract class BaseViewModel(
    protected val navigator: AppNavigator
) : ViewModel() {

    /**
     * 导航回上一页
     */
    fun navigateBack() {
        viewModelScope.launch {
            navigator.navigateBack()
        }
    }

    /**
     * 导航回上一页并携带结果
     */
    fun navigateBack(result: Map<String, Any>) {
        viewModelScope.launch {
            navigator.navigateBack(result)
        }
    }

    /**
     * 导航到指定路由
     *
     * @param route 目标路由
     */
    fun toPage(route: String) {
        viewModelScope.launch {
            navigator.navigateTo(route)
        }
    }

    /**
     * 关闭当前页面并导航到指定路由
     * 
     * @param route 目标路由
     * @param currentRoute 当前页面路由，将被关闭
     */
    fun toPageAndCloseCurrent(route: String, currentRoute: String) {
        viewModelScope.launch {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(
                    route = currentRoute,
                    inclusive = true,  // 设为true表示当前页面也会被弹出
                    saveState = false  // 不保存状态
                )
                .build()
            navigator.navigateTo(route, navOptions)
        }
    }

    /**
     * 携带ID参数导航到指定路由
     *
     * @param route 基础路由
     * @param id ID参数值
     */
    fun toPage(route: String, id: Long) {
        toPage("${route}/$id")
    }

    /**
     * 携带ID参数导航到指定路由并关闭当前页面
     *
     * @param route 基础路由
     * @param id ID参数值
     * @param currentRoute 当前页面路由，将被关闭
     */
    fun toPageAndCloseCurrent(route: String, id: Long, currentRoute: String) {
        toPageAndCloseCurrent("${route}/$id", currentRoute)
    }

    /**
     * 携带参数导航到指定路由
     *
     * @param route 基础路由
     * @param args 参数Map
     */
    fun toPage(route: String, args: Map<String, Any>) {
        viewModelScope.launch {
            val fullRoute = buildRouteWithArgs(route, args)
            navigator.navigateTo(fullRoute)
        }
    }

    /**
     * 携带参数导航到指定路由并关闭当前页面
     *
     * @param route 基础路由
     * @param args 参数Map
     * @param currentRoute 当前页面路由，将被关闭
     */
    fun toPageAndCloseCurrent(route: String, args: Map<String, Any>, currentRoute: String) {
        viewModelScope.launch {
            val fullRoute = buildRouteWithArgs(route, args)
            val navOptions = NavOptions.Builder()
                .setPopUpTo(
                    route = currentRoute,
                    inclusive = true,  // 设为true表示当前页面也会被弹出
                    saveState = false  // 不保存状态
                )
                .build()
            navigator.navigateTo(fullRoute, navOptions)
        }
    }

    /**
     * 构建带参数的路由
     *
     * @param baseRoute 基础路由
     * @param args 参数Map
     * @return 完整路由字符串
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