package com.joker.coolmall.core.network.interceptor

import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 日志拦截器 - 记录网络请求日志
 *
 * @author Joker.X
 */
@Singleton
class LoggingInterceptor @Inject constructor() {

    /**
     * 初始化日志拦截器
     *
     * @return HTTP日志拦截器实例
     * @author Joker.X
     */
    @Inject
    fun init(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }
} 