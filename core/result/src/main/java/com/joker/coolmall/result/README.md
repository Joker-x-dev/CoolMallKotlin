# ResultHandler - 网络请求结果处理工具类

这是一个用于简化ViewModel中网络请求结果处理的工具类，特别是处理网络请求的`Loading`、`Success`和`Error`
三种状态的逻辑。

## 功能特点

- 自动处理网络请求的三种状态：加载中、成功、错误
- 简化代码，减少重复的样板代码
- 默认显示错误Toast提示，无需提供Context
- 提供多个回调接口，可以灵活处理各种状态
- 自动处理成功但服务器返回错误的情况

## 如何使用

### 1. 完整功能方法 - handleResult

完整功能方法提供对请求过程中所有状态和数据的全面控制：

```kotlin
// 在ViewModel中
fun loadData() {
    ResultHandler.handleResult(
        scope = viewModelScope,
        flow = repository.getData(params).asResult(),
        showToast = true,  // 可选参数，默认为true，错误时自动显示Toast
        onLoading = {
            // 处理加载中状态
            _isLoading.value = true
        },
        onSuccess = { response ->
            // 处理整个响应对象（不管成功与否）
            _rawResponse.value = response
        },
        onSuccessWithData = { data ->
            // 只有当请求成功且有数据时才会调用
            _data.value = data
        },
        onError = { message, exception ->
            // 处理错误状态
            _errorMessage.value = message
        }
    )
}
```

### 2. 简化方法 - handleResultWithData

如果您只关心成功数据，可以使用简化版本：

```kotlin
// 在ViewModel中
fun loadUserData() {
    ResultHandler.handleResultWithData(
        scope = viewModelScope,
        flow = repository.getUserData().asResult(),
        onLoading = {
            _isLoading.value = true
        },
        onData = { userData ->
            // 只有成功并且有数据时才会调用
            _userData.value = userData
        },
        onError = { message, _ ->
            _isLoading.value = false
        }
    )
}
```

### 3. 最简用法 - 只处理成功数据

```kotlin
// 在ViewModel中
fun loadSimpleData() {
    ResultHandler.handleResultWithData(
        scope = viewModelScope,
        flow = repository.getSimpleData().asResult(),
        onData = { data ->
            // 只处理成功的数据
            _data.value = data
        }
    )
}
```

## 在SmsLoginViewModel中的应用

以下是在验证码登录场景中使用ResultHandler的实际示例：

```kotlin
/**
 * 发送短信验证码
 */
fun sendVerificationCode() {
    val params = mapOf(
        "phone" to phone.value,
        "captchaId" to captcha.value.captchaId,
        "code" to imageCode.value
    )
    
    ResultHandler.handleResultWithData(
        scope = viewModelScope,
        flow = authRepository.getSmsCode(params).asResult(),
        onData = { smsCode ->
            // 成功获取验证码，发送通知
            NotificationUtil.sendVerificationCodeNotification(
                context = context,
                code = smsCode
            )
        }
    )
}

/**
 * 执行短信登录操作
 */
fun login() {
    val params = mapOf(
        "phone" to phone.value,
        "smsCode" to verificationCode.value
    )
    
    ResultHandler.handleResultWithData(
        scope = viewModelScope,
        flow = authRepository.loginByPhone(params).asResult(),
        onData = { authData ->
            appState.updateAuth(authData)
            ToastUtils.showSuccess("登录成功")
        }
    )
}
```

## 关于Toast提示

工具类使用 `ToastUtils` 自动显示错误提示，不需要提供Context：

- `ToastUtils` 在应用启动时已经完成初始化，可直接使用
- 错误提示默认开启，可通过 `showToast = false` 关闭
- 显示成功提示可以使用 `ToastUtils.showSuccess("成功消息")`

## 注意事项

1. **错误处理**: 所有网络异常和其他异常都会被捕获并通过`onError`回调函数传递
2. **空值处理**: 工具类内部处理了空值情况，当响应数据为`null`时不会崩溃
3. **类型安全**: 泛型参数`T`确保类型安全，编译器会检查类型匹配

## 与BaseNetWorkViewModel的关系

此工具类可以与项目中已有的`BaseNetWorkViewModel`基类配合使用：

- `BaseNetWorkViewModel`适用于整个页面的数据加载和状态管理
- `ResultHandler`更适合处理页面内的局部网络请求（如表单提交、点击操作）或多个独立的网络请求

两者可以根据具体场景选择使用。 