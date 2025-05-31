# BaseNetWorkViewModel 使用指南

本文档介绍如何使用 `BaseNetWorkViewModel` 处理网络请求并管理状态。

## 1. 基本概念

`BaseNetWorkViewModel` 是一个抽象基类，用于封装网络请求相关的通用逻辑，包括：

- 状态管理（加载中、成功、错误）
- 错误处理
- 数据流收集
- 路由参数ID获取（新增功能）

它使用 Kotlin Flow 和 Result 类处理异步数据流，并自动处理网络响应。

## 2. 核心组件

- **NetworkResponse\<T\>**: 网络响应的通用模型，包含：
    - `data: T?`: 响应数据
    - `isSucceeded: Boolean`: 请求是否成功
    - `message: String?`: 错误消息或成功提示

- **Result\<T\>**: 包装网络请求状态的密封类：
    - `Loading`: 加载中
    - `Success`: 成功，包含数据
    - `Error`: 错误，包含异常

- **BaseNetWorkUiState\<T\>**: 网络UI状态的泛型密封类：
    - `Loading`: 加载中
    - `Success<T>`: 成功，包含泛型数据T
    - `Error`: 错误，包含错误信息

## 3. 使用方法

### 3.1 创建ViewModel

继承 `BaseNetWorkViewModel` 并实现抽象方法：

```kotlin
@HiltViewModel
class HomeViewModel @Inject constructor(
    navigator: AppNavigator,
    private val repository: HomeRepository
) : BaseNetWorkViewModel<Home>(navigator) {

    // 必须在init块中调用executeRequest()方法
    init {
        super.executeRequest()
    }

    // 重写此方法返回API请求的Flow
    override fun requestApiFlow(): Flow<NetworkResponse<Home>> {
        return repository.getHomeData()
    }

    // 可选：自定义处理成功数据
    override fun onRequestSuccess(data: Home) {
        // 在这里处理数据，例如转换或过滤
        super.onRequestSuccess(data)
    }

    // 导航到详情页
    fun toGoodsDetail(goodsId: Long) {
        super.toPge(GoodsRoutes.DETAIL, goodsId)
    }
}
```

### 3.1.1 使用路由参数ID

对于需要路由参数的场景，如详情页，可以利用基类提供的路由参数ID获取功能：

```kotlin
@HiltViewModel
class GoodsDetailViewModel @Inject constructor(
    navigator: AppNavigator,
    savedStateHandle: SavedStateHandle,
    private val goodsRepository: GoodsRepository,
) : BaseNetWorkViewModel<Goods>(
    navigator = navigator,
    savedStateHandle = savedStateHandle,
    idKey = GoodsDetailRoutes.GOODS_ID_ARG // 指定路由参数的键名
) {

    init {
        super.executeRequest()
    }

    override fun requestApiFlow(): Flow<NetworkResponse<Goods>> {
        // 直接使用基类提供的requiredId获取路由参数
        return goodsRepository.getGoodsInfo(requiredId.toString())

        // 如果参数可能为空，也可以使用id属性
        // return if (id != null) {
        //     goodsRepository.getGoodsInfo(id.toString())
        // } else {
        //     flow { emit(NetworkResponse.failed("商品ID不存在")) }
        // }
    }
}
```

### 3.1.2 登录场景示例

该基类同样适用于登录等操作性请求：

```kotlin
@HiltViewModel
class LoginViewModel @Inject constructor(
    navigator: AppNavigator,
    private val authRepository: AuthRepository
) : BaseNetWorkViewModel<UserInfo>(navigator) {

    // 用户名和密码
    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    // 更新用户名
    fun updateUsername(value: String) {
        _username.value = value
    }

    // 更新密码
    fun updatePassword(value: String) {
        _password.value = value
    }

    // 重写API请求流方法
    override fun requestApiFlow(): Flow<NetworkResponse<UserInfo>> {
        return authRepository.login(username.value, password.value)
    }

    // 重写成功回调，处理登录成功后的操作
    override fun onRequestSuccess(data: UserInfo) {
        // 保存用户信息到本地
        authRepository.saveUserInfo(data)

        // 调用父类方法更新状态
        super.onRequestSuccess(data)

        // 登录成功后导航到首页
        navigator.navigateTo(MainRoutes.HOME)
    }

    // 登录按钮点击
    fun login() {
        executeRequest()
    }
}
```

### 3.2 创建Repository

```kotlin
interface HomeRepository {
    fun getHomeData(): Flow<NetworkResponse<Home>>
}

class HomeRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : HomeRepository {
    override fun getHomeData(): Flow<NetworkResponse<Home>> {
        return flow {
            // 调用API服务
            val response = apiService.getHomeData()
            emit(response)
        }
    }
}
```

### 3.3 创建UI，使用状态

```kotlin
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    
    // 使用uiState渲染UI
    when (val state = uiState) {
        is BaseNetWorkUiState.Loading -> {
            LoadingIndicator()
        }
        is BaseNetWorkUiState.Success -> {
            // 成功状态包含泛型数据
            val data = state.data
            if (data != null) {
                HomeContent(data = data)
            }
        }
        is BaseNetWorkUiState.Error -> {
            val message = state.message ?: "未知错误"
            ErrorView(
                message = message, 
                onRetry = { viewModel.retryRequest() }
            )
        }
    }
}
```

## 4. 工作流程

1. 子类实现 `requestApiFlow()` 方法，返回 `Flow<NetworkResponse<T>>`
2. 子类在 `init` 块中调用 `super.executeRequest()` 方法，或在特定事件（如按钮点击）时调用
3. `executeRequest()` 方法执行以下步骤：
    - 调用子类实现的 `requestApiFlow()` 方法获取API请求流
    - 使用 `asResult()` 扩展函数将数据流转换为 `Result` 类型
    - 根据结果设置相应的状态：
        - `Result.Loading` → `BaseNetWorkUiState.Loading`
        - `Result.Success` → 检查网络响应是否成功:
            - 成功且有数据 → 调用 `onRequestSuccess()` → `BaseNetWorkUiState.Success(data)`
            - 失败或无数据 → 调用 `onRequestError()` → `BaseNetWorkUiState.Error`
        - `Result.Error` → 调用 `onRequestError()` → `BaseNetWorkUiState.Error`

## 5. 路由参数ID获取

`BaseNetWorkViewModel` 提供了两种获取路由参数ID的方式：

### 5.1 可空ID属性 (id)

```kotlin
protected val id: Long?
```

- 当路由参数可能不存在时使用
- 返回可空类型，需要自行处理空值情况
- 适用于参数可选的场景

### 5.2 非空ID属性 (requiredId)

```kotlin
protected val requiredId: Long
```

- 当路由参数必须存在时使用
- 如果参数不存在，会抛出异常
- 适用于参数必选的场景，如详情页

### 5.3 配置方法

在构造函数中指定参数：

```kotlin
BaseNetWorkViewModel(
    navigator = navigator,
    savedStateHandle = savedStateHandle, // 路由参数来源
    idKey = "your_id_key"               // 参数键名
)
```

## 6. 优势

- **代码复用**: 避免在每个ViewModel中重复编写相同的网络请求逻辑
- **关注点分离**: 子类只关注获取数据，基类处理状态管理
- **统一错误处理**: 提供一致的错误处理机制
- **安全性**: 通过捕获异常和空值检查增强可靠性
- **类型安全**: 泛型设计确保类型一致性，减少类型转换错误
- **易于使用**: 简洁的API，子类只需实现少量方法
- **命名清晰**: 方法名称 `requestApiFlow` 直观表明这是API请求，适用于任何类型的网络请求
- **路由参数封装**: 减少路由参数获取的重复代码

## 7. 注意事项

- 子类**必须**在 `init` 块中调用 `super.executeRequest()` 方法，或在适当的时机调用
- 确保实现 `requestApiFlow()` 方法返回正确的 `Flow<NetworkResponse<T>>` 类型
- 避免在 `requestApiFlow()` 中使用尚未初始化的属性
- 自定义 `onRequestSuccess` 或 `onRequestError` 时，记得调用父类方法
- 使用 `requiredId` 前，确保路由参数一定存在，否则会抛出异常
- 如果多个路由参数使用不同的键名，需要在子类中单独处理

## 8. 扩展与自定义

如果需要额外功能，可以重写以下方法：

- `onRequestSuccess(data: T)`: 自定义处理成功数据的逻辑
- `onRequestError(message: String, exception: Throwable)`: 自定义处理错误的逻辑

### 自定义示例：

```kotlin
// 添加日志和过滤功能
override fun onRequestSuccess(data: Home) {
    Log.d(TAG, "接收到首页数据: ${data.banner?.size ?: 0} 个轮播图")
    
    // 空安全检查示例
    val filteredBanners = data.banner?.filter { it.isActive } ?: emptyList()
    val processedData = data.copy(banner = filteredBanners)
    
    // 调用父类方法更新状态
    super.onRequestSuccess(processedData)
}

// 自定义错误处理
override fun onRequestError(message: String, exception: Throwable) {
    // 记录错误日志
    Log.e(TAG, "数据加载错误: $message", exception)
    
    // 可以进行特殊错误处理，例如网络错误重试
    if (exception is IOException) {
        // 处理网络错误
    }
    
    // 调用父类方法更新状态
    super.onRequestError(message, exception)
}
``` 