package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.entity.Home
import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.GET

/**
 * 页面相关接口
 */
interface PageService {

    /**
     * 获取首页数据
     */
    @GET("page/home")
    suspend fun getHomeData(): NetworkResponse<Home>
}