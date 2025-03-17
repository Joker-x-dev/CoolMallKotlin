package com.joker.coolmall.core.model

import kotlinx.serialization.Serializable

/**
 * 商品模型
 */
@Serializable
data class Goods(
    /**
     * 商品ID
     */
    val id: String = "",
    
    /**
     * 商品名称
     */
    val name: String = "",
    
    /**
     * 商品价格
     */
    val price: Double = 0.0,
    
    /**
     * 商品图片
     */
    val image: String = "",
    
    /**
     * 商品描述
     */
    val description: String = "",
    
    /**
     * 商品库存
     */
    val stock: Int = 0
)