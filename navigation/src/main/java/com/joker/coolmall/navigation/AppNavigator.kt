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
 */
@Singleton
class AppNavigator @Inject constructor() {
    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents: SharedFlow<NavigationEvent> = _navigationEvents.asSharedFlow()

    /**
     * 导航到指定路由
     */
    suspend fun navigateTo(route: String, navOptions: NavOptions? = null) {
        _navigationEvents.emit(NavigationEvent.NavigateTo(route, navOptions))
    }

    /**
     * 返回上一页
     */
    suspend fun navigateBack() {
        _navigationEvents.emit(NavigationEvent.NavigateBack)
    }

    /**
     * 返回到指定路由
     */
    suspend fun navigateBackTo(route: String, inclusive: Boolean = false) {
        _navigationEvents.emit(NavigationEvent.NavigateBackTo(route, inclusive))
    }
}

/**
 * 处理导航事件的NavController扩展函数
 */
fun NavController.handleNavigationEvent(event: NavigationEvent) {
    when (event) {
        is NavigationEvent.NavigateTo -> {
            this.navigate(event.route, event.navOptions)
        }

        is NavigationEvent.NavigateBack -> {
            this.popBackStack()
        }

        is NavigationEvent.NavigateBackTo -> {
            this.popBackStack(event.route, event.inclusive)
        }
    }
} 