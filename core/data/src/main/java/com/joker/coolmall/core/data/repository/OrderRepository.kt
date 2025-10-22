package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.model.entity.Logistics
import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.model.entity.OrderCount
import com.joker.coolmall.core.model.request.CancelOrderRequest

import com.joker.coolmall.core.model.request.CreateOrderRequest

import com.joker.coolmall.core.model.request.OrderPageRequest
import com.joker.coolmall.core.model.request.RefundOrderRequest
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
 *
 * @param orderNetworkDataSource 订单网络数据源
 * @author Joker.X
 */
class OrderRepository @Inject constructor(
    private val orderNetworkDataSource: OrderNetworkDataSource
) {

    /**
     * 支付宝APP支付
     *
     * @param params 支付参数
     * @return 支付信息Flow
     * @author Joker.X
     */
    fun alipayAppPay(params: Map<String, Long>): Flow<NetworkResponse<String>> = flow {
        emit(orderNetworkDataSource.alipayAppPay(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 申请退款
     *
     * @param params 退款请求参数
     * @return 退款结果Flow
     * @author Joker.X
     */
    fun refundOrder(params: RefundOrderRequest): Flow<NetworkResponse<Boolean>> = flow {
        emit(orderNetworkDataSource.refundOrder(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 分页查询订单
     *
     * @param params 订单分页查询参数
     * @return 订单分页数据Flow
     * @author Joker.X
     */
    fun getOrderPage(params: OrderPageRequest): Flow<NetworkResponse<NetworkPageData<Order>>> =
        flow {
            emit(orderNetworkDataSource.getOrderPage(params))
        }.flowOn(Dispatchers.IO)

    /**
     * 创建订单
     *
     * @param params 创建订单请求参数
     * @return 订单信息Flow
     * @author Joker.X
     */
    fun createOrder(params: CreateOrderRequest): Flow<NetworkResponse<Order>> = flow {
        emit(orderNetworkDataSource.createOrder(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 取消订单
     *
     * @param params 取消订单请求参数
     * @return 取消结果Flow
     * @author Joker.X
     */
    fun cancelOrder(params: CancelOrderRequest): Flow<NetworkResponse<Boolean>> = flow {
        emit(orderNetworkDataSource.cancelOrder(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 获取用户订单统计
     *
     * @return 订单统计Flow
     * @author Joker.X
     */
    fun getUserOrderCount(): Flow<NetworkResponse<OrderCount>> = flow {
        emit(orderNetworkDataSource.getUserOrderCount())
    }.flowOn(Dispatchers.IO)

    /**
     * 获取订单物流信息
     *
     * @param orderId 订单ID
     * @return 物流信息Flow
     * @author Joker.X
     */
    fun getOrderLogistics(orderId: Long): Flow<NetworkResponse<Logistics>> = flow {
        emit(orderNetworkDataSource.getOrderLogistics(orderId))
    }.flowOn(Dispatchers.IO)

    /**
     * 获取订单详情
     *
     * @param id 订单ID
     * @return 订单详情Flow
     * @author Joker.X
     */
    fun getOrderInfo(id: Long): Flow<NetworkResponse<Order>> = flow {
        emit(orderNetworkDataSource.getOrderInfo(id))
    }.flowOn(Dispatchers.IO)

    /**
     * 确认收货
     *
     * @param orderId 订单ID
     * @return 确认结果Flow
     * @author Joker.X
     */
    fun confirmReceive(orderId: Long): Flow<NetworkResponse<Boolean>> = flow {
        emit(orderNetworkDataSource.confirmReceive(orderId))
    }.flowOn(Dispatchers.IO)
}
