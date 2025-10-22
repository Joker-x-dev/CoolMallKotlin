package com.joker.coolmall.result

/**
 * 网络请求结果包装类
 *
 * @param T 数据类型
 * @author Joker.X
 */
sealed interface Result<out T> {
    /**
     * 加载中状态
     *
     * @author Joker.X
     */
    data object Loading : Result<Nothing>

    /**
     * 成功状态，包含数据
     *
     * @param T 数据类型
     * @param data 成功返回的数据
     * @author Joker.X
     */
    data class Success<T>(val data: T) : Result<T>

    /**
     * 错误状态，包含异常信息
     *
     * @param exception 异常对象
     * @author Joker.X
     */
    data class Error(val exception: Throwable) : Result<Nothing>
}
