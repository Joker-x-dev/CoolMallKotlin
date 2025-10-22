package com.joker.coolmall.core.common.base.state

/**
 * 加载更多状态
 *
 * @author Joker.X
 */
sealed class LoadMoreState {
    /**
     * 可上拉加载更多状态
     */
    object PullToLoad : LoadMoreState()

    /**
     * 加载中状态
     */
    object Loading : LoadMoreState()

    /**
     * 成功状态
     */
    object Success : LoadMoreState()

    /**
     * 错误状态
     */
    object Error : LoadMoreState()

    /**
     * 没有更多数据状态
     */
    object NoMore : LoadMoreState()
}