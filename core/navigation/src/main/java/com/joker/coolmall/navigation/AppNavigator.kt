package com.joker.coolmall.core.navigation

import androidx.navigation3.runtime.NavKey
import com.joker.coolmall.core.data.state.AppState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 导航管理器
 *
 * 提供给 ViewModel 的统一导航入口：
 * 1. ViewModel 直接调用导航 API
 * 2. AppNavHost 注册 BackStack 控制器
 * 3. 导航结果通过 resultEvents 分发
 *
 * @author Joker.X
 */
@Singleton
class AppNavigator @Inject constructor(
    private val appState: AppState,
) {
    /**
     * 导航控制器访问锁
     */
    private val lock = Any()

    /**
     * 当前活跃的导航控制器
     */
    private var controller: NavigationController? = null

    /**
     * 控制器未注册时缓存的导航命令队列
     */
    private val pendingCommands = ArrayDeque<NavigationCommand>()

    /**
     * 页面结果事件流
     */
    private val _resultEvents = MutableSharedFlow<ResultEvent>(extraBufferCapacity = 32)

    /**
     * 路由拦截器
     */
    private val routeInterceptor: RouteInterceptor = RouteInterceptor()

    /**
     * 注册导航控制器
     *
     * @author Joker.X
     */
    fun attachController(navigationController: NavigationController) {
        synchronized(lock) {
            controller = navigationController
            while (pendingCommands.isNotEmpty()) {
                pendingCommands.removeFirst().execute(navigationController)
            }
        }
    }

    /**
     * 注销导航控制器
     *
     * @author Joker.X
     */
    fun detachController(navigationController: NavigationController) {
        synchronized(lock) {
            if (controller === navigationController) {
                controller = null
            }
        }
    }

    /**
     * 导航到指定路由
     *
     * @param route 类型安全路由对象
     * @param navOptions 导航选项（可选）
     * @author Joker.X
     */
    fun navigateTo(route: NavKey, navOptions: NavigationOptions? = null) {
        val targetRoute = resolveTargetRoute(route)
        executeOrEnqueue(NavigationCommand.NavigateTo(targetRoute, navOptions))
    }

    /**
     * 返回上一页
     *
     * @author Joker.X
     */
    fun navigateBack() {
        executeOrEnqueue(NavigationCommand.NavigateUp)
    }

    /**
     * 返回上一页并携带类型安全结果
     *
     * @param key 类型安全结果 Key
     * @param result 返回结果
     * @author Joker.X
     */
    fun <T> popBackStackWithResult(key: NavigationResultKey<T>, result: T) {
        executeOrEnqueue(NavigationCommand.PopBackStackWithResult(key, result))
    }

    /**
     * 返回到指定路由
     *
     * @param route 目标路由对象
     * @param inclusive 是否包含目标路由
     * @author Joker.X
     */
    fun navigateBackTo(route: NavKey, inclusive: Boolean = false) {
        executeOrEnqueue(NavigationCommand.NavigateBackTo(route, inclusive))
    }

    /**
     * 监听某个 ResultKey 对应的结果流
     *
     * @param key 类型安全结果 Key
     * @return 强类型结果流
     * @author Joker.X
     */
    fun <T> resultEvents(key: NavigationResultKey<T>): Flow<T> {
        return _resultEvents
            .filter { it.key == key.key }
            .map { key.deserialize(it.rawValue) }
    }

    /**
     * 分发回传结果事件
     *
     * @param key 类型安全结果 Key
     * @param result 返回结果
     * @author Joker.X
     */
    internal fun <T> dispatchResult(key: NavigationResultKey<T>, result: T) {
        val rawValue = key.serialize(result)
        _resultEvents.tryEmit(ResultEvent(key = key.key, rawValue = rawValue))
    }

    /**
     * 执行导航命令（若控制器未注册则先缓存）
     *
     * @author Joker.X
     */
    private fun executeOrEnqueue(command: NavigationCommand) {
        synchronized(lock) {
            val currentController = controller
            if (currentController != null) {
                command.execute(currentController)
            } else {
                pendingCommands.addLast(command)
            }
        }
    }

    /**
     * 解析最终跳转路由
     *
     * 当目标路由需要登录且当前未登录时，返回登录页面路由。
     *
     * @param route 原始目标路由
     * @return 实际执行跳转的路由
     * @author Joker.X
     */
    private fun resolveTargetRoute(route: NavKey): NavKey {
        return if (routeInterceptor.requiresLogin(route) && !appState.isLoggedIn.value) {
            routeInterceptor.getLoginRoute()
        } else {
            route
        }
    }
}

/**
 * 导航回传结果事件
 *
 * @param key 结果 Key
 * @param rawValue 序列化后的结果值
 * @author Joker.X
 */
private data class ResultEvent(
    val key: String,
    val rawValue: Any,
)
