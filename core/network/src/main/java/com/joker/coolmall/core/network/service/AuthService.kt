package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.Auth
import com.joker.coolmall.core.model.Captcha
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
    suspend fun loginByWxApp(@Body params: Map<String, String>): NetworkResponse<Auth>

    /**
     * 用户注册
     */
    @POST("user/login/register")
    suspend fun register(@Body params: Map<String, String>): NetworkResponse<Auth>

    /**
     * 验证码
     */
    @POST("user/login/smsCode")
    suspend fun getSmsCode(@Body params: Map<String, String>): NetworkResponse<String>

    /**
     * 刷新token
     */
    @POST("user/login/refreshToken")
    suspend fun refreshToken(@Body params: Map<String, String>): NetworkResponse<Auth>

    /**
     * 手机号登录
     */
    @POST("user/login/phone")
    suspend fun loginByPhone(@Body params: Map<String, String>): NetworkResponse<Auth>

    /**
     * 密码登录
     */
    @POST("user/login/password")
    suspend fun loginByPassword(@Body params: Map<String, String>): NetworkResponse<Auth>

    /**
     * 图片验证码
     */
    @GET("user/login/captcha")
    suspend fun getCaptcha(): NetworkResponse<Captcha>
} 