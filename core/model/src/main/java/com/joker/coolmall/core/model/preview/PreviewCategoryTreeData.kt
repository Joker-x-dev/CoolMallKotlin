package com.joker.coolmall.core.model.preview

import com.joker.coolmall.core.model.entity.CategoryTree

/**
 * 预览分类树形数据
 *
 */
val previewCategoryTreeList = listOf(
    CategoryTree(
        id = 1L,
        name = "手机",
        parentId = null,
        sortNum = 0,
        pic = null,
        status = 1,
        createTime = null,
        updateTime = null,
        children = listOf(
            CategoryTree(
                id = 11L,
                name = "小米手机",
                parentId = 1,
                sortNum = 0,
                pic = "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png",
                status = 1,
                createTime = null,
                updateTime = null
            ),
            CategoryTree(
                id = 12L,
                name = "Redmi手机",
                parentId = 1,
                sortNum = 0,
                pic = "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png",
                status = 1,
                createTime = null,
                updateTime = null
            )
        )
    ),
    CategoryTree(
        id = 2L,
        name = "电脑",
        parentId = null,
        sortNum = 0,
        pic = null,
        status = 1,
        createTime = null,
        updateTime = null,
        children = listOf(
            CategoryTree(
                id = 21L,
                name = "笔记本",
                parentId = 2,
                sortNum = 0,
                pic = "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png",
                status = 1,
                createTime = null,
                updateTime = null
            ),
            CategoryTree(
                id = 22L,
                name = "台式机",
                parentId = 2,
                sortNum = 0,
                pic = "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png",
                status = 1,
                createTime = null,
                updateTime = null
            )
        )
    )
)

/**
 * 单个预览分类数据（一级分类）
 *
 */
val previewCategoryTree = previewCategoryList.first()