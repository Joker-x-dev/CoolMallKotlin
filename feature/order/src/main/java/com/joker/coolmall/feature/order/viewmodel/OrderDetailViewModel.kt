package com.joker.coolmall.feature.order.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.toRoute
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
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.RefreshResultKey
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
 *
 * @param navigator 导航器
 * @param appState 应用状态
 * @param savedStateHandle 保存状态句柄
 * @param orderRepository 订单仓库
 * @param commonRepository 通用仓库
 * @author Joker.X
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
    appState = appState
) {
    // 从路由获取订单ID
    private val detailRoute = savedStateHandle.toRoute<OrderRoutes.Detail>()
    private val requiredOrderId: Long = detailRoute.orderId

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
     *
     * @author Joker.X
     */
    override fun requestApiFlow(): Flow<NetworkResponse<Order>> {
        return orderRepository.getOrderInfo(requiredOrderId)
    }

    /**
     * 处理请求成功的逻辑
     *
     * @author Joker.X
     */
    override fun onRequestSuccess(data: Order) {
        _cartList.value = convertOrderGoodsToCart(data)
        super.setSuccessState(data)
    }

    /**
     * 确认收货
     *
     * @author Joker.X
     */
    fun confirmOrder() {
        showConfirmReceiveDialog()
    }

    /**
     * 显示确认收货弹窗
     *
     * @author Joker.X
     */
    fun showConfirmReceiveDialog() {
        _showConfirmDialog.value = true
    }

    /**
     * 隐藏确认收货弹窗
     *
     * @author Joker.X
     */
    fun hideConfirmReceiveDialog() {
        _showConfirmDialog.value = false
    }

    /**
     * 确认收货订单
     *
     * @author Joker.X
     */
    fun confirmReceiveOrder() {
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = orderRepository.confirmReceive(requiredOrderId).asResult(),
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
     *
     * @author Joker.X
     */
    fun cancelOrder() {
        showCancelModal()
    }

    /**
     * 取消订单弹窗展开完成后加载取消原因
     *
     * 由 View 层在弹窗动画完成后调用，避免在动画过程中网络请求卡顿UI
     *
     * @author Joker.X
     */
    fun onCancelModalExpanded() {
        loadCancelReasons()
    }

    /**
     * 加载取消原因字典数据
     *
     * @author Joker.X
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
     *
     * @author Joker.X
     */
    fun showCancelModal() {
        _cancelModalVisible.value = true
    }

    /**
     * 隐藏取消原因选择弹窗
     *
     * @author Joker.X
     */
    fun hideCancelModal() {
        _cancelModalVisible.value = false
    }

    /**
     * 选择取消原因
     *
     * @author Joker.X
     */
    fun selectCancelReason(reason: DictItem) {
        _selectedCancelReason.value = reason
    }

    /**
     * 确认取消订单
     *
     * @author Joker.X
     */
    fun confirmCancelOrder() {
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = orderRepository.cancelOrder(
                CancelOrderRequest(
                    orderId = requiredOrderId,
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
     *
     * @author Joker.X
     */
    fun navigateToPayment() {
        val order = super.getSuccessData()
        val orderId = order.id
        val paymentPrice = order.price - order.discountPrice // 实付金额

        navigate(OrderRoutes.Pay(orderId = orderId, price = paymentPrice))
    }

    /**
     * 观察来自支付页面的刷新状态
     * 当从支付页面返回时，如果支付成功，则刷新订单详情
     *
     * @author Joker.X
     */
    fun observeRefreshState(backStackEntry: NavBackStackEntry?) {
        backStackEntry?.savedStateHandle?.let { savedStateHandle ->
            viewModelScope.launch {
                savedStateHandle.getStateFlow(RefreshResultKey.key, false)
                    .collect { shouldRefresh ->
                        if (shouldRefresh) {
                            // 刷新订单详情
                            retryRequest()

                            // 标记需要在返回时刷新列表
                            shouldRefreshListOnBack = true

                            // 重置刷新标志，避免重复刷新
                            savedStateHandle[RefreshResultKey.key] = false
                        }
                    }
            }
        }
    }

    /**
     * 处理返回按钮点击
     * 根据是否需要刷新列表决定传递参数
     *
     * @author Joker.X
     */
    fun handleBackClick() {
        if (shouldRefreshListOnBack) {
            // 使用 NavigationResult 回传刷新状态给上一个页面（通常是订单列表）
            popBackStackWithResult(RefreshResultKey, true)
            shouldRefreshListOnBack = false
        } else {
            navigateBack()
        }
    }

    /**
     * 显示再次购买弹窗
     *
     * @author Joker.X
     */
    fun showRebuyModal() {
        _rebuyModalVisible.value = true
    }

    /**
     * 隐藏再次购买弹窗
     *
     * @author Joker.X
     */
    fun hideRebuyModal() {
        _rebuyModalVisible.value = false
    }

    /**
     * 显示商品评论弹窗
     *
     * @author Joker.X
     */
    fun showCommentModal() {
        _commentModalVisible.value = true
    }

    /**
     * 隐藏商品评论弹窗
     *
     * @author Joker.X
     */
    fun hideCommentModal() {
        _commentModalVisible.value = false
    }

    /**
     * 处理再次购买逻辑
     *
     * @author Joker.X
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
     *
     * @author Joker.X
     */
    fun toGoodsDetail(goodsId: Long) {
        // 隐藏弹窗
        hideRebuyModal()
        navigate(GoodsRoutes.Detail(goodsId = goodsId))
    }

    /**
     * 跳转到订单物流页面
     *
     * @author Joker.X
     */
    fun toOrderLogistics() {
        val order = super.getSuccessData()
        val orderId = order.id
        navigate(OrderRoutes.Logistics(orderId = orderId))
    }

    /**
     * 跳转到退款申请页面
     *
     * @author Joker.X
     */
    fun toOrderRefund() {
        val order = super.getSuccessData()
        val orderId = order.id
        navigate(OrderRoutes.Refund(orderId = orderId))
    }

    /**
     * 跳转到订单评价页面
     *
     * @author Joker.X
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
            navigate(OrderRoutes.Comment(orderId = orderId, goodsId = goodsId))
        }
    }

    /**
     * 跳转到指定商品的订单评价页面
     *
     * @author Joker.X
     */
    fun toOrderCommentForGoods(goodsId: Long) {
        val order = super.getSuccessData()
        val orderId = order.id
        // 隐藏弹窗
        hideCommentModal()

        navigate(OrderRoutes.Comment(orderId = orderId, goodsId = goodsId))
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