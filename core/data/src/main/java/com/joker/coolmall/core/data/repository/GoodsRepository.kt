package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.model.entity.Category
import com.joker.coolmall.core.model.entity.Comment
import com.joker.coolmall.core.model.entity.Goods
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
 *
 * @param goodsNetworkDataSource 商品网络数据源
 * @author Joker.X
 */
class GoodsRepository @Inject constructor(
    private val goodsNetworkDataSource: GoodsNetworkDataSource
) {
    /**
     * 查询商品分类
     *
     * @return 商品分类列表Flow
     * @author Joker.X
     */
    fun getGoodsTypeList(): Flow<NetworkResponse<List<Category>>> = flow {
        emit(goodsNetworkDataSource.getGoodsTypeList())
    }.flowOn(Dispatchers.IO)

    /**
     * 查询商品规格
     *
     * @param params 查询参数，包含商品ID
     * @return 商品规格列表Flow
     * @author Joker.X
     */
    fun getGoodsSpecList(params: Map<String, Long>): Flow<NetworkResponse<List<GoodsSpec>>> = flow {
        emit(goodsNetworkDataSource.getGoodsSpecList(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 查询搜索关键词列表
     *
     * @return 搜索关键词列表Flow
     * @author Joker.X
     */
    fun getSearchKeywordList(): Flow<NetworkResponse<List<GoodsSearchKeyword>>> = flow {
        emit(goodsNetworkDataSource.getSearchKeywordList())
    }.flowOn(Dispatchers.IO)

    /**
     * 分页查询商品
     *
     * @param params 搜索请求参数
     * @return 商品分页数据Flow
     * @author Joker.X
     */
    fun getGoodsPage(params: GoodsSearchRequest): Flow<NetworkResponse<NetworkPageData<Goods>>> =
        flow {
            emit(goodsNetworkDataSource.getGoodsPage(params))
        }.flowOn(Dispatchers.IO)

    /**
     * 获取商品信息
     *
     * @param id 商品ID
     * @return 商品信息Flow
     * @author Joker.X
     */
    fun getGoodsInfo(id: String): Flow<NetworkResponse<Goods>> = flow {
        emit(goodsNetworkDataSource.getGoodsInfo(id))
    }.flowOn(Dispatchers.IO)

    /**
     * 提交商品评论
     *
     * @param params 评论提交请求参数
     * @return 提交结果Flow
     * @author Joker.X
     */
    fun submitGoodsComment(params: GoodsCommentSubmitRequest): Flow<NetworkResponse<Boolean>> =
        flow {
            emit(goodsNetworkDataSource.submitGoodsComment(params))
        }.flowOn(Dispatchers.IO)

    /**
     * 分页查询商品评论
     *
     * @param params 评论分页查询参数
     * @return 评论分页数据Flow
     * @author Joker.X
     */
    fun getGoodsCommentPage(params: GoodsCommentPageRequest): Flow<NetworkResponse<NetworkPageData<Comment>>> =
        flow {
            emit(goodsNetworkDataSource.getGoodsCommentPage(params))
        }.flowOn(Dispatchers.IO)
}
