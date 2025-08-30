package com.joker.coolmall.feature.order.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.repository.CommonRepository
import com.joker.coolmall.core.data.repository.OrderRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.DictItem
import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.model.request.CancelOrderRequest
import com.joker.coolmall.core.model.request.DictDataRequest
import com.joker.coolmall.core.model.request.OrderPageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.feature.order.model.OrderStatus
import com.joker.coolmall.feature.order.navigation.OrderPayRoutes
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.GoodsRoutes
import com.joker.coolmall.navigation.routes.OrderRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 订单列表视图模型
 */
@HiltViewModel
class OrderListViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    private val orderRepository: OrderRepository,
    private val commonRepository: CommonRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(navigator, appState) {

    /**
     * 当前选中的标签索引
     */
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    /**
     * 是否正在进行标签切换动画
     */
    private val _isAnimatingTabChange = MutableStateFlow(false)
    val isAnimatingTabChange: StateFlow<Boolean> = _isAnimatingTabChange.asStateFlow()

    /**
     * 每个标签页的页码
     */
    private val pageIndices = MutableList(OrderStatus.entries.size) { 1 }

    /**
     * 标记每个标签页是否已加载过数据
     */
    private val tabDataLoaded = MutableList(OrderStatus.entries.size) { false }

    /**
     * 每页大小
     */
    private val pageSize = 10

    /**
     * 每个标签页的网络请求UI状态
     */
    private val _uiStates = OrderStatus.entries.map {
        MutableStateFlow<BaseNetWorkListUiState>(BaseNetWorkListUiState.Loading)
    }
    val uiStates: List<StateFlow<BaseNetWorkListUiState>> = _uiStates.map { it.asStateFlow() }

    /**
     * 每个标签页的列表数据
     */
    private val _listDataMap = OrderStatus.entries.map {
        MutableStateFlow<List<Order>>(emptyList())
    }
    val listDataMap: List<StateFlow<List<Order>>> = _listDataMap.map { it.asStateFlow() }

    /**
     * 每个标签页的加载更多状态
     */
    private val _loadMoreStates = OrderStatus.entries.map {
        MutableStateFlow<LoadMoreState>(LoadMoreState.PullToLoad)
    }
    val loadMoreStates: List<StateFlow<LoadMoreState>> = _loadMoreStates.map { it.asStateFlow() }

    /**
     * 每个标签页的下拉刷新状态
     */
    private val _refreshingStates = OrderStatus.entries.map {
        MutableStateFlow(false)
    }

    /**
     * 每个标签页的下拉刷新状态
     */
    val refreshingStates: List<StateFlow<Boolean>> = _refreshingStates.map { it.asStateFlow() }

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
     * 当前要取消的订单ID
     */
    private var _currentCancelOrderId: Long = 0L

    /**
     * 确认收货弹窗的显示状态
     */
    private val _showConfirmDialog = MutableStateFlow(false)
    val showConfirmDialog: StateFlow<Boolean> = _showConfirmDialog.asStateFlow()

    /**
     * 当前要确认收货的订单ID
     */
    private var _currentConfirmOrderId: Long = 0L

    init {
        // 从URL参数中获取初始标签索引
        savedStateHandle.get<String>("tab")?.toIntOrNull()?.let { tabIndex ->
            if (tabIndex in OrderStatus.entries.indices) {
                _selectedTabIndex.value = tabIndex
            }
        }

        // 加载当前选中标签页的数据
        loadTabDataIfNeeded(_selectedTabIndex.value)
    }

    /**
     * 观察来自其他页面的刷新状态
     * 当从其他页面返回时，如果刷新标志为true，则刷新订单列表
     */
    fun observeRefreshState(backStackEntry: NavBackStackEntry?) {
        backStackEntry?.savedStateHandle?.let { savedStateHandle ->
            viewModelScope.launch {
                savedStateHandle.getStateFlow<Boolean>("refresh", false).collect { shouldRefresh ->
                    if (shouldRefresh) {
                        // 刷新全部标签页
                        refreshSpecificTabs(listOf(0, 1, 2, 3, 4, 5, 6))

                        // 重置刷新标志，避免重复刷新
                        savedStateHandle["refresh"] = false
                    }
                }
            }
        }
    }

    /**
     * 重置指定标签页的加载状态
     */
    private fun resetTabLoadState(tabIndex: Int) {
        if (tabIndex in tabDataLoaded.indices) {
            tabDataLoaded[tabIndex] = false
            pageIndices[tabIndex] = 1
        }
    }

    /**
     * 刷新指定的标签页
     * @param tabIndices 需要刷新的标签页索引列表
     */
    private fun refreshSpecificTabs(tabIndices: List<Int>) {
        // 重置指定标签页的加载状态
        tabIndices.forEach { tabIndex ->
            if (tabIndex in tabDataLoaded.indices) {
                resetTabLoadState(tabIndex)
            }
        }
        
        // 如果当前显示的标签页在刷新列表中，则立即刷新
        val currentTab = _selectedTabIndex.value
        if (currentTab in tabIndices) {
            loadTabDataIfNeeded(currentTab)
        }
    }

    /**
     * 如果标签页数据尚未加载，则加载数据
     */
    private fun loadTabDataIfNeeded(tabIndex: Int) {
        if (!tabDataLoaded[tabIndex]) {
            // 标记该标签页已尝试加载数据
            tabDataLoaded[tabIndex] = true
            // 加载该标签页的数据
            loadListData(tabIndex)
        }
    }

    /**
     * 加载指定标签页的列表数据
     */
    private fun loadListData(tabIndex: Int) {
        // 设置UI状态 - 仅首次加载显示加载中状态
        if (_loadMoreStates[tabIndex].value == LoadMoreState.Loading && pageIndices[tabIndex] == 1) {
            _uiStates[tabIndex].value = BaseNetWorkListUiState.Loading
        }

        ResultHandler.handleResult(
            showToast = false,
            scope = viewModelScope,
            flow = orderRepository.getOrderPage(
                OrderPageRequest(
                    page = pageIndices[tabIndex],
                    size = pageSize,
                    status = getStatusFilter(tabIndex)
                )
            ).asResult(),
            onSuccess = { response ->
                handleSuccess(tabIndex, response.data)
            },
            onError = { message, exception ->
                handleError(tabIndex, message, exception)
            }
        )
    }

    /**
     * 处理成功响应
     */
    private fun handleSuccess(tabIndex: Int, data: NetworkPageData<Order>?) {
        val newList = data?.list ?: emptyList()
        val pagination = data?.pagination

        // 计算是否还有下一页数据
        val hasNextPage = if (pagination != null) {
            val total = pagination.total ?: 0
            val size = pagination.size ?: pageSize
            val currentPageNum = pagination.page ?: pageIndices[tabIndex]

            // 当前页的数据量 * 当前页码 < 总数据量，说明还有下一页
            size * currentPageNum < total
        } else {
            false
        }

        when {
            pageIndices[tabIndex] == 1 -> {
                // 刷新或首次加载 - 重置列表
                _listDataMap[tabIndex].value = newList
                _refreshingStates[tabIndex].value = false

                // 更新加载状态
                if (newList.isEmpty()) {
                    _uiStates[tabIndex].value = BaseNetWorkListUiState.Empty
                } else {
                    _uiStates[tabIndex].value = BaseNetWorkListUiState.Success
                    _loadMoreStates[tabIndex].value =
                        if (hasNextPage) LoadMoreState.PullToLoad else LoadMoreState.NoMore
                }
            }

            else -> {
                // 加载更多 - 先显示加载成功，延迟更新数据
                viewModelScope.launch {
                    _loadMoreStates[tabIndex].value = LoadMoreState.Success
                    delay(400)
                    _listDataMap[tabIndex].value = _listDataMap[tabIndex].value + newList
                    _loadMoreStates[tabIndex].value =
                        if (hasNextPage) LoadMoreState.PullToLoad else LoadMoreState.NoMore
                }
            }
        }
    }

    /**
     * 处理错误响应
     */
    private fun handleError(tabIndex: Int, message: String?, exception: Throwable?) {
        _refreshingStates[tabIndex].value = false

        if (pageIndices[tabIndex] == 1) {
            // 首次加载或刷新失败
            if (_listDataMap[tabIndex].value.isEmpty()) {
                _uiStates[tabIndex].value = BaseNetWorkListUiState.Error
            }
            _loadMoreStates[tabIndex].value = LoadMoreState.PullToLoad
        } else {
            // 加载更多失败，回退页码
            pageIndices[tabIndex]--
            _loadMoreStates[tabIndex].value = LoadMoreState.Error
        }
    }

    /**
     * 重试加载
     */
    fun retryRequest(tabIndex: Int = _selectedTabIndex.value) {
        pageIndices[tabIndex] = 1
        _loadMoreStates[tabIndex].value = LoadMoreState.Loading
        loadListData(tabIndex)
    }

    /**
     * 触发下拉刷新
     */
    fun onRefresh(tabIndex: Int = _selectedTabIndex.value) {
        // 如果正在加载中，则不重复请求
        if (_loadMoreStates[tabIndex].value == LoadMoreState.Loading) {
            return
        }

        _refreshingStates[tabIndex].value = true
        pageIndices[tabIndex] = 1
        loadListData(tabIndex)
    }

    /**
     * 加载更多数据
     */
    fun onLoadMore(tabIndex: Int = _selectedTabIndex.value) {
        // 只有在可加载更多和加载失败状态下才能触发加载
        if (_loadMoreStates[tabIndex].value == LoadMoreState.Loading ||
            _loadMoreStates[tabIndex].value == LoadMoreState.NoMore ||
            _loadMoreStates[tabIndex].value == LoadMoreState.Success
        ) {
            return
        }

        _loadMoreStates[tabIndex].value = LoadMoreState.Loading
        pageIndices[tabIndex]++
        loadListData(tabIndex)
    }

    /**
     * 判断是否应该触发加载更多
     */
    fun shouldTriggerLoadMore(
        lastIndex: Int,
        totalCount: Int,
        tabIndex: Int = _selectedTabIndex.value
    ): Boolean {
        return lastIndex >= totalCount - 3 &&
                _loadMoreStates[tabIndex].value != LoadMoreState.Loading &&
                _loadMoreStates[tabIndex].value != LoadMoreState.NoMore &&
                _listDataMap[tabIndex].value.isNotEmpty()
    }

    /**
     * 更新选中的标签
     */
    fun updateSelectedTab(index: Int) {
        if (_selectedTabIndex.value != index) {
            _selectedTabIndex.value = index
            _isAnimatingTabChange.value = true

            // 当切换到新标签页时，检查并按需加载数据
            loadTabDataIfNeeded(index)
        }
    }

    /**
     * 通知标签切换动画已完成
     */
    fun notifyAnimationCompleted() {
        _isAnimatingTabChange.value = false
    }

    /**
     * 根据页面滑动更新选中的标签
     */
    fun updateTabByPage(index: Int) {
        if (!_isAnimatingTabChange.value) {
            _selectedTabIndex.value = index

            // 当滑动到新标签页时，检查并按需加载数据
            loadTabDataIfNeeded(index)
        }
    }

    /**
     * 获取指定标签的状态过滤条件
     */
    private fun getStatusFilter(tabIndex: Int): List<Int>? {
        return when (OrderStatus.entries[tabIndex]) {
            OrderStatus.ALL -> null
            OrderStatus.UNPAID -> listOf(0)
            OrderStatus.UNSHIPPED -> listOf(1)
            OrderStatus.UNRECEIVED -> listOf(2)
            OrderStatus.AFTER_SALE -> listOf(5, 6)
            OrderStatus.UNEVALUATED -> listOf(3)
            OrderStatus.COMPLETED -> listOf(4)
        }
    }

    /**
     * 跳转到订单详情页面
     */
    fun toOrderDetailPage(orderId: Long) {
        super.toPage(OrderRoutes.DETAIL, orderId)
    }

    /**
     * 跳转到支付页面
     *
     * @param order 订单对象
     */
    fun toPaymentPage(order: Order) {
        val orderId = order.id
        val paymentPrice = order.price - order.discountPrice // 实付金额

        // 构建带参数的支付路由：/order/pay/{orderId}/{paymentPrice}
        val paymentRoute = OrderPayRoutes.ORDER_PAY_PATTERN
            .replace("{${OrderPayRoutes.ORDER_ID_ARG}}", orderId.toString())
            .replace("{${OrderPayRoutes.PRICE_ARG}}", paymentPrice.toString())

        toPage(paymentRoute)
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
    fun toOrderLogistics(orderId: Long) {
        toPage(OrderRoutes.LOGISTICS, orderId)
    }

    /**
     * 跳转到退款申请页面
     */
    fun toOrderRefund(orderId: Long) {
        toPage(OrderRoutes.REFUND, orderId)
    }

    /**
     * 跳转到订单评价页面
     */
    fun toOrderComment(orderId: Long) {
        toPage(OrderRoutes.COMMENT, orderId)
    }

    /**
     * 取消订单
     */
    fun cancelOrder(orderId: Long) {
        _currentCancelOrderId = orderId
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
        _selectedCancelReason.value = null
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
                    orderId = _currentCancelOrderId,
                    remark = _selectedCancelReason.value?.name ?: ""
                )
            ).asResult(),
            onData = { _ ->
                // 取消订单会影响：全部(0)、待付款(1)、待发货(2)
                refreshSpecificTabs(listOf(0, 1, 2))
            }
        )
    }

    /**
     * 显示确认收货弹窗
     */
    fun showConfirmReceiveDialog(orderId: Long) {
        _currentConfirmOrderId = orderId
        _showConfirmDialog.value = true
    }

    /**
     * 隐藏确认收货弹窗
     */
    fun hideConfirmReceiveDialog() {
        _showConfirmDialog.value = false
        _currentConfirmOrderId = 0L
    }

    /**
     * 确认收货
     */
    fun confirmReceiveOrder() {
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = orderRepository.confirmReceive(_currentConfirmOrderId).asResult(),
            onData = { _ ->
                // 隐藏弹窗
                hideConfirmReceiveDialog()
                
                // 确认收货会影响：全部(0)、待收货(3)、待评价(5)
                refreshSpecificTabs(listOf(0, 3, 5))
            }
        )
    }
}