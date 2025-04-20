# NotificationUtil - 通知工具类

这是一个用于发送系统通知的工具类，支持发送普通通知和验证码通知。

## 功能特点

- 支持 Android 8.0+ 的通知渠道管理
- 内置默认通知渠道和验证码通知渠道
- 支持发送带有点击事件的普通通知
- 专门针对验证码场景优化的通知
- 支持取消特定通知或所有通知

## 如何使用

### 1. 初始化通知渠道（可选）

建议在应用启动时（如在 Application 类中）初始化通知渠道：

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 初始化通知渠道
        NotificationUtil.initNotificationChannels(this)
    }
}
```

### 2. 发送普通通知

```kotlin
// 基本用法
val notificationId = NotificationUtil.sendNotification(
    context = context,
    title = "通知标题",
    content = "通知内容"
)

// 带有点击事件的通知
val intent = Intent(context, MainActivity::class.java)
val notificationId = NotificationUtil.sendNotification(
    context = context,
    title = "通知标题",
    content = "通知内容",
    intent = intent
)

// 自定义图标的通知
val notificationId = NotificationUtil.sendNotification(
    context = context,
    title = "通知标题",
    content = "通知内容",
    iconResId = R.drawable.ic_notification
)
```

### 3. 发送验证码通知

特别针对验证码场景的通知，使用高优先级显示：

```kotlin
val notificationId = NotificationUtil.sendVerificationCodeNotification(
    context = context,
    code = "123456"  // 验证码内容
)

// 使用自定义图标
val notificationId = NotificationUtil.sendVerificationCodeNotification(
    context = context,
    code = "123456",
    iconResId = R.drawable.ic_verification_code
)
```

### 4. 取消通知

取消特定通知：

```kotlin
NotificationUtil.cancelNotification(context, notificationId)
```

取消所有通知：

```kotlin
NotificationUtil.cancelAllNotifications(context)
```

## 在验证码登录场景中的使用示例

在 `SmsLoginViewModel` 中使用 `NotificationUtil` 发送验证码通知：

```kotlin
fun sendVerificationCode() {
    viewModelScope.launch {
        // 发送短信验证码请求
        authRepository.getSmsCode(params)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        // 成功获取验证码，发送通知
                        result.data.data?.let { smsCode ->
                            NotificationUtil.sendVerificationCodeNotification(
                                context = context,
                                code = smsCode
                            )
                        }
                    }
                    // 其他状态处理...
                }
            }
    }
}
```

## 权限需求

在 Android 13 (API 级别 33) 及更高版本中，需要添加 `POST_NOTIFICATIONS` 权限：

```xml
<!-- AndroidManifest.xml -->
<manifest ...>
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
    ...
</manifest>
```

在运行时，您需要请求用户授予此权限：

```kotlin
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
}
```

## 注意事项

- 为不同类型的通知创建不同的通知渠道是良好的用户体验
- 确保在发送通知前已经处理好权限问题
- 避免发送过多通知导致用户体验下降
- 通知ID基于时间戳生成，确保唯一性 