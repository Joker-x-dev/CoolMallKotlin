package com.joker.coolmall.core.network.datasource.auth

import com.joker.coolmall.core.model.entity.Auth
import com.joker.coolmall.core.model.entity.Captcha
import com.joker.coolmall.core.model.request.QQLoginRequest
import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 认证相关数据源接口
 *
 * @author Joker.X
 */
interface AuthNetworkDataSource {

    /**
     * 微信APP授权登录
     *
     * @param params 请求参数
     * @return 认证信息响应
     * @author Joker.X
     */
    suspend fun loginByWxApp(params: Map<String, String>): NetworkResponse<Auth>

    /**
     * QQ APP授权登录
     *
     * @param params QQ登录请求参数
     * @return 认证信息响应
     * @author Joker.X
     */
    suspend fun loginByQqApp(params: QQLoginRequest): NetworkResponse<Auth>

    /**
     * 用户注册
     *
     * @param params 注册请求参数
     * @return 认证信息响应
     * @author Joker.X
     */
    suspend fun register(params: Map<String, String>): NetworkResponse<Auth>

    /**
     * 获取短信验证码
     *
     * @param params 请求参数
     * @return 验证码发送结果
     * @author Joker.X
     */
    suspend fun getSmsCode(params: Map<String, String>): NetworkResponse<String>

    /**
     * 刷新token
     *
     * @param params 刷新token请求参数
     * @return 认证信息响应
     * @author Joker.X
     */
    suspend fun refreshToken(params: Map<String, String>): NetworkResponse<Auth>

    /**
     * 手机号登录
     *
     * @param params 手机号登录请求参数
     * @return 认证信息响应
     * @author Joker.X
     */
    suspend fun loginByPhone(params: Map<String, String>): NetworkResponse<Auth>

    /**
     * 密码登录
     *
     * @param params 密码登录请求参数
     * @return 认证信息响应
     * @author Joker.X
     */
    suspend fun loginByPassword(params: Map<String, String>): NetworkResponse<Auth>

    /**
     * 获取图片验证码
     *
     * @return 图片验证码响应
     * @author Joker.X
     */
    suspend fun getCaptcha(): NetworkResponse<Captcha>
} 