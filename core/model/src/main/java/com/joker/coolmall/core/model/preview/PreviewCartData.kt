package com.joker.coolmall.core.model.preview

import com.joker.coolmall.core.model.entity.Cart
import com.joker.coolmall.core.model.entity.CartGoodsSpec

/**
 * 预览购物车数据列表
 *
 * @author Joker.X
 */
val previewCartList = listOf(
    Cart(
        goodsId = 1L,
        goodsName = "Redmi K80",
        goodsMainPic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png",
        spec = listOf(
            CartGoodsSpec(
                id = 101L,
                goodsId = 1L,
                name = "雪岩白 12GB+256GB",
                price = 249900,  // 2499.00
                stock = 100,
                count = 2,
                images = listOf("https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png")
            ),
            CartGoodsSpec(
                id = 102L,
                goodsId = 1L,
                name = "雪岩白 16GB+1TB",
                price = 359900,  // 3599.00
                stock = 50,
                count = 1,
                images = listOf("https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png")
            )
        )
    ),
    Cart(
        goodsId = 2L,
        goodsName = "Redmi Note 13 Pro+",
        goodsMainPic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png",
        spec = listOf(
            CartGoodsSpec(
                id = 201L,
                goodsId = 2L,
                name = "墨羽 12GB+512GB",
                price = 199900,  // 1999.00
                stock = 200,
                count = 1,
                images = listOf("https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png")
            )
        )
    )
)

/**
 * 单个预览购物车数据
 *
 * @author Joker.X
 */
val previewCart = previewCartList.first()

/**
 * 单个预览购物车规格
 *
 * @author Joker.X
 */
val previewCartSpec = previewCart.spec.firstOrNull() 