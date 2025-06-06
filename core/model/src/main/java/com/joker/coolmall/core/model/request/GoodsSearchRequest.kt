package com.joker.coolmall.core.model.request

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.Serializable

/**
 * 商品搜索分页请求模型
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