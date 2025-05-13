package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 认证令牌模型
 * 与后端登录返回的token数据对应
 */
@Serializable
data class Auth(

    /**
     * token
     */
    val token: String = "",

    /**
     * 刷新 token
     */
    val refreshToken: String = "",

    /**
     * token 过期时间(秒)
     */
    val expire: Long = 0,

    /**
     * 刷新令牌过期时间
     */
    val refreshExpire: Long = 0,

    /**
     * 令牌创建时间(不来自服务端)
     */
    val createdAt: Long = System.currentTimeMillis()
) {
    /**
     * 检查访问令牌是否过期
     */
    fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val expirationTime = createdAt + (expire * 1000)
        return currentTime >= expirationTime
    }

    /**
     * 检查刷新令牌是否过期
     */
    fun isRefreshTokenExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val expirationTime = createdAt + (refreshExpire * 1000)
        return currentTime >= expirationTime
    }

    /**
     * 检查令牌是否需要刷新（过期前15分钟）
     */
    fun shouldRefresh(): Boolean {
        val currentTime = System.currentTimeMillis()
        val refreshTime = createdAt + (expire * 1000) - (15 * 60 * 1000) // 提前15分钟刷新
        return currentTime >= refreshTime && !isRefreshTokenExpired()
    }
} 