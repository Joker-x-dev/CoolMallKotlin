package com.joker.coolmall.feature.order.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
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
import com.joker.coolmall.core.model.request.DictDataRequest
import com.joker.coolmall.core.model.request.RefundOrderRequest
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.RefreshResultKey
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
 * 退款申请 ViewModel
 *
 * @param navigator 导航器
 * @param appState 应用状态
 * @param savedStateHandle 保存状态句柄
 * @param orderRepository 订单仓库
 * @param commonRepository 通用仓库
 * @author Joker.X
 */
@HiltViewModel
class OrderRefundViewModel @Inject constructor(
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
    private val refundRoute = savedStateHandle.toRoute<OrderRoutes.Refund>()
    private val requiredOrderId: Long = refundRoute.orderId

    /**
     * 退款原因选择弹窗的显示状态
     */
    private val _refundModalVisible = MutableStateFlow(false)
    val refundModalVisible: StateFlow<Boolean> = _refundModalVisible.asStateFlow()

    /**
     * 退款原因弹出层 ui 状态
     */
    private val _refundReasonsModalUiState =
        MutableStateFlow<BaseNetWorkUiState<List<DictItem>>>(BaseNetWorkUiState.Loading)
    val refundReasonsModalUiState: StateFlow<BaseNetWorkUiState<List<DictItem>>> =
        _refundReasonsModalUiState.asStateFlow()

    /**
     * 选中的退款原因
     */
    private val _selectedRefundReason = MutableStateFlow<DictItem?>(null)
    val selectedRefundReason: StateFlow<DictItem?> = _selectedRefundReason.asStateFlow()

    /**
     * 转换后的购物车列表
     */
    private val _cartList = MutableStateFlow<List<Cart>>(emptyList())
    val cartList = _cartList.asStateFlow()

    init {
        super.executeRequest()
    }

    /**
     * 重写请求API的方法
     *
     * @return 订单网络响应流
     * @author Joker.X
     */
    override fun requestApiFlow(): Flow<NetworkResponse<Order>> {
        return orderRepository.getOrderInfo(requiredOrderId)
    }

    /**
     * 处理请求成功的逻辑
     *
     * @param data 订单数据
     * @author Joker.X
     */
    override fun onRequestSuccess(data: Order) {
        _cartList.value = convertOrderGoodsToCart(data)
        super.setSuccessState(data)
    }

    /**
     * 显示退款原因选择弹窗
     *
     * @author Joker.X
     */
    fun showRefundModal() {
        _refundModalVisible.value = true
    }

    /**
     * 退款弹窗展开完成后加载退款原因
     *
     * 由 View 层在弹窗动画完成后调用，避免在动画过程中网络请求卡顿UI
     *
     * @author Joker.X
     */
    fun onRefundModalExpanded() {
        loadRefundReasons()
    }

    /**
     * 隐藏退款原因选择弹窗
     *
     * @author Joker.X
     */
    fun hideRefundModal() {
        _refundModalVisible.value = false
    }

    /**
     * 选择退款原因
     *
     * @param reason 退款原因
     * @author Joker.X
     */
    fun selectRefundReason(reason: DictItem?) {
        _selectedRefundReason.value = reason
    }

    /**
     * 加载退款原因字典数据
     *
     * @author Joker.X
     */
    fun loadRefundReasons() {
        // 如果 ui 状态为成功，则不重复加载
        if (_refundReasonsModalUiState.value is BaseNetWorkUiState.Success) {
            return
        }
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = commonRepository.getDictData(
                DictDataRequest(
                    types = listOf("orderRefundReason")
                )
            ).asResult(),
            showToast = false,
            onLoading = { _refundReasonsModalUiState.value = BaseNetWorkUiState.Loading },
            onData = { data ->
                _refundReasonsModalUiState.value =
                    BaseNetWorkUiState.Success(data.orderRefundReason!!)
            },
            onError = { _, _ ->
                _refundReasonsModalUiState.value = BaseNetWorkUiState.Error()
            }
        )
    }

    /**
     * 提交退款申请
     *
     * @author Joker.X
     */
    fun submitRefundApplication() {
        val selectedReason = _selectedRefundReason.value
        if (selectedReason == null) return

        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = orderRepository.refundOrder(
                RefundOrderRequest(
                    orderId = requiredOrderId,
                    reason = selectedReason.name ?: ""
                )
            ).asResult(),
            onData = { _ ->
                // 使用 NavigationResult 回传刷新信号，通知上一个页面刷新
                popBackStackWithResult(RefreshResultKey, true)
            }
        )
    }

    /**
     * 将Order中的goodsList转换为Cart类型的列表
     * 参考OrderDetailViewModel中的处理方法
     *
     * @param order 订单对象
     * @return 购物车列表
     * @author Joker.X
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