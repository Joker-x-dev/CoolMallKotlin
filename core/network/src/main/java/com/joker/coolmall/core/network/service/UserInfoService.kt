package com.joker.coolmall.core.network.service

import User
import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * 用户信息相关接口
 */
interface UserInfoService {

    /**
     * 更新用户信息
     */
    @POST("user/info/updatePerson")
    suspend fun updatePersonInfo(@Body params: Map<String, Any>): NetworkResponse<Any>

    /**
     * 更新用户密码
     */
    @POST("user/info/updatePassword")
    suspend fun updatePassword(@Body params: Map<String, String>): NetworkResponse<Any>

    /**
     * 注销
     */
    @POST("user/info/logoff")
    suspend fun logoff(@Body params: Map<String, Any>): NetworkResponse<Any>

    /**
     * 绑定手机号
     */
    @POST("user/info/bindPhone")
    suspend fun bindPhone(@Body params: Map<String, String>): NetworkResponse<Any>

    /**
     * 用户个人信息
     */
    @GET("user/info/person")
    suspend fun getPersonInfo(): NetworkResponse<User>
} 