package com.joker.coolmall.feature.order.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.OrderRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Cart
import com.joker.coolmall.core.model.entity.CartGoodsSpec
import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.feature.order.navigation.OrderDetailRoutes
import com.joker.coolmall.feature.order.navigation.OrderPayRoutes
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.GoodsRoutes
import com.joker.coolmall.navigation.routes.OrderRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 订单详情视图模型
 */
@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    savedStateHandle: SavedStateHandle,
    private val orderRepository: OrderRepository
) : BaseNetWorkViewModel<Order>(
    navigator = navigator,
    appState = appState,
    savedStateHandle = savedStateHandle,
    idKey = OrderDetailRoutes.ORDER_ID_ARG
) {

    private val _cartList = MutableStateFlow<List<Cart>>(emptyList())
    val cartList = _cartList.asStateFlow()

    // 标记是否需要在返回时刷新列表
    private var shouldRefreshListOnBack = false

    init {
        super.executeRequest()
    }

    /**
     * 重写请求API的方法
     */
    override fun requestApiFlow(): Flow<NetworkResponse<Order>> {
        return orderRepository.getOrderInfo(requiredId)
    }

    /**
     * 处理请求成功的逻辑
     */
    override fun onRequestSuccess(data: Order) {
        _cartList.value = convertOrderGoodsToCart(data)
        super.setSuccessState(data)
    }

    /**
     * 跳转到支付页面
     */
    fun navigateToPayment() {
        val order = super.getSuccessData()
        val orderId = order.id
        val paymentPrice = order.price - order.discountPrice // 实付金额

        // 构建带参数的支付路由：/order/pay/{orderId}/{paymentPrice}
        val paymentRoute = OrderPayRoutes.ORDER_PAY_PATTERN
            .replace("{${OrderPayRoutes.ORDER_ID_ARG}}", orderId.toString())
            .replace("{${OrderPayRoutes.PRICE_ARG}}", paymentPrice.toString())

        toPage(paymentRoute)
    }

    /**
     * 取消订单
     */
    fun cancelOrder() {
        // TODO: 实现取消订单逻辑
    }

    /**
     * 确认收货
     */
    fun confirmOrder() {
        // TODO: 实现确认收货逻辑
    }

    /**
     * 观察来自支付页面的刷新状态
     * 当从支付页面返回时，如果支付成功，则刷新订单详情
     */
    fun observeRefreshState(backStackEntry: NavBackStackEntry?) {
        backStackEntry?.savedStateHandle?.let { savedStateHandle ->
            viewModelScope.launch {
                savedStateHandle.getStateFlow<Boolean>("refresh", false).collect { shouldRefresh ->
                    if (shouldRefresh) {
                        // 刷新订单详情
                        retryRequest()

                        // 标记需要在返回时刷新列表
                        shouldRefreshListOnBack = true

                        // 重置刷新标志，避免重复刷新
                        savedStateHandle["refresh"] = false
                    }
                }
            }
        }
    }

    /**
     * 处理返回按钮点击
     * 根据是否需要刷新列表决定传递参数
     */
    fun handleBackClick() {
        if (shouldRefreshListOnBack) {
            // 如果需要刷新列表，则传递刷新标志
            navigateBack(mapOf("refresh" to true))
            // 重置标志
            shouldRefreshListOnBack = false
        } else {
            // 正常返回
            navigateBack()
        }
    }

    /**
     * 跳转到商品详情页面（再次购买）
     */
    fun toGoodsDetail(goodsId: Long) {
        toPage(GoodsRoutes.DETAIL, goodsId)
    }

    /**
     * 跳转到订单物流页面
     */
    fun toOrderLogistics() {
        val order = super.getSuccessData()
        val orderId = order.id
        toPage(OrderRoutes.LOGISTICS, orderId)
    }

    /**
     * 跳转到退款申请页面
     */
    fun toOrderRefund() {
        val order = super.getSuccessData()
        val orderId = order.id
        toPage(OrderRoutes.REFUND, orderId)
    }

    /**
     * 跳转到订单评价页面
     */
    fun toOrderComment() {
        val order = super.getSuccessData()
        val orderId = order.id
        toPage(OrderRoutes.COMMENT, orderId)
    }

    /**
     * 将Order中的goodsList转换为Cart类型的列表
     * 参考OrderConfirmViewModel中的处理方法
     */
    private fun convertOrderGoodsToCart(order: Order): List<Cart> {
        return order.goodsList?.let { goodsList ->
            // 按商品ID分组
            val groupedGoods = goodsList.groupBy { it.goodsId }

            // 为每个商品ID创建一个Cart对象
            groupedGoods.map { (goodsId, items) ->
                val firstItem = items.first()

                Cart().apply {
                    this.goodsId = goodsId
                    this.goodsName = firstItem.goodsInfo?.title ?: ""
                    this.goodsMainPic = firstItem.goodsInfo?.mainPic ?: ""

                    // 收集该商品的所有规格
                    val allSpecs = mutableListOf<CartGoodsSpec>()

                    // 遍历该商品的所有选中项
                    items.forEach { orderGoods ->
                        // 如果有规格信息，转换为CartGoodsSpec并添加
                        orderGoods.spec?.let { spec ->
                            val cartSpec = CartGoodsSpec(
                                id = spec.id,
                                goodsId = spec.goodsId,
                                name = spec.name,
                                price = spec.price,
                                stock = spec.stock,
                                count = orderGoods.count,
                                images = spec.images
                            )
                            allSpecs.add(cartSpec)
                        }
                    }

                    // 设置规格列表
                    this.spec = allSpecs
                }
            }
        } ?: emptyList()
    }
}