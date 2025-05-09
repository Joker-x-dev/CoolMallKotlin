package com.joker.coolmall.feature.order.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 订单确认页面ViewModel
 */
@HiltViewModel
class OrderConfirmViewModel @Inject constructor(
    navigator: AppNavigator,
    savedStateHandle: SavedStateHandle
) : BaseNetWorkViewModel<Any>(
    navigator = navigator,
    savedStateHandle = savedStateHandle
) {
    override fun requestApiFlow(): Flow<NetworkResponse<Any>> {
        TODO("Not yet implemented")
    }
}