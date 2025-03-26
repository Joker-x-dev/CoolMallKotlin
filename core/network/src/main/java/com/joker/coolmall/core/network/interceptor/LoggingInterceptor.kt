package com.joker.coolmall.core.network.interceptor

import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 日志拦截器 - 记录网络请求日志
 */
@Singleton
class LoggingInterceptor @Inject constructor() {
    
    @Inject
    fun init(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }
} 