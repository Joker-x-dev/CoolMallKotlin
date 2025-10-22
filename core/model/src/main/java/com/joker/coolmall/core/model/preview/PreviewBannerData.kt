package com.joker.coolmall.core.model.preview

import com.joker.coolmall.core.model.entity.Banner

/**
 * 预览轮播图数据
 *
 * @author Joker.X
 */
val previewBannerList = listOf(
    Banner(
        id = 1L,
        description = "新品上市",
        path = "goods/detail/1",
        pic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app/banner/banner1.png",
        sortNum = 1,
        status = 1,
        createTime = "2024-01-01 12:00:00",
        updateTime = "2024-01-01 12:00:00"
    ),
    Banner(
        id = 2L,
        description = "限时特惠",
        path = "activity/sale",
        pic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app/banner/banner2.png",
        sortNum = 2,
        status = 1,
        createTime = "2024-01-02 12:00:00",
        updateTime = "2024-01-02 12:00:00"
    ),
    Banner(
        id = 3L,
        description = "年货节",
        path = "activity/festival",
        pic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app/banner/banner3.png",
        sortNum = 3,
        status = 1,
        createTime = "2024-01-03 12:00:00",
        updateTime = "2024-01-03 12:00:00"
    )
)

/**
 * 单个预览轮播图数据
 *
 * @author Joker.X
 */
val previewBanner = previewBannerList.first() 