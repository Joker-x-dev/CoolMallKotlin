package com.joker.coolmall.feature.common.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 贡献者列表 ViewModel
 */
@HiltViewModel
class ContributorsViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState
) : BaseViewModel(navigator, appState) {
}