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
 *
 * @param feedbackNetworkDataSource 意见反馈网络数据源
 * @author Joker.X
 */
class FeedbackRepository @Inject constructor(
    private val feedbackNetworkDataSource: FeedbackNetworkDataSource
) {
    /**
     * 提交意见反馈
     *
     * @param params 反馈提交请求参数
     * @return 提交结果Flow
     * @author Joker.X
     */
    fun submitFeedback(params: FeedbackSubmitRequest): Flow<NetworkResponse<Boolean>> = flow {
        emit(feedbackNetworkDataSource.submitFeedback(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 分页查询意见反馈列表
     *
     * @param params 分页请求参数
     * @return 反馈列表分页数据Flow
     * @author Joker.X
     */
    fun getFeedbackPage(params: PageRequest): Flow<NetworkResponse<NetworkPageData<Feedback>>> =
        flow {
            emit(feedbackNetworkDataSource.getFeedbackPage(params))
        }.flowOn(Dispatchers.IO)
}
