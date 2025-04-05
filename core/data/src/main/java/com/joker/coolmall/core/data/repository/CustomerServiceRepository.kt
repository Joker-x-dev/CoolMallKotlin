package com.joker.coolmall.core.data.repository

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
    fun createSession(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(customerServiceNetworkDataSource.createSession(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 会话详情
     */
    fun getSessionDetail(id: String): Flow<NetworkResponse<Any>> = flow {
        emit(customerServiceNetworkDataSource.getSessionDetail(id))
    }.flowOn(Dispatchers.IO)

    /**
     * 消息已读
     */
    fun readMessage(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(customerServiceNetworkDataSource.readMessage(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 分页查询消息
     */
    fun getMessagePage(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(customerServiceNetworkDataSource.getMessagePage(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 未读消息数
     */
    fun getUnreadCount(): Flow<NetworkResponse<Any>> = flow {
        emit(customerServiceNetworkDataSource.getUnreadCount())
    }.flowOn(Dispatchers.IO)
} 