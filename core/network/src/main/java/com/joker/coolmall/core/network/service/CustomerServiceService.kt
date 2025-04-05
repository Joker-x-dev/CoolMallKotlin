package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * 客服相关接口
 */
interface CustomerServiceService {

    /**
     * 创建会话
     */
    @POST("cs/session/create")
    suspend fun createSession(@Body params: Any): NetworkResponse<Any>

    /**
     * 会话详情
     */
    @GET("cs/session/detail")
    suspend fun getSessionDetail(@Query("id") id: String): NetworkResponse<Any>

    /**
     * 消息已读
     */
    @POST("cs/msg/read")
    suspend fun readMessage(@Body params: Any): NetworkResponse<Any>

    /**
     * 分页查询消息
     */
    @POST("cs/msg/page")
    suspend fun getMessagePage(@Body params: Any): NetworkResponse<Any>

    /**
     * 未读消息数
     */
    @GET("cs/msg/unreadCount")
    suspend fun getUnreadCount(): NetworkResponse<Any>
} 