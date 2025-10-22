package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.entity.Auth
import com.joker.coolmall.core.model.entity.Captcha
import com.joker.coolmall.core.model.request.QQLoginRequest
import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * 认证相关接口
 *
 * @author Joker.X
 */
interface AuthService {

    /**
     * 微信APP授权登录
     *
     * @param params 请求参数
     * @return 认证信息响应
     * @author Joker.X
     */
    @POST("user/login/wxApp")
    suspend fun loginByWxApp(@Body params: Map<String, String>): NetworkResponse<Auth>

    /**
     * QQ APP授权登录
     *
     * @param params QQ登录请求参数
     * @return 认证信息响应
     * @author Joker.X
     */
    @POST("user/login/qq")
    suspend fun loginByQqApp(@Body params: QQLoginRequest): NetworkResponse<Auth>

    /**
     * 用户注册
     *
     * @param params 注册请求参数
     * @return 认证信息响应
     * @author Joker.X
     */
    @POST("user/login/register")
    suspend fun register(@Body params: Map<String, String>): NetworkResponse<Auth>

    /**
     * 获取短信验证码
     *
     * @param params 请求参数
     * @return 验证码发送结果
     * @author Joker.X
     */
    @POST("user/login/smsCode")
    suspend fun getSmsCode(@Body params: Map<String, String>): NetworkResponse<String>

    /**
     * 刷新token
     *
     * @param params 刷新token请求参数
     * @return 认证信息响应
     * @author Joker.X
     */
    @POST("user/login/refreshToken")
    suspend fun refreshToken(@Body params: Map<String, String>): NetworkResponse<Auth>

    /**
     * 手机号登录
     *
     * @param params 手机号登录请求参数
     * @return 认证信息响应
     * @author Joker.X
     */
    @POST("user/login/phone")
    suspend fun loginByPhone(@Body params: Map<String, String>): NetworkResponse<Auth>

    /**
     * 密码登录
     *
     * @param params 密码登录请求参数
     * @return 认证信息响应
     * @author Joker.X
     */
    @POST("user/login/password")
    suspend fun loginByPassword(@Body params: Map<String, String>): NetworkResponse<Auth>

    /**
     * 获取图片验证码
     *
     * @return 图片验证码响应
     * @author Joker.X
     */
    @GET("user/login/captcha")
    suspend fun getCaptcha(): NetworkResponse<Captcha>
} 