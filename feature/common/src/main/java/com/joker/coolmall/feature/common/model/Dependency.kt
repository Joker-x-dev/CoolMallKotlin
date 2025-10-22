package com.joker.coolmall.feature.common.model

/**
 * 依赖库数据模型
 * 用于表示项目中使用的第三方依赖库信息
 *
 * @author Joker.X
 */
data class Dependency(
    /**
     * 依赖库名称
     */
    val name: String,

    /**
     * 依赖库版本号
     */
    val version: String,

    /**
     * 依赖库描述
     */
    val description: String,

    /**
     * 依赖库官方网站链接
     */
    val websiteUrl: String,

    /**
     * 依赖库类型/分类
     */
    val category: String
)