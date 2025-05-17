package com.joker.coolmall.feature.order.viewmodel

import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkListViewModel
import com.joker.coolmall.core.data.repository.OrderRepository
import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.model.request.OrderPageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.feature.order.model.OrderStatus
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * 订单列表视图模型
 */
@HiltViewModel
class OrderListViewModel @Inject constructor(
    navigator: AppNavigator,
    private val orderRepository: OrderRepository
) : BaseNetWorkListViewModel<Order>(navigator) {

    /**
     * 当前选中的标签索引
     */
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    init {
        initLoad()
    }

    /**
     * 实现基类的抽象方法，提供订单列表数据的请求
     */
    override fun requestListData(): Flow<NetworkResponse<NetworkPageData<Order>>> {
        return orderRepository.getOrderPage(
            OrderPageRequest(
                page = super.currentPage,
                size = super.pageSize,
                status = getCurrentStatusFilter()
            )
        )
    }

    /**
     * 更新选中的标签
     */
    fun updateSelectedTab(index: Int) {
        if (_selectedTabIndex.value != index) {
            _selectedTabIndex.value = index
            currentPage = 1
            _loadMoreState.value = LoadMoreState.Loading
            loadListData()
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