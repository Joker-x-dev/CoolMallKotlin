package com.joker.coolmall.core.network.interceptor

import com.joker.coolmall.core.datastore.datasource.auth.AuthStoreDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 认证拦截器 - 添加授权头信息
 */
@Singleton
class AuthInterceptor @Inject constructor(
    private val authStoreDataSource: AuthStoreDataSource
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // 从 DataStore 获取 token，使用 runBlocking 调用挂起函数
        val token = runBlocking {
            authStoreDataSource.getToken() ?: ""
        }

        // 如果有Token，添加到请求头
        val request = if (token.isNotBlank()) {
            originalRequest.newBuilder()
                .header("Authorization", token)
                .build()
        } else {
            originalRequest
        }

        return chain.proceed(request)
    }
} 