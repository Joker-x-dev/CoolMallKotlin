package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.model.entity.Category
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.model.entity.GoodsSpec
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
     * 修改搜索关键词
     */
    fun updateSearchKeyword(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(goodsNetworkDataSource.updateSearchKeyword(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 分页查询搜索关键词
     */
    fun getSearchKeywordPage(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(goodsNetworkDataSource.getSearchKeywordPage(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 查询搜索关键词列表
     */
    fun getSearchKeywordList(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(goodsNetworkDataSource.getSearchKeywordList(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 删除搜索关键词
     */
    fun deleteSearchKeyword(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(goodsNetworkDataSource.deleteSearchKeyword(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 添加搜索关键词
     */
    fun addSearchKeyword(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(goodsNetworkDataSource.addSearchKeyword(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 搜索关键词信息
     */
    fun getSearchKeywordInfo(id: String): Flow<NetworkResponse<Any>> = flow {
        emit(goodsNetworkDataSource.getSearchKeywordInfo(id))
    }.flowOn(Dispatchers.IO)

    /**
     * 分页查询商品
     */
    fun getGoodsPage(params: Any): Flow<NetworkResponse<Any>> = flow {
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
    fun submitGoodsComment(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(goodsNetworkDataSource.submitGoodsComment(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 分页查询商品评论
     */
    fun getGoodsCommentPage(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(goodsNetworkDataSource.getGoodsCommentPage(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 查询商品列表
     */
    fun getGoodsList(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(goodsNetworkDataSource.getGoodsList(params))
    }.flowOn(Dispatchers.IO)
} 