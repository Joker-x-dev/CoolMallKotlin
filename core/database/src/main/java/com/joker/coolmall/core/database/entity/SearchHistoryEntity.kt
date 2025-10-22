package com.joker.coolmall.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 搜索历史数据库实体
 *
 * @param keyword 搜索关键词，主键
 * @param searchTime 搜索时间戳
 * @author Joker.X
 */
@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    /**
     * 主键，使用搜索关键词作为主键
     */
    @PrimaryKey
    val keyword: String,

    /**
     * 搜索时间戳
     */
    val searchTime: Long = System.currentTimeMillis()
)