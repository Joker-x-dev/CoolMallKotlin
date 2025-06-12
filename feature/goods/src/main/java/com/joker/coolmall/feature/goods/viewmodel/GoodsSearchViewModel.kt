package com.joker.coolmall.feature.goods.viewmodel

import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.GoodsRepository
import com.joker.coolmall.core.data.repository.SearchHistoryRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.GoodsSearchKeyword
import com.joker.coolmall.core.model.entity.SearchHistory
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.GoodsRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 商品搜索 ViewModel
 */
@HiltViewModel
class GoodsSearchViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    private val goodsRepository: GoodsRepository,
    private val searchHistoryRepository: SearchHistoryRepository
) : BaseNetWorkViewModel<List<GoodsSearchKeyword>>(navigator, appState) {

    // 搜索历史状态
    private val _searchHistoryList = MutableStateFlow<List<SearchHistory>>(emptyList())
    val searchHistoryList: StateFlow<List<SearchHistory>> = _searchHistoryList.asStateFlow()

    init {
        super.executeRequest()
        loadSearchHistory()
    }

    /**
     * 重写父类方法，返回商品搜索关键词列表的 Flow
     */
    override fun requestApiFlow(): Flow<NetworkResponse<List<GoodsSearchKeyword>>> {
        return goodsRepository.getSearchKeywordList()
    }

    /**
     * 加载搜索历史
     */
    private fun loadSearchHistory() {
        viewModelScope.launch {
            searchHistoryRepository.getRecentSearchHistory(10).collect { historyList ->
                _searchHistoryList.value = historyList
            }
        }
    }

    /**
     * 执行搜索
     * @param keyword 搜索关键词
     */
    fun onSearch(keyword: String) {
        if (keyword.isNotBlank()) {
            viewModelScope.launch {
                // 添加到搜索历史
                searchHistoryRepository.addSearchHistory(keyword)
                // 跳转到商品分类页面，传递搜索关键词
                super.toPage("${GoodsRoutes.CATEGORY}?keyword=$keyword")
            }
        }
    }

    /**
     * 点击搜索关键词
     * @param keyword 搜索关键词
     */
    fun onKeywordClick(keyword: String) {
        onSearch(keyword)
    }

    /**
     * 点击搜索历史
     * @param searchHistory 搜索历史
     */
    fun onSearchHistoryClick(searchHistory: SearchHistory) {
        onSearch(searchHistory.keyword)
    }

    /**
     * 清空搜索历史
     */
    fun clearSearchHistory() {
        viewModelScope.launch {
            searchHistoryRepository.clearAllSearchHistory()
        }
    }
}