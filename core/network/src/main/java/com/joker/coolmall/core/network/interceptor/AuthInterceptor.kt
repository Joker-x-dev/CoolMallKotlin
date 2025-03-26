package com.joker.coolmall.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 认证拦截器 - 添加授权头信息
 */
@Singleton
class AuthInterceptor @Inject constructor(
//    private val userPreferencesDataSource: UserPreferencesDataSource
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val token = ""

        // 如果有Token，添加到请求头
        val request = if (token.isNotBlank()) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        return chain.proceed(request)
    }
} 