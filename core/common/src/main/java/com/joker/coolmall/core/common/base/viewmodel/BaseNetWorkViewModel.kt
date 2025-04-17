package com.joker.coolmall.core.common.base.viewmodel

import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.common.result.Result
import com.joker.coolmall.core.common.result.asResult
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.AppNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * 网络请求ViewModel基类
 *
 * 基于Flow的异步数据流模式，子类只需重写fetchFlow方法
 *
 * @param T 数据类型
 */
abstract class BaseNetWorkViewModel<T> constructor(
    navigator: AppNavigator
) : BaseViewModel<T>(navigator) {

    // 网络请求UI状态，初始为加载中状态
    private val _uiState = MutableStateFlow<BaseNetWorkUiState<T>>(BaseNetWorkUiState.Loading as BaseNetWorkUiState<T>)
    val uiState: StateFlow<BaseNetWorkUiState<T>> = _uiState.asStateFlow()

    /**
     * 子类必须重写此方法，返回Flow<NetworkResponse<T>>类型的数据流
     * 注意：此方法不应在基类构造函数中调用，以避免子类属性初始化问题
     */
    protected abstract fun fetchFlow(): Flow<NetworkResponse<T>>

    /**
     * 加载数据
     * 自动处理状态管理和错误处理
     */
    fun loadData() {
        viewModelScope.launch {
            try {
                val flow = fetchFlow() // 确保调用此方法时子类已完全初始化
                flow.asResult()
                    .collectLatest { result ->
                        when (result) {
                            is Result.Loading -> setStateLoading()
                            is Result.Success -> {
                                val networkResponse = result.data
                                if (networkResponse.isSucceeded && networkResponse.data != null) {
                                    handleSuccess(networkResponse.data!!)
                                } else {
                                    handleError(
                                        networkResponse.message ?: "未知错误",
                                        Exception(networkResponse.message)
                                    )
                                }
                            }

                            is Result.Error -> {
                                val message = result.exception.message ?: "未知错误"
                                handleError(message, result.exception)
                            }
                        }
                    }
            } catch (e: Exception) {
                // 捕获任何异常，包括NullPointerException
                handleError(e.message ?: "加载数据时发生错误", e)
            }
        }
    }

    /**
     * 处理成功结果，子类可重写此方法自定义处理逻辑
     */
    protected open fun handleSuccess(data: T) {
        setStateSuccess(data)
    }

    /**
     * 处理错误结果，子类可重写此方法自定义处理逻辑
     */
    protected open fun handleError(message: String, exception: Throwable) {
        setStateError(message, exception)
    }

    /**
     * 重试加载数据
     */
    fun retry() {
        setStateLoading()
        loadData()
    }

    /**
     * 设置网络状态为加载中
     */
    protected fun setStateLoading() {
        _uiState.value = BaseNetWorkUiState.Loading as BaseNetWorkUiState<T>
    }

    /**
     * 设置网络状态为成功
     *
     * @param data 成功返回的数据
     */
    protected fun setStateSuccess(data: T) {
        _uiState.value = BaseNetWorkUiState.Success(data)
    }

    /**
     * 设置网络状态为错误
     *
     * @param message 错误信息
     * @param exception 异常信息
     */
    protected fun setStateError(message: String? = null, exception: Throwable? = null) {
        _uiState.value = BaseNetWorkUiState.Error(message, exception) as BaseNetWorkUiState<T>
    }
}