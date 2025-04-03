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
 */
@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    // 当前选中的导航目标
    private val _currentDestination = MutableStateFlow(TopLevelDestination.HOME.route)
    val currentDestination: StateFlow<String> = _currentDestination.asStateFlow()

    // 当前页面索引
    private val _currentPageIndex = MutableStateFlow(0)
    val currentPageIndex: StateFlow<Int> = _currentPageIndex.asStateFlow()

    /**
     * 更新当前选中的导航项
     */
    fun updateDestination(index: Int) {
        val destination = TopLevelDestination.entries[index]
        _currentDestination.value = destination.route
        _currentPageIndex.value = index
    }
}

