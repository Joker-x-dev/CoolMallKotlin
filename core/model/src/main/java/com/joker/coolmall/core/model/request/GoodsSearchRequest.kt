package com.joker.coolmall.core.model.request

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.Serializable

/**
 * 商品搜索分页请求模型
 *
 * @param page 页码
 * @param size 每页大小
 * @param typeId 商品分类ID列表
 * @param minPrice 最低价格
 * @param maxPrice 最高价格
 * @param keyWord 搜索关键词
 * @param order 排序字段
 * @param sort 排序方式 asc升序 desc降序
 * @param recommend 推荐
 * @param featured 精选
 * @author Joker.X
 */
@Serializable
data class GoodsSearchRequest(
    /**
     * 页码
     */
    @EncodeDefault
    val page: Int = 1,

    /**
     * 每页大小
     */
    @EncodeDefault
    val size: Int = 20,

    /**
     * 商品分类ID列表
     */
    val typeId: List<Long>? = null,

    /**
     * 最低价格
     */
    val minPrice: String? = null,

    /**
     * 最高价格
     */
    val maxPrice: String? = null,

    /**
     * 搜索关键词
     */
    val keyWord: String? = null,

    /**
     * 排序字段（如：sold、price等）
     */
    val order: String? = null,

    /**
     * 排序方式："asc"升序，"desc"降序
     */
    val sort: String? = null,

    /**
     * 推荐
     */
    val recommend: Boolean = false,

    /**
     * 精选
     */
    val featured: Boolean = false,
)