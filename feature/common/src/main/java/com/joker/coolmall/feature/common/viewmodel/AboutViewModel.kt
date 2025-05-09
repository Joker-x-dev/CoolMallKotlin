package com.joker.coolmall.feature.common.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 关于我们 ViewModel
 */
@HiltViewModel
class AboutViewModel @Inject constructor(
    navigator: AppNavigator,
) : BaseViewModel(navigator) {
}