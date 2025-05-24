package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.model.request.CreateOrderRequest
import com.joker.coolmall.core.model.request.OrderPageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.datasource.order.OrderNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 订单相关仓库
 */
class OrderRepository @Inject constructor(
    private val orderNetworkDataSource: OrderNetworkDataSource
) {

    /**
     * 支付宝APP支付
     */
    fun alipayAppPay(params: Map<String, Long>): Flow<NetworkResponse<String>> = flow {
        emit(orderNetworkDataSource.alipayAppPay(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 修改订单
     */
    fun updateOrder(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(orderNetworkDataSource.updateOrder(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 退款
     */
    fun refundOrder(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(orderNetworkDataSource.refundOrder(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 分页查询订单
     */
    fun getOrderPage(params: OrderPageRequest): Flow<NetworkResponse<NetworkPageData<Order>>> =
        flow {
            emit(orderNetworkDataSource.getOrderPage(params))
        }.flowOn(Dispatchers.IO)

    /**
     * 创建订单
     */
    fun createOrder(params: CreateOrderRequest): Flow<NetworkResponse<Order>> = flow {
        emit(orderNetworkDataSource.createOrder(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 取消订单
     */
    fun cancelOrder(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(orderNetworkDataSource.cancelOrder(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 用户订单统计
     */
    fun getUserOrderCount(): Flow<NetworkResponse<Any>> = flow {
        emit(orderNetworkDataSource.getUserOrderCount())
    }.flowOn(Dispatchers.IO)

    /**
     * 物流信息
     */
    fun getOrderLogistics(id: String): Flow<NetworkResponse<Any>> = flow {
        emit(orderNetworkDataSource.getOrderLogistics(id))
    }.flowOn(Dispatchers.IO)

    /**
     * 订单信息
     */
    fun getOrderInfo(id: Long): Flow<NetworkResponse<Order>> = flow {
        emit(orderNetworkDataSource.getOrderInfo(id))
    }.flowOn(Dispatchers.IO)

    /**
     * 确认收货
     */
    fun confirmReceive(id: Long): Flow<NetworkResponse<Any>> = flow {
        emit(orderNetworkDataSource.confirmReceive(id))
    }.flowOn(Dispatchers.IO)
} 