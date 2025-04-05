package com.joker.coolmall.core.network.datasource.goods

import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 商品相关数据源接口
 */
interface GoodsNetworkDataSource {

    /**
     * 查询商品分类
     */
    suspend fun getGoodsTypeList(params: Any): NetworkResponse<Any>

    /**
     * 查询商品规格
     */
    suspend fun getGoodsSpecList(params: Any): NetworkResponse<Any>

    /**
     * 修改搜索关键词
     */
    suspend fun updateSearchKeyword(params: Any): NetworkResponse<Any>

    /**
     * 分页查询搜索关键词
     */
    suspend fun getSearchKeywordPage(params: Any): NetworkResponse<Any>

    /**
     * 查询搜索关键词列表
     */
    suspend fun getSearchKeywordList(params: Any): NetworkResponse<Any>

    /**
     * 删除搜索关键词
     */
    suspend fun deleteSearchKeyword(params: Any): NetworkResponse<Any>

    /**
     * 添加搜索关键词
     */
    suspend fun addSearchKeyword(params: Any): NetworkResponse<Any>

    /**
     * 搜索关键词信息
     */
    suspend fun getSearchKeywordInfo(id: String): NetworkResponse<Any>

    /**
     * 分页查询商品
     */
    suspend fun getGoodsPage(params: Any): NetworkResponse<Any>

    /**
     * 商品信息
     */
    suspend fun getGoodsInfo(id: String): NetworkResponse<Any>

    /**
     * 提交商品评论
     */
    suspend fun submitGoodsComment(params: Any): NetworkResponse<Any>

    /**
     * 分页查询商品评论
     */
    suspend fun getGoodsCommentPage(params: Any): NetworkResponse<Any>

    /**
     * 查询商品列表
     */
    suspend fun getGoodsList(params: Any): NetworkResponse<Any>
} 