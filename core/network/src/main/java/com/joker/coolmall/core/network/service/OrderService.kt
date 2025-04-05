package com.joker.coolmall.core.network.service

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
     * 支付回调通知处理
     */
    @POST("order/pay/wxNotify")
    suspend fun wxPayNotify(@Body params: Any): NetworkResponse<Any>

    /**
     * 微信APP支付
     */
    @POST("order/pay/wxAppPay")
    suspend fun wxAppPay(@Body params: Any): NetworkResponse<Any>

    /**
     * 修改订单
     */
    @POST("order/info/update")
    suspend fun updateOrder(@Body params: Any): NetworkResponse<Any>

    /**
     * 退款
     */
    @POST("order/info/refund")
    suspend fun refundOrder(@Body params: Any): NetworkResponse<Any>

    /**
     * 分页查询订单
     */
    @POST("order/info/page")
    suspend fun getOrderPage(@Body params: Any): NetworkResponse<Any>

    /**
     * 创建订单
     */
    @POST("order/info/create")
    suspend fun createOrder(@Body params: Any): NetworkResponse<Any>

    /**
     * 取消订单
     */
    @POST("order/info/cancel")
    suspend fun cancelOrder(@Body params: Any): NetworkResponse<Any>

    /**
     * 用户订单统计
     */
    @GET("order/info/userCount")
    suspend fun getUserOrderCount(): NetworkResponse<Any>

    /**
     * 物流信息
     */
    @GET("order/info/logistics")
    suspend fun getOrderLogistics(@Query("id") id: String): NetworkResponse<Any>

    /**
     * 订单信息
     */
    @GET("order/info/info")
    suspend fun getOrderInfo(@Query("id") id: String): NetworkResponse<Any>

    /**
     * 确认收货
     */
    @GET("order/info/confirm")
    suspend fun confirmReceive(@Query("id") id: String): NetworkResponse<Any>
} 