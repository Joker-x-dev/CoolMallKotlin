package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.model.entity.Auth
import com.joker.coolmall.core.model.entity.Captcha
import com.joker.coolmall.core.model.request.QQLoginRequest
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.datasource.auth.AuthNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 认证相关仓库
 */
class AuthRepository @Inject constructor(
    private val authNetworkDataSource: AuthNetworkDataSource
) {
    /**
     * 微信APP授权登录
     */
    fun loginByWxApp(params: Map<String, String>): Flow<NetworkResponse<Auth>> = flow {
        emit(authNetworkDataSource.loginByWxApp(params))
    }.flowOn(Dispatchers.IO)

    /**
     * QQ APP授权登录
     */
    fun loginByQqApp(params: QQLoginRequest): Flow<NetworkResponse<Auth>> = flow {
        emit(authNetworkDataSource.loginByQqApp(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 用户注册
     */
    fun register(params: Map<String, String>): Flow<NetworkResponse<Auth>> = flow {
        emit(authNetworkDataSource.register(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 验证码
     */
    fun getSmsCode(params: Map<String, String>): Flow<NetworkResponse<String>> = flow {
        emit(authNetworkDataSource.getSmsCode(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 刷新token
     */
    fun refreshToken(params: Map<String, String>): Flow<NetworkResponse<Auth>> = flow {
        emit(authNetworkDataSource.refreshToken(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 手机号登录
     */
    fun loginByPhone(params: Map<String, String>): Flow<NetworkResponse<Auth>> = flow {
        emit(authNetworkDataSource.loginByPhone(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 密码登录
     */
    fun loginByPassword(params: Map<String, String>): Flow<NetworkResponse<Auth>> = flow {
        emit(authNetworkDataSource.loginByPassword(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 图片验证码
     */
    fun getCaptcha(): Flow<NetworkResponse<Captcha>> = flow {
        emit(authNetworkDataSource.getCaptcha())
    }.flowOn(Dispatchers.IO)
} 