package com.joker.coolmall.core.datastore.datasource.auth

import com.joker.coolmall.core.model.entity.Auth
import com.joker.coolmall.core.util.storage.MMKVUtils
import jakarta.inject.Inject
import kotlinx.serialization.json.Json

/**
 * 本地用户认证相关数据源实现类
 * 负责处理所有与用户认证相关的本地存储
 *
 * @author Joker.X
 */
class AuthStoreDataSourceImpl @Inject constructor() : AuthStoreDataSource {

    companion object {
        private const val KEY_AUTH = "auth_info"
    }

    private val json = Json { ignoreUnknownKeys = true }

    /**
     * 保存认证信息
     *
     * @param auth 认证信息对象
     * @author Joker.X
     */
    override suspend fun saveAuth(auth: Auth) {
        val authJson = json.encodeToString(auth)
        MMKVUtils.putString(KEY_AUTH, authJson)
    }

    /**
     * 获取认证信息
     *
     * @return 认证信息对象，如不存在则返回null
     * @author Joker.X
     */
    override suspend fun getAuth(): Auth? {
        val authJson = MMKVUtils.getString(KEY_AUTH, "")
        if (authJson.isEmpty()) return null

        return try {
            json.decodeFromString<Auth>(authJson)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 获取用户 token
     *
     * @return token字符串，如不存在则返回null
     * @author Joker.X
     */
    override suspend fun getToken(): String? {
        return getAuth()?.token
    }

    /**
     * 清除认证信息
     *
     * @author Joker.X
     */
    override suspend fun clearAuth() {
        MMKVUtils.remove(KEY_AUTH)
    }

    /**
     * 检查是否已登录（有认证信息且未过期）
     *
     * @return 是否已登录
     * @author Joker.X
     */
    override suspend fun isLoggedIn(): Boolean {
        val auth = getAuth() ?: return false
        return !auth.isExpired() && auth.token.isNotEmpty()
    }
} 