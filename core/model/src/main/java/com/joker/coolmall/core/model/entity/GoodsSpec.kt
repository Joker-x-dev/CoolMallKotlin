package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 规格模型
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