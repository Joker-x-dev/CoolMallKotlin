package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.model.entity.CsMsg
import com.joker.coolmall.core.model.entity.CsSession
import com.joker.coolmall.core.model.request.MessagePageRequest
import com.joker.coolmall.core.model.request.ReadMessageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.datasource.cs.CustomerServiceNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 客服相关仓库
 */
class CustomerServiceRepository @Inject constructor(
    private val customerServiceNetworkDataSource: CustomerServiceNetworkDataSource
) {
    /**
     * 创建会话
     */
    fun createSession(): Flow<NetworkResponse<CsSession>> = flow {
        emit(customerServiceNetworkDataSource.createSession())
    }.flowOn(Dispatchers.IO)

    /**
     * 会话详情
     */
    fun getSessionDetail(): Flow<NetworkResponse<CsSession>> = flow {
        emit(customerServiceNetworkDataSource.getSessionDetail())
    }.flowOn(Dispatchers.IO)

    /**
     * 消息标记为已读
     */
    fun readMessage(params: ReadMessageRequest): Flow<NetworkResponse<Boolean>> = flow {
        emit(customerServiceNetworkDataSource.readMessage(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 分页查询消息
     */
    fun getMessagePage(params: MessagePageRequest): Flow<NetworkResponse<NetworkPageData<CsMsg>>> =
        flow {
            emit(customerServiceNetworkDataSource.getMessagePage(params))
        }.flowOn(Dispatchers.IO)

    /**
     * 未读消息数
     */
    fun getUnreadCount(): Flow<NetworkResponse<Int>> = flow {
        emit(customerServiceNetworkDataSource.getUnreadCount())
    }.flowOn(Dispatchers.IO)
} 