package com.joker.coolmall.navigation.routes

import kotlinx.serialization.Serializable

/**
 * 用户模块路由
 *
 * @author Joker.X
 */
object UserRoutes {
    /**
     * 个人中心路由
     *
     * @author Joker.X
     */
    @Serializable
    data object Profile

    /**
     * 收货地址列表路由
     *
     * @param isSelectMode 是否为选择模式（选择后返回地址）
     * @author Joker.X
     */
    @Serializable
    data class AddressList(val isSelectMode: Boolean = false)

    /**
     * 收货地址详情/编辑路由
     *
     * @param isEditMode 是否为编辑模式
     * @param addressId 地址ID（0表示新增）
     * @author Joker.X
     */
    @Serializable
    data class AddressDetail(
        val isEditMode: Boolean = false,
        val addressId: Long = 0L
    )

    /**
     * 用户足迹路由
     *
     * @author Joker.X
     */
    @Serializable
    data object Footprint
}
