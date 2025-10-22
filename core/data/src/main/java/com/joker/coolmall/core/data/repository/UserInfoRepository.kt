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
 *
 * @param userInfoNetworkDataSource 用户信息网络数据源
 * @author Joker.X
 */
class UserInfoRepository @Inject constructor(
    private val userInfoNetworkDataSource: UserInfoNetworkDataSource
) {
    /**
     * 更新用户信息
     *
     * @param params 更新参数
     * @return 更新结果Flow
     * @author Joker.X
     */
    fun updatePersonInfo(params: Map<String, Any>): Flow<NetworkResponse<Any>> = flow {
        emit(userInfoNetworkDataSource.updatePersonInfo(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 更新用户密码
     *
     * @param params 密码更新参数
     * @return 更新结果Flow
     * @author Joker.X
     */
    fun updatePassword(params: Map<String, String>): Flow<NetworkResponse<Any>> = flow {
        emit(userInfoNetworkDataSource.updatePassword(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 注销账户
     *
     * @param params 注销参数
     * @return 注销结果Flow
     * @author Joker.X
     */
    fun logoff(params: Map<String, Any>): Flow<NetworkResponse<Any>> = flow {
        emit(userInfoNetworkDataSource.logoff(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 绑定手机号
     *
     * @param params 手机号绑定参数
     * @return 绑定结果Flow
     * @author Joker.X
     */
    fun bindPhone(params: Map<String, String>): Flow<NetworkResponse<Any>> = flow {
        emit(userInfoNetworkDataSource.bindPhone(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 获取用户个人信息
     *
     * @return 用户信息Flow
     * @author Joker.X
     */
    fun getPersonInfo(): Flow<NetworkResponse<User>> = flow {
        emit(userInfoNetworkDataSource.getPersonInfo())
    }.flowOn(Dispatchers.IO)
}
