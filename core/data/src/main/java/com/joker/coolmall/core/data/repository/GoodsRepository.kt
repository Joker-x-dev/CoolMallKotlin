package com.joker.coolmall.core.data.repository

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
import com.joker.coolmall.core.network.datasource.goods.GoodsNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 商品相关仓库
 */
class GoodsRepository @Inject constructor(
    private val goodsNetworkDataSource: GoodsNetworkDataSource
) {
    /**
     * 查询商品分类
     */
    fun getGoodsTypeList(): Flow<NetworkResponse<List<Category>>> = flow {
        emit(goodsNetworkDataSource.getGoodsTypeList())
    }.flowOn(Dispatchers.IO)

    /**
     * 查询商品规格
     */
    fun getGoodsSpecList(params: Map<String, Long>): Flow<NetworkResponse<List<GoodsSpec>>> = flow {
        emit(goodsNetworkDataSource.getGoodsSpecList(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 查询搜索关键词列表
     */
    fun getSearchKeywordList(): Flow<NetworkResponse<List<GoodsSearchKeyword>>> = flow {
        emit(goodsNetworkDataSource.getSearchKeywordList())
    }.flowOn(Dispatchers.IO)

    /**
     * 分页查询商品
     */
    fun getGoodsPage(params: GoodsSearchRequest): Flow<NetworkResponse<NetworkPageData<Goods>>> =
        flow {
            emit(goodsNetworkDataSource.getGoodsPage(params))
        }.flowOn(Dispatchers.IO)

    /**
     * 商品信息
     */
    fun getGoodsInfo(id: String): Flow<NetworkResponse<Goods>> = flow {
        emit(goodsNetworkDataSource.getGoodsInfo(id))
    }.flowOn(Dispatchers.IO)

    /**
     * 提交商品评论
     */
    fun submitGoodsComment(params: GoodsCommentSubmitRequest): Flow<NetworkResponse<Boolean>> = flow {
        emit(goodsNetworkDataSource.submitGoodsComment(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 分页查询商品评论
     */
    fun getGoodsCommentPage(params: GoodsCommentPageRequest): Flow<NetworkResponse<NetworkPageData<Comment>>> = flow {
        emit(goodsNetworkDataSource.getGoodsCommentPage(params))
    }.flowOn(Dispatchers.IO)
}