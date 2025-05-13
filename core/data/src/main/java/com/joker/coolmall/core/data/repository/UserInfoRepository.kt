package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.model.entity.User
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.datasource.userinfo.UserInfoNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 用户信息相关仓库
 */
class UserInfoRepository @Inject constructor(
    private val userInfoNetworkDataSource: UserInfoNetworkDataSource
) {
    /**
     * 更新用户信息
     */
    fun updatePersonInfo(params: Map<String, Any>): Flow<NetworkResponse<Any>> = flow {
        emit(userInfoNetworkDataSource.updatePersonInfo(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 更新用户密码
     */
    fun updatePassword(params: Map<String, String>): Flow<NetworkResponse<Any>> = flow {
        emit(userInfoNetworkDataSource.updatePassword(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 注销
     */
    fun logoff(params: Map<String, Any>): Flow<NetworkResponse<Any>> = flow {
        emit(userInfoNetworkDataSource.logoff(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 绑定手机号
     */
    fun bindPhone(params: Map<String, String>): Flow<NetworkResponse<Any>> = flow {
        emit(userInfoNetworkDataSource.bindPhone(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 用户个人信息
     */
    fun getPersonInfo(): Flow<NetworkResponse<User>> = flow {
        emit(userInfoNetworkDataSource.getPersonInfo())
    }.flowOn(Dispatchers.IO)
} 