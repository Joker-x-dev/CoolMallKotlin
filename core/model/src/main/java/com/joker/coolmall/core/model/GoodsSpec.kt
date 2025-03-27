package com.joker.coolmall.core.model

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
    val images: Array<String>? = null,

    /**
     * 创建时间
     */
    val createTime: String? = null,

    /**
     * 更新时间
     */
    val updateTime: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GoodsSpec

        if (id != other.id) return false
        if (goodsId != other.goodsId) return false
        if (price != other.price) return false
        if (stock != other.stock) return false
        if (sortNum != other.sortNum) return false
        if (name != other.name) return false
        if (!images.contentEquals(other.images)) return false
        if (createTime != other.createTime) return false
        if (updateTime != other.updateTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + goodsId.hashCode()
        result = 31 * result + price
        result = 31 * result + stock
        result = 31 * result + sortNum
        result = 31 * result + name.hashCode()
        result = 31 * result + (images?.contentHashCode() ?: 0)
        result = 31 * result + (createTime?.hashCode() ?: 0)
        result = 31 * result + (updateTime?.hashCode() ?: 0)
        return result
    }
}