package com.joker.coolmall.core.datastore.datasource.userinfo

import com.joker.coolmall.core.model.entity.User
import com.joker.coolmall.core.util.storage.MMKVUtils
import jakarta.inject.Inject
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject

/**
 * 本地用户信息相关数据源实现类
 * 负责处理所有与用户信息相关的本地存储
 *
 * @author Joker.X
 */
class UserInfoStoreDataSourceImpl @Inject constructor() : UserInfoStoreDataSource {

    companion object {
        private const val KEY_USER_INFO = "user_info"
    }

    private val json = Json { ignoreUnknownKeys = true }

    /**
     * 保存用户信息
     *
     * @param user 用户信息对象
     * @author Joker.X
     */
    override suspend fun saveUserInfo(user: User) {
        val userJson = json.encodeToString(user)
        MMKVUtils.putString(KEY_USER_INFO, userJson)
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息对象，如不存在则返回null
     * @author Joker.X
     */
    override suspend fun getUserInfo(): User? {
        val userJson = MMKVUtils.getString(KEY_USER_INFO, "")
        if (userJson.isEmpty()) return null

        return try {
            json.decodeFromString<User>(userJson)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 更新用户信息中的特定字段
     *
     * @param updates 需要更新的字段映射
     * @author Joker.X
     */
    override suspend fun updateUserInfo(updates: Map<String, Any?>) {
        val currentUser = getUserInfo() ?: return
        val userJson = MMKVUtils.getString(KEY_USER_INFO, "")
        if (userJson.isEmpty()) return

        try {
            // 解析当前用户JSON为可变映射
            val userObject = json.parseToJsonElement(userJson).jsonObject.toMutableMap()

            // 应用更新
            updates.forEach { (key, value) ->
                when (value) {
                    is String -> userObject[key] = JsonPrimitive(value)
                    is Number -> userObject[key] = JsonPrimitive(value)
                    is Boolean -> userObject[key] = JsonPrimitive(value)
                    null -> userObject.remove(key)
                }
            }

            // 保存更新后的JSON
            val updatedJson = JsonObject(userObject).toString()
            MMKVUtils.putString(KEY_USER_INFO, updatedJson)
        } catch (e: Exception) {
            // 如果更新失败，至少保留原始数据
        }
    }

    /**
     * 清除用户信息
     *
     * @author Joker.X
     */
    override suspend fun clearUserInfo() {
        MMKVUtils.remove(KEY_USER_INFO)
    }

    /**
     * 获取用户ID
     *
     * @return 用户ID，如不存在则返回0
     * @author Joker.X
     */
    override suspend fun getUserId(): Long {
        return getUserInfo()?.id ?: 0L
    }

    /**
     * 获取用户昵称
     *
     * @return 用户昵称，如不存在则返回null
     * @author Joker.X
     */
    override suspend fun getNickName(): String? {
        return getUserInfo()?.nickName
    }

    /**
     * 获取用户头像URL
     *
     * @return 用户头像URL，如不存在则返回null
     * @author Joker.X
     */
    override suspend fun getAvatarUrl(): String? {
        return getUserInfo()?.avatarUrl
    }
} 