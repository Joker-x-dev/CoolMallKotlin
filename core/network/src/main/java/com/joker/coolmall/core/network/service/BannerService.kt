package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 轮播图相关接口
 *
 * @author Joker.X
 */
interface BannerService {

    /**
     * 查询轮播图列表
     *
     * @param params 请求参数
     * @return 轮播图列表响应
     * @author Joker.X
     */
    @POST("info/banner/list")
    suspend fun getBannerList(@Body params: Any): NetworkResponse<Any>
} 