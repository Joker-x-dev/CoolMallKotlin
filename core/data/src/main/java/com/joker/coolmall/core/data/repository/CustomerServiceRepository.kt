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
 *
 * @param customerServiceNetworkDataSource 客服网络数据源
 * @author Joker.X
 */
class CustomerServiceRepository @Inject constructor(
    private val customerServiceNetworkDataSource: CustomerServiceNetworkDataSource
) {
    /**
     * 创建客服会话
     *
     * @return 客服会话Flow
     * @author Joker.X
     */
    fun createSession(): Flow<NetworkResponse<CsSession>> = flow {
        emit(customerServiceNetworkDataSource.createSession())
    }.flowOn(Dispatchers.IO)

    /**
     * 获取会话详情
     *
     * @return 会话详情Flow
     * @author Joker.X
     */
    fun getSessionDetail(): Flow<NetworkResponse<CsSession>> = flow {
        emit(customerServiceNetworkDataSource.getSessionDetail())
    }.flowOn(Dispatchers.IO)

    /**
     * 将消息标记为已读
     *
     * @param params 已读消息请求参数
     * @return 标记结果Flow
     * @author Joker.X
     */
    fun readMessage(params: ReadMessageRequest): Flow<NetworkResponse<Boolean>> = flow {
        emit(customerServiceNetworkDataSource.readMessage(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 分页查询消息
     *
     * @param params 消息分页查询参数
     * @return 消息分页数据Flow
     * @author Joker.X
     */
    fun getMessagePage(params: MessagePageRequest): Flow<NetworkResponse<NetworkPageData<CsMsg>>> =
        flow {
            emit(customerServiceNetworkDataSource.getMessagePage(params))
        }.flowOn(Dispatchers.IO)

    /**
     * 获取未读消息数
     *
     * @return 未读消息数Flow
     * @author Joker.X
     */
    fun getUnreadCount(): Flow<NetworkResponse<Int>> = flow {
        emit(customerServiceNetworkDataSource.getUnreadCount())
    }.flowOn(Dispatchers.IO)
}
