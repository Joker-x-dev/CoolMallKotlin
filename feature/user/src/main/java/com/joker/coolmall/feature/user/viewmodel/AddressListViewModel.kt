package com.joker.coolmall.feature.user.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.AddressRepository
import com.joker.coolmall.core.model.Address
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.feature.user.navigation.AddressDetailRoutes
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.UserRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 收货地址列表 ViewModel
 */
@HiltViewModel
class AddressListViewModel @Inject constructor(
    navigator: AppNavigator,
    private val addressRepository: AddressRepository
) : BaseNetWorkViewModel<List<Address>>(
    navigator = navigator
) {

    init {
        super.executeRequest()
    }

    /**
     * 通过重写来给父类提供API请求的Flow
     */
    override fun requestApiFlow(): Flow<NetworkResponse<List<Address>>> {
        return addressRepository.getAddressList()
    }

    /**
     * 跳转到收货地址详情 - 新增模式
     */
    fun toAddressDetailPage() {
        val args = mapOf(
            AddressDetailRoutes.IS_EDIT_MODE_ARG to false, AddressDetailRoutes.ADDRESS_ID_ARG to ""
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