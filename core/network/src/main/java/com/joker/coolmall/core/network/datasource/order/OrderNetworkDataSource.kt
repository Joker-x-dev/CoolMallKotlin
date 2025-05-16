package com.joker.coolmall.core.network.datasource.order

import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.model.request.CreateOrderRequest
import com.joker.coolmall.core.model.request.OrderPageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 订单相关数据源接口
 */
interface OrderNetworkDataSource {

    /**
     * 支付回调通知处理
     */
    suspend fun wxPayNotify(params: Any): NetworkResponse<Any>

    /**
     * 微信APP支付
     */
    suspend fun wxAppPay(params: Any): NetworkResponse<Any>

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
    suspend fun getUserOrderCount(): NetworkResponse<Any>

    /**
     * 物流信息
     */
    suspend fun getOrderLogistics(id: String): NetworkResponse<Any>

    /**
     * 订单信息
     */
    suspend fun getOrderInfo(id: String): NetworkResponse<Any>

    /**
     * 确认收货
     */
    suspend fun confirmReceive(id: String): NetworkResponse<Any>
} 