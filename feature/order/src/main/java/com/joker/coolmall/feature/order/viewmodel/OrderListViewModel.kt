package com.joker.coolmall.feature.order.viewmodel

import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.repository.OrderRepository
import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.model.request.OrderPageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.ui.component.loading.LoadMoreState
import com.joker.coolmall.feature.order.model.OrderStatus
import com.joker.coolmall.navigation.AppNavigator
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
    private val orderRepository: OrderRepository
) : BaseViewModel(navigator) {

    /**
     * 页码和分页大小
     */
    private var currentPage = 1

    /**
     * 每页数量
     */
    private val pageSize = 6

    /**
     * 网络请求UI状态
     */
    private val _uiState = MutableStateFlow<BaseNetWorkListUiState>(BaseNetWorkListUiState.Loading)
    val uiState: StateFlow<BaseNetWorkListUiState> = _uiState.asStateFlow()

    /**
     * 订单列表数据
     */
    private val _orderList = MutableStateFlow<List<Order>>(emptyList())
    val orderList: StateFlow<List<Order>> = _orderList.asStateFlow()

    /**
     * 加载更多状态
     */
    private val _loadMoreState = MutableStateFlow<LoadMoreState>(LoadMoreState.PullToLoad)
    val loadMoreState: StateFlow<LoadMoreState> = _loadMoreState.asStateFlow()

    /**
     * 下拉刷新状态 (仅用于PullToRefresh组件)
     */
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    /**
     * 当前选中的标签索引
     */
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    init {
        loadOrders()
    }

    /**
     * 加载订单列表
     */
    private fun loadOrders() {
        // 设置UI状态 - 仅首次加载显示加载中状态
        if (_loadMoreState.value == LoadMoreState.Loading && currentPage == 1) {
            _uiState.value = BaseNetWorkListUiState.Loading
        }

        ResultHandler.handleResult(
            scope = viewModelScope,
            flow = orderRepository.getOrderPage(
                OrderPageRequest(
                    page = currentPage,
                    size = pageSize,
                    status = getCurrentStatusFilter()
                )
            ).asResult(),
            onSuccess = { response ->
                handleSuccess(response.data)
            },
            onError = { message, exception ->
                handleError(message, exception)
            }
        )
    }

    /**
     * 处理成功响应
     */
    private fun handleSuccess(data: NetworkPageData<Order>?) {
        val newList = data?.list ?: emptyList()
        val pagination = data?.pagination

        // 计算是否还有下一页数据
        val hasNextPage = if (pagination != null) {
            val total = pagination.total ?: 0
            val size = pagination.size ?: pageSize
            val currentPageNum = pagination.page ?: currentPage

            // 当前页的数据量 * 当前页码 < 总数据量，说明还有下一页
            size * currentPageNum < total
        } else {
            false
        }

        when {
            currentPage == 1 -> {
                // 刷新或首次加载 - 重置列表
                _orderList.value = newList
                _isRefreshing.value = false

                // 更新加载状态
                if (newList.isEmpty()) {
                    _uiState.value = BaseNetWorkListUiState.Empty
                } else {
                    _uiState.value = BaseNetWorkListUiState.Success
                    _loadMoreState.value =
                        if (hasNextPage) LoadMoreState.PullToLoad else LoadMoreState.NoMore
                }
            }

            else -> {
                // 加载更多 - 先显示加载成功，延迟更新数据
                viewModelScope.launch {
                    _loadMoreState.value = LoadMoreState.Success
                    delay(400)
                    _orderList.value = _orderList.value + newList
                    _loadMoreState.value =
                        if (hasNextPage) LoadMoreState.PullToLoad else LoadMoreState.NoMore
                }
            }
        }
    }

    /**
     * 处理错误响应
     */
    private fun handleError(message: String?, exception: Throwable?) {
        _isRefreshing.value = false

        if (currentPage == 1) {
            // 首次加载或刷新失败
            if (_orderList.value.isEmpty()) {
                _uiState.value = BaseNetWorkListUiState.Error
            }
            _loadMoreState.value = LoadMoreState.PullToLoad
        } else {
            // 加载更多失败，回退页码
            currentPage--
            _loadMoreState.value = LoadMoreState.Error
        }
    }

    /**
     * 重试请求
     */
    fun retryRequest() {
        currentPage = 1
        _loadMoreState.value = LoadMoreState.Loading
        loadOrders()
    }

    /**
     * 触发下拉刷新
     */
    fun onRefresh() {
        // 如果正在加载中，则不重复请求
        if (_loadMoreState.value == LoadMoreState.Loading) {
            return
        }

        _isRefreshing.value = true
        currentPage = 1
        loadOrders()
    }

    /**
     * 加载更多数据
     */
    fun onLoadMore() {
        // 只有在可加载更多状态下才能触发加载
        if (_loadMoreState.value != LoadMoreState.PullToLoad) {
            return
        }

        _loadMoreState.value = LoadMoreState.Loading
        currentPage++
        loadOrders()
    }

    /**
     * 更新选中的标签
     */
    fun updateSelectedTab(index: Int) {
        if (_selectedTabIndex.value != index) {
            _selectedTabIndex.value = index
            currentPage = 1
            _loadMoreState.value = LoadMoreState.Loading
            loadOrders()
        }
    }

    /**
     * 获取当前状态的过滤条件
     */
    private fun getCurrentStatusFilter(): List<Int>? {
        return when (OrderStatus.entries[_selectedTabIndex.value]) {
            OrderStatus.ALL -> null
            OrderStatus.UNPAID -> listOf(0)
            OrderStatus.UNSHIPPED -> listOf(1)
            OrderStatus.UNRECEIVED -> listOf(2)
            OrderStatus.AFTER_SALE -> listOf(5, 6)
            OrderStatus.UNEVALUATED -> listOf(3)
            OrderStatus.COMPLETED -> listOf(4)
        }
    }
} 