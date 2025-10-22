package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.entity.User
import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * 用户信息相关接口
 *
 * @author Joker.X
 */
interface UserInfoService {

    /**
     * 更新用户个人信息
     *
     * @param params 用户信息参数
     * @return 更新结果响应
     * @author Joker.X
     */
    @POST("user/info/updatePerson")
    suspend fun updatePersonInfo(@Body params: Map<String, Any>): NetworkResponse<Any>

    /**
     * 更新用户密码
     *
     * @param params 密码参数
     * @return 更新结果响应
     * @author Joker.X
     */
    @POST("user/info/updatePassword")
    suspend fun updatePassword(@Body params: Map<String, String>): NetworkResponse<Any>

    /**
     * 注销账号
     *
     * @param params 注销参数
     * @return 注销结果响应
     * @author Joker.X
     */
    @POST("user/info/logoff")
    suspend fun logoff(@Body params: Map<String, Any>): NetworkResponse<Any>

    /**
     * 绑定手机号
     *
     * @param params 绑定参数
     * @return 绑定结果响应
     * @author Joker.X
     */
    @POST("user/info/bindPhone")
    suspend fun bindPhone(@Body params: Map<String, String>): NetworkResponse<Any>

    /**
     * 获取用户个人信息
     *
     * @return 用户信息响应
     * @author Joker.X
     */
    @GET("user/info/person")
    suspend fun getPersonInfo(): NetworkResponse<User>
}
