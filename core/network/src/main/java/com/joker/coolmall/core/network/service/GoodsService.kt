package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.Category
import com.joker.coolmall.core.model.Goods
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
    suspend fun getGoodsSpecList(@Body params: Any): NetworkResponse<Any>

    /**
     * 修改搜索关键词
     */
    @POST("goods/search/keyword/update")
    suspend fun updateSearchKeyword(@Body params: Any): NetworkResponse<Any>

    /**
     * 分页查询搜索关键词
     */
    @POST("goods/search/keyword/page")
    suspend fun getSearchKeywordPage(@Body params: Any): NetworkResponse<Any>

    /**
     * 查询搜索关键词列表
     */
    @POST("goods/search/keyword/list")
    suspend fun getSearchKeywordList(@Body params: Any): NetworkResponse<Any>

    /**
     * 删除搜索关键词
     */
    @POST("goods/search/keyword/delete")
    suspend fun deleteSearchKeyword(@Body params: Any): NetworkResponse<Any>

    /**
     * 添加搜索关键词
     */
    @POST("goods/search/keyword/add")
    suspend fun addSearchKeyword(@Body params: Any): NetworkResponse<Any>

    /**
     * 搜索关键词信息
     */
    @GET("goods/search/keyword/info")
    suspend fun getSearchKeywordInfo(@Query("id") id: String): NetworkResponse<Any>

    /**
     * 分页查询商品
     */
    @POST("goods/info/page")
    suspend fun getGoodsPage(@Body params: Any): NetworkResponse<Any>

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

    /**
     * 查询商品列表
     */
    @POST("app/goods/list")
    suspend fun getGoodsList(@Body params: Any): NetworkResponse<Any>
} 