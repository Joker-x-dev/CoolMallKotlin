package com.joker.coolmall.core.network.datasource.goods

import com.joker.coolmall.core.model.entity.Category
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.model.entity.Comment
import com.joker.coolmall.core.model.entity.GoodsSearchKeyword
import com.joker.coolmall.core.model.entity.GoodsSpec
import com.joker.coolmall.core.model.request.GoodsCommentPageRequest
import com.joker.coolmall.core.model.request.GoodsCommentSubmitRequest
import com.joker.coolmall.core.model.request.GoodsSearchRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 商品相关数据源接口
 */
interface GoodsNetworkDataSource {

    /**
     * 查询商品分类
     */
    suspend fun getGoodsTypeList(): NetworkResponse<List<Category>>

    /**
     * 查询商品规格
     */
    suspend fun getGoodsSpecList(params: Map<String, Long>): NetworkResponse<List<GoodsSpec>>

    /**
     * 查询搜索关键词列表
     */
    suspend fun getSearchKeywordList(): NetworkResponse<List<GoodsSearchKeyword>>

    /**
     * 分页查询商品
     */
    suspend fun getGoodsPage(params: GoodsSearchRequest): NetworkResponse<NetworkPageData<Goods>>

    /**
     * 商品信息
     */
    suspend fun getGoodsInfo(id: String): NetworkResponse<Goods>

    /**
     * 提交商品评论
     */
    suspend fun submitGoodsComment(params: GoodsCommentSubmitRequest): NetworkResponse<Boolean>

    /**
     * 分页查询商品评论
     */
    suspend fun getGoodsCommentPage(params: GoodsCommentPageRequest): NetworkResponse<NetworkPageData<Comment>>
}