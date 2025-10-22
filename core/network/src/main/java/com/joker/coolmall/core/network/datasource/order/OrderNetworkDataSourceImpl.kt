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
import com.joker.coolmall.core.network.base.BaseNetworkDataSource
import com.joker.coolmall.core.network.service.OrderService
import javax.inject.Inject

/**
 * 订单相关数据源实现类
 * 负责处理所有与订单相关的网络请求
 *
 * @param orderService 订单服务接口，用于发起实际的网络请求
 * @author Joker.X
 */
class OrderNetworkDataSourceImpl @Inject constructor(
    private val orderService: OrderService
) : BaseNetworkDataSource(), OrderNetworkDataSource {

    /**
     * 支付宝APP支付
     *
     * @param params 请求参数，包含订单ID和支付信息
     * @return 支付参数响应数据
     * @author Joker.X
     */
    override suspend fun alipayAppPay(params: Map<String, Long>): NetworkResponse<String> {
        return orderService.alipayAppPay(params)
    }

    /**
     * 修改订单
     *
     * @param params 请求参数，包含订单ID和修改信息
     * @return 修改结果响应数据
     * @author Joker.X
     */
    override suspend fun updateOrder(params: Any): NetworkResponse<Any> {
        return orderService.updateOrder(params)
    }

    /**
     * 申请订单退款
     *
     * @param params 请求参数，包含订单ID和退款信息
     * @return 退款申请结果响应数据
     * @author Joker.X
     */
    override suspend fun refundOrder(params: RefundOrderRequest): NetworkResponse<Boolean> {
        return orderService.refundOrder(params)
    }

    /**
     * 分页查询订单
     *
     * @param params 请求参数，包含分页和查询条件
     * @return 订单分页列表响应数据
     * @author Joker.X
     */
    override suspend fun getOrderPage(params: OrderPageRequest): NetworkResponse<NetworkPageData<Order>> {
        return orderService.getOrderPage(params)
    }

    /**
     * 创建订单
     *
     * @param params 请求参数，包含商品、地址、支付等信息
     * @return 创建结果响应数据
     * @author Joker.X
     */
    override suspend fun createOrder(params: CreateOrderRequest): NetworkResponse<Order> {
        return orderService.createOrder(params)
    }

    /**
     * 取消订单
     *
     * @param params 请求参数，包含订单ID和取消原因
     * @return 取消结果响应数据
     * @author Joker.X
     */
    override suspend fun cancelOrder(params: CancelOrderRequest): NetworkResponse<Boolean> {
        return orderService.cancelOrder(params)
    }

    /**
     * 获取用户订单统计
     *
     * @return 订单统计信息响应数据
     * @author Joker.X
     */
    override suspend fun getUserOrderCount(): NetworkResponse<OrderCount> {
        return orderService.getUserOrderCount()
    }

    /**
     * 获取订单物流信息
     *
     * @param orderId 订单ID
     * @return 物流信息响应数据
     * @author Joker.X
     */
    override suspend fun getOrderLogistics(orderId: Long): NetworkResponse<Logistics> {
        return orderService.getOrderLogistics(orderId)
    }

    /**
     * 获取订单详情
     *
     * @param id 订单ID
     * @return 订单详情响应数据
     * @author Joker.X
     */
    override suspend fun getOrderInfo(id: Long): NetworkResponse<Order> {
        return orderService.getOrderInfo(id)
    }

    /**
     * 确认收货
     *
     * @param orderId 订单ID
     * @return 确认结果响应数据
     * @author Joker.X
     */
    override suspend fun confirmReceive(orderId: Long): NetworkResponse<Boolean> {
        return orderService.confirmReceive(orderId)
    }
}
