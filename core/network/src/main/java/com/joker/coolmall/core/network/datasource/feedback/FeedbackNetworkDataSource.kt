/**
 * 意见反馈相关数据源接口
 *
 * @author Joker.X
 */
package com.joker.coolmall.core.network.datasource.feedback

import com.joker.coolmall.core.model.entity.Feedback
import com.joker.coolmall.core.model.request.FeedbackSubmitRequest
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 意见反馈相关数据源接口
 */
interface FeedbackNetworkDataSource {

    /**
     * 提交意见反馈
     */
    suspend fun submitFeedback(params: FeedbackSubmitRequest): NetworkResponse<Boolean>

    /**
     * 分页查询意见反馈列表
     */
    suspend fun getFeedbackPage(params: PageRequest): NetworkResponse<NetworkPageData<Feedback>>
}