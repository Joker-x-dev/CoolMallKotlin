package com.joker.coolmall.feature.user.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.AddressRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Address
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.RefreshResultKey
import com.joker.coolmall.navigation.routes.UserRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * 收货地址详情ViewModel
 *
 * @author Joker.X
 */
@HiltViewModel
class AddressDetailViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    savedStateHandle: SavedStateHandle,
    private val addressRepository: AddressRepository
) : BaseNetWorkViewModel<Address>(
    navigator = navigator,
    appState = appState
) {
    // 从路由获取参数
    private val addressDetailRoute = savedStateHandle.toRoute<UserRoutes.AddressDetail>()

    // 是否编辑模式
    val isEditMode: Boolean = addressDetailRoute.isEditMode

    /**
     * 联系人
     */
    private val _contactName = MutableStateFlow("")
    val contactName: StateFlow<String> = _contactName

    /**
     * 手机号
     */
    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone

    /**
     * 省
     */
    private val _province = MutableStateFlow("")
    val province: StateFlow<String> = _province

    /**
     * 市
     */
    private val _city = MutableStateFlow("")
    val city: StateFlow<String> = _city

    /**
     * 区
     */
    private val _district = MutableStateFlow("")
    val district: StateFlow<String> = _district

    /**
     * 详细地址
     */
    private val _detailAddress = MutableStateFlow("")
    val detailAddress: StateFlow<String> = _detailAddress

    /**
     * 是否默认地址
     */
    private val _isDefaultAddress = MutableStateFlow(false)
    val isDefaultAddress: StateFlow<Boolean> = _isDefaultAddress

    init {
        // 如果是编辑模式且地址ID有效，则执行请求
        if (addressDetailRoute.isEditMode && addressDetailRoute.addressId > 0) {
            super.executeRequest()
        } else {
            // 新增模式，设置初始状态为成功
            setSuccessState(Address())
        }
    }

    /**
     * 通过重写来给父类提供API请求的Flow
     * 根据地址ID获取地址详情
     *
     * @return 网络响应的Flow
     * @author Joker.X
     */
    override fun requestApiFlow(): Flow<NetworkResponse<Address>> {
        return addressRepository.getAddressInfo(addressDetailRoute.addressId)
    }

    /**
     * 更新表单数据
     *
     * @param address 地址信息
     * @author Joker.X
     */
    private fun updateFormData(address: Address) {
        _contactName.value = address.contact
        _phone.value = address.phone
        _province.value = address.province
        _city.value = address.city
        _district.value = address.district
        _detailAddress.value = address.address
        _isDefaultAddress.value = address.isDefault
    }

    /**
     * 更新联系人
     *
     * @param value 联系人姓名
     * @author Joker.X
     */
    fun updateContactName(value: String) {
        _contactName.value = value
    }

    /**
     * 更新手机号
     *
     * @param value 手机号
     * @author Joker.X
     */
    fun updatePhone(value: String) {
        _phone.value = value
    }

    /**
     * 更新省市区
     *
     * @param province 省
     * @param city 市
     * @param district 区
     * @author Joker.X
     */
    fun updateRegion(province: String, city: String, district: String) {
        _province.value = province
        _city.value = city
        _district.value = district
    }

    /**
     * 更新详细地址
     *
     * @param value 详细地址
     * @author Joker.X
     */
    fun updateDetailAddress(value: String) {
        _detailAddress.value = value
    }

    /**
     * 更新是否默认地址
     *
     * @param value 是否默认地址
     * @author Joker.X
     */
    fun updateIsDefaultAddress(value: Boolean) {
        _isDefaultAddress.value = value
    }

    /**
     * 保存地址信息
     * 根据是否为编辑模式调用不同的保存逻辑
     *
     * @author Joker.X
     */
    fun saveAddress() {
        val address = Address(
            // 编辑模式下使用已有地址ID，新建模式下为 0
            id = if (isEditMode) addressDetailRoute.addressId else 0L,
            contact = _contactName.value,
            phone = _phone.value,
            province = _province.value,
            city = _city.value,
            district = _district.value,
            address = _detailAddress.value,
            isDefault = _isDefaultAddress.value
        )

        if (isEditMode) {
            updateAddress(address)
        } else {
            addAddress(address)
        }
    }

    /**
     * 修改地址
     *
     * @param address 地址信息
     * @author Joker.X
     */
    private fun updateAddress(address: Address) {
        ResultHandler.handleResult(
            scope = viewModelScope,
            flow = addressRepository.updateAddress(address).asResult(),
            onSuccess = { _ ->
                // 使用 NavigationResult 回传刷新信号，通知地址列表页面刷新
                super.popBackStackWithResult(RefreshResultKey, true)
            }
        )
    }

    /**
     * 新增地址
     *
     * @param address 地址信息
     * @author Joker.X
     */
    private fun addAddress(address: Address) {
        ResultHandler.handleResult(
            scope = viewModelScope,
            flow = addressRepository.addAddress(address).asResult(),
            onSuccess = { _ ->
                // 使用 NavigationResult 回传刷新信号，通知地址列表页面刷新
                super.popBackStackWithResult(RefreshResultKey, true)
            }
        )
    }

    /**
     * 重写请求成功处理方法，用于更新表单数据
     *
     * @param data 地址数据
     * @author Joker.X
     */
    override fun onRequestSuccess(data: Address) {
        updateFormData(data)
        super.onRequestSuccess(data)
    }
}