package com.joker.coolmall.core.model.preview

import com.joker.coolmall.core.model.entity.Category

/**
 * 预览分类数据
 *
 * @author Joker.X
 */
val previewCategoryList = listOf(
    // 一级分类
    Category(
        id = 1L,
        name = "手机",
        parentId = null,
        sortNum = 1,
        pic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app/category/phone.png",
        status = 1,
        createTime = "2024-01-01 12:00:00",
        updateTime = "2024-01-01 12:00:00"
    ),
    Category(
        id = 2L,
        name = "电脑",
        parentId = null,
        sortNum = 2,
        pic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app/category/computer.png",
        status = 1,
        createTime = "2024-01-01 12:00:00",
        updateTime = "2024-01-01 12:00:00"
    ),
    // 二级分类
    Category(
        id = 11L,
        name = "小米手机",
        parentId = 1,
        sortNum = 1,
        pic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app/category/xiaomi.png",
        status = 1,
        createTime = "2024-01-01 12:00:00",
        updateTime = "2024-01-01 12:00:00"
    ),
    Category(
        id = 12L,
        name = "Redmi手机",
        parentId = 1,
        sortNum = 2,
        pic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app/category/redmi.png",
        status = 1,
        createTime = "2024-01-01 12:00:00",
        updateTime = "2024-01-01 12:00:00"
    ),
    Category(
        id = 21L,
        name = "RedmiBook",
        parentId = 2,
        sortNum = 1,
        pic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app/category/redmibook.png",
        status = 1,
        createTime = "2024-01-01 12:00:00",
        updateTime = "2024-01-01 12:00:00"
    ),
    Category(
        id = 22L,
        name = "小米笔记本",
        parentId = 2,
        sortNum = 2,
        pic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app/category/xiaomi_laptop.png",
        status = 1,
        createTime = "2024-01-01 12:00:00",
        updateTime = "2024-01-01 12:00:00"
    )
)

/**
 * 单个预览分类数据（一级分类）
 *
 * @author Joker.X
 */
val previewCategory = previewCategoryList.first()

/**
 * 获取指定一级分类下的所有二级分类
 *
 * @param parentId 父分类ID
 * @return 二级分类列表
 * @author Joker.X
 */
fun getSubCategories(parentId: Long): List<Category> {
    return previewCategoryList.filter { it.parentId == parentId.toInt() }
} 