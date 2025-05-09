# ViewModel 模板

## 说明

这是用于快速创建 ViewModel 的模板。使用时，只需要填写：
- NAME：ViewModel 类名（通常直接使用文件名）
- CN_NAME：中文名称（用于注释）
- FEATURE_NAME：功能模块名（通常可从包名自动推导）

## 模板代码

```kotlin
package com.joker.coolmall.feature.${FEATURE_NAME}.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ${CN_NAME} ViewModel
 */
@HiltViewModel
class ${NAME} @Inject constructor(
    navigator: AppNavigator,
) : BaseViewModel(navigator) {
}
```

## 使用示例

创建"关于我们"页面的 ViewModel 时：
- 文件名：AboutViewModel
- NAME：AboutViewModel （可自动从文件名获取）
- CN_NAME：关于我们 