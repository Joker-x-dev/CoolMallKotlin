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

    // 标记是否正在进行页面切换动画
    private val _isAnimatingPageChange = MutableStateFlow(false)
    val isAnimatingPageChange: StateFlow<Boolean> = _isAnimatingPageChange.asStateFlow()

    /**
     * 更新当前选中的导航项和页面索引
     */
    fun updateDestination(index: Int) {
        val destination = TopLevelDestination.entries[index]
        // 立即更新页面索引，但仅在目标页面到达时更新导航状态
        _currentPageIndex.value = index
        _currentDestination.value = destination.route

        // 标记正在进行页面切换动画
        _isAnimatingPageChange.value = true
    }

    /**
     * 根据页面索引更新导航项，仅在滑动结束时更新
     */
    fun updatePageIndex(index: Int) {
        if (_isAnimatingPageChange.value) {
            // 如果是程序触发的动画滑动，且已经到达目标页面，则结束动画状态
            if (_currentPageIndex.value == index) {
                _isAnimatingPageChange.value = false
            }
            // 在动画过程中不更新中间页面的导航状态
            return
        }

        // 用户滑动导致的页面变化时，立即更新状态
        _currentPageIndex.value = index
        _currentDestination.value = TopLevelDestination.entries[index].route
    }

    /**
     * 通知动画已完成
     */
    fun notifyAnimationCompleted() {
        _isAnimatingPageChange.value = false
    }

    /**
     * 预览页面变化，在用户滑动过程中提前更新UI，但不改变内部状态
     */
    fun previewPageChange(index: Int) {
        // 只更新底部导航栏状态，不改变页面索引和动画状态
        _currentDestination.value = TopLevelDestination.entries[index].route
    }
}

