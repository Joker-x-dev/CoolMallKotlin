package com.joker.coolmall.feature.order.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.OrderRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Logistics
import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.OrderRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * 订单物流 ViewModel
 *
 * @param navigator 导航器
 * @param appState 应用状态
 * @param savedStateHandle 保存状态句柄
 * @param orderRepository 订单仓库
 * @author Joker.X
 */
@HiltViewModel
class OrderLogisticsViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    savedStateHandle: SavedStateHandle,
    private val orderRepository: OrderRepository,
) : BaseNetWorkViewModel<Order>(
    navigator = navigator,
    appState = appState
) {
    // 从路由获取订单ID
    private val logisticsRoute = savedStateHandle.toRoute<OrderRoutes.Logistics>()

    // 从路由获取订单ID
    private val requiredOrderId: Long = logisticsRoute.orderId

    /**
     * 订单物流数据
     */
    private val _orderLogisticsUiState = MutableStateFlow(Logistics())
    val orderLogisticsUiState: StateFlow<Logistics> = _orderLogisticsUiState

    init {
        super.executeRequest()
        getOrderLogistics()
    }

    /**
     * 重写请求API的方法
     *
     * @return 订单网络响应流
     * @author Joker.X
     */
    override fun requestApiFlow(): Flow<NetworkResponse<Order>> {
        return orderRepository.getOrderInfo(requiredOrderId)
    }

    /**
     * 获取订单物流信息
     *
     * @author Joker.X
     */
    fun getOrderLogistics() {
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = orderRepository.getOrderLogistics(requiredOrderId).asResult(),
            onData = { data -> _orderLogisticsUiState.value = data }
        )
    }
}