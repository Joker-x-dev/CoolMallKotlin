# LogUtils 使用指南

LogUtils 是基于 [Timber](https://github.com/JakeWharton/timber) 封装的日志工具类，提供了统一的日志记录接口，支持不同级别的日志输出。

## 特性

- 简洁易用的API
- 支持不同级别的日志（VERBOSE、DEBUG、INFO、WARN、ERROR、ASSERT）
- 支持格式化日志内容
- 支持异常信息记录
- 支持自定义日志树实现
- 区分调试和生产环境

## 使用方法

### 1. 初始化

在 Application 的 onCreate 方法中初始化 LogUtils：

```kotlin
// 在 Application 类中
override fun onCreate() {
    super.onCreate()
    
    LogUtils.init(this, BuildConfig.DEBUG)
}
```

### 2. 记录日志

```kotlin
// 基本用法
LogUtils.d("这是一条调试日志")
LogUtils.i("这是一条信息日志")
LogUtils.w("这是一条警告日志")
LogUtils.e("这是一条错误日志")

// 格式化日志
LogUtils.d("用户 %s 登录成功", username)
LogUtils.i("处理了 %d 条数据", count)

// 记录异常
try {
    // 可能抛出异常的代码
} catch (e: Exception) {
    LogUtils.e(e, "操作失败")
}

// 带参数的异常日志
LogUtils.e(exception, "用户 %s 执行操作时出错", username)
```

### 3. 日志级别

LogUtils 支持以下日志级别，按严重程度递增：

- `v()`: Verbose - 最详细的日志信息，通常只在开发阶段使用
- `d()`: Debug - 调试信息，用于开发期间
- `i()`: Info - 常规信息，如操作成功
- `w()`: Warn - 警告信息，不会导致程序崩溃但需要注意
- `e()`: Error - 错误信息，表示发生了异常
- `wtf()`: Assert - 致命错误，表示发生了不应该发生的情况

### 4. 自定义日志树

可以通过实现 `Timber.Tree` 类来创建自定义日志树，例如将日志保存到文件或上传到服务器：

```kotlin
// 创建自定义日志树
class FileLoggingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        // 将日志写入文件
    }
}

// 植入自定义日志树
LogUtils.plant(FileLoggingTree())
```

### 5. 生产环境日志

LogUtils 已内置了一个 `CrashReportingTree` 实现示例，可以在生产环境中使用它来上报错误：

```kotlin
// 在 Application 类中
override fun onCreate() {
    super.onCreate()
    
    if (BuildConfig.DEBUG) {
        LogUtils.init(this, true)
    } else {
        LogUtils.init(this, false)
        LogUtils.plant(LogUtils.CrashReportingTree())
    }
}
```

## 最佳实践

1. 在调试构建中，尽量使用详细日志来帮助开发
2. 在生产构建中，只记录重要的错误和警告信息
3. 使用格式化字符串而不是字符串连接，可以提高性能
4. 为不同模块添加自定义标签，以便于日志分析
5. 日志中不要包含敏感信息（如密码、令牌等）

## 注意事项

- LogUtils 是线程安全的，可以在任何线程中调用
- 如果未初始化 LogUtils，日志将不会被记录
- 生产环境中请谨慎使用日志，避免性能影响和信息泄露 