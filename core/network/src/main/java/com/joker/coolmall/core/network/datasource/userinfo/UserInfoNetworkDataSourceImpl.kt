package com.joker.coolmall.core.network.datasource.userinfo

import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.base.BaseNetworkDataSource
import com.joker.coolmall.core.network.service.UserInfoService
import javax.inject.Inject

/**
 * 用户信息相关数据源实现类
 * 负责处理所有与用户信息相关的网络请求
 * 
 * @property userInfoService 用户信息服务接口，用于发起实际的网络请求
 */
class UserInfoNetworkDataSourceImpl @Inject constructor(
    private val userInfoService: UserInfoService
) : BaseNetworkDataSource(), UserInfoNetworkDataSource {

    /**
     * 更新用户个人信息
     * 
     * @param params 请求参数，包含用户信息
     * @return 更新结果响应数据
     */
    override suspend fun updatePersonInfo(params: Any): NetworkResponse<Any> {
        return userInfoService.updatePersonInfo(params)
    }

    /**
     * 更新用户密码
     * 
     * @param params 请求参数，包含旧密码和新密码
     * @return 更新结果响应数据
     */
    override suspend fun updatePassword(params: Any): NetworkResponse<Any> {
        return userInfoService.updatePassword(params)
    }

    /**
     * 注销账号
     * 
     * @param params 请求参数
     * @return 注销结果响应数据
     */
    override suspend fun logoff(params: Any): NetworkResponse<Any> {
        return userInfoService.logoff(params)
    }

    /**
     * 绑定手机号
     * 
     * @param params 请求参数，包含手机号和验证码
     * @return 绑定结果响应数据
     */
    override suspend fun bindPhone(params: Any): NetworkResponse<Any> {
        return userInfoService.bindPhone(params)
    }

    /**
     * 获取用户个人信息
     * 
     * @return 用户个人信息响应数据
     */
    override suspend fun getPersonInfo(): NetworkResponse<Any> {
        return userInfoService.getPersonInfo()
    }
} 