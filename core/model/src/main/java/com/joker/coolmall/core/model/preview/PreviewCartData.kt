package com.joker.coolmall.core.model.preview

import com.joker.coolmall.core.model.entity.Cart
import com.joker.coolmall.core.model.entity.CartGoodsSpec

val previewCartList = listOf(
    Cart().apply {
        goodsId = 1L
        goodsName = "苹果 iPhone 15 Pro"
        goodsMainPic = "https://example.com/iphone15pro.jpg"
        spec = listOf(
            CartGoodsSpec(
                id = 101L,
                goodsId = 1L,
                name = "256GB 银色",
                price = 8999,
                stock = 10,
                count = 1,
                images = listOf("https://example.com/iphone15pro_silver.jpg")
            )
        )
    },
    Cart().apply {
        goodsId = 2L
        goodsName = "小米 14 Ultra"
        goodsMainPic = "https://example.com/xiaomi14ultra.jpg"
        spec = listOf(
            CartGoodsSpec(
                id = 102L,
                goodsId = 2L,
                name = "12GB+256GB 黑色",
                price = 6499,
                stock = 5,
                count = 2,
                images = listOf("https://example.com/xiaomi14ultra_black.jpg")
            )
        )
    }
)

val previewCart = previewCartList.first()
val previewCartSpec = previewCart.spec.firstOrNull() 