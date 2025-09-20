package com.joker.coolmall.feature.common.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.CommonRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 隐私政策 ViewModel
 */
@HiltViewModel
class PrivacyPolicyViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    private val commonRepository: CommonRepository
) : BaseNetWorkViewModel<String>(navigator, appState) {

    init {
        super.executeRequest()
    }

    override fun requestApiFlow(): Flow<NetworkResponse<String>> {
        return commonRepository.getParam("privacyPolicy")
    }
}