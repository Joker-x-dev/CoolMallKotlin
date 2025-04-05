package com.joker.coolmall.core.network.datasource.cs

import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 客服相关数据源接口
 */
interface CustomerServiceNetworkDataSource {

    /**
     * 创建会话
     */
    suspend fun createSession(params: Any): NetworkResponse<Any>

    /**
     * 会话详情
     */
    suspend fun getSessionDetail(id: String): NetworkResponse<Any>

    /**
     * 消息已读
     */
    suspend fun readMessage(params: Any): NetworkResponse<Any>

    /**
     * 分页查询消息
     */
    suspend fun getMessagePage(params: Any): NetworkResponse<Any>

    /**
     * 未读消息数
     */
    suspend fun getUnreadCount(): NetworkResponse<Any>
} 