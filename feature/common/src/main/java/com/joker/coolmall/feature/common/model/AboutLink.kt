package com.joker.coolmall.feature.common.model

/**
 * 链接项
 *
 * @author Joker.X
 */
data class LinkItem(
    val title: String,
    val url: String,
    val description: String? = null
)

/**
 * 链接分类
 *
 * @author Joker.X
 */
data class LinkCategory(
    val title: String,
    val links: List<LinkItem>
) 