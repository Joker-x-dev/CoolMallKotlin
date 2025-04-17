package com.joker.coolmall.core.datastore.datasource.auth

import com.joker.coolmall.core.model.Auth

/**
 * 本地用户认证相关数据源接口
 */
interface AuthStoreDataSource {

    /**
     * 保存认证信息
     * @param auth 认证信息对象
     */
    suspend fun saveAuth(auth: Auth)

    /**
     * 获取认证信息
     * @return 认证信息对象，如不存在则返回null
     */
    suspend fun getAuth(): Auth?

    /**
     * 获取用户 token
     * @return token字符串，如不存在则返回null
     */
    suspend fun getToken(): String?

    /**
     * 清除认证信息
     */
    suspend fun clearAuth()

    /**
     * 检查是否已登录（有认证信息且未过期）
     * @return 是否已登录
     */
    suspend fun isLoggedIn(): Boolean
} 