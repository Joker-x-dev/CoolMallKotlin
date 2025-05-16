package com.joker.coolmall.result

import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.util.log.LogUtils
import com.joker.coolmall.core.util.toast.ToastUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import java.io.PrintWriter
import java.io.StringWriter

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
            try {
                flow.collectLatest { result ->
                    when (result) {
                        is Result.Loading -> onLoading()
                        is Result.Success -> handleSuccess(
                            response = result.data,
                            onSuccess = onSuccess,
                            onSuccessWithData = onSuccessWithData,
                            showToast = showToast,
                            onError = onError
                        )

                        is Result.Error -> handleError(
                            errorMsg = result.exception.message ?: "网络请求失败",
                            throwable = result.exception,
                            showToast = showToast,
                            onError = onError
                        )
                    }
                }
            } catch (e: Exception) {
                handleError(
                    errorMsg = "请求处理异常",
                    throwable = e,
                    showToast = showToast,
                    onError = onError
                )
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

    /**
     * 获取完整的异常堆栈信息
     */
    private fun getStackTraceString(throwable: Throwable): String {
        return StringWriter().apply {
            throwable.printStackTrace(PrintWriter(this))
        }.toString()
    }

    /**
     * 格式化错误日志信息
     */
    private fun formatErrorLog(
        errorMsg: String,
        throwable: Throwable?,
        additionalInfo: String = ""
    ): String = buildString {
        appendLine("=== 网络请求错误 ===")
        appendLine("错误信息: $errorMsg")
        if (additionalInfo.isNotEmpty()) {
            appendLine("附加信息: $additionalInfo")
        }
        throwable?.let {
            appendLine("异常类型: ${it.javaClass.name}")
            appendLine("异常堆栈:")
            appendLine(getStackTraceString(it))
        }
        appendLine("==================")
    }

    /**
     * 获取错误类型描述
     */
    private fun getErrorTypeDescription(throwable: Throwable): String = when (throwable) {
        is SerializationException -> buildString {
            append("JSON解析错误\n")
            append("错误位置: ${throwable.message}")
        }

        is java.net.SocketTimeoutException -> "网络连接超时"
        is java.net.UnknownHostException -> "无法解析主机地址"
        is java.io.IOException -> "网络IO异常"
        else -> "未知异常类型"
    }

    /**
     * 处理错误状态
     */
    private fun handleError(
        errorMsg: String,
        throwable: Throwable?,
        showToast: Boolean,
        onError: (String, Throwable?) -> Unit
    ) {
        val additionalInfo = throwable?.let { getErrorTypeDescription(it) } ?: ""
        LogUtils.e(formatErrorLog(errorMsg, throwable, additionalInfo))
        onError(errorMsg, throwable)
        if (showToast) {
            ToastUtils.showError(errorMsg)
        }
    }

    /**
     * 处理成功状态
     */
    private fun <T> handleSuccess(
        response: NetworkResponse<T>,
        onSuccess: (NetworkResponse<T>) -> Unit,
        onSuccessWithData: (T) -> Unit,
        showToast: Boolean,
        onError: (String, Throwable?) -> Unit
    ) {
        onSuccess(response)
        if (response.isSucceeded) {
            onSuccessWithData(response.data ?: Unit as T)
        } else {
            val errorMsg = response.message ?: "未知错误"
            handleError(errorMsg, Exception(errorMsg), showToast, onError)
        }
    }
}