package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 轮播图相关接口
 */
interface BannerService {

    /**
     * 查询轮播图列表
     */
    @POST("info/banner/list")
    suspend fun getBannerList(@Body params: Any): NetworkResponse<Any>
} 