package com.joker.coolmall.core.common.base.viewmodel

import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.navigation.NavigationResultKey
import com.joker.coolmall.core.navigation.RefreshResult
import com.joker.coolmall.core.navigation.RefreshResultKey
import com.joker.coolmall.core.navigation.resultEvents
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 网络请求ViewModel基类
 *
 * 基于Flow的异步数据流模式，子类只需重写requestApiFlow方法。
 *
 * @param T 数据类型
 * @author Joker.X
 */
abstract class BaseNetWorkViewModel<T> : BaseViewModel() {
    /**
     * 刷新结果监听任务
     *
     * 用于保证只注册一次刷新结果监听，避免重复 collect 导致重复请求。
     * 当该任务不为 null 时，表示当前 ViewModel 已经建立监听。
     */
    private var refreshObserveJob: Job? = null

    /**
     * 通用网络请求UI状态
     * 初始为加载中状态
     */
    val _uiState: MutableStateFlow<BaseNetWorkUiState<T>> =
        MutableStateFlow(BaseNetWorkUiState.Loading)
    val uiState: StateFlow<BaseNetWorkUiState<T>> = _uiState.asStateFlow()

    /**
     * 控制请求失败时是否显示Toast提示
     * 子类可重写此属性以自定义行为
     */
    protected open val showErrorToast: Boolean = false

    /**
     * 是否启用最少加载时间（240毫秒）
     * 子类可重写此属性以启用最少加载时间功能
     */
    protected open val enableMinLoadingTime: Boolean = false

    /**
     * 请求开始时间，用于计算最少加载时间
     */
    private var requestStartTime: Long = 0

    /**
     * 子类必须重写此方法，提供API请求的Flow
     * 适用于各种网络操作：GET、POST、PUT、DELETE等
     */
    protected abstract fun requestApiFlow(): Flow<NetworkResponse<T>>

    /**
     * 加载或刷新数据
     * 使用ResultHandler自动处理状态管理和错误处理
     */
    fun executeRequest() {
        // 记录请求开始时间
        if (enableMinLoadingTime) requestStartTime = System.currentTimeMillis()

        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = requestApiFlow().asResult(),
            showToast = showErrorToast,
            onLoading = { onRequestStart() },
            onData = { data -> onRequestSuccess(data) },
            onError = { message, exception -> onRequestError(message, exception) }
        )
    }

    /**
     * 请求开始前执行的方法
     * 子类可重写此方法以在请求开始前执行自定义逻辑
     */
    protected open fun onRequestStart() {
        setLoadingState()
    }

    /**
     * 处理成功结果，子类可重写此方法自定义处理逻辑
     */
    protected open fun onRequestSuccess(data: T) {
        if (enableMinLoadingTime) {
            val elapsedTime = System.currentTimeMillis() - requestStartTime
            val minLoadingTime = 240L

            if (elapsedTime < minLoadingTime) {
                // 延迟设置成功状态
                viewModelScope.launch {
                    delay(minLoadingTime - elapsedTime)
                    setSuccessState(data)
                }
            } else {
                setSuccessState(data)
            }
        } else {
            setSuccessState(data)
        }
    }

    /**
     * 处理错误结果，子类可重写此方法自定义处理逻辑
     */
    protected open fun onRequestError(message: String, exception: Throwable?) {
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

    /**
     * 获取当前页面 uiState 成功以后的数据
     * 注意：此方法仅适用于当前页面的 uiState 为成功状态时
     *
     * @return 成功状态下的 T 类型数据
     * @throws IllegalStateException 当 uiState 不为成功状态时抛出异常
     */
    fun getSuccessData(): T {
        return (uiState.value as? BaseNetWorkUiState.Success)?.data
            ?: throw IllegalStateException("Current page uiState is not in Success state, unable to retrieve data")
    }

    /**
     * 注册页面刷新信号监听（基于 NavigationResultKey）。
     *
     * 推荐在 ViewModel 的 `init` 中调用一次，不依赖 View 层 `LaunchedEffect`。
     * 内部已做去重：重复调用不会重复注册。
     *
     * @param key 刷新结果的类型安全 Key，默认使用全局的 [RefreshResultKey]
     */
    fun observeRefreshState(
        key: NavigationResultKey<RefreshResult> = RefreshResultKey,
    ) {
        if (refreshObserveJob != null) return
        refreshObserveJob = viewModelScope.launch {
            resultEvents(key).collect { refreshResult ->
                if (refreshResult.refresh == true) {
                    executeRequest()
                }
            }
        }
    }
}
