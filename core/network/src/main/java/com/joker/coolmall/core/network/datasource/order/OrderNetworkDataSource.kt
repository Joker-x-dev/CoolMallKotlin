package com.joker.coolmall.core.network.datasource.order

import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.model.entity.OrderCount
import com.joker.coolmall.core.model.request.CreateOrderRequest
import com.joker.coolmall.core.model.request.OrderPageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 订单相关数据源接口
 */
interface OrderNetworkDataSource {

    /**
     * 支付宝APP支付
     */
    suspend fun alipayAppPay(params: Map<String, Long>): NetworkResponse<String>

    /**
     * 修改订单
     */
    suspend fun updateOrder(params: Any): NetworkResponse<Any>

    /**
     * 退款
     */
    suspend fun refundOrder(params: Any): NetworkResponse<Any>

    /**
     * 分页查询订单
     */
    suspend fun getOrderPage(params: OrderPageRequest): NetworkResponse<NetworkPageData<Order>>

    /**
     * 创建订单
     */
    suspend fun createOrder(params: CreateOrderRequest): NetworkResponse<Order>

    /**
     * 取消订单
     */
    suspend fun cancelOrder(params: Any): NetworkResponse<Any>

    /**
     * 用户订单统计
     */
    suspend fun getUserOrderCount(): NetworkResponse<OrderCount>

    /**
     * 物流信息
     */
    suspend fun getOrderLogistics(id: String): NetworkResponse<Any>

    /**
     * 订单信息
     */
    suspend fun getOrderInfo(id: Long): NetworkResponse<Order>

    /**
     * 确认收货
     */
    suspend fun confirmReceive(id: Long): NetworkResponse<Any>
}