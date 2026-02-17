package com.joker.coolmall.core.navigation

/**
 * 公共导航返回结果定义
 *
 * 用于沉淀跨模块可复用的页面返回结果模型。
 *
 * @author Joker.X
 */
data class RefreshResult(
    /**
     * 是否需要刷新
     *
     * true 表示需要刷新，false 或 null 表示不刷新。
     */
    val refresh: Boolean? = null,
)
