package com.joker.coolmall.core.common.base.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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
) : BaseViewModel(navigator) {

    /**
     * 通用网络请求UI状态
     * 初始为加载中状态
     */
    private val _uiState: MutableStateFlow<BaseNetWorkUiState<T>> =
        MutableStateFlow(BaseNetWorkUiState.Loading)
    val uiState: StateFlow<BaseNetWorkUiState<T>> = _uiState.asStateFlow()

    /**
     * 控制请求失败时是否显示Toast提示
     * 子类可重写此属性以自定义行为
     */
    protected open val showErrorToast: Boolean = true

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
     * 使用ResultHandler自动处理状态管理和错误处理
     */
    fun executeRequest() {
        try {
            ResultHandler.handleResultWithData(
                scope = viewModelScope,
                flow = requestApiFlow().asResult(),
                showToast = showErrorToast,
                onLoading = { onRequestStart() },
                onData = { data -> onRequestSuccess(data) },
                onError = { message, exception -> onRequestError(message, exception) }
            )
        } catch (e: Exception) {
            // 捕获任何异常，包括NullPointerException
            onRequestError(e.message ?: "加载数据时发生错误", e)
        }
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
        setSuccessState(data)
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
     * 视图层调用此方法，监听页面刷新信号。
     * @param backStackEntry 当前页面的NavBackStackEntry
     * @param key 刷新信号的key，默认是"refresh"，可自定义
     *
     * 用法：在Composable中调用 viewModel.observeRefreshState(backStackEntry, key = "refreshXXX")
     * 只需调用一次，自动去重和解绑，无内存泄漏。
     */
    fun observeRefreshState(backStackEntry: NavBackStackEntry?, key: String = "refresh") {
        if (backStackEntry == null) return
        val owner: LifecycleOwner = backStackEntry
        backStackEntry.savedStateHandle
            .getLiveData<Boolean>(key)
            .observe(owner, object : Observer<Boolean> {
                override fun onChanged(value: Boolean) {
                    if (value == true) {
                        executeRequest()
                        // 只刷新一次
                        backStackEntry.savedStateHandle[key] = false
                    }
                }
            })
    }
}