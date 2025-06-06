package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 商品模型
 */
@Serializable
data class Goods(

    /**
     * ID
     */
    val id: Long = 0,

    /**
     * 类型ID
     */
    val typeId: Long = 0,

    /**
     * 标题
     */
    val title: String = "",

    /**
     * 副标题
     */
    val subTitle: String? = null,

    /**
     * 主图
     */
    val mainPic: String = "",

    /**
     * 图片
     */
    val pics: List<String>? = null,

    /**
     * 价格
     */
    val price: Int = 0,

    /**
     * 已售
     */
    val sold: Int = 0,

    /**
     * 详情
     */
    val content: String? = null,

    /**
     * 推荐
     */
    val recommend: Boolean = false,

    /**
     * 精选
     */
    val featured: Boolean = false,

    /**
     * 状态 0-下架 1-上架
     */
    val status: Int = 0,

    /**
     * 排序
     */
    val sortNum: Int = 0,

    /**
     * 规格
     */
    val specs: List<GoodsSpec>? = null,

    /**
     * 创建时间
     */
    val createTime: String? = null,

    /**
     * 更新时间
     */
    val updateTime: String? = null
)