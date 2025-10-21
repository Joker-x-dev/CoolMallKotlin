package com.joker.coolmall.feature.order.model

import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.model.entity.Order

/**
 * 订单标签页状态
 *
 * 用于封装每个标签页的状态数据和回调
 *
 * @param uiState UI网络状态
 * @param orderList 订单列表
 * @param isRefreshing 是否正在刷新
 * @param loadMoreState 加载更多状态
 * @param onRetry 重试回调
 * @param onRefresh 刷新回调
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 是否应触发加载更多的判断函数
 * @author Joker.X
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