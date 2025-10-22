package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 规格模型
 *
 * @param id ID
 * @param goodsId 商品ID
 * @param name 名称
 * @param price 价格
 * @param stock 库存
 * @param sortNum 排序
 * @param images 图片
 * @param createTime 创建时间
 * @param updateTime 更新时间
 * @author Joker.X
 */
@Serializable
data class GoodsSpec(
    /**
     * ID
     */
    val id: Long = 0,

    /**
     * 商品ID
     */
    val goodsId: Long = 0,

    /**
     * 名称
     */
    val name: String = "",

    /**
     * 价格
     */
    val price: Int = 0,

    /**
     * 库存
     */
    val stock: Int = 0,

    /**
     * 排序
     */
    val sortNum: Int = 0,

    /**
     * 图片
     */
    val images: List<String>? = null,

    /**
     * 创建时间
     */
    val createTime: String? = null,

    /**
     * 更新时间
     */
    val updateTime: String? = null
)