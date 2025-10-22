package com.joker.coolmall.core.common.base.state

/**
 * 列表页UI状态
 *
 * 封装列表页面的四种状态：加载中、成功、错误和空数据
 *
 * @author Joker.X
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