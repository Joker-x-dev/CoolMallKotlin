package com.joker.coolmall.core.network.datasource.auth

import com.joker.coolmall.core.model.entity.Auth
import com.joker.coolmall.core.model.entity.Captcha
import com.joker.coolmall.core.model.request.QQLoginRequest
import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 认证相关数据源接口
 */
interface AuthNetworkDataSource {

    /**
     * 微信APP授权登录
     */
    suspend fun loginByWxApp(params: Map<String, String>): NetworkResponse<Auth>

    /**
     * QQ APP授权登录
     */
    suspend fun loginByQqApp(params: QQLoginRequest): NetworkResponse<Auth>

    /**
     * 用户注册
     */
    suspend fun register(params: Map<String, String>): NetworkResponse<Auth>

    /**
     * 验证码
     */
    suspend fun getSmsCode(params: Map<String, String>): NetworkResponse<String>

    /**
     * 刷新token
     */
    suspend fun refreshToken(params: Map<String, String>): NetworkResponse<Auth>

    /**
     * 手机号登录
     */
    suspend fun loginByPhone(params: Map<String, String>): NetworkResponse<Auth>

    /**
     * 密码登录
     */
    suspend fun loginByPassword(params: Map<String, String>): NetworkResponse<Auth>

    /**
     * 图片验证码
     */
    suspend fun getCaptcha(): NetworkResponse<Captcha>
} 