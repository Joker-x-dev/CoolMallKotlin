package com.joker.coolmall.feature.order.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.OrderRepository
import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.model.request.OrderPageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 订单列表视图模型
 */
@HiltViewModel
class OrderListViewModel @Inject constructor(
    navigator: AppNavigator,
    private val orderRepository: OrderRepository
) : BaseNetWorkViewModel<NetworkPageData<Order>>(navigator) {

    init {
        super.executeRequest()
    }

    override fun requestApiFlow(): Flow<NetworkResponse<NetworkPageData<Order>>> {
        return orderRepository.getOrderPage(OrderPageRequest())
    }
} 