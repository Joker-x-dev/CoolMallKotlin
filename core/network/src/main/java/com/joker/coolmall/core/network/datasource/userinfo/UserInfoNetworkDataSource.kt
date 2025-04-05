package com.joker.coolmall.core.network.datasource.userinfo

import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 用户信息相关数据源接口
 */
interface UserInfoNetworkDataSource {

    /**
     * 更新用户信息
     */
    suspend fun updatePersonInfo(params: Any): NetworkResponse<Any>

    /**
     * 更新用户密码
     */
    suspend fun updatePassword(params: Any): NetworkResponse<Any>

    /**
     * 注销
     */
    suspend fun logoff(params: Any): NetworkResponse<Any>

    /**
     * 绑定手机号
     */
    suspend fun bindPhone(params: Any): NetworkResponse<Any>

    /**
     * 用户个人信息
     */
    suspend fun getPersonInfo(): NetworkResponse<Any>
} 