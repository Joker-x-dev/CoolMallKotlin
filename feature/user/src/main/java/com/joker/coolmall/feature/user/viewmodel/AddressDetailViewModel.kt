package com.joker.coolmall.feature.user.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 收货地址详情ViewModel
 */
@HiltViewModel
class AddressDetailViewModel @Inject constructor(
    navigator: AppNavigator,
) : BaseViewModel(
    navigator = navigator
) {
}