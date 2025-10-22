package com.joker.coolmall.core.network.datasource.feedback

import com.joker.coolmall.core.model.entity.Feedback
import com.joker.coolmall.core.model.request.FeedbackSubmitRequest
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 意见反馈相关数据源接口
 *
 * @author Joker.X
 */
interface FeedbackNetworkDataSource {

    /**
     * 提交意见反馈
     *
     * @param params 反馈提交请求参数
     * @return 提交结果响应
     * @author Joker.X
     */
    suspend fun submitFeedback(params: FeedbackSubmitRequest): NetworkResponse<Boolean>

    /**
     * 分页查询意见反馈列表
     *
     * @param params 分页请求参数
     * @return 反馈分页数据响应
     * @author Joker.X
     */
    suspend fun getFeedbackPage(params: PageRequest): NetworkResponse<NetworkPageData<Feedback>>
}
