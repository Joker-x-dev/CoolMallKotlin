# AppState 全局状态管理使用指南

## 简介

`AppState` 是一个基于 Kotlin 协程和 StateFlow 实现的全局状态管理解决方案，专为 Android
应用设计，用于管理和同步整个应用中的用户相关状态。它采用响应式编程模式，确保状态变化能够自动传播到所有订阅者。

## 核心特性

- **全局单例**：通过 Dagger/Hilt 依赖注入确保整个应用只有一个 AppState 实例
- **响应式**：基于 StateFlow 实现，状态变更会自动通知所有观察者
- **状态持久化**：自动将状态保存到本地存储并在应用启动时恢复
- **线程安全**：基于协程实现的异步操作，确保线程安全
- **集中管理**：统一管理用户认证、个人信息等相关状态

## 主要状态

AppState 管理以下核心状态：

1. `isLoggedIn`：用户是否已登录
2. `userId`：当前登录用户的ID
3. `auth`：用户认证信息（token等）
4. `userInfo`：用户个人信息

## 使用方法

### 1. 在 Application 中初始化

AppState 通过 Hilt 注入到 Application 中，无需手动初始化：

```kotlin
@HiltAndroidApp
class Application : Application() {
    @Inject
    lateinit var appState: AppState

    override fun onCreate() {
        super.onCreate()
        // AppState 已通过 Hilt 自动初始化
    }
}
```

### 2. 在 ViewModel 中使用

```kotlin
@HiltViewModel
class UserViewModel @Inject constructor(
    private val appState: AppState,
    private val authRepository: AuthRepository,
    private val userInfoRepository: UserInfoRepository
) : ViewModel() {
    
    // 订阅状态流
    val isLoggedIn: StateFlow<Boolean> = appState.isLoggedIn
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )
    
    val userInfo: StateFlow<User?> = appState.userInfo
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    
    // 登录并更新全局状态
    fun login(account: String, password: String) {
        viewModelScope.launch {
            // 调用登录 API
            val auth = ... // 获取登录返回的 Auth 信息
            val user = ... // 获取用户信息
            
            // 更新全局状态
            appState.updateUserState(auth, user)
        }
    }
    
    // 登出
    fun logout() {
        viewModelScope.launch {
            // 调用登出 API
            authRepository.logout()
            
            // 清除全局状态
            appState.logout()
        }
    }
}
```

### 3. 在 Composable 中使用

```kotlin
@Composable
fun UserScreen(
    viewModel: UserViewModel = hiltViewModel()
) {
    // 收集状态流
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
    val userInfo by viewModel.userInfo.collectAsStateWithLifecycle()
    
    // 根据状态显示不同 UI
    if (isLoggedIn) {
        // 显示已登录用户界面
        UserInfoContent(userInfo = userInfo)
    } else {
        // 显示登录界面
        LoginContent(
            onLoginClick = { username, password ->
                viewModel.login(username, password)
            }
        )
    }
}
```

## AppState 主要方法

### 初始化状态

```kotlin
// 自动从本地存储恢复状态
private suspend fun initializeState()
```

### 更新状态

```kotlin
// 更新用户登录状态（登录成功后调用）
suspend fun updateUserState(auth: Auth, user: User)

// 只更新用户信息
suspend fun updateUserInfo(user: User)

// 更新认证信息（如刷新 token）
suspend fun updateAuth(auth: Auth)
```

### 清除状态

```kotlin
// 用户登出，清除所有状态
suspend fun logout()
```

### 状态检查

```kotlin
// 检查 token 是否需要刷新
suspend fun shouldRefreshToken(): Boolean
```

## 最佳实践

1. **职责分离**：
    - AppState 只负责状态管理，不处理业务逻辑
    - ViewModel 处理业务逻辑并调用 AppState 更新状态
    - UI 层只负责展示状态和捕获用户交互

2. **状态订阅**：
    - 在 ViewModel 中使用 `stateIn` 操作符将 StateFlow 转换为热流
    - 在 Composable 中使用 `collectAsStateWithLifecycle()` 收集状态

3. **状态更新**：
    - 所有涉及用户状态的更新都应通过 AppState 进行
    - 不要直接从 Repository 更新状态

4. **使用时机**：
    - 登录/注册成功后：`updateUserState()`
    - 更新用户资料后：`updateUserInfo()`
    - 刷新 token 后：`updateAuth()`
    - 退出登录后：`logout()`

## 完整流程示例

1. **用户登录**：
   ```kotlin
   // 1. 调用登录 API
   val response = authRepository.login(params)
   // 2. 成功后获取用户信息
   val userInfo = userInfoRepository.getPersonInfo()
   // 3. 更新全局状态
   appState.updateUserState(response.data, userInfo.data)
   ```

2. **更新用户信息**：
   ```kotlin
   // 1. 调用更新 API
   userInfoRepository.updatePersonInfo(params)
   // 2. 重新获取最新信息
   val updatedUserInfo = userInfoRepository.getPersonInfo()
   // 3. 更新全局状态
   appState.updateUserInfo(updatedUserInfo.data)
   ```

3. **刷新 Token**：
   ```kotlin
   if (appState.shouldRefreshToken()) {
       val refreshResponse = authRepository.refreshToken(params)
       appState.updateAuth(refreshResponse.data)
   }
   ```

4. **登出**：
   ```kotlin
   // 调用登出 API (无需等待响应)
   authRepository.logout()
   // 清除全局状态
   appState.logout()
   ```

---

通过 AppState 管理全局状态，您可以确保整个应用中的用户状态保持一致，任何组件中的状态更新都会自动反映到所有使用该状态的组件中，从而简化状态管理并提高应用的可维护性。 