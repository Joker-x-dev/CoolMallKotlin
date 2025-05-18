package com.joker.coolmall.core.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.joker.coolmall.core.network.BuildConfig
import com.joker.coolmall.core.network.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // 使用BuildConfig中的BASE_URL替换硬编码值
    private const val BASE_URL = BuildConfig.BASE_URL
//    private val BASE_URL = ""

    // 配置Json序列化
    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
//        encodeDefaults = true
    }

    // 提供OkHttpClient
    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS) // 连接超时时间
            .writeTimeout(10, TimeUnit.SECONDS) // 写超时时间
            .readTimeout(10, TimeUnit.SECONDS) // 读超时时间
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(ChuckerInterceptor.Builder(context).build())
                }
            }
            // 请求失败重试
            .retryOnConnectionFailure(true)
            .build()
    }

    // 提供Retrofit实例
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    // 添加这个提供HttpLoggingInterceptor的方法
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }
}