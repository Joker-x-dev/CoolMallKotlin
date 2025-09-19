# AppState 使用示例代码

下面提供了 AppState 在实际项目中使用的示例代码，包括 ViewModel 和 Composable UI 部分。

## ViewModel 示例

```kotlin
package com.joker.coolmall.feature.user.viewmodel

import User
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.result.Result
import com.joker.coolmall.core.common.result.asResult
import com.joker.coolmall.core.data.repository.AuthRepository
import com.joker.coolmall.core.data.repository.UserInfoRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.Auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 用户相关ViewModel
 */
@HiltViewModel
class UserViewModel @Inject constructor(
    private val appState: AppState,
    private val authRepository: AuthRepository,
    private val userInfoRepository: UserInfoRepository
) : ViewModel() {

    // 从AppState获取用户登录状态
    val isLoggedIn: StateFlow<Boolean> = appState.isLoggedIn
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    // 从AppState获取用户信息
    val userInfo: StateFlow<User?> = appState.userInfo
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    /**
     * 执行登录操作
     */
    fun login(account: String, password: String) {
        viewModelScope.launch {
            val params = mapOf("username" to account, "password" to password)
            authRepository.login(params)
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Success -> {
                            if (result.data.isSucceeded && result.data.data != null) {
                                val auth = result.data.data!!
                                // 登录成功后获取用户信息
                                getUserInfo(auth)
                            }
                        }
                        else -> { /* 处理其他状态 */ }
                    }
                }
        }
    }

    /**
     * 获取用户信息
     */
    private fun getUserInfo(auth: Auth) {
        viewModelScope.launch {
            userInfoRepository.getPersonInfo()
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Success -> {
                            if (result.data.isSucceeded && result.data.data != null) {
                                val user = result.data.data!!
                                // 更新全局状态
                                appState.updateUserState(auth, user)
                            }
                        }
                        else -> { /* 处理其他状态 */ }
                    }
                }
        }
    }

    /**
     * 用户登出
     */
    fun logout() {
        viewModelScope.launch {
            // 调用远程登出API
            authRepository.logout()
                .collectLatest {
                    // 无论远程是否成功，都清除本地状态
                    appState.logout()
                }
        }
    }
}
```

## Composable UI 示例

```kotlin
package com.joker.coolmall.feature.user.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joker.coolmall.feature.user.viewmodel.UserViewModel

/**
 * 用户个人中心界面
 */
@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = hiltViewModel()
) {
    // 收集当前登录状态
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
    
    // 根据登录状态显示不同的UI
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (isLoggedIn) {
            // 已登录状态显示用户信息
            UserInfoContent(viewModel = viewModel)
        } else {
            // 未登录状态显示登录表单
            LoginContent(viewModel = viewModel)
        }
    }
}

/**
 * 已登录用户界面
 */
@Composable
private fun UserInfoContent(
    viewModel: UserViewModel,
    modifier: Modifier = Modifier
) {
    // 收集用户信息
    val userInfo by viewModel.userInfo.collectAsStateWithLifecycle()
    
    Column(modifier = modifier) {
        Text(text = "欢迎回来，${userInfo?.nickName ?: "用户"}")
        Spacer(modifier = Modifier.height(16.dp))
        
        // 显示用户ID
        Text(text = "用户ID: ${userInfo?.id ?: "未知"}")
        Spacer(modifier = Modifier.height(8.dp))
        
        // 显示手机号
        Text(text = "手机号: ${userInfo?.phone ?: "未绑定"}")
        Spacer(modifier = Modifier.height(16.dp))
        
        // 退出登录
        Button(onClick = { viewModel.logout() }) {
            Text("退出登录")
        }
    }
}

/**
 * 登录界面
 */
@Composable
private fun LoginContent(
    viewModel: UserViewModel,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    Column(modifier = modifier) {
        Text(text = "请登录")
        Spacer(modifier = Modifier.height(16.dp))
        
        // 用户名输入框
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("用户名") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        // 密码输入框
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("密码") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        // 登录按钮
        Button(onClick = { viewModel.login(username, password) }) {
            Text("登录")
        }
    }
}
```

## 在MainActivity中使用AppState

```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: AppNavigator
    
    @Inject
    lateinit var appState: AppState

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        
        // 使用AppState登录状态来决定启动页面显示时长
        var isReady = false
        lifecycleScope.launch {
            // 确保AppState已完成初始化
            delay(300)
            isReady = true
        }
        
        // 根据AppState中的登录状态决定是否继续显示启动页
        splashScreen.setKeepOnScreenCondition {
            !isReady
        }
        
        // 设置Compose内容
        setContent {
            AppTheme {
                // 应用导航，可以根据登录状态决定起始页面
                AppNavHost(navigator = navigator)
            }
        }
    }
}
```

## 刷新Token的示例

```kotlin
/**
 * Token管理ViewModel
 */
@HiltViewModel
class TokenViewModel @Inject constructor(
    private val appState: AppState,
    private val authRepository: AuthRepository
) : ViewModel() {

    /**
     * 检查并在需要时刷新Token
     * 可以在应用启动时或进入关键页面前调用
     */
    fun checkAndRefreshTokenIfNeeded() {
        viewModelScope.launch {
            if (appState.shouldRefreshToken()) {
                refreshToken()
            }
        }
    }
    
    /**
     * 刷新Token
     */
    private fun refreshToken() {
        viewModelScope.launch {
            val currentAuth = appState.auth.value ?: return@launch
            val params = mapOf("refreshToken" to currentAuth.refreshToken)
            
            authRepository.refreshToken(params)
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Success -> {
                            if (result.data.isSucceeded && result.data.data != null) {
                                // 更新全局状态中的认证信息
                                appState.updateAuth(result.data.data!!)
                            }
                        }
                        is Result.Error -> {
                            // Token刷新失败，可能需要重新登录
                            appState.logout()
                        }
                        else -> { /* 处理其他状态 */ }
                    }
                }
        }
    }
}
``` 