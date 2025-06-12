package com.joker.coolmall.core.database.datasource.searchhistory

import com.joker.coolmall.core.database.dao.SearchHistoryDao
import com.joker.coolmall.core.database.entity.SearchHistoryEntity
import com.joker.coolmall.core.model.entity.SearchHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 搜索历史数据源
 * 负责搜索历史相关的数据库操作
 */
@Singleton
class SearchHistoryDataSource @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao
) {

    /**
     * 添加搜索历史记录
     */
    suspend fun addSearchHistory(searchHistory: SearchHistory) {
        searchHistoryDao.insertSearchHistory(searchHistory.toSearchHistoryEntity())
    }

    /**
     * 根据关键词删除搜索历史记录
     */
    suspend fun removeSearchHistory(keyword: String) {
        searchHistoryDao.deleteSearchHistoryByKeyword(keyword)
    }

    /**
     * 清空所有搜索历史记录
     */
    suspend fun clearAllSearchHistory() {
        searchHistoryDao.clearAllSearchHistory()
    }

    /**
     * 获取所有搜索历史记录
     * 返回响应式Flow，按搜索时间倒序排列
     */
    fun getAllSearchHistory(): Flow<List<SearchHistory>> {
        return searchHistoryDao.getAllSearchHistory().map { entities ->
            entities.map { it.toSearchHistory() }
        }
    }

    /**
     * 获取搜索历史记录总数量
     * 返回响应式Flow
     */
    fun getSearchHistoryCount(): Flow<Int> {
        return searchHistoryDao.getSearchHistoryCount()
    }

    /**
     * 获取指定数量的最新搜索历史记录
     */
    fun getRecentSearchHistory(limit: Int): Flow<List<SearchHistory>> {
        return searchHistoryDao.getRecentSearchHistory(limit).map { entities ->
            entities.map { it.toSearchHistory() }
        }
    }

    /**
     * 根据关键词获取搜索历史记录
     */
    suspend fun getSearchHistoryByKeyword(keyword: String): SearchHistory? {
        return searchHistoryDao.getSearchHistoryByKeyword(keyword)?.toSearchHistory()
    }

    // 扩展函数：将实体模型转换为领域模型
    private fun SearchHistoryEntity.toSearchHistory(): SearchHistory {
        return SearchHistory().apply {
            keyword = this@toSearchHistory.keyword
            searchTime = this@toSearchHistory.searchTime
        }
    }

    // 扩展函数：将领域模型转换为实体模型
    private fun SearchHistory.toSearchHistoryEntity(): SearchHistoryEntity {
        return SearchHistoryEntity(
            keyword = this.keyword,
            searchTime = this.searchTime
        )
    }
}