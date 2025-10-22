package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.datastore.datasource.userinfo.UserInfoStoreDataSource
import com.joker.coolmall.core.model.entity.User
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 用户信息本地存储仓库
 * 负责处理用户个人信息的本地存储操作
 *
 * @param userInfoStoreDataSource 用户信息本地数据源
 * @author Joker.X
 */
@Singleton
class UserInfoStoreRepository @Inject constructor(
    private val userInfoStoreDataSource: UserInfoStoreDataSource
) {
    /**
     * 保存用户信息到本地
     *
     * @param user 用户信息
     * @author Joker.X
     */
    suspend fun saveUserInfo(user: User) {
        userInfoStoreDataSource.saveUserInfo(user)
    }

    /**
     * 从本地获取用户信息
     *
     * @return 用户信息，如不存在则返回null
     * @author Joker.X
     */
    suspend fun getUserInfo(): User? {
        return userInfoStoreDataSource.getUserInfo()
    }

    /**
     * 更新本地用户信息中的特定字段
     *
     * @param updates 需要更新的字段映射
     * @author Joker.X
     */
    suspend fun updateUserInfo(updates: Map<String, Any?>) {
        userInfoStoreDataSource.updateUserInfo(updates)
    }

    /**
     * 清除本地用户信息
     *
     * @author Joker.X
     */
    suspend fun clearUserInfo() {
        userInfoStoreDataSource.clearUserInfo()
    }

    /**
     * 获取用户ID
     *
     * @return 用户ID，如不存在则返回0
     * @author Joker.X
     */
    suspend fun getUserId(): Long {
        return userInfoStoreDataSource.getUserId()
    }

    /**
     * 获取用户昵称
     *
     * @return 用户昵称，如不存在则返回null
     * @author Joker.X
     */
    suspend fun getNickName(): String? {
        return userInfoStoreDataSource.getNickName()
    }

    /**
     * 获取用户头像URL
     *
     * @return 用户头像URL，如不存在则返回null
     * @author Joker.X
     */
    suspend fun getAvatarUrl(): String? {
        return userInfoStoreDataSource.getAvatarUrl()
    }
}
