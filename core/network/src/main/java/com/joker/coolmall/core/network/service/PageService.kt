package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.entity.ConfirmOrder
import com.joker.coolmall.core.model.entity.GoodsDetail
import com.joker.coolmall.core.model.entity.Home
import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 页面相关接口
 *
 * @author Joker.X
 */
interface PageService {

    /**
     * 获取首页数据
     *
     * @return 首页数据响应
     * @author Joker.X
     */
    @GET("page/home")
    suspend fun getHomeData(): NetworkResponse<Home>

    /**
     * 获取商品详情
     *
     * @param goodsId 商品ID
     * @return 商品详情响应
     * @author Joker.X
     */
    @GET("page/goodsDetail")
    suspend fun getGoodsDetail(@Query("goodsId") goodsId: Long): NetworkResponse<GoodsDetail>

    /**
     * 获取确认订单页面数据
     *
     * @return 确认订单页面数据响应
     * @author Joker.X
     */
    @GET("page/confirmOrder")
    suspend fun getConfirmOrder(): NetworkResponse<ConfirmOrder>
}
