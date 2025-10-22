package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 商品模型
 *
 * @param id ID
 * @param typeId 类型ID
 * @param title 标题
 * @param subTitle 副标题
 * @param mainPic 主图
 * @param pics 图片
 * @param price 价格
 * @param sold 已售
 * @param content 详情富文本(已弃用)
 * @param contentPics 详情图片
 * @param recommend 推荐
 * @param featured 精选
 * @param status 状态 0-下架 1-上架
 * @param sortNum 排序
 * @param specs 规格
 * @param createTime 创建时间
 * @param updateTime 更新时间
 * @author Joker.X
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
     * 详情富文本(已弃用)
     */
    val content: String? = null,

    /**
     * 详情图片
     */
    val contentPics: List<String>? = null,

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