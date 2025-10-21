package com.joker.coolmall.feature.user.state

/**
 * 足迹 ui 状态
 *
 * @author Joker.X
 */
sealed class FootprintUiState {
    /**
     * 加载中状态
     */
    data object Loading : FootprintUiState()

    /**
     * 成功状态
     */
    data object Success : FootprintUiState()

    /**
     * 空数据状态
     */
    object Empty : FootprintUiState()
}