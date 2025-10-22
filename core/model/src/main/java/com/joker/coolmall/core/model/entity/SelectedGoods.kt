package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 已选商品项模型
 * 用于规格选择后返回的数据，表示用户选择的单个商品规格项
 *
 * @param goodsId 商品 id
 * @param goodsInfo 商品信息
 * @param spec 规格信息
 * @param count 数量
 * @author Joker.X
 */
@Serializable
data class SelectedGoods(
    /**
     * 商品 id
     */
    var goodsId: Long = 0,

    /**
     * 商品信息
     */
    var goodsInfo: Goods? = null,

    /**
     * 规格
     */
    var spec: GoodsSpec? = null,

    /**
     * 数量
     */
    var count: Int = 0
)