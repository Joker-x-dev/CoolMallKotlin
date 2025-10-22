package com.joker.coolmall.core.data.state

import com.joker.coolmall.core.data.di.ApplicationScope
import com.joker.coolmall.core.data.repository.AuthStoreRepository
import com.joker.coolmall.core.data.repository.UserInfoRepository
import com.joker.coolmall.core.data.repository.UserInfoStoreRepository
import com.joker.coolmall.core.model.entity.Auth
import com.joker.coolmall.core.model.entity.User
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 全局应用状态管理类
 *
 * 该类负责管理整个应用的全局状态，包括：
 * - 用户登录状态
 * - 用户认证信息（token等）
 * - 用户个人信息
 *
 * 通过StateFlow提供响应式的状态管理，任何组件都可以订阅状态变化
 *
 * @param authStoreRepository 认证信息存储仓库
 * @param userInfoStoreRepository 用户信息存储仓库
 * @param userInfoRepository 用户信息网络仓库
 * @param applicationScope 应用级协程作用域
 * @author Joker.X
 */
@Singleton
class AppState @Inject constructor(
    private val authStoreRepository: AuthStoreRepository,
    private val userInfoStoreRepository: UserInfoStoreRepository,
    private val userInfoRepository: UserInfoRepository,
    @param:ApplicationScope private val applicationScope: CoroutineScope
) {
    // 用户登录状态
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    // 用户ID
    private val _userId = MutableStateFlow(0L)
    val userId: StateFlow<Long> = _userId.asStateFlow()

    // 用户授权信息
    private val _auth = MutableStateFlow<Auth?>(null)
    val auth: StateFlow<Auth?> = _auth.asStateFlow()

    // 用户信息
    private val _userInfo = MutableStateFlow<User?>(null)
    val userInfo: StateFlow<User?> = _userInfo.asStateFlow()

    /**
     * 初始化应用状态
     * 由外部调用而不是自动初始化
     *
     * @author Joker.X
     */
    fun initialize() {
        applicationScope.launch {
            initializeState()
        }
    }

    /**
     * 从本地存储初始化应用状态
     *
     * @author Joker.X
     */
    private suspend fun initializeState() {
        // 获取认证信息
        val authData = authStoreRepository.getAuth()
        val loggedIn = authStoreRepository.isLoggedIn()
        _isLoggedIn.value = loggedIn
        _auth.value = authData

        // 仅在已登录状态下加载用户信息
        if (loggedIn) {
            val user = userInfoStoreRepository.getUserInfo()
            _userInfo.value = user
            _userId.value = user?.id ?: 0L
        }
    }

    /**
     * 更新用户登录状态
     *
     * @param auth 认证信息
     * @param user 用户信息
     * @author Joker.X
     */
    suspend fun updateUserState(auth: Auth, user: User) {
        // 保存到本地存储
        authStoreRepository.saveAuth(auth)
        userInfoStoreRepository.saveUserInfo(user)

        // 更新内存中的状态
        _auth.value = auth
        _userInfo.value = user
        _userId.value = user.id
        _isLoggedIn.value = true
    }

    /**
     * 更新用户信息
     *
     * @param user 新的用户信息
     * @author Joker.X
     */
    suspend fun updateUserInfo(user: User) {
        // 保存到本地存储
        userInfoStoreRepository.saveUserInfo(user)

        // 更新内存中的状态
        _userInfo.value = user
        _userId.value = user.id
    }

    /**
     * 更新认证信息（如token刷新）
     *
     * @param auth 新的认证信息
     * @author Joker.X
     */
    suspend fun updateAuth(auth: Auth) {
        // 保存到本地存储
        authStoreRepository.saveAuth(auth)

        // 更新内存中的状态
        _auth.value = auth

        // 设置登录状态
        _isLoggedIn.value = true
    }

    /**
     * 用户登出
     *
     * @author Joker.X
     */
    suspend fun logout() {
        // 清除本地存储
        authStoreRepository.clearAuth()
        userInfoStoreRepository.clearUserInfo()

        // 重置内存中的状态
        _isLoggedIn.value = false
        _auth.value = null
        _userInfo.value = null
        _userId.value = 0L
    }

    /**
     * 检查token是否需要刷新
     *
     * @return 是否需要刷新token
     * @author Joker.X
     */
    suspend fun shouldRefreshToken(): Boolean {
        return authStoreRepository.shouldRefreshToken()
    }

    /**
     * 从网络获取最新的用户信息并更新到状态
     *
     * @author Joker.X
     */
    fun refreshUserInfo() {
        if (!_isLoggedIn.value) return
        ResultHandler.handleResultWithData(
            scope = applicationScope,
            flow = userInfoRepository.getPersonInfo().asResult(),
            onData = { data ->
                applicationScope.launch {
                    updateUserInfo(data)
                }
            }
        )
    }
}