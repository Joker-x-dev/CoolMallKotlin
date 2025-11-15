package com.joker.coolmall.feature.order.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.joker.coolmall.navigation.NavigationResultKey

/**
 * 导航结果监听扩展函数
 * 
 * 用于监听从其他页面返回的结果数据，基于 [NavigationResultKey] 实现类型安全。
 * 
 * 使用示例：
 * ```kotlin
 * @Composable
 * fun OrderConfirmRoute(
 *     navController: NavController,
 *     viewModel: OrderConfirmViewModel = hiltViewModel()
 * ) {
 *     // 监听地址选择结果
 *     navController.observeResult(SelectAddressResultKey) { address ->
 *         viewModel.onAddressSelected(address)
 *     }
 * }
 * ```
 *
 * 工作原理：
 * - 通过 [DisposableEffect] 监听页面生命周期
 * - 当页面恢复（ON_RESUME）时，检查 SavedStateHandle 中是否有返回结果
 * - 如果有结果，则调用回调函数并自动清除结果，避免重复消费
 *
 * @param T 结果数据类型
 * @param key 类型安全的导航结果 Key，定义在 navigation 模块的 results 包中
 * @param onResult 收到结果后的回调函数
 * @author Joker.X
 */
@Composable
fun <T> NavController.observeResult(
    key: NavigationResultKey<T>,
    onResult: (T) -> Unit
) {
    val backStackEntry = currentBackStackEntry ?: return
    val savedStateHandle = backStackEntry.savedStateHandle

    DisposableEffect(backStackEntry, key) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val raw = savedStateHandle.get<Any?>(key.key)
                if (raw != null) {
                    @Suppress("UNCHECKED_CAST")
                    val result = key.deserialize(raw)
                    onResult(result)
                    savedStateHandle.remove<Any?>(key.key)
                }
            }
        }
        backStackEntry.lifecycle.addObserver(observer)
        onDispose {
            backStackEntry.lifecycle.removeObserver(observer)
        }
    }
}
