# PermissionUtils 使用指南

基于 XXPermissions 框架封装的权限工具类，提供常用权限的快捷申请方法。

## 最新更新

- ✨ **代码重构优化**：提取公共方法，消除重复代码
- 🔧 **适配最新版本**：完全适配最新版本的 XXPermissions 框架
- 📝 **API 更新**：使用 `PermissionLists` 替代已弃用的 `Permission` 类
- 🎯 **统一处理逻辑**：统一权限申请结果处理和错误提示

## 特性

- 🚀 简化权限申请流程
- 📱 支持常用权限类型
- 🎯 自动处理权限拒绝情况
- 💬 集成 Toast 提示
- 🔧 支持自定义权限组合
- 🛠️ 代码结构优化，减少重复逻辑

## 使用方法

### 1. 存储权限

申请读写外部存储权限：

```kotlin
PermissionUtils.requestStoragePermission(this) { granted ->
    if (granted) {
        // 权限获取成功，可以进行文件操作
        saveFileToStorage()
    } else {
        // 权限获取失败
        showErrorMessage()
    }
}
```

### 2. 相机权限

申请相机权限：

```kotlin
PermissionUtils.requestCameraPermission(this) { granted ->
    if (granted) {
        // 权限获取成功，可以打开相机
        openCamera()
    }
}
```

### 3. 相册权限

申请读取相册权限：

```kotlin
PermissionUtils.requestGalleryPermission(this) { granted ->
    if (granted) {
        // 权限获取成功，可以访问相册
        openGallery()
    }
}
```

### 4. 通知权限

申请通知权限（Android 13+）：

```kotlin
PermissionUtils.requestNotificationPermission(this) { granted ->
    if (granted) {
        // 权限获取成功，可以发送通知
        sendNotification()
    }
}
```

### 5. 录音权限

申请录音权限：

```kotlin
PermissionUtils.requestAudioPermission(this) { granted ->
    if (granted) {
        // 权限获取成功，可以开始录音
        startRecording()
    }
}
```

### 6. 位置权限

申请位置权限：

```kotlin
PermissionUtils.requestLocationPermission(this) { granted ->
    if (granted) {
        // 权限获取成功，可以获取位置信息
        getCurrentLocation()
    }
}
```

### 7. 组合权限

申请相机和相册权限：

```kotlin
PermissionUtils.requestCameraAndGalleryPermission(this) { granted ->
    if (granted) {
        // 权限获取成功，可以拍照或选择图片
        showImagePickerDialog()
    }
}
```

### 8. 自定义权限

申请自定义权限组合：

```kotlin
val permissions = arrayOf(
    PermissionLists.getCameraPermission(),
    PermissionLists.getRecordAudioPermission(),
    PermissionLists.getWriteExternalStoragePermission()
)

PermissionUtils.requestCustomPermissions(this, permissions) { granted, deniedPermissions ->
    if (granted) {
        // 所有权限获取成功
        startVideoRecording()
    } else {
        // 部分权限被拒绝
        Log.d("Permission", "被拒绝的权限: $deniedPermissions")
    }
}
```

## 权限检查

### 检查单个权限

```kotlin
if (PermissionUtils.hasPermission(this, PermissionLists.getCameraPermission())) {
    // 已有相机权限
    openCamera()
} else {
    // 需要申请权限
    PermissionUtils.requestCameraPermission(this) { granted ->
        if (granted) openCamera()
    }
}
```

### 检查多个权限

```kotlin
val permissions = arrayOf(
    PermissionLists.getCameraPermission(),
    PermissionLists.getRecordAudioPermission()
)
if (PermissionUtils.hasPermissions(this, permissions)) {
    // 已有所有权限
    startVideoRecording()
} else {
    // 需要申请权限
    PermissionUtils.requestCustomPermissions(this, permissions) { granted, _ ->
        if (granted) startVideoRecording()
    }
}
```

## 权限设置

### 跳转到应用权限设置页面

```kotlin
// 跳转到应用权限设置页面
PermissionUtils.openPermissionSettings(this)

// 跳转到指定权限的设置页面
val permissions = arrayOf(PermissionLists.getCameraPermission())
PermissionUtils.openPermissionSettings(this, permissions)
```

## 支持的上下文类型

工具类支持以下上下文类型：

- `FragmentActivity`
- `Fragment`
- `Context`（需要强制转换）

## 权限处理流程

1. **权限授予**：显示成功提示，执行回调
2. **权限拒绝**：显示失败提示，执行回调
3. **永久拒绝**：显示永久拒绝提示，自动跳转到设置页面

## 注意事项

1. **Android 版本适配**：工具类会根据 Android 版本自动选择合适的权限
2. **权限组合**：某些权限在不同 Android 版本下可能有所不同
3. **用户体验**：建议在申请权限前向用户说明权限用途
4. **错误处理**：务必处理权限被拒绝的情况

## 常用权限说明

| 权限类型 | 说明     | 适用场景      |
|------|--------|-----------|
| 存储权限 | 读写外部存储 | 文件下载、图片保存 |
| 相机权限 | 使用相机   | 拍照、扫码     |
| 相册权限 | 读取媒体文件 | 选择图片、视频   |
| 通知权限 | 发送通知   | 消息推送      |
| 录音权限 | 录制音频   | 语音消息、通话   |
| 位置权限 | 获取位置信息 | 地图导航、附近功能 |

## 最佳实践

1. **按需申请**：只在需要时申请权限
2. **用户教育**：向用户解释权限用途
3. **优雅降级**：权限被拒绝时提供替代方案
4. **及时检查**：在使用功能前检查权限状态

```kotlin
// 推荐的使用模式
fun takePhoto() {
    if (PermissionUtils.hasPermission(this, PermissionLists.getCameraPermission())) {
        // 直接使用相机
        openCamera()
    } else {
        // 申请权限
        PermissionUtils.requestCameraPermission(this) { granted ->
            if (granted) {
                openCamera()
            } else {
                // 提供替代方案
                showGalleryPicker()
            }
        }
    }
}
```