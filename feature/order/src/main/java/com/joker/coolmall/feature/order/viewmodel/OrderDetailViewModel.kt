package com.joker.coolmall.feature.order.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.CommonRepository
import com.joker.coolmall.core.data.repository.OrderRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Cart
import com.joker.coolmall.core.model.entity.CartGoodsSpec
import com.joker.coolmall.core.model.entity.DictItem
import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.model.request.CancelOrderRequest
import com.joker.coolmall.core.model.request.DictDataRequest
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.feature.order.navigation.OrderCommentRoutes
import com.joker.coolmall.feature.order.navigation.OrderDetailRoutes
import com.joker.coolmall.feature.order.navigation.OrderPayRoutes
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.GoodsRoutes
import com.joker.coolmall.navigation.routes.OrderRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val orderRepository: OrderRepository,
    private val commonRepository: CommonRepository
) : BaseNetWorkViewModel<Order>(
    navigator = navigator,
    appState = appState,
    savedStateHandle = savedStateHandle,
    idKey = OrderDetailRoutes.ORDER_ID_ARG
) {

    private val _cartList = MutableStateFlow<List<Cart>>(emptyList())
    val cartList = _cartList.asStateFlow()

    /**
     * 取消原因选择弹窗的显示状态
     */
    private val _cancelModalVisible = MutableStateFlow(false)
    val cancelModalVisible: StateFlow<Boolean> = _cancelModalVisible.asStateFlow()

    /**
     * 取消原因弹出层 ui 状态
     */
    private val _cancelReasonsModalUiState =
        MutableStateFlow<BaseNetWorkUiState<List<DictItem>>>(BaseNetWorkUiState.Loading)
    val cancelReasonsModalUiState: StateFlow<BaseNetWorkUiState<List<DictItem>>> =
        _cancelReasonsModalUiState.asStateFlow()

    /**
     * 选中的取消原因
     */
    private val _selectedCancelReason = MutableStateFlow<DictItem?>(null)
    val selectedCancelReason: StateFlow<DictItem?> = _selectedCancelReason.asStateFlow()

    /**
     * 确认收货弹窗的显示状态
     */
    private val _showConfirmDialog = MutableStateFlow(false)
    val showConfirmDialog: StateFlow<Boolean> = _showConfirmDialog.asStateFlow()

    /**
     * 再次购买弹窗的显示状态
     */
    private val _rebuyModalVisible = MutableStateFlow(false)
    val rebuyModalVisible: StateFlow<Boolean> = _rebuyModalVisible.asStateFlow()

    /**
     * 商品评论弹窗的显示状态
     */
    private val _commentModalVisible = MutableStateFlow(false)
    val commentModalVisible: StateFlow<Boolean> = _commentModalVisible.asStateFlow()

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
     * 确认收货
     */
    fun confirmOrder() {
        showConfirmReceiveDialog()
    }

    /**
     * 显示确认收货弹窗
     */
    fun showConfirmReceiveDialog() {
        _showConfirmDialog.value = true
    }

    /**
     * 隐藏确认收货弹窗
     */
    fun hideConfirmReceiveDialog() {
        _showConfirmDialog.value = false
    }

    /**
     * 确认收货订单
     */
    fun confirmReceiveOrder() {
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = orderRepository.confirmReceive(requiredId).asResult(),
            onData = { _ ->
                // 刷新订单详情
                retryRequest()
                // 标记需要在返回时刷新列表
                shouldRefreshListOnBack = true
                // 隐藏弹窗
                hideConfirmReceiveDialog()
            }
        )
    }

    /**
     * 取消订单
     */
    fun cancelOrder() {
        showCancelModal()
        viewModelScope.launch {
            // 延迟加载商品规格，避免阻塞UI线程
            delay(300)
            loadCancelReasons()
        }
    }

    /**
     * 加载取消原因字典数据
     */
    fun loadCancelReasons() {
        // 如果 ui 状态为成功，则不重复加载
        if (_cancelReasonsModalUiState.value is BaseNetWorkUiState.Success) {
            return
        }
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = commonRepository.getDictData(
                DictDataRequest(
                    types = listOf("orderCancelReason")
                )
            ).asResult(),
            showToast = false,
            onLoading = { _cancelReasonsModalUiState.value = BaseNetWorkUiState.Loading },
            onData = { data ->
                _cancelReasonsModalUiState.value =
                    BaseNetWorkUiState.Success(data.orderCancelReason!!)
            },
            onError = { _, _ ->
                _cancelReasonsModalUiState.value = BaseNetWorkUiState.Error()
            }
        )
    }

    /**
     * 显示取消原因选择弹窗
     */
    fun showCancelModal() {
        _cancelModalVisible.value = true
    }

    /**
     * 隐藏取消原因选择弹窗
     */
    fun hideCancelModal() {
        _cancelModalVisible.value = false
    }

    /**
     * 选择取消原因
     */
    fun selectCancelReason(reason: DictItem) {
        _selectedCancelReason.value = reason
    }

    /**
     * 确认取消订单
     */
    fun confirmCancelOrder() {
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = orderRepository.cancelOrder(
                CancelOrderRequest(
                    orderId = requiredId,
                    remark = _selectedCancelReason.value?.name ?: ""
                )
            ).asResult(),
            onData = { _ ->
                // 刷新订单详情
                retryRequest()
                // 标记需要在返回时刷新列表
                shouldRefreshListOnBack = true
            }
        )
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
     * 显示再次购买弹窗
     */
    fun showRebuyModal() {
        _rebuyModalVisible.value = true
    }

    /**
     * 隐藏再次购买弹窗
     */
    fun hideRebuyModal() {
        _rebuyModalVisible.value = false
    }

    /**
     * 显示商品评论弹窗
     */
    fun showCommentModal() {
        _commentModalVisible.value = true
    }

    /**
     * 隐藏商品评论弹窗
     */
    fun hideCommentModal() {
        _commentModalVisible.value = false
    }

    /**
     * 处理再次购买逻辑
     */
    fun handleRebuy() {
        val cartList = _cartList.value
        if (cartList.size > 1) {
            // 多个商品时显示弹窗让用户选择
            showRebuyModal()
        } else {
            // 单个商品时直接跳转
            val goodsId = cartList.firstOrNull()?.goodsId ?: 0L
            toGoodsDetail(goodsId)
        }
    }

    /**
     * 跳转到商品详情页面（再次购买）
     */
    fun toGoodsDetail(goodsId: Long) {
        // 隐藏弹窗
        hideRebuyModal()
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
        val cartList = _cartList.value
        if (cartList.size > 1) {
            // 多个商品时显示弹窗让用户选择
            showCommentModal()
        } else {
            // 单个商品时直接跳转
            val order = super.getSuccessData()
            val orderId = order.id
            val goodsId = cartList.firstOrNull()?.goodsId ?: 0L

            // 构建带参数的评价路由：/order/comment/{orderId}/{goodsId}
            val commentRoute = OrderCommentRoutes.COMMENT_PATTERN
                .replace("{${OrderCommentRoutes.ORDER_ID_ARG}}", orderId.toString())
                .replace("{${OrderCommentRoutes.GOODS_ID_ARG}}", goodsId.toString())

            toPage(commentRoute)
        }
    }

    /**
     * 跳转到指定商品的订单评价页面
     */
    fun toOrderCommentForGoods(goodsId: Long) {
        val order = super.getSuccessData()
        val orderId = order.id
        // 隐藏弹窗
        hideCommentModal()

        // 构建带参数的评价路由：/order/comment/{orderId}/{goodsId}
        val commentRoute = OrderCommentRoutes.COMMENT_PATTERN
            .replace("{${OrderCommentRoutes.ORDER_ID_ARG}}", orderId.toString())
            .replace("{${OrderCommentRoutes.GOODS_ID_ARG}}", goodsId.toString())

        toPage(commentRoute)
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