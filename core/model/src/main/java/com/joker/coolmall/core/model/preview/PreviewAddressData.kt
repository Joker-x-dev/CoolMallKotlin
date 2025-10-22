package com.joker.coolmall.core.model.preview

import com.joker.coolmall.core.model.entity.Address

/**
 * 预览地址数据
 *
 * @author Joker.X
 */
val previewAddressList = listOf(
    Address(
        id = 1L,
        userId = 1L,
        contact = "张三",
        phone = "13800138000",
        province = "广东省",
        city = "深圳市",
        district = "南山区",
        address = "科技园科兴科学园B座1001",
        isDefault = true,
        createTime = "2024-01-01 12:00:00",
        updateTime = "2024-01-01 12:00:00"
    ),
    Address(
        id = 2L,
        userId = 1L,
        contact = "李四",
        phone = "13900139000",
        province = "广东省",
        city = "广州市",
        district = "天河区",
        address = "珠江新城花城汇B座2001",
        isDefault = false,
        createTime = "2024-01-02 12:00:00",
        updateTime = "2024-01-02 12:00:00"
    )
)

/**
 * 单个预览地址数据
 *
 * @author Joker.X
 */
val previewAddress = previewAddressList.first() 