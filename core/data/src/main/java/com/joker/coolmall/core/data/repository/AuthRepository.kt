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
 *
 * @param authNetworkDataSource 认证网络数据源
 * @author Joker.X
 */
class AuthRepository @Inject constructor(
    private val authNetworkDataSource: AuthNetworkDataSource
) {
    /**
     * 微信APP授权登录
     *
     * @param params 登录参数
     * @return 认证信息Flow
     * @author Joker.X
     */
    fun loginByWxApp(params: Map<String, String>): Flow<NetworkResponse<Auth>> = flow {
        emit(authNetworkDataSource.loginByWxApp(params))
    }.flowOn(Dispatchers.IO)

    /**
     * QQ APP授权登录
     *
     * @param params QQ登录请求参数
     * @return 认证信息Flow
     * @author Joker.X
     */
    fun loginByQqApp(params: QQLoginRequest): Flow<NetworkResponse<Auth>> = flow {
        emit(authNetworkDataSource.loginByQqApp(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 用户注册
     *
     * @param params 注册参数
     * @return 认证信息Flow
     * @author Joker.X
     */
    fun register(params: Map<String, String>): Flow<NetworkResponse<Auth>> = flow {
        emit(authNetworkDataSource.register(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 获取短信验证码
     *
     * @param params 请求参数
     * @return 验证码发送结果Flow
     * @author Joker.X
     */
    fun getSmsCode(params: Map<String, String>): Flow<NetworkResponse<String>> = flow {
        emit(authNetworkDataSource.getSmsCode(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 刷新token
     *
     * @param params 刷新token参数
     * @return 新的认证信息Flow
     * @author Joker.X
     */
    fun refreshToken(params: Map<String, String>): Flow<NetworkResponse<Auth>> = flow {
        emit(authNetworkDataSource.refreshToken(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 手机号登录
     *
     * @param params 登录参数
     * @return 认证信息Flow
     * @author Joker.X
     */
    fun loginByPhone(params: Map<String, String>): Flow<NetworkResponse<Auth>> = flow {
        emit(authNetworkDataSource.loginByPhone(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 密码登录
     *
     * @param params 登录参数
     * @return 认证信息Flow
     * @author Joker.X
     */
    fun loginByPassword(params: Map<String, String>): Flow<NetworkResponse<Auth>> = flow {
        emit(authNetworkDataSource.loginByPassword(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 获取图片验证码
     *
     * @return 图片验证码Flow
     * @author Joker.X
     */
    fun getCaptcha(): Flow<NetworkResponse<Captcha>> = flow {
        emit(authNetworkDataSource.getCaptcha())
    }.flowOn(Dispatchers.IO)
}
