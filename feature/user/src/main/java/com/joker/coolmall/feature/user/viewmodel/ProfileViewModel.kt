package com.joker.coolmall.feature.user.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 个人中心ViewModel
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    navigator: AppNavigator
) : BaseViewModel(
    navigator = navigator
) {
}