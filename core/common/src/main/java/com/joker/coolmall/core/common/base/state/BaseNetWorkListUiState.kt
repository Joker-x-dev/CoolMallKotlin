package com.joker.coolmall.core.common.base.state

/**
 * 列表页面的 UI 状态
 */
sealed class BaseNetWorkListUiState {
    /**
     * 加载中状态
     */
    object Loading : BaseNetWorkListUiState()

    /**
     * 成功状态
     */
    object Success : BaseNetWorkListUiState()

    /**
     * 错误状态
     */
    object Error : BaseNetWorkListUiState()

    /**
     * 空数据状态
     */
    object Empty : BaseNetWorkListUiState()
}