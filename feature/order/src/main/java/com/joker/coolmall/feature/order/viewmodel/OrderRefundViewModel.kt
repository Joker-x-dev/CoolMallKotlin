/**
 * 退款申请 ViewModel
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
 * 退款申请 ViewModel
 */
@HiltViewModel
class OrderRefundViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
) : BaseViewModel(navigator, appState) {
}