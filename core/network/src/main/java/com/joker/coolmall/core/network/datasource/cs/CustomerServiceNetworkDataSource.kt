package com.joker.coolmall.core.network.datasource.cs

import com.joker.coolmall.core.model.entity.CsMsg
import com.joker.coolmall.core.model.entity.CsSession
import com.joker.coolmall.core.model.request.MessagePageRequest
import com.joker.coolmall.core.model.request.ReadMessageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 客服相关数据源接口
 */
interface CustomerServiceNetworkDataSource {

    /**
     * 创建会话
     */
    suspend fun createSession(): NetworkResponse<CsSession>

    /**
     * 会话详情
     */
    suspend fun getSessionDetail(): NetworkResponse<CsSession>

    /**
     * 消息标记为已读
     */
    suspend fun readMessage(params: ReadMessageRequest): NetworkResponse<Boolean>

    /**
     * 分页查询消息
     */
    suspend fun getMessagePage(params: MessagePageRequest): NetworkResponse<NetworkPageData<CsMsg>>

    /**
     * 未读消息数
     */
    suspend fun getUnreadCount(): NetworkResponse<Int>
} 