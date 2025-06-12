package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.entity.Category
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.model.entity.GoodsSearchKeyword
import com.joker.coolmall.core.model.entity.GoodsSpec
import com.joker.coolmall.core.model.request.GoodsSearchRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * 商品相关接口
 */
interface GoodsService {

    /**
     * 查询商品分类
     */
    @POST("goods/type/list")
    suspend fun getGoodsTypeList(): NetworkResponse<List<Category>>

    /**
     * 查询商品规格
     */
    @POST("goods/spec/list")
    suspend fun getGoodsSpecList(@Body params: Map<String, Long>): NetworkResponse<List<GoodsSpec>>

    /**
     * 查询搜索关键词列表
     */
    @POST("goods/search/keyword/list")
    suspend fun getSearchKeywordList(): NetworkResponse<List<GoodsSearchKeyword>>

    /**
     * 分页查询商品
     */
    @POST("goods/info/page")
    suspend fun getGoodsPage(@Body params: GoodsSearchRequest): NetworkResponse<NetworkPageData<Goods>>

    /**
     * 商品信息
     */
    @GET("goods/info/info")
    suspend fun getGoodsInfo(@Query("id") id: String): NetworkResponse<Goods>

    /**
     * 提交商品评论
     */
    @POST("goods/comment/submit")
    suspend fun submitGoodsComment(@Body params: Any): NetworkResponse<Any>

    /**
     * 分页查询商品评论
     */
    @POST("goods/comment/page")
    suspend fun getGoodsCommentPage(@Body params: Any): NetworkResponse<Any>
} 