package com.joker.coolmall.core.network.datasource.auth

import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.base.BaseNetworkDataSource
import com.joker.coolmall.core.network.service.AuthService
import javax.inject.Inject

/**
 * 认证相关数据源实现类
 * 负责处理所有与用户认证相关的网络请求
 * 
 * @property authService 认证服务接口，用于发起实际的网络请求
 */
class AuthNetworkDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : BaseNetworkDataSource(), AuthNetworkDataSource {

    /**
     * 微信APP授权登录
     * 
     * @param params 请求参数，包含微信授权信息
     * @return 登录结果响应数据
     */
    override suspend fun loginByWxApp(params: Any): NetworkResponse<Any> {
        return authService.loginByWxApp(params)
    }

    /**
     * 一键手机号登录
     * 
     * @param params 请求参数，包含手机号信息
     * @return 登录结果响应数据
     */
    override suspend fun loginByUniPhone(params: Any): NetworkResponse<Any> {
        return authService.loginByUniPhone(params)
    }

    /**
     * 获取短信验证码
     * 
     * @param params 请求参数，包含手机号
     * @return 验证码发送结果响应数据
     */
    override suspend fun getSmsCode(params: Any): NetworkResponse<Any> {
        return authService.getSmsCode(params)
    }

    /**
     * 刷新访问令牌
     * 
     * @param params 请求参数，包含refresh_token
     * @return 刷新结果响应数据
     */
    override suspend fun refreshToken(params: Any): NetworkResponse<Any> {
        return authService.refreshToken(params)
    }

    /**
     * 手机号验证码登录
     * 
     * @param params 请求参数，包含手机号和验证码
     * @return 登录结果响应数据
     */
    override suspend fun loginByPhone(params: Any): NetworkResponse<Any> {
        return authService.loginByPhone(params)
    }

    /**
     * 账号密码登录
     * 
     * @param params 请求参数，包含账号和密码
     * @return 登录结果响应数据
     */
    override suspend fun loginByPassword(params: Any): NetworkResponse<Any> {
        return authService.loginByPassword(params)
    }

    /**
     * 获取图片验证码
     * 
     * @return 图片验证码响应数据
     */
    override suspend fun getCaptcha(): NetworkResponse<Any> {
        return authService.getCaptcha()
    }
} 