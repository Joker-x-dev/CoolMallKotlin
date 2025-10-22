package com.joker.coolmall.core.network.datasource.cs

import com.joker.coolmall.core.model.entity.CsMsg
import com.joker.coolmall.core.model.entity.CsSession
import com.joker.coolmall.core.model.request.MessagePageRequest
import com.joker.coolmall.core.model.request.ReadMessageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.base.BaseNetworkDataSource
import com.joker.coolmall.core.network.service.CustomerServiceService
import javax.inject.Inject

/**
 * 客服相关数据源实现类
 * 负责处理所有与客服相关的网络请求
 *
 * @param customerServiceService 客服服务接口，用于发起实际的网络请求
 * @author Joker.X
 */
class CustomerServiceNetworkDataSourceImpl @Inject constructor(
    private val customerServiceService: CustomerServiceService
) : BaseNetworkDataSource(), CustomerServiceNetworkDataSource {

    /**
     * 创建客服会话
     *
     * @return 会话创建结果响应数据
     * @author Joker.X
     */
    override suspend fun createSession(): NetworkResponse<CsSession> {
        return customerServiceService.createSession()
    }

    /**
     * 获取会话详情
     *
     * @return 会话详情响应数据
     * @author Joker.X
     */
    override suspend fun getSessionDetail(): NetworkResponse<CsSession> {
        return customerServiceService.getSessionDetail()
    }

    /**
     * 标记消息为已读
     *
     * @param params 请求参数，包含消息ID等
     * @return 操作结果响应数据
     * @author Joker.X
     */
    override suspend fun readMessage(params: ReadMessageRequest): NetworkResponse<Boolean> {
        return customerServiceService.readMessage(params)
    }

    /**
     * 分页查询消息
     *
     * @param params 请求参数，包含分页和会话ID等
     * @return 消息分页列表响应数据
     * @author Joker.X
     */
    override suspend fun getMessagePage(params: MessagePageRequest): NetworkResponse<NetworkPageData<CsMsg>> {
        return customerServiceService.getMessagePage(params)
    }

    /**
     * 获取未读消息数
     *
     * @return 未读消息数响应数据
     * @author Joker.X
     */
    override suspend fun getUnreadCount(): NetworkResponse<Int> {
        return customerServiceService.getUnreadCount()
    }
}
