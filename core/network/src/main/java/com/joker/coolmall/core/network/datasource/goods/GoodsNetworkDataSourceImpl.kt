package com.joker.coolmall.core.network.datasource.goods

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
import com.joker.coolmall.core.network.base.BaseNetworkDataSource
import com.joker.coolmall.core.network.service.GoodsService
import javax.inject.Inject

/**
 * 商品相关数据源实现类
 * 负责处理所有与商品相关的网络请求
 *
 * @param goodsService 商品服务接口，用于发起实际的网络请求
 * @author Joker.X
 */
class GoodsNetworkDataSourceImpl @Inject constructor(
    private val goodsService: GoodsService
) : BaseNetworkDataSource(), GoodsNetworkDataSource {

    /**
     * 查询商品分类列表
     *
     * @return 商品分类列表响应数据
     * @author Joker.X
     */
    override suspend fun getGoodsTypeList(): NetworkResponse<List<Category>> {
        return goodsService.getGoodsTypeList()
    }

    /**
     * 查询商品规格列表
     *
     * @param params 请求参数
     * @return 商品规格列表响应数据
     * @author Joker.X
     */
    override suspend fun getGoodsSpecList(params: Map<String, Long>): NetworkResponse<List<GoodsSpec>> {
        return goodsService.getGoodsSpecList(params)
    }

    /**
     * 查询搜索关键词列表
     *
     * @return 搜索关键词列表响应数据
     * @author Joker.X
     */
    override suspend fun getSearchKeywordList(): NetworkResponse<List<GoodsSearchKeyword>> {
        return goodsService.getSearchKeywordList()
    }

    /**
     * 分页查询商品
     *
     * @param params 请求参数，包含分页和筛选信息
     * @return 商品分页列表响应数据
     * @author Joker.X
     */
    override suspend fun getGoodsPage(params: GoodsSearchRequest): NetworkResponse<NetworkPageData<Goods>> {
        return goodsService.getGoodsPage(params)
    }

    /**
     * 获取商品详情
     *
     * @param id 商品ID
     * @return 商品详情响应数据
     * @author Joker.X
     */
    override suspend fun getGoodsInfo(id: String): NetworkResponse<Goods> {
        return goodsService.getGoodsInfo(id)
    }

    /**
     * 提交商品评论
     *
     * @param params 请求参数，包含评论内容和商品ID
     * @return 评论提交结果响应数据
     * @author Joker.X
     */
    override suspend fun submitGoodsComment(params: GoodsCommentSubmitRequest): NetworkResponse<Boolean> {
        return goodsService.submitGoodsComment(params)
    }

    /**
     * 分页查询商品评论
     *
     * @param params 请求参数，包含分页和商品ID
     * @return 商品评论分页列表响应数据
     * @author Joker.X
     */
    override suspend fun getGoodsCommentPage(params: GoodsCommentPageRequest): NetworkResponse<NetworkPageData<Comment>> {
        return goodsService.getGoodsCommentPage(params)
    }
}
