package com.joker.coolmall.core.common.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.navigation.AppNavigator
import kotlinx.coroutines.launch

/**
 * 基础ViewModel
 *
 * 提供所有ViewModel通用的功能：
 * 1. 导航
 * 2. 基础UI状态管理
 * 3. 错误处理
 *
 * @param T 数据类型
 */
abstract class BaseViewModel<T> constructor(
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
     * 导航到指定路由
     *
     * @param route 目标路由
     */
    fun toPge(route: String) {
        viewModelScope.launch {
            navigator.navigateTo(route)
        }
    }

    /**
     * 携带ID参数导航到指定路由
     *
     * @param route 基础路由
     * @param id ID参数值
     */
    fun toPge(route: String, id: Long) {
        toPge("${route}/$id")
    }

    /**
     * 携带参数导航到指定路由
     *
     * @param route 基础路由
     * @param args 参数Map
     */
    fun toPge(route: String, args: Map<String, Any>) {
        viewModelScope.launch {
            val fullRoute = buildRouteWithArgs(route, args)
            navigator.navigateTo(fullRoute)
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