package com.joker.coolmall.feature.main.state

/**
 * 分类页面UI状态
 */
sealed class CategoryUiState {
    /**
     * 加载中状态
     */
    object Loading : CategoryUiState()

    /**
     * 加载成功状态
     * @param data 分类数据列表
     */
    data class Success(val data: List<String>) : CategoryUiState()

    /**
     * 加载失败状态
     * @param message 错误信息
     */
    data class Error(val message: String) : CategoryUiState()
} 