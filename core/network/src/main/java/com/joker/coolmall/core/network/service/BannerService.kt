package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.Banner
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.POST

/**
 * 轮播图API服务接口
 */
interface BannerService {

    /**
     * 获取轮播图列表
     */
    @POST("/info/banner/list")
    suspend fun getBannerList(): NetworkResponse<NetworkPageData<Banner>>
}