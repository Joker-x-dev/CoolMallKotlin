package com.joker.coolmall.core.network.datasource.userinfo

import com.joker.coolmall.core.model.entity.User
import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 用户信息相关数据源接口
 *
 * @author Joker.X
 */
interface UserInfoNetworkDataSource {

    /**
     * 更新用户个人信息
     *
     * @param params 用户信息参数
     * @return 更新结果响应
     * @author Joker.X
     */
    suspend fun updatePersonInfo(params: Map<String, Any>): NetworkResponse<Any>

    /**
     * 更新用户密码
     *
     * @param params 密码参数
     * @return 更新结果响应
     * @author Joker.X
     */
    suspend fun updatePassword(params: Map<String, String>): NetworkResponse<Any>

    /**
     * 注销账号
     *
     * @param params 注销参数
     * @return 注销结果响应
     * @author Joker.X
     */
    suspend fun logoff(params: Map<String, Any>): NetworkResponse<Any>

    /**
     * 绑定手机号
     *
     * @param params 绑定参数
     * @return 绑定结果响应
     * @author Joker.X
     */
    suspend fun bindPhone(params: Map<String, String>): NetworkResponse<Any>

    /**
     * 获取用户个人信息
     *
     * @return 用户信息响应
     * @author Joker.X
     */
    suspend fun getPersonInfo(): NetworkResponse<User>
}
