package com.joker.coolmall.feature.order.model

import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.model.entity.Order

/**
 * 订单标签页状态
 *
 * 用于封装每个标签页的状态数据和回调
 */
data class OrderTabState(
    val uiState: BaseNetWorkListUiState,
    val orderList: List<Order>,
    val isRefreshing: Boolean,
    val loadMoreState: LoadMoreState,
    val onRetry: () -> Unit,
    val onRefresh: () -> Unit,
    val onLoadMore: () -> Unit,
    val shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean
)