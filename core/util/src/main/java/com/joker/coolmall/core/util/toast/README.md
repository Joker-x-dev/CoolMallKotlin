# ToastUtils 使用指南

ToastUtils 是基于 [Toaster](https://github.com/getActivity/Toaster)
框架封装的吐司工具类，提供了统一的吐司显示接口，支持多种样式的吐司消息。

## 特性

- 简洁易用的API
- 支持多种样式（普通、成功、失败、警告）
- 支持黑白两种主题风格
- 支持短时间和长时间显示
- 支持延迟显示
- 支持自定义样式

## 使用方法

### 1. 初始化

在 Application 的 onCreate 方法中初始化 ToastUtils：

```kotlin
// 在 Application 类中
override fun onCreate() {
    super.onCreate()
    
    // 根据应用主题设置Toast样式
    ToastUtils.init(this, isDarkTheme)
}
```

### 2. 显示普通吐司

```kotlin
// 显示简单文本
ToastUtils.show("这是一条普通提示")

// 显示资源字符串
ToastUtils.show(R.string.message)

// 显示短时间吐司
ToastUtils.showShort("这是短时间提示")

// 显示长时间吐司
ToastUtils.showLong("这是长时间提示")
```

### 3. 显示特殊样式吐司

```kotlin
// 成功样式
ToastUtils.showSuccess("操作成功")

// 错误样式
ToastUtils.showError("操作失败")

// 警告样式
ToastUtils.showWarning("请注意")

// 使用资源ID（需要上下文）
ToastUtils.showSuccess(context, R.string.success_message)
ToastUtils.showError(context, R.string.error_message)
ToastUtils.showWarning(context, R.string.warning_message)

// 使用资源ID（不需要上下文，但必须在初始化后使用）
ToastUtils.showSuccess(R.string.success_message)
ToastUtils.showError(R.string.error_message)
ToastUtils.showWarning(R.string.warning_message)
```

### 4. 延迟显示吐司

```kotlin
// 延迟2秒显示
ToastUtils.delayedShow("这是延迟显示的提示", 2000)

// 使用资源ID
ToastUtils.delayedShow(R.string.delayed_message, 2000)
```

### 5. 设置吐司样式

```kotlin
// 设置黑色样式（深色文字，浅色背景）
ToastUtils.setBlackStyle()

// 设置白色样式（浅色文字，深色背景）
ToastUtils.setWhiteStyle()
```

### 6. 取消吐司

```kotlin
// 取消当前显示的吐司
ToastUtils.cancel()
```

## 自定义样式

ToastUtils 预设了三种特殊样式：成功、失败、警告。这些样式使用了自定义布局：

- 成功样式：`R.layout.toast_success`
- 失败样式：`R.layout.toast_error`
- 警告样式：`R.layout.toast_warn`

如果需要添加新的样式，可以：

1. 创建新的布局文件
2. 使用 `CustomToastStyle` 应用该布局
3. 封装为新的方法

例如：

```kotlin
fun showCustom(text: CharSequence) {
    val params = ToastParams()
    params.text = text
    params.style = CustomToastStyle(R.layout.toast_custom)
    Toaster.show(params)
}
```

## 主题适配

ToastUtils 支持根据应用主题自动选择合适的样式：

- 浅色主题下默认使用 `BlackToastStyle`（深色文字，浅色背景）
- 深色主题下默认使用 `WhiteToastStyle`（浅色文字，深色背景）

初始化时只需传入当前主题模式：

```kotlin
// 根据应用主题初始化
val isDarkTheme = ... // 获取当前是否为深色主题
ToastUtils.init(application, isDarkTheme)
```

## 最佳实践

1. 根据消息类型选择合适的样式：
    - 普通提示信息：使用 `show()`
    - 操作成功提示：使用 `showSuccess()`
    - 操作失败提示：使用 `showError()`
    - 需要注意的提示：使用 `showWarning()`

2. 合理使用短时间和长时间显示：
    - 短时间（`showShort()`）：简单、不太重要的提示
    - 长时间（`showLong()`）：需要用户注意的重要提示

3. 避免连续多次调用，这会导致吐司叠加显示

## 注意事项

- ToastUtils 建议在主线程中调用，避免在子线程中使用
- 特殊样式的吐司（成功、失败、警告）需要自定义布局支持
- 使用不需要上下文的资源ID方法前，必须确保 ToastUtils 已经初始化
- 不要在吐司中显示敏感信息
- 避免过于频繁地显示吐司，影响用户体验 