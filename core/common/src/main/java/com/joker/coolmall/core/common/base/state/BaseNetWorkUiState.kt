package com.joker.coolmall.core.common.base.state

/**
 * 网络请求UI状态
 * 只包含最基本的网络请求状态
 *
 * @param T 数据类型
 * @author Joker.X
 */
sealed class BaseNetWorkUiState<out T> {
    /**
     * 加载中状态
     */
    data object Loading : BaseNetWorkUiState<Nothing>()

    /**
     * 成功状态
     *
     * @param data 成功返回的数据
     */
    data class Success<T>(var data: T) : BaseNetWorkUiState<T>()

    /**
     * 错误状态
     *
     * @param message 错误信息，可为空
     * @param exception 异常信息
     */
    data class Error(val message: String? = null, val exception: Throwable? = null) :
        BaseNetWorkUiState<Nothing>()
}