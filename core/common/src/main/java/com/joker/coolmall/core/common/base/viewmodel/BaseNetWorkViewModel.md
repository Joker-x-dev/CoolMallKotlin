# BaseNetWorkViewModel 使用指南

本文档介绍如何使用 `BaseNetWorkViewModel` 处理网络请求并管理状态。

## 1. 基本概念

`BaseNetWorkViewModel` 是一个抽象基类，用于封装网络请求相关的通用逻辑，包括：

- 状态管理（加载中、成功、错误）
- 错误处理
- 数据流收集

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
    private val repository: HomeRepository,
    savedStateHandle: SavedStateHandle
) : BaseNetWorkViewModel<Home>(navigator) {

    // 必须在init块中调用loadData()方法
    init {
        super.loadData()
    }

    // 重写此方法返回网络响应数据流
    override fun fetchFlow(): Flow<NetworkResponse<Home>> {
        return repository.getHomeData()
    }
    
    // 可选：自定义处理成功数据
    override fun handleSuccess(data: Home) {
        // 在这里处理数据，例如转换或过滤
        super.handleSuccess(data)
    }
    
    // 导航到详情页
    fun toGoodsDetail(goodsId: Long) {
        super.toPge(GoodsRoutes.DETAIL, goodsId)
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
            ErrorView(message = message, onRetry = { viewModel.retry() })
        }
    }
}
```

## 4. 工作流程

1. 子类实现 `fetchFlow()` 方法，返回 `Flow<NetworkResponse<T>>`
2. 子类在 `init` 块中调用 `super.loadData()` 方法
3. `loadData()` 方法执行以下步骤：
   - 调用子类实现的 `fetchFlow()` 方法获取数据流
   - 使用 `asResult()` 扩展函数将数据流转换为 `Result` 类型
   - 根据结果设置相应的状态：
     - `Result.Loading` → `BaseNetWorkUiState.Loading`
     - `Result.Success` → 检查网络响应是否成功:
       - 成功且有数据 → `BaseNetWorkUiState.Success(data)`
       - 失败或无数据 → `BaseNetWorkUiState.Error`
     - `Result.Error` → `BaseNetWorkUiState.Error`

## 5. 优势

- **代码复用**: 避免在每个ViewModel中重复编写相同的网络请求逻辑
- **关注点分离**: 子类只关注获取数据，基类处理状态管理
- **统一错误处理**: 提供一致的错误处理机制
- **安全性**: 通过捕获异常和空值检查增强可靠性
- **类型安全**: 泛型设计确保类型一致性，减少类型转换错误
- **易于使用**: 简洁的API，子类只需实现少量方法

## 6. 注意事项

- 子类**必须**在 `init` 块中调用 `super.loadData()` 方法
- 确保实现 `fetchFlow()` 方法返回正确的 `Flow<NetworkResponse<T>>` 类型
- 避免在 `fetchFlow()` 中使用尚未初始化的属性
- 自定义 `handleSuccess` 或 `handleError` 时，记得调用父类方法

## 7. 扩展与自定义

如果需要额外功能，可以重写以下方法：

- `handleSuccess(data: T)`: 自定义处理成功数据的逻辑
- `handleError(message: String, exception: Throwable)`: 自定义处理错误的逻辑

### 自定义示例：

```kotlin
// 添加日志和过滤功能
override fun handleSuccess(data: Home) {
    Log.d(TAG, "接收到首页数据: ${data.banner?.size ?: 0} 个轮播图")
    
    // 空安全检查示例
    val filteredBanners = data.banner?.filter { it.isActive } ?: emptyList()
    val processedData = data.copy(banner = filteredBanners)
    
    // 调用父类方法更新状态
    super.handleSuccess(processedData)
}

// 自定义错误处理
override fun handleError(message: String, exception: Throwable) {
    // 记录错误日志
    Log.e(TAG, "数据加载错误: $message", exception)
    
    // 可以进行特殊错误处理，例如网络错误重试
    if (exception is IOException) {
        // 处理网络错误
    }
    
    // 调用父类方法更新状态
    super.handleError(message, exception)
}
``` 