package com.joker.coolmall.navigation.results

import com.joker.coolmall.core.model.entity.Address
import com.joker.coolmall.navigation.NavigationResultKey
import kotlinx.serialization.json.Json

/**
 * 地址选择结果的类型安全 NavigationResultKey
 *
 * 用于在地址选择页面和调用页面之间传递选中的地址对象。
 *
 * 使用示例：
 * ```kotlin
 * // 1. 发起地址选择页面
 * navigate(UserRoutes.AddressList(isSelectMode = true))
 *
 * // 2. 在地址列表页面中返回选中的地址
 * popBackStackWithResult(SelectAddressResultKey, selectedAddress)
 *
 * // 3. 在调用页面中接收地址结果
 * navController.collectResult(SelectAddressResultKey) { address ->
 *     viewModel.onAddressSelected(address)
 * }
 * ```
 *
 * @author Joker.X
 */
object SelectAddressResultKey : NavigationResultKey<Address> {
    /**
     * 序列化：将 Address 对象转为 JSON 字符串
     *
     * 避免 "Can't put value with type Address into saved state" 异常
     */
    override fun serialize(value: Address): Any {
        return Json.encodeToString(value)
    }

    /**
     * 反序列化：将 JSON 字符串还原为 Address 对象
     */
    override fun deserialize(raw: Any): Address {
        return Json.decodeFromString(raw as String)
    }
}
