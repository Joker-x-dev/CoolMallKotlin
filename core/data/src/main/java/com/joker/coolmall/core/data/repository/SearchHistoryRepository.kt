package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.database.datasource.searchhistory.SearchHistoryDataSource
import com.joker.coolmall.core.model.entity.SearchHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 搜索历史仓库
 * 负责处理搜索历史相关的业务逻辑
 *
 * @param searchHistoryDataSource 搜索历史数据源
 * @author Joker.X
 */
@Singleton
class SearchHistoryRepository @Inject constructor(
    private val searchHistoryDataSource: SearchHistoryDataSource
) {

    /**
     * 添加搜索历史
     *
     * @param keyword 搜索关键词
     * @author Joker.X
     */
    suspend fun addSearchHistory(keyword: String) {
        if (keyword.isNotBlank()) {
            val searchHistory = SearchHistory(
                keyword = keyword.trim(),
                searchTime = System.currentTimeMillis()
            )
            searchHistoryDataSource.addSearchHistory(searchHistory)
        }
    }

    /**
     * 根据关键词删除搜索历史
     *
     * @param keyword 搜索关键词
     * @author Joker.X
     */
    suspend fun removeSearchHistory(keyword: String) {
        searchHistoryDataSource.removeSearchHistory(keyword)
    }

    /**
     * 清空所有搜索历史
     *
     * @author Joker.X
     */
    suspend fun clearAllSearchHistory() {
        searchHistoryDataSource.clearAllSearchHistory()
    }

    /**
     * 获取所有搜索历史
     *
     * @return 搜索历史列表的Flow
     * @author Joker.X
     */
    fun getAllSearchHistory(): Flow<List<SearchHistory>> {
        // 使用IO调度器处理数据库操作
        return searchHistoryDataSource.getAllSearchHistory().flowOn(Dispatchers.IO)
    }

    /**
     * 获取搜索历史总数量
     *
     * @return 搜索历史数量的Flow
     * @author Joker.X
     */
    fun getSearchHistoryCount(): Flow<Int> {
        // 使用IO调度器处理数据库操作
        return searchHistoryDataSource.getSearchHistoryCount().flowOn(Dispatchers.IO)
    }

    /**
     * 获取指定数量的最新搜索历史
     *
     * @param limit 限制数量
     * @return 搜索历史列表的Flow
     * @author Joker.X
     */
    fun getRecentSearchHistory(limit: Int): Flow<List<SearchHistory>> {
        // 使用IO调度器处理数据库操作
        return searchHistoryDataSource.getRecentSearchHistory(limit).flowOn(Dispatchers.IO)
    }

    /**
     * 根据关键词获取搜索历史
     *
     * @param keyword 搜索关键词
     * @return 搜索历史，如不存在则返回null
     * @author Joker.X
     */
    suspend fun getSearchHistoryByKeyword(keyword: String): SearchHistory? {
        return searchHistoryDataSource.getSearchHistoryByKeyword(keyword)
    }
}
