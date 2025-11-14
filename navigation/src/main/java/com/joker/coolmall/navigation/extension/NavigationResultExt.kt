package com.joker.coolmall.navigation.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.navigation.NavController
import com.joker.coolmall.navigation.NavigationResultKey

/**
 * 使用 [NavigationResultKey] 类型安全地接收上一个页面返回的结果。
 *
 * 使用方式：
 * ```kotlin
 * @Composable
 * fun OrderConfirmRoute(navController: NavController, viewModel: OrderConfirmViewModel = hiltViewModel()) {
 *     // 监听地址选择结果
 *     navController.collectResult(SelectAddressResultKey) { address ->
 *         viewModel.onAddressSelected(address)
 *     }
 * }
 * ```
 *
 * 内部通过 SavedStateHandle.getStateFlow 实现连续监听，并在消费后自动清除结果，避免重复处理。
 *
 * @param T 结果类型
 * @param key 类型安全的导航结果 Key
 * @param onResult 收到结果后的回调
 * @author Joker.X
 */
@Composable
fun <T> NavController.CollectResult(
    key: NavigationResultKey<T>,
    onResult: (T) -> Unit
) {
    val savedStateHandle = currentBackStackEntry?.savedStateHandle ?: return

    // 使用 StateFlow 持续监听指定 key 的结果（底层类型由 NavigationResultKey 决定）
    val resultState: State<T?> = produceState(
        initialValue = null,
        key1 = savedStateHandle,
        key2 = key
    ) {
        savedStateHandle.getStateFlow<Any?>(key.key, null).collect { raw ->
            @Suppress("UNCHECKED_CAST")
            val typedKey = key
            value = raw?.let { typedKey.deserialize(it) }
        }
    }

    // 当结果变化且不为空时回调，并清除结果避免重复消费
    LaunchedEffect(resultState.value) {
        val result = resultState.value ?: return@LaunchedEffect
        onResult(result)
        savedStateHandle.remove<Any?>(key.key)
    }
}
