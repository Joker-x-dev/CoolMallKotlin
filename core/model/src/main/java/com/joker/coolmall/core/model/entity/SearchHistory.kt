package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 搜索历史模型
 *
 * @param keyword 搜索关键词
 * @param searchTime 搜索时间戳
 * @author Joker.X
 */
@Serializable
data class SearchHistory(
    /**
     * 搜索关键词
     */
    var keyword: String = "",

    /**
     * 搜索时间戳
     */
    var searchTime: Long = System.currentTimeMillis()
)