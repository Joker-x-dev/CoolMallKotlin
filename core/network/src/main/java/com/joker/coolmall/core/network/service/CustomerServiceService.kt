package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.entity.CsMsg
import com.joker.coolmall.core.model.entity.CsSession
import com.joker.coolmall.core.model.request.MessagePageRequest
import com.joker.coolmall.core.model.request.ReadMessageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * 客服相关接口
 */
interface CustomerServiceService {

    /**
     * 创建会话
     */
    @POST("cs/session/create")
    suspend fun createSession(): NetworkResponse<CsSession>

    /**
     * 会话详情
     */
    @GET("cs/session/detail")
    suspend fun getSessionDetail(): NetworkResponse<CsSession>

    /**
     * 消息标记为已读
     */
    @POST("cs/msg/read")
    suspend fun readMessage(@Body params: ReadMessageRequest): NetworkResponse<Boolean>

    /**
     * 分页查询消息
     */
    @POST("cs/msg/page")
    suspend fun getMessagePage(@Body params: MessagePageRequest): NetworkResponse<NetworkPageData<CsMsg>>

    /**
     * 未读消息数
     */
    @GET("cs/msg/unreadCount")
    suspend fun getUnreadCount(): NetworkResponse<Int>
} 