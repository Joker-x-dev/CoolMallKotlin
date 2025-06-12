package com.joker.coolmall.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.joker.coolmall.core.database.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * 搜索历史数据访问对象
 */
@Dao
interface SearchHistoryDao {

    /**
     * 插入或更新搜索历史记录
     * 如果已存在相同keyword的记录，则替换（更新搜索时间）
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(searchHistory: SearchHistoryEntity)

    /**
     * 根据关键词删除搜索历史记录
     */
    @Query("DELETE FROM search_history WHERE keyword = :keyword")
    suspend fun deleteSearchHistoryByKeyword(keyword: String)

    /**
     * 清空所有搜索历史记录
     */
    @Query("DELETE FROM search_history")
    suspend fun clearAllSearchHistory()

    /**
     * 获取所有搜索历史记录，按搜索时间倒序排列（最新的在前面）
     * 使用Flow实现响应式
     */
    @Query("SELECT * FROM search_history ORDER BY searchTime DESC")
    fun getAllSearchHistory(): Flow<List<SearchHistoryEntity>>

    /**
     * 获取搜索历史记录数量
     */
    @Query("SELECT COUNT(*) FROM search_history")
    fun getSearchHistoryCount(): Flow<Int>

    /**
     * 获取指定数量的最新搜索历史记录
     */
    @Query("SELECT * FROM search_history ORDER BY searchTime DESC LIMIT :limit")
    fun getRecentSearchHistory(limit: Int): Flow<List<SearchHistoryEntity>>

    /**
     * 根据关键词查询搜索历史记录
     */
    @Query("SELECT * FROM search_history WHERE keyword = :keyword")
    suspend fun getSearchHistoryByKeyword(keyword: String): SearchHistoryEntity?
}