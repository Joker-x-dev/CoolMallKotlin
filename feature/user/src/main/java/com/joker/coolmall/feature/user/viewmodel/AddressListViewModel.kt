package com.joker.coolmall.feature.user.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.feature.user.navigation.AddressDetailRoutes
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.UserRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 收货地址列表 ViewModel
 */
@HiltViewModel
class AddressListViewModel @Inject constructor(
    navigator: AppNavigator
) : BaseViewModel(
    navigator = navigator
) {
    /**
     * 跳转到收货地址详情 - 新增模式
     */
    fun toAddressDetailPage() {
        val args = mapOf(
            AddressDetailRoutes.IS_EDIT_MODE_ARG to false,
            AddressDetailRoutes.ADDRESS_ID_ARG to ""
        )
        super.toPage(UserRoutes.ADDRESS_DETAIL, args)
    }
    
    /**
     * 跳转到收货地址详情 - 编辑模式
     * 
     * @param addressId 待编辑的地址ID
     */
    fun toAddressDetailEditPage(addressId: Long) {
        val args = mapOf(
            AddressDetailRoutes.IS_EDIT_MODE_ARG to true,
            AddressDetailRoutes.ADDRESS_ID_ARG to addressId.toString()
        )
        super.toPage(UserRoutes.ADDRESS_DETAIL, args)
    }
}