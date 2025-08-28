/**
 * 订单物流 ViewModel
 *
 * @author Joker.X
 */
package com.joker.coolmall.feature.order.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 订单物流 ViewModel
 */
@HiltViewModel
class OrderLogisticsViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
) : BaseViewModel(navigator, appState) {
}