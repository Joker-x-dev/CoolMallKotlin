package com.joker.coolmall.core.navigation.user

import com.joker.coolmall.navigation.navigate

/**
 * 用户模块导航封装
 *
 * @author Joker.X
 */
object UserNavigator {

    /**
     * 跳转到个人中心页
     *
     * @author Joker.X
     */
    fun toProfile() {
        navigate(UserRoutes.Profile)
    }

    /**
     * 跳转到地址列表页
     *
     * @param isSelectMode 是否为选择模式
     * @author Joker.X
     */
    fun toAddressList(isSelectMode: Boolean = false) {
        navigate(UserRoutes.AddressList(isSelectMode = isSelectMode))
    }

    /**
     * 跳转到地址详情页
     *
     * @param isEditMode 是否编辑模式
     * @param addressId 地址ID
     * @author Joker.X
     */
    fun toAddressDetail(isEditMode: Boolean = false, addressId: Long = 0L) {
        navigate(
            UserRoutes.AddressDetail(
                isEditMode = isEditMode,
                addressId = addressId,
            )
        )
    }

    /**
     * 跳转到足迹页
     *
     * @author Joker.X
     */
    fun toFootprint() {
        navigate(UserRoutes.Footprint)
    }
}
