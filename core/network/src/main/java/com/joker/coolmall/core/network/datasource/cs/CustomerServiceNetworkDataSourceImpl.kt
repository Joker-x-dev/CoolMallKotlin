package com.joker.coolmall.core.network.datasource.cs

import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.base.BaseNetworkDataSource
import com.joker.coolmall.core.network.service.CustomerServiceService
import javax.inject.Inject

/**
 * 客服相关数据源实现类
 * 负责处理所有与客服相关的网络请求
 * 
 * @property customerServiceService 客服服务接口，用于发起实际的网络请求
 */
class CustomerServiceNetworkDataSourceImpl @Inject constructor(
    private val customerServiceService: CustomerServiceService
) : BaseNetworkDataSource(), CustomerServiceNetworkDataSource {

    /**
     * 创建客服会话
     * 
     * @param params 请求参数，包含用户信息等
     * @return 会话创建结果响应数据
     */
    override suspend fun createSession(params: Any): NetworkResponse<Any> {
        return customerServiceService.createSession(params)
    }

    /**
     * 获取会话详情
     * 
     * @param id 会话ID
     * @return 会话详情响应数据
     */
    override suspend fun getSessionDetail(id: String): NetworkResponse<Any> {
        return customerServiceService.getSessionDetail(id)
    }

    /**
     * 标记消息为已读
     * 
     * @param params 请求参数，包含消息ID等
     * @return 操作结果响应数据
     */
    override suspend fun readMessage(params: Any): NetworkResponse<Any> {
        return customerServiceService.readMessage(params)
    }

    /**
     * 分页查询消息
     * 
     * @param params 请求参数，包含分页和会话ID等
     * @return 消息分页列表响应数据
     */
    override suspend fun getMessagePage(params: Any): NetworkResponse<Any> {
        return customerServiceService.getMessagePage(params)
    }

    /**
     * 获取未读消息数
     * 
     * @return 未读消息数响应数据
     */
    override suspend fun getUnreadCount(): NetworkResponse<Any> {
        return customerServiceService.getUnreadCount()
    }
} 