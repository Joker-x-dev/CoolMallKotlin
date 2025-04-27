package com.joker.coolmall.feature.user.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.AddressRepository
import com.joker.coolmall.core.model.Address
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.feature.user.navigation.AddressDetailRoutes
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 收货地址详情ViewModel
 */
@HiltViewModel
class AddressDetailViewModel @Inject constructor(
    navigator: AppNavigator,
    savedStateHandle: SavedStateHandle,
    private val addressRepository: AddressRepository
) : BaseNetWorkViewModel<Address>(
    navigator = navigator,
    savedStateHandle = savedStateHandle,
    idKey = AddressDetailRoutes.ADDRESS_ID_ARG
) {
    // 是否为编辑模式
    val isEditMode: Boolean =
        savedStateHandle.get<Boolean>(AddressDetailRoutes.IS_EDIT_MODE_ARG) ?: false

    // 直接使用父类提供的 id
    val addressId: Long? = if (isEditMode) {
        // 从父类中获取 id
        super.id
    } else null

    init {
        // 如果是编辑模式且地址ID有效，则执行请求
        if (isEditMode && addressId != null) {
            super.executeRequest()
        }
    }

    /**
     * 通过重写来给父类提供API请求的Flow
     * 根据地址ID获取地址详情
     */
    override fun requestApiFlow(): Flow<NetworkResponse<Address>> {
        // 此处使用父类提供的 requiredId
        return addressRepository.getAddressInfo(requiredId)
    }

    /**
     * 保存地址信息
     * 根据是否为编辑模式调用不同的保存逻辑
     */
    fun saveAddress() {
        // 保存成功后返回上一页
        navigateBack()
    }
}