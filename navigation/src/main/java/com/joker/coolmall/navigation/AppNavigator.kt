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
 *
 * 负责处理应用内所有的导航请求，采用事件驱动模式：
 * 1. ViewModel 通过 AppNavigator 发送导航事件
 * 2. AppNavHost 监听事件并通过 NavController 执行导航
 * 3. 实现了 ViewModel 与 NavController 的解耦
 *
 * 优势：
 * - ViewModel 无需持有 NavController 引用
 * - 支持类型安全的路由导航
 * - 便于单元测试
 * - 统一管理导航逻辑
 *
 * 使用示例：
 * ```kotlin
 * // 在 ViewModel 中
 * class MyViewModel @Inject constructor(
 *     private val navigator: AppNavigator
 * ) : ViewModel() {
 *     fun navigateToDetail() {
 *         viewModelScope.launch {
 *             navigator.navigateTo(GoodsRoutes.Detail(id = 123))
 *         }
 *     }
 * }
 * ```
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
     * @param route 类型安全的路由对象（必须是 @Serializable）
     * @param navOptions 导航选项（可选）
     *
     * 使用示例：
     * ```kotlin
     * // 简单导航
     * navigateTo(MainRoutes.Home)
     *
     * // 带参数导航
     * navigateTo(GoodsRoutes.Detail(goodsId = 123))
     *
     * // 带 NavOptions
     * navigateTo(UserRoutes.Profile, navOptions)
     * ```
     *
     * @author Joker.X
     */
    suspend fun navigateTo(route: Any, navOptions: NavOptions? = null) {
        _navigationEvents.emit(NavigationEvent.NavigateTo(route, navOptions))
    }

    /**
     * 返回上一页（简单返回，无结果）
     *
     * 使用示例：
     * ```kotlin
     * navigateBack()
     * ```
     *
     * @author Joker.X
     */
    suspend fun navigateBack() {
        _navigationEvents.emit(NavigationEvent.NavigateUp)
    }

    /**
     * 返回上一页并携带类型安全的结果（使用 NavigationResultKey）
     *
     * 这是 V3.2 版本的最终方案，实现了端到端的类型安全
     *
     * @param key 类型安全的结果 Key
     * @param result 要传递的结果对象
     *
     * 使用示例：
     * ```kotlin
     * // 1. 定义返回结果数据类型
     * @Serializable
     * data class Address(val id: Long, val fullAddress: String)
     *
     * // 2. 定义 ResultKey
     * object SelectAddressResultKey : NavigationResultKey<Address>
     *
     * // 3. 在发送方使用
     * popBackStackWithResult(SelectAddressResultKey, address)
     * ```
     *
     * 在接收方使用：
     * ```kotlin
     * navController.collectResult(SelectAddressResultKey) { address ->
     *     // address 是强类型的 Address 对象，绝对类型安全
     *     viewModel.updateAddress(address)
     * }
     * ```
     *
     * @author Joker.X
     */
    suspend fun <T> popBackStackWithResult(key: NavigationResultKey<T>, result: T) {
        _navigationEvents.emit(NavigationEvent.PopBackStackWithResult(key, result))
    }

    /**
     * 返回到指定路由
     *
     * @param route 目标路由对象（必须是 @Serializable）
     * @param inclusive 是否包含目标路由本身（true: 目标也会被弹出，false: 保留目标）
     *
     * 使用示例：
     * ```kotlin
     * // 返回到主页并保留主页
     * navigateBackTo(MainRoutes.Main, inclusive = false)
     *
     * // 返回到登录页并移除登录页（重新加载登录页）
     * navigateBackTo(AuthRoutes.Login, inclusive = true)
     * ```
     *
     * @author Joker.X
     */
    suspend fun navigateBackTo(route: Any, inclusive: Boolean = false) {
        _navigationEvents.emit(NavigationEvent.NavigateBackTo(route, inclusive))
    }
}

/**
 * 处理导航事件的 NavController 扩展函数
 *
 * 将导航事件转换为实际的导航操作
 *
 * @param event 导航事件
 * @author Joker.X
 */
fun NavController.handleNavigationEvent(event: NavigationEvent) {
    when (event) {
        is NavigationEvent.NavigateTo -> {
            // 使用类型安全的 navigate 方法
            this.navigate(event.route, event.navOptions)
        }

        is NavigationEvent.NavigateUp -> {
            // 简单返回，不携带结果
            this.popBackStack()
        }

        is NavigationEvent.PopBackStackWithResult<*> -> {
            // 使用 NavigationResultKey 的类型安全结果回传
            // 通过 key.serialize 将结果转换为 SavedStateHandle 支持的类型
            @Suppress("UNCHECKED_CAST")
            val key = event.key as NavigationResultKey<Any?>
            val rawValue = key.serialize(event.result)
            previousBackStackEntry?.savedStateHandle?.set(key.key, rawValue)
            this.popBackStack()
        }

        is NavigationEvent.NavigateBackTo -> {
            // 弹出回退栈到指定路由
            this.popBackStack(event.route, event.inclusive)
        }
    }
}
