package com.joker.coolmall.core.datastore.datasource.userinfo

import com.joker.coolmall.core.model.entity.User


/**
 * 本地用户信息相关数据源接口
 */
interface UserInfoStoreDataSource {

    /**
     * 保存用户信息
     * @param user 用户信息对象
     */
    suspend fun saveUserInfo(user: User)

    /**
     * 获取用户信息
     * @return 用户信息对象，如不存在则返回null
     */
    suspend fun getUserInfo(): User?

    /**
     * 更新用户信息中的特定字段
     * @param updates 需要更新的字段映射
     */
    suspend fun updateUserInfo(updates: Map<String, Any?>)

    /**
     * 清除用户信息
     */
    suspend fun clearUserInfo()

    /**
     * 获取用户ID
     * @return 用户ID，如不存在则返回0
     */
    suspend fun getUserId(): Long

    /**
     * 获取用户昵称
     * @return 用户昵称，如不存在则返回null
     */
    suspend fun getNickName(): String?

    /**
     * 获取用户头像URL
     * @return 用户头像URL，如不存在则返回null
     */
    suspend fun getAvatarUrl(): String?
} 