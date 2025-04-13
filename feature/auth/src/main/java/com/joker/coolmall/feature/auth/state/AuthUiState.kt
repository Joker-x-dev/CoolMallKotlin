package com.joker.coolmall.feature.auth.state

/**
 * 认证模块UI状态封装类
 *
 * 该接口使用sealed interface来表示认证流程所有可能的UI状态：
 * - 初始状态 [Initial]
 * - 加载中状态 [Loading]
 * - 认证成功状态 [Success]
 * - 认证失败状态 [Error]
 */
sealed interface AuthUiState {
    /**
     * 初始状态
     */
    object Initial : AuthUiState

    /**
     * 加载中状态
     */
    object Loading : AuthUiState

    /**
     * 认证成功状态
     *
     * @property token 认证成功后的令牌
     */
    data class Success(
        val token: String
    ) : AuthUiState

    /**
     * 认证失败状态
     *
     * @property message 错误信息
     */
    data class Error(
        val message: String
    ) : AuthUiState
}