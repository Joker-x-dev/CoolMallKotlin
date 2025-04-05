package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * 认证相关接口
 */
interface AuthService {

    /**
     * 微信APP授权登录
     */
    @POST("user/login/wxApp")
    suspend fun loginByWxApp(@Body params: Any): NetworkResponse<Any>

    /**
     * 一键手机号登录
     */
    @POST("user/login/uniPhone")
    suspend fun loginByUniPhone(@Body params: Any): NetworkResponse<Any>

    /**
     * 验证码
     */
    @POST("user/login/smsCode")
    suspend fun getSmsCode(@Body params: Any): NetworkResponse<Any>

    /**
     * 刷新token
     */
    @POST("user/login/refreshToken")
    suspend fun refreshToken(@Body params: Any): NetworkResponse<Any>

    /**
     * 手机号登录
     */
    @POST("user/login/phone")
    suspend fun loginByPhone(@Body params: Any): NetworkResponse<Any>

    /**
     * 密码登录
     */
    @POST("user/login/password")
    suspend fun loginByPassword(@Body params: Any): NetworkResponse<Any>

    /**
     * 图片验证码
     */
    @GET("user/login/captcha")
    suspend fun getCaptcha(): NetworkResponse<Any>
} 