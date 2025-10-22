package com.joker.coolmall.core.network.datasource.order

import com.joker.coolmall.core.model.entity.Logistics
import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.model.entity.OrderCount
import com.joker.coolmall.core.model.request.CancelOrderRequest
import com.joker.coolmall.core.model.request.CreateOrderRequest
import com.joker.coolmall.core.model.request.OrderPageRequest
import com.joker.coolmall.core.model.request.RefundOrderRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 订单相关数据源接口
 *
 * @author Joker.X
 */
interface OrderNetworkDataSource {

    /**
     * 支付宝APP支付
     *
     * @param params 支付参数
     * @return 支付信息响应
     * @author Joker.X
     */
    suspend fun alipayAppPay(params: Map<String, Long>): NetworkResponse<String>

    /**
     * 修改订单
     *
     * @param params 修改参数
     * @return 修改结果响应
     * @author Joker.X
     */
    suspend fun updateOrder(params: Any): NetworkResponse<Any>

    /**
     * 申请订单退款
     *
     * @param params 退款请求参数
     * @return 退款结果响应
     * @author Joker.X
     */
    suspend fun refundOrder(params: RefundOrderRequest): NetworkResponse<Boolean>

    /**
     * 分页查询订单
     *
     * @param params 订单分页请求参数
     * @return 订单分页数据响应
     * @author Joker.X
     */
    suspend fun getOrderPage(params: OrderPageRequest): NetworkResponse<NetworkPageData<Order>>

    /**
     * 创建订单
     *
     * @param params 创建订单请求参数
     * @return 订单创建结果响应
     * @author Joker.X
     */
    suspend fun createOrder(params: CreateOrderRequest): NetworkResponse<Order>

    /**
     * 取消订单
     *
     * @param params 取消订单请求参数
     * @return 取消结果响应
     * @author Joker.X
     */
    suspend fun cancelOrder(params: CancelOrderRequest): NetworkResponse<Boolean>

    /**
     * 获取用户订单统计
     *
     * @return 订单统计信息响应
     * @author Joker.X
     */
    suspend fun getUserOrderCount(): NetworkResponse<OrderCount>

    /**
     * 获取订单物流信息
     *
     * @param orderId 订单ID
     * @return 物流信息响应
     * @author Joker.X
     */
    suspend fun getOrderLogistics(orderId: Long): NetworkResponse<Logistics>

    /**
     * 获取订单信息
     *
     * @param id 订单ID
     * @return 订单信息响应
     * @author Joker.X
     */
    suspend fun getOrderInfo(id: Long): NetworkResponse<Order>

    /**
     * 确认收货
     *
     * @param orderId 订单ID
     * @return 确认结果响应
     * @author Joker.X
     */
    suspend fun confirmReceive(orderId: Long): NetworkResponse<Boolean>
}
