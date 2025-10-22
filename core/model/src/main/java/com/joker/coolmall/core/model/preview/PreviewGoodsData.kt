package com.joker.coolmall.core.model.preview

import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.model.entity.GoodsSpec

/**
 * 预览商品数据列表
 *
 * @author Joker.X
 */
val previewGoodsList = listOf(
    Goods(
        id = 1L,
        typeId = 100L,
        title = "苹果 iPhone 15 Pro",
        subTitle = "A17芯片，超视网膜屏幕",
        mainPic = "https://example.com/iphone15pro.jpg",
        pics = listOf(
            "https://example.com/iphone15pro_1.jpg",
            "https://example.com/iphone15pro_2.jpg"
        ),
        price = 8999,
        sold = 1200,
        content = "全新一代旗舰手机，性能强劲。",
        status = 1,
        sortNum = 1,
        specs = listOf(
            GoodsSpec(
                id = 201L,
                goodsId = 1L,
                name = "256GB 银色",
                price = 8999,
                stock = 10,
                sortNum = 1,
                images = listOf("https://example.com/iphone15pro_silver.jpg")
            ),
            GoodsSpec(
                id = 202L,
                goodsId = 1L,
                name = "512GB 黑色",
                price = 10999,
                stock = 5,
                sortNum = 2,
                images = listOf("https://example.com/iphone15pro_black.jpg")
            )
        ),
        contentPics = listOf(
            "https://example.com/iphone15pro_content_1.jpg",
            "https://example.com/iphone15pro_content_2.jpg",
            "https://example.com/iphone15pro_content_3.jpg",
            "https://example.com/iphone15pro_content_4.jpg",
        )
    ),
    Goods(
        id = 2L,
        typeId = 101L,
        title = "小米 14 Ultra",
        subTitle = "徕卡影像，骁龙8 Gen3",
        mainPic = "https://example.com/xiaomi14ultra.jpg",
        pics = listOf(
            "https://example.com/xiaomi14ultra_1.jpg",
            "https://example.com/xiaomi14ultra_2.jpg"
        ),
        price = 6499,
        sold = 800,
        content = "高端影像旗舰，性能与拍照兼备。",
        status = 1,
        sortNum = 2,
        specs = listOf(
            GoodsSpec(
                id = 203L,
                goodsId = 2L,
                name = "12GB+256GB 黑色",
                price = 6499,
                stock = 8,
                sortNum = 1,
                images = listOf("https://example.com/xiaomi14ultra_black.jpg")
            )
        ),
        contentPics = listOf(
            "https://example.com/iphone15pro_content_1.jpg",
            "https://example.com/iphone15pro_content_2.jpg",
            "https://example.com/iphone15pro_content_3.jpg",
            "https://example.com/iphone15pro_content_4.jpg",
        )
    )
)

/**
 * 单个预览商品数据
 *
 * @author Joker.X
 */
val previewGoods = previewGoodsList.first()

/**
 * 单个预览商品规格
 *
 * @author Joker.X
 */
val previewGoodsSpec = previewGoods.specs?.firstOrNull() 