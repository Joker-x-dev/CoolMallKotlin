package com.joker.coolmall.core.network.datasource.cs

import com.joker.coolmall.core.model.entity.CsMsg
import com.joker.coolmall.core.model.entity.CsSession
import com.joker.coolmall.core.model.request.MessagePageRequest
import com.joker.coolmall.core.model.request.ReadMessageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 客服相关数据源接口
 *
 * @author Joker.X
 */
interface CustomerServiceNetworkDataSource {

    /**
     * 创建客服会话
     *
     * @return 客服会话响应
     * @author Joker.X
     */
    suspend fun createSession(): NetworkResponse<CsSession>

    /**
     * 获取会话详情
     *
     * @return 会话详情响应
     * @author Joker.X
     */
    suspend fun getSessionDetail(): NetworkResponse<CsSession>

    /**
     * 标记消息为已读
     *
     * @param params 已读消息请求参数
     * @return 操作结果响应
     * @author Joker.X
     */
    suspend fun readMessage(params: ReadMessageRequest): NetworkResponse<Boolean>

    /**
     * 分页查询消息
     *
     * @param params 消息分页请求参数
     * @return 消息分页数据响应
     * @author Joker.X
     */
    suspend fun getMessagePage(params: MessagePageRequest): NetworkResponse<NetworkPageData<CsMsg>>

    /**
     * 获取未读消息数
     *
     * @return 未读消息数响应
     * @author Joker.X
     */
    suspend fun getUnreadCount(): NetworkResponse<Int>
}
