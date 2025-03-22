package com.joker.coolmall.core.common.result

/**
 * 网络请求结果包装类
 */
sealed interface Result<out T> {
    /**
     * 加载中状态
     */
    data object Loading : Result<Nothing>
    
    /**
     * 成功状态，包含数据
     */
    data class Success<T>(val data: T) : Result<T>
    
    /**
     * 错误状态，包含异常信息
     */
    data class Error(val exception: Throwable) : Result<Nothing>
}
