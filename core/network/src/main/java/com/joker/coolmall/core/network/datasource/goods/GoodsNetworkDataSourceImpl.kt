package com.joker.coolmall.core.network.datasource.goods

import com.joker.coolmall.core.model.Category
import com.joker.coolmall.core.model.Goods
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.base.BaseNetworkDataSource
import com.joker.coolmall.core.network.service.GoodsService
import javax.inject.Inject

/**
 * 商品相关数据源实现类
 * 负责处理所有与商品相关的网络请求
 *
 * @property goodsService 商品服务接口，用于发起实际的网络请求
 */
class GoodsNetworkDataSourceImpl @Inject constructor(
    private val goodsService: GoodsService
) : BaseNetworkDataSource(), GoodsNetworkDataSource {

    /**
     * 查询商品分类列表
     *
     * @return 商品分类列表响应数据
     */
    override suspend fun getGoodsTypeList(): NetworkResponse<List<Category>> {
        return goodsService.getGoodsTypeList()
    }

    /**
     * 查询商品规格列表
     *
     * @param params 请求参数
     * @return 商品规格列表响应数据
     */
    override suspend fun getGoodsSpecList(params: Any): NetworkResponse<Any> {
        return goodsService.getGoodsSpecList(params)
    }

    /**
     * 修改搜索关键词
     *
     * @param params 请求参数，包含关键词信息
     * @return 修改结果响应数据
     */
    override suspend fun updateSearchKeyword(params: Any): NetworkResponse<Any> {
        return goodsService.updateSearchKeyword(params)
    }

    /**
     * 分页查询搜索关键词
     *
     * @param params 请求参数，包含分页信息
     * @return 搜索关键词分页列表响应数据
     */
    override suspend fun getSearchKeywordPage(params: Any): NetworkResponse<Any> {
        return goodsService.getSearchKeywordPage(params)
    }

    /**
     * 查询搜索关键词列表
     *
     * @param params 请求参数
     * @return 搜索关键词列表响应数据
     */
    override suspend fun getSearchKeywordList(params: Any): NetworkResponse<Any> {
        return goodsService.getSearchKeywordList(params)
    }

    /**
     * 删除搜索关键词
     *
     * @param params 请求参数，包含关键词ID
     * @return 删除结果响应数据
     */
    override suspend fun deleteSearchKeyword(params: Any): NetworkResponse<Any> {
        return goodsService.deleteSearchKeyword(params)
    }

    /**
     * 添加搜索关键词
     *
     * @param params 请求参数，包含关键词信息
     * @return 添加结果响应数据
     */
    override suspend fun addSearchKeyword(params: Any): NetworkResponse<Any> {
        return goodsService.addSearchKeyword(params)
    }

    /**
     * 获取搜索关键词详情
     *
     * @param id 关键词ID
     * @return 搜索关键词详情响应数据
     */
    override suspend fun getSearchKeywordInfo(id: String): NetworkResponse<Any> {
        return goodsService.getSearchKeywordInfo(id)
    }

    /**
     * 分页查询商品
     *
     * @param params 请求参数，包含分页和筛选信息
     * @return 商品分页列表响应数据
     */
    override suspend fun getGoodsPage(params: Any): NetworkResponse<Any> {
        return goodsService.getGoodsPage(params)
    }

    /**
     * 获取商品详情
     *
     * @param id 商品ID
     * @return 商品详情响应数据
     */
    override suspend fun getGoodsInfo(id: String): NetworkResponse<Goods> {
        return goodsService.getGoodsInfo(id)
    }

    /**
     * 提交商品评论
     *
     * @param params 请求参数，包含评论内容和商品ID
     * @return 评论提交结果响应数据
     */
    override suspend fun submitGoodsComment(params: Any): NetworkResponse<Any> {
        return goodsService.submitGoodsComment(params)
    }

    /**
     * 分页查询商品评论
     *
     * @param params 请求参数，包含分页和商品ID
     * @return 商品评论分页列表响应数据
     */
    override suspend fun getGoodsCommentPage(params: Any): NetworkResponse<Any> {
        return goodsService.getGoodsCommentPage(params)
    }

    /**
     * 查询商品列表
     *
     * @param params 请求参数，包含筛选条件
     * @return 商品列表响应数据
     */
    override suspend fun getGoodsList(params: Any): NetworkResponse<Any> {
        return goodsService.getGoodsList(params)
    }
} 