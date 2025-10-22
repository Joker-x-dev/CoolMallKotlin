package com.joker.coolmall.core.network.datasource.auth

import com.joker.coolmall.core.model.entity.Auth
import com.joker.coolmall.core.model.entity.Captcha
import com.joker.coolmall.core.model.request.QQLoginRequest
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.base.BaseNetworkDataSource
import com.joker.coolmall.core.network.service.AuthService
import javax.inject.Inject

/**
 * 认证相关数据源实现类
 * 负责处理所有与用户认证相关的网络请求
 *
 * @param authService 认证服务接口，用于发起实际的网络请求
 * @author Joker.X
 */
class AuthNetworkDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : BaseNetworkDataSource(), AuthNetworkDataSource {

    /**
     * 微信APP授权登录
     *
     * @param params 请求参数，包含微信授权信息
     * @return 登录结果响应数据
     * @author Joker.X
     */
    override suspend fun loginByWxApp(params: Map<String, String>): NetworkResponse<Auth> {
        return authService.loginByWxApp(params)
    }

    /**
     * QQ APP授权登录
     *
     * @param params 请求参数，包含QQ授权信息
     * @return 登录结果响应数据
     * @author Joker.X
     */
    override suspend fun loginByQqApp(params: QQLoginRequest): NetworkResponse<Auth> {
        return authService.loginByQqApp(params)
    }

    /**
     * 用户注册
     *
     * @param params 请求参数，包含手机号、验证码、密码和确认密码
     * @return 注册结果响应数据
     * @author Joker.X
     */
    override suspend fun register(params: Map<String, String>): NetworkResponse<Auth> {
        return authService.register(params)
    }

    /**
     * 获取短信验证码
     *
     * @param params 请求参数，包含手机号
     * @return 验证码发送结果响应数据
     * @author Joker.X
     */
    override suspend fun getSmsCode(params: Map<String, String>): NetworkResponse<String> {
        return authService.getSmsCode(params)
    }

    /**
     * 刷新访问令牌
     *
     * @param params 请求参数，包含refresh_token
     * @return 刷新结果响应数据
     * @author Joker.X
     */
    override suspend fun refreshToken(params: Map<String, String>): NetworkResponse<Auth> {
        return authService.refreshToken(params)
    }

    /**
     * 手机号验证码登录
     *
     * @param params 请求参数，包含手机号和验证码
     * @return 登录结果响应数据
     * @author Joker.X
     */
    override suspend fun loginByPhone(params: Map<String, String>): NetworkResponse<Auth> {
        return authService.loginByPhone(params)
    }

    /**
     * 账号密码登录
     *
     * @param params 请求参数，包含账号和密码
     * @return 登录结果响应数据
     * @author Joker.X
     */
    override suspend fun loginByPassword(params: Map<String, String>): NetworkResponse<Auth> {
        return authService.loginByPassword(params)
    }

    /**
     * 获取图片验证码
     *
     * @return 图片验证码响应数据
     * @author Joker.X
     */
    override suspend fun getCaptcha(): NetworkResponse<Captcha> {
        return authService.getCaptcha()
    }
} 