package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.entity.Logistics
import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.model.entity.OrderCount
import com.joker.coolmall.core.model.request.CancelOrderRequest
import com.joker.coolmall.core.model.request.CreateOrderRequest
import com.joker.coolmall.core.model.request.OrderPageRequest
import com.joker.coolmall.core.model.request.RefundOrderRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * 订单相关接口
 */
interface OrderService {

    /**
     * 支付宝APP支付
     */
    @POST("order/pay/alipayAppPay")
    suspend fun alipayAppPay(@Body params: Map<String, Long>): NetworkResponse<String>

    /**
     * 修改订单
     */
    @POST("order/info/update")
    suspend fun updateOrder(@Body params: Any): NetworkResponse<Any>

    /**
     * 退款
     */
    @POST("order/info/refund")
    suspend fun refundOrder(@Body params: RefundOrderRequest): NetworkResponse<Boolean>

    /**
     * 分页查询订单
     */
    @POST("order/info/page")
    suspend fun getOrderPage(@Body params: OrderPageRequest): NetworkResponse<NetworkPageData<Order>>

    /**
     * 创建订单
     */
    @POST("order/info/create")
    suspend fun createOrder(@Body params: CreateOrderRequest): NetworkResponse<Order>

    /**
     * 取消订单
     */
    @POST("order/info/cancel")
    suspend fun cancelOrder(@Body params: CancelOrderRequest): NetworkResponse<Boolean>

    /**
     * 用户订单统计
     */
    @GET("order/info/userCount")
    suspend fun getUserOrderCount(): NetworkResponse<OrderCount>

    /**
     * 物流信息
     */
    @GET("order/info/logistics")
    suspend fun getOrderLogistics(@Query("orderId") orderId: Long): NetworkResponse<Logistics>

    /**
     * 订单信息
     */
    @GET("order/info/info")
    suspend fun getOrderInfo(@Query("id") id: Long): NetworkResponse<Order>

    /**
     * 确认收货
     */
    @GET("order/info/confirm")
    suspend fun confirmReceive(@Query("orderId") orderId: Long): NetworkResponse<Boolean>
}