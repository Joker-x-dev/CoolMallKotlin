package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.Goods
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * 商品API服务接口
 */
interface GoodsService {

    /**
     * 获取商品列表
     */
    @POST("goods/info/page")
    suspend fun getGoods(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): NetworkResponse<NetworkPageData<Goods>>

    /**
     * 添加/更新商品
     */
    @POST("goods")
    suspend fun saveGoods(@Body goods: Goods): NetworkResponse<Goods>
} 