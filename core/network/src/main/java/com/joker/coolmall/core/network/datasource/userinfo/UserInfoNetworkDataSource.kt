package com.joker.coolmall.core.network.datasource.userinfo

import User
import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 用户信息相关数据源接口
 */
interface UserInfoNetworkDataSource {

    /**
     * 更新用户信息
     */
    suspend fun updatePersonInfo(params: Map<String, Any>): NetworkResponse<Any>

    /**
     * 更新用户密码
     */
    suspend fun updatePassword(params: Map<String, String>): NetworkResponse<Any>

    /**
     * 注销
     */
    suspend fun logoff(params: Map<String, Any>): NetworkResponse<Any>

    /**
     * 绑定手机号
     */
    suspend fun bindPhone(params: Map<String, String>): NetworkResponse<Any>

    /**
     * 用户个人信息
     */
    suspend fun getPersonInfo(): NetworkResponse<User>
} 