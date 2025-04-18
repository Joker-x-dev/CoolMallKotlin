package com.joker.coolmall.core.common.base.viewmodel

import androidx.lifecycle.SavedStateHandle
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
 * 基于Flow的异步数据流模式，子类只需重写requestApiFlow方法
 * 支持自动从SavedStateHandle获取路由参数ID
 *
 * @param T 数据类型
 * @param navigator 导航控制器
 * @param savedStateHandle 保存状态句柄，用于获取路由参数
 * @param idKey 路由参数ID的键名，默认为null
 */
abstract class BaseNetWorkViewModel<T>(
    navigator: AppNavigator,
    protected val savedStateHandle: SavedStateHandle? = null,
    protected val idKey: String? = null
) : BaseViewModel<T>(navigator) {

    /**
     * 通用网络请求UI状态
     * 初始为加载中状态
     */
    private val _uiState: MutableStateFlow<BaseNetWorkUiState<T>> =
        MutableStateFlow(BaseNetWorkUiState.Loading)
    val uiState: StateFlow<BaseNetWorkUiState<T>> = _uiState.asStateFlow()

    /**
     * 通用路由参数ID，子类可直接使用
     * 需要在构造函数中传入SavedStateHandle和idKey才能使用
     * 返回可空的Long类型ID参数
     */
    protected val id: Long? by lazy {
        if (savedStateHandle != null && idKey != null) {
            savedStateHandle[idKey]
        } else {
            null
        }
    }

    /**
     * 获取必须存在的ID参数，如果不存在则抛出异常
     * 当确定路由参数必须存在时使用此方法
     * 返回非空的Long类型ID参数
     *
     * @throws IllegalStateException 当ID参数不存在时抛出
     */
    protected val requiredId: Long by lazy {
        checkNotNull(id) { "必须的路由参数ID不存在，请确保传入了正确的SavedStateHandle和idKey" }
    }

    /**
     * 子类必须重写此方法，提供API请求的Flow
     * 适用于各种网络操作：GET、POST、PUT、DELETE等
     *
     * 注意：此方法不应在基类构造函数中调用，以避免子类属性初始化问题
     */
    protected abstract fun requestApiFlow(): Flow<NetworkResponse<T>>

    /**
     * 加载或刷新数据
     * 自动处理状态管理和错误处理
     */
    fun executeRequest() {
        viewModelScope.launch {
            try {
                val flow = requestApiFlow() // 确保调用此方法时子类已完全初始化
                flow.asResult()
                    .collectLatest { result ->
                        when (result) {
                            is Result.Loading -> setLoadingState()
                            is Result.Success -> {
                                val networkResponse = result.data
                                if (networkResponse.isSucceeded && networkResponse.data != null) {
                                    onRequestSuccess(networkResponse.data!!)
                                } else {
                                    onRequestError(
                                        networkResponse.message ?: "未知错误",
                                        Exception(networkResponse.message)
                                    )
                                }
                            }

                            is Result.Error -> {
                                val message = result.exception.message ?: "未知错误"
                                onRequestError(message, result.exception)
                            }
                        }
                    }
            } catch (e: Exception) {
                // 捕获任何异常，包括NullPointerException
                onRequestError(e.message ?: "加载数据时发生错误", e)
            }
        }
    }

    /**
     * 处理成功结果，子类可重写此方法自定义处理逻辑
     */
    protected open fun onRequestSuccess(data: T) {
        setSuccessState(data)
    }

    /**
     * 处理错误结果，子类可重写此方法自定义处理逻辑
     */
    protected open fun onRequestError(message: String, exception: Throwable) {
        setErrorState(message, exception)
    }

    /**
     * 重试请求
     */
    fun retryRequest() {
        setLoadingState()
        executeRequest()
    }

    /**
     * 设置网络状态为加载中
     */
    protected open fun setLoadingState() {
        _uiState.value = BaseNetWorkUiState.Loading
    }

    /**
     * 设置网络状态为成功
     *
     * @param data 成功返回的数据
     */
    protected open fun setSuccessState(data: T) {
        _uiState.value = BaseNetWorkUiState.Success(data)
    }

    /**
     * 设置网络状态为错误
     *
     * @param message 错误信息
     * @param exception 异常信息
     */
    protected open fun setErrorState(message: String? = null, exception: Throwable? = null) {
        _uiState.value = BaseNetWorkUiState.Error(message, exception)
    }
}