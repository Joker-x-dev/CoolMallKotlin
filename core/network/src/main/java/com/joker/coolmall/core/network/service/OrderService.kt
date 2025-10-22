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
 *
 * @author Joker.X
 */
interface OrderService {

    /**
     * 支付宝APP支付
     *
     * @param params 支付参数
     * @return 支付信息响应
     * @author Joker.X
     */
    @POST("order/pay/alipayAppPay")
    suspend fun alipayAppPay(@Body params: Map<String, Long>): NetworkResponse<String>

    /**
     * 修改订单
     *
     * @param params 修改参数
     * @return 修改结果响应
     * @author Joker.X
     */
    @POST("order/info/update")
    suspend fun updateOrder(@Body params: Any): NetworkResponse<Any>

    /**
     * 申请订单退款
     *
     * @param params 退款请求参数
     * @return 退款结果响应
     * @author Joker.X
     */
    @POST("order/info/refund")
    suspend fun refundOrder(@Body params: RefundOrderRequest): NetworkResponse<Boolean>

    /**
     * 分页查询订单
     *
     * @param params 订单分页请求参数
     * @return 订单分页数据响应
     * @author Joker.X
     */
    @POST("order/info/page")
    suspend fun getOrderPage(@Body params: OrderPageRequest): NetworkResponse<NetworkPageData<Order>>

    /**
     * 创建订单
     *
     * @param params 创建订单请求参数
     * @return 订单创建结果响应
     * @author Joker.X
     */
    @POST("order/info/create")
    suspend fun createOrder(@Body params: CreateOrderRequest): NetworkResponse<Order>

    /**
     * 取消订单
     *
     * @param params 取消订单请求参数
     * @return 取消结果响应
     * @author Joker.X
     */
    @POST("order/info/cancel")
    suspend fun cancelOrder(@Body params: CancelOrderRequest): NetworkResponse<Boolean>

    /**
     * 获取用户订单统计
     *
     * @return 订单统计信息响应
     * @author Joker.X
     */
    @GET("order/info/userCount")
    suspend fun getUserOrderCount(): NetworkResponse<OrderCount>

    /**
     * 获取订单物流信息
     *
     * @param orderId 订单ID
     * @return 物流信息响应
     * @author Joker.X
     */
    @GET("order/info/logistics")
    suspend fun getOrderLogistics(@Query("orderId") orderId: Long): NetworkResponse<Logistics>

    /**
     * 获取订单信息
     *
     * @param id 订单ID
     * @return 订单信息响应
     * @author Joker.X
     */
    @GET("order/info/info")
    suspend fun getOrderInfo(@Query("id") id: Long): NetworkResponse<Order>

    /**
     * 确认收货
     *
     * @param orderId 订单ID
     * @return 确认结果响应
     * @author Joker.X
     */
    @GET("order/info/confirm")
    suspend fun confirmReceive(@Query("orderId") orderId: Long): NetworkResponse<Boolean>
}
