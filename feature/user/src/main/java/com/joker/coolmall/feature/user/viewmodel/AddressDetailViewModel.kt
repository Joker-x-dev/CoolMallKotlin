package com.joker.coolmall.feature.user.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.feature.user.navigation.AddressDetailRoutes
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 收货地址详情ViewModel
 */
@HiltViewModel
class AddressDetailViewModel @Inject constructor(
    navigator: AppNavigator,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(
    navigator = navigator
) {
    // 是否为编辑模式
    val isEditMode: Boolean = savedStateHandle.get<Boolean>(AddressDetailRoutes.IS_EDIT_MODE_ARG) ?: false
    
    // 地址ID，仅在编辑模式下使用
    private val addressIdStr: String = savedStateHandle.get<String>(AddressDetailRoutes.ADDRESS_ID_ARG) ?: ""
    
    // 转换地址ID，如果是编辑模式且有效ID则返回Long值，否则返回null
    val addressId: Long? = if (isEditMode && addressIdStr.isNotEmpty()) {
        try {
            addressIdStr.toLong()
        } catch (e: NumberFormatException) {
            null
        }
    } else null
    
    /**
     * 保存地址信息
     * 根据是否为编辑模式调用不同的保存逻辑
     */
    fun saveAddress() {
        // 保存成功后返回上一页
        navigateBack()
    }
}