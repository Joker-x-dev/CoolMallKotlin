package com.joker.coolmall.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 导航管理器
 * 负责处理应用内所有的导航请求
 *
 * @author Joker.X
 */
@Singleton
class AppNavigator @Inject constructor() {
    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents: SharedFlow<NavigationEvent> = _navigationEvents.asSharedFlow()

    /**
     * 导航到指定路由
     *
     * @param route 目标路由
     * @param navOptions 导航选项
     * @author Joker.X
     */
    suspend fun navigateTo(route: String, navOptions: NavOptions? = null) {
        _navigationEvents.emit(NavigationEvent.NavigateTo(route, navOptions))
    }

    /**
     * 返回上一页
     *
     * @author Joker.X
     */
    suspend fun navigateBack() {
        _navigationEvents.emit(NavigationEvent.NavigateBack())
    }

    /**
     * 返回上一页并携带结果
     *
     * @param result 返回结果数据
     * @author Joker.X
     */
    suspend fun navigateBack(result: Map<String, Any>) {
        _navigationEvents.emit(NavigationEvent.NavigateBack(result))
    }

    /**
     * 返回到指定路由
     *
     * @param route 目标路由
     * @param inclusive 是否包含目标路由本身
     * @author Joker.X
     */
    suspend fun navigateBackTo(route: String, inclusive: Boolean = false) {
        _navigationEvents.emit(NavigationEvent.NavigateBackTo(route, inclusive))
    }
}

/**
 * 处理导航事件的NavController扩展函数
 *
 * @param event 导航事件
 * @author Joker.X
 */
fun NavController.handleNavigationEvent(event: NavigationEvent) {
    when (event) {
        is NavigationEvent.NavigateTo -> {
            this.navigate(event.route, event.navOptions)
        }

        is NavigationEvent.NavigateBack -> {
            // 在返回前保存结果
            event.result?.let { result ->
                previousBackStackEntry?.savedStateHandle?.apply {
                    result.forEach { (key, value) ->
                        set(key, value)
                    }
                }
            }
            this.popBackStack()
        }

        is NavigationEvent.NavigateBackTo -> {
            this.popBackStack(event.route, event.inclusive)
        }
    }
} 