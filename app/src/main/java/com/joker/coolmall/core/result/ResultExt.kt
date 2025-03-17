package com.joker.coolmall.core.result

import com.joker.coolmall.core.model.response.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * 将网络响应Flow转换为Result类型
 */
fun <T> Flow<NetworkResponse<T>>.asResult(): Flow<Result<T>> {
    return this
        .map { response ->
            if (response.isSucceeded && response.data != null) {
                Result.Success(response.data)
            } else {
                Result.Error(Exception(response.message ?: "未知错误"))
            }
        }
        .onStart { emit(Result.Loading) }
        .catch { exception ->
            emit(Result.Error(exception))
        }
} 