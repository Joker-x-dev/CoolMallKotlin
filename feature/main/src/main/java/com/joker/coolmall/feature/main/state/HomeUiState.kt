package com.joker.coolmall.feature.main.state

import com.joker.coolmall.core.model.entity.Home

/**
 * 首页UI状态封装类
 *
 * 该接口使用sealed interface来表示首页所有可能的UI状态：
 * - 加载中状态 [Loading]
 * - 加载成功状态 [Success]
 * - 加载失败状态 [Error]
 *
 * @author Joker.X
 */
sealed interface HomeUiState {
    /**
     * 数据加载中状态
     */
    object Loading : HomeUiState

    /**
     * 数据加载成功状态
     *
     * @property data 首页数据模型
     */
    data class Success(
        val data: Home,
    ) : HomeUiState

    /**
     * 数据加载失败状态
     *
     * @property message 错误信息
     */
    data class Error(
        val message: String
    ) : HomeUiState
}