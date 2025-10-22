package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.entity.Feedback
import com.joker.coolmall.core.model.request.FeedbackSubmitRequest
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 意见反馈相关接口
 *
 * @author Joker.X
 */
interface FeedbackService {

    /**
     * 提交意见反馈
     *
     * @param params 反馈提交请求参数
     * @return 提交结果响应
     * @author Joker.X
     */
    @POST("app/feedback/submit")
    suspend fun submitFeedback(@Body params: FeedbackSubmitRequest): NetworkResponse<Boolean>

    /**
     * 分页查询意见反馈列表
     *
     * @param params 分页请求参数
     * @return 反馈分页数据响应
     * @author Joker.X
     */
    @POST("app/feedback/page")
    suspend fun getFeedbackPage(@Body params: PageRequest): NetworkResponse<NetworkPageData<Feedback>>
}
