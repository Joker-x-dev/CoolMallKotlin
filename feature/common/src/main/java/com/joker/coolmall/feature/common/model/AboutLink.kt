package com.joker.coolmall.feature.common.model

/**
 * 链接项
 */
data class LinkItem(
    val title: String,
    val url: String,
    val description: String? = null
)

/**
 * 链接分类
 */
data class LinkCategory(
    val title: String,
    val links: List<LinkItem>
) 