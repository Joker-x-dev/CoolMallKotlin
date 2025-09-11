/**
 * 意见反馈相关仓库
 *
 * @author Joker.X
 */
package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.model.entity.Feedback
import com.joker.coolmall.core.model.request.FeedbackSubmitRequest
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.datasource.feedback.FeedbackNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 意见反馈相关仓库
 */
class FeedbackRepository @Inject constructor(
    private val feedbackNetworkDataSource: FeedbackNetworkDataSource
) {
    /**
     * 提交意见反馈
     */
    fun submitFeedback(params: FeedbackSubmitRequest): Flow<NetworkResponse<Any>> = flow {
        emit(feedbackNetworkDataSource.submitFeedback(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 分页查询意见反馈列表
     */
    fun getFeedbackPage(params: PageRequest): Flow<NetworkResponse<NetworkPageData<Feedback>>> = flow {
        emit(feedbackNetworkDataSource.getFeedbackPage(params))
    }.flowOn(Dispatchers.IO)
}