package com.joker.coolmall.result

import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.util.toast.ToastUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * 网络请求结果处理工具类
 * 用于简化ViewModel中对网络请求结果的处理
 */
object ResultHandler {

    /**
     * 处理网络请求结果，自动处理Loading、Success和Error状态
     *
     * @param T 数据类型
     * @param scope CoroutineScope，通常是viewModelScope
     * @param flow 包含Result的Flow
     * @param showToast 是否显示错误Toast，默认为true
     * @param onLoading 加载中状态的回调
     * @param onSuccess 成功状态的回调，接收NetworkResponse对象
     * @param onSuccessWithData 成功且有数据状态的回调，接收数据对象
     * @param onError 错误状态的回调，接收错误消息和异常
     */
    fun <T> handleResult(
        scope: CoroutineScope,
        flow: Flow<Result<NetworkResponse<T>>>,
        showToast: Boolean = true,
        onLoading: () -> Unit = {},
        onSuccess: (NetworkResponse<T>) -> Unit = {},
        onSuccessWithData: (T) -> Unit = {},
        onError: (String, Throwable?) -> Unit = { _, _ -> }
    ) {
        scope.launch {
            flow.collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
                        onLoading()
                    }

                    is Result.Success -> {
                        val response = result.data
                        onSuccess(response)

                        if (response.isSucceeded) {
                            onSuccessWithData(response.data ?: Unit as T)
                        } else {
                            val errorMsg = response.message ?: "未知错误"
                            onError(errorMsg, Exception(errorMsg))

                            if (showToast) {
                                ToastUtils.showError(errorMsg)
                            }
                        }
                    }

                    is Result.Error -> {
                        val errorMsg = result.exception.message ?: "网络请求失败"
                        onError(errorMsg, result.exception)

                        if (showToast) {
                            ToastUtils.showError(errorMsg)
                        }
                    }
                }
            }
        }
    }

    /**
     * 处理网络请求结果的简化版本，直接处理成功的数据
     * 适用于只关心成功数据的场景
     *
     * @param T 数据类型
     * @param scope CoroutineScope，通常是viewModelScope
     * @param flow 包含Result的Flow
     * @param showToast 是否显示错误Toast，默认为true
     * @param onLoading 加载中状态的回调
     * @param onData 成功且有数据状态的回调，只有当请求成功且有数据时才会调用
     * @param onError 错误状态的回调，接收错误消息和异常
     */
    fun <T> handleResultWithData(
        scope: CoroutineScope,
        flow: Flow<Result<NetworkResponse<T>>>,
        showToast: Boolean = true,
        onLoading: () -> Unit = {},
        onData: (T) -> Unit,
        onError: (String, Throwable?) -> Unit = { _, _ -> }
    ) {
        handleResult(
            scope = scope,
            flow = flow,
            showToast = showToast,
            onLoading = onLoading,
            onSuccessWithData = onData,
            onError = onError
        )
    }
} 