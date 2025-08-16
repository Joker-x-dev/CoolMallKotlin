package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.entity.Home
import com.joker.coolmall.core.model.entity.GoodsDetail
import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 页面相关接口
 */
interface PageService {

    /**
     * 获取首页数据
     */
    @GET("page/home")
    suspend fun getHomeData(): NetworkResponse<Home>

    /**
     * 获取商品详情
     * @param goodsId 商品ID
     */
    @GET("page/goodsDetail")
    suspend fun getGoodsDetail(@Query("goodsId") goodsId: Long): NetworkResponse<GoodsDetail>
}