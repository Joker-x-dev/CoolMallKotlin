package com.joker.coolmall.feature.order.viewmodel

import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.OrderRepository
import com.joker.coolmall.core.model.entity.Logistics
import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.navigation.order.OrderRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * 订单物流 ViewModel
 *
 * @param navKey 路由参数
 * @param orderRepository 订单仓库
 * @author Joker.X
 */
@HiltViewModel(assistedFactory = OrderLogisticsViewModel.Factory::class)
class OrderLogisticsViewModel @AssistedInject constructor(
    @Assisted navKey: OrderRoutes.Logistics,
    private val orderRepository: OrderRepository,
) : BaseNetWorkViewModel<Order>() {
    // 从路由获取订单ID
    private val requiredOrderId: Long = navKey.orderId

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

    /**
     * Assisted Factory
     *
     * @author Joker.X
     */
    @AssistedFactory
    interface Factory {
        /**
         * 创建 ViewModel 实例
         *
         * @param navKey 路由参数
         * @return ViewModel 实例
         * @author Joker.X
         */
        fun create(navKey: OrderRoutes.Logistics): OrderLogisticsViewModel
    }
}
