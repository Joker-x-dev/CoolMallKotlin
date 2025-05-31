package com.joker.coolmall.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 用户足迹数据库实体
 */
@Entity(tableName = "footprints")
data class FootprintEntity(
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
     * 商品副标题
     */
    val goodsSubTitle: String?,

    /**
     * 商品主图
     */
    val goodsMainPic: String,

    /**
     * 商品价格
     */
    val goodsPrice: Int,

    /**
     * 已售数量
     */
    val goodsSold: Int,

    /**
     * 浏览时间戳
     */
    val viewTime: Long = System.currentTimeMillis()
)