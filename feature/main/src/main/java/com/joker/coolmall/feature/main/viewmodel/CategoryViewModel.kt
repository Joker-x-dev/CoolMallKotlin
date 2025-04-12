package com.joker.coolmall.feature.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.feature.main.state.CategoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 分类页面ViewModel
 */
@HiltViewModel
class CategoryViewModel @Inject constructor() : ViewModel() {

    /**
     * 分类页面UI状态
     */
    private val _uiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val uiState: StateFlow<CategoryUiState> = _uiState

    /**
     * 分类数据
     */
    private var categoryItems: List<String> = emptyList()
    
    /**
     * 是否正在加载更多
     */
    private var isLoadingMore = false

    init {
        // 初始化时加载分类数据
        loadInitialData()
    }

    /**
     * 加载初始数据
     */
    fun loadInitialData() {
        viewModelScope.launch {
            _uiState.value = CategoryUiState.Loading
            delay(300) // 模拟网络请求延迟
            categoryItems = List(20) { "分类 ${it + 1}" }
            _uiState.value = CategoryUiState.Success(categoryItems)
        }
    }

    /**
     * 刷新数据
     * @param onFinish 刷新完成回调，参数表示是否成功
     */
    fun refreshData(onFinish: (Boolean) -> Unit) {
        viewModelScope.launch {
            delay(600) // 模拟网络请求延迟
            categoryItems = List(20) { "分类 ${it + 1}" }
            _uiState.value = CategoryUiState.Success(categoryItems)
            onFinish(true)
        }
    }

    /**
     * 加载更多数据
     * @param onFinish 加载完成回调，第一个参数表示是否成功，第二个参数表示是否有更多数据
     */
    fun loadMoreData(onFinish: (Boolean, Boolean) -> Unit) {
        if (isLoadingMore) {
            onFinish(false, true)
            return
        }
        
        isLoadingMore = true
        viewModelScope.launch {
            delay(800) // 模拟网络请求延迟
            
            val hasMore = categoryItems.size < 60
            if (hasMore) {
                val startIndex = categoryItems.size
                val newItems = categoryItems + List(10) { "分类 ${startIndex + it + 1}" }
                categoryItems = newItems
                _uiState.value = CategoryUiState.Success(categoryItems)
            }
            
            isLoadingMore = false
            onFinish(true, hasMore)
        }
    }
}
