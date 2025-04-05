package com.joker.coolmall.core.network.datasource.auth

import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 认证相关数据源接口
 */
interface AuthNetworkDataSource {

    /**
     * 微信APP授权登录
     */
    suspend fun loginByWxApp(params: Any): NetworkResponse<Any>

    /**
     * 一键手机号登录
     */
    suspend fun loginByUniPhone(params: Any): NetworkResponse<Any>

    /**
     * 验证码
     */
    suspend fun getSmsCode(params: Any): NetworkResponse<Any>

    /**
     * 刷新token
     */
    suspend fun refreshToken(params: Any): NetworkResponse<Any>

    /**
     * 手机号登录
     */
    suspend fun loginByPhone(params: Any): NetworkResponse<Any>

    /**
     * 密码登录
     */
    suspend fun loginByPassword(params: Any): NetworkResponse<Any>

    /**
     * 图片验证码
     */
    suspend fun getCaptcha(): NetworkResponse<Any>
} 