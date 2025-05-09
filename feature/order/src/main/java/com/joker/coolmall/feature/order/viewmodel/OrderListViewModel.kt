package com.joker.coolmall.feature.order.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 订单列表视图模型
 */
@HiltViewModel
class OrderListViewModel @Inject constructor(
    navigator: AppNavigator,
) : BaseViewModel(navigator) {
} 