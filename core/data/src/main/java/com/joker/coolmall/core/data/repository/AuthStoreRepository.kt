package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.datastore.datasource.auth.AuthStoreDataSource
import com.joker.coolmall.core.model.entity.Auth
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 用户认证本地存储仓库
 * 负责处理认证相关信息的本地存储操作
 *
 * @param authStoreDataSource 认证本地数据源
 * @author Joker.X
 */
@Singleton
class AuthStoreRepository @Inject constructor(
    private val authStoreDataSource: AuthStoreDataSource
) {
    /**
     * 保存认证信息到本地
     *
     * @param auth 认证信息
     * @author Joker.X
     */
    suspend fun saveAuth(auth: Auth) {
        authStoreDataSource.saveAuth(auth)
    }

    /**
     * 从本地获取认证信息
     *
     * @return 认证信息，如不存在则返回null
     * @author Joker.X
     */
    suspend fun getAuth(): Auth? {
        return authStoreDataSource.getAuth()
    }

    /**
     * 从本地获取Token
     *
     * @return Token，如不存在则返回null
     * @author Joker.X
     */
    suspend fun getToken(): String? {
        return authStoreDataSource.getToken()
    }

    /**
     * 清除本地认证信息
     *
     * @author Joker.X
     */
    suspend fun clearAuth() {
        authStoreDataSource.clearAuth()
    }

    /**
     * 检查用户是否已登录
     *
     * @return 是否已登录
     * @author Joker.X
     */
    suspend fun isLoggedIn(): Boolean {
        return authStoreDataSource.isLoggedIn()
    }

    /**
     * 检查Token是否需要刷新
     *
     * @return 是否需要刷新Token
     * @author Joker.X
     */
    suspend fun shouldRefreshToken(): Boolean {
        val auth = authStoreDataSource.getAuth() ?: return false
        return auth.shouldRefresh()
    }
}
