package com.joker.coolmall.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.joker.coolmall.core.database.util.CartSpecConverter
import com.joker.coolmall.core.model.entity.CartGoodsSpec

/**
 * 购物车数据库实体
 */
@Entity(tableName = "carts")
data class CartEntity(
    /**
     * 主键，使用商品ID作为主键
     */
    @PrimaryKey
    val goodsId: Long,

    /**
     * 商品名称
     */
    val goodsName: String,

    /**
     * 商品主图
     */
    val goodsMainPic: String,

    /**
     * 规格列表 - 需要类型转换器
     */
     @param:TypeConverters(CartSpecConverter::class)
    val spec: List<CartGoodsSpec> = emptyList()
)