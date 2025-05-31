# MMKVUtils 使用指南

MMKVUtils 是基于腾讯 [MMKV](https://github.com/Tencent/MMKV)
封装的高性能键值存储工具类，提供了统一的接口进行数据存取，支持多种数据类型和多实例模式。

## 特性

- 基于 mmap 内存映射的高性能键值存储
- 支持多种数据类型（基本类型、String、ByteArray、Parcelable、Set 等）
- 支持多实例管理，包括默认实例和命名实例
- 支持多进程访问模式
- 支持数据加密功能
- 防止应用崩溃导致数据丢失
- 读写性能远优于 SharedPreferences

## 使用方法

### 1. 初始化

在 Application 的 onCreate 方法中初始化 MMKVUtils：

```kotlin
// 在 Application 类中
override fun onCreate() {
    super.onCreate()
    
    // 初始化 MMKV
    val rootDir = MMKVUtils.init(this)
    Log.d("MMKVUtils", "MMKV root: $rootDir")
}
```

### 2. 基本数据存取

```kotlin
// 存储布尔值
MMKVUtils.putBoolean("isLogin", true)
val isLogin = MMKVUtils.getBoolean("isLogin", false)

// 存储整数
MMKVUtils.putInt("userId", 10086)
val userId = MMKVUtils.getInt("userId", 0)

// 存储长整数
MMKVUtils.putLong("timestamp", System.currentTimeMillis())
val timestamp = MMKVUtils.getLong("timestamp", 0L)

// 存储浮点数
MMKVUtils.putFloat("price", 99.9f)
val price = MMKVUtils.getFloat("price", 0f)

// 存储双精度浮点数
MMKVUtils.putDouble("distance", 12.34)
val distance = MMKVUtils.getDouble("distance", 0.0)

// 存储字符串
MMKVUtils.putString("username", "张三")
val username = MMKVUtils.getString("username", "")

// 存储字节数组
val bytes = "Hello MMKV".toByteArray()
MMKVUtils.putBytes("binaryData", bytes)
val data = MMKVUtils.getBytes("binaryData")

// 存储字符串集合
val tags = setOf("Android", "Kotlin", "MMKV")
MMKVUtils.putStringSet("tags", tags)
val storedTags = MMKVUtils.getStringSet("tags")
```

### 3. 存储序列化对象

对于复杂对象，需要实现 Parcelable 接口：

```kotlin
// 假设 UserInfo 是一个实现了 Parcelable 的数据类
val user = UserInfo(id = 10086, name = "张三", age = 30)

// 存储对象
MMKVUtils.putParcelable("userInfo", user)

// 获取对象
val storedUser = MMKVUtils.getParcelable("userInfo", UserInfo::class.java)
```

### 4. 使用命名实例

当需要分组管理数据或者隔离不同模块的数据时，可以使用命名实例：

```kotlin
// 获取指定名称的实例
val userKV = MMKVUtils.getInstance("user")

// 使用命名实例存取数据
userKV.encode("name", "张三")
userKV.encode("age", 30)
val name = userKV.decodeString("name")
val age = userKV.decodeInt("age")
```

### 5. 多进程访问

当需要在多个进程之间共享数据时，可以使用多进程模式：

```kotlin
// 获取支持多进程访问的实例
val sharedKV = MMKVUtils.getMultiProcessInstance("shared_data")

// 使用多进程实例存取数据
sharedKV.encode("message", "可以被其他进程读取")
val message = sharedKV.decodeString("message")
```

### 6. 加密存储

对于敏感数据，可以使用加密功能：

```kotlin
// 获取加密的实例
val secureKV = MMKVUtils.getEncryptedInstance("secure_data", "your_secret_key")

// 使用加密实例存取数据
secureKV.encode("password", "123456")
val password = secureKV.decodeString("password")
```

### 7. 数据管理

```kotlin
// 检查键是否存在
if (MMKVUtils.containsKey("userId")) {
    // 键存在
}

// 删除单个键值对
MMKVUtils.remove("username")

// 删除指定前缀的键值对
MMKVUtils.removeValuesForKeys("temp_")

// 获取所有键名
val allKeys = MMKVUtils.getAllKeys()

// 清除所有数据
MMKVUtils.clearAll()

// 获取存储大小（字节）
val size = MMKVUtils.totalSize()

// 获取键值对数量
val count = MMKVUtils.count()
```

## 高级特性

### 自定义存储目录

```kotlin
// 获取使用自定义存储目录的实例
val customDirKV = MMKVUtils.getInstance("custom", 
    MMKV.SINGLE_PROCESS_MODE, 
    null, 
    "/path/to/custom/dir")
```

### 异步写入

MMKV 默认是同步写入的，但其底层实现是基于 mmap 的，所以即使应用崩溃，数据也不会丢失。因此实际使用中，可以直接在主线程进行读写操作，性能影响很小。

### 性能建议

1. 较大数据的写入，建议放在工作线程中执行
2. 避免频繁读写同一个大的数据结构，可以拆分为多个小的键值对
3. 对于不常变动的配置信息，可以在内存中缓存，减少读取次数

## 注意事项

- MMKVUtils 必须在使用前初始化，否则会抛出异常
- MMKV 本身不提供数据同步功能，如需同步到云端，需自行实现
- 序列化对象必须实现 Parcelable 接口
- 多进程模式下，性能会略有下降，请按实际需求使用
- 加密功能会有一定的性能开销
- 注意保管好加密密钥，密钥丢失将无法恢复数据 