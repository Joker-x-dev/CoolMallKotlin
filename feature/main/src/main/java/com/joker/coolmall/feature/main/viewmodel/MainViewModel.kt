package com.joker.coolmall.feature.main.viewmodel

import androidx.lifecycle.ViewModel
import com.joker.coolmall.feature.main.model.TopLevelDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * 主页面ViewModel
 *
 * @author Joker.X
 */
@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    // 当前页面索引
    private val _currentPageIndex = MutableStateFlow(0)
    val currentPageIndex: StateFlow<Int> = _currentPageIndex.asStateFlow()

    /**
     * 更新当前选中的导航项和页面索引
     *
     * @param index 导航项索引
     * @author Joker.X
     */
    fun updateDestination(index: Int) {
        _currentPageIndex.value = index
    }

    /**
     * 根据页面索引更新导航项
     *
     * @param index 页面索引
     * @author Joker.X
     */
    fun updatePageIndex(index: Int) {
        _currentPageIndex.value = index
    }
}

