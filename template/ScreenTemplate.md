# 页面模板 (Screen Template)

## 说明

这是用于快速创建 Compose 页面的模板。使用时，只需要填写：
- PAGE_NAME：页面名称（不含 Screen 后缀）
- CN_NAME：中文名称（用于注释和页面显示）
- FEATURE_NAME：功能模块名（通常可从包名自动推导）

## 模板代码

```kotlin
package com.joker.coolmall.feature.${FEATURE_NAME}.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.feature.${FEATURE_NAME}.viewmodel.${PAGE_NAME}ViewModel

/**
 * ${CN_NAME}路由
 *
 * @param viewModel ${CN_NAME} ViewModel
 */
@Composable
internal fun ${PAGE_NAME}Route(
    viewModel: ${PAGE_NAME}ViewModel = hiltViewModel()
) {
    ${PAGE_NAME}Screen(
        onBackClick = viewModel::navigateBack,
        onRetry = {}
    )
}

/**
 * ${CN_NAME}界面
 *
 * @param uiState UI状态
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ${PAGE_NAME}Screen(
    uiState: BaseNetWorkUiState<Any> = BaseNetWorkUiState.Loading,
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    AppScaffold(
        onBackClick = onBackClick
    ) {
        BaseNetWorkView(
            uiState = uiState,
            onRetry = onRetry
        ) {
            ${PAGE_NAME}ContentView()
        }
    }
}

/**
 * ${CN_NAME}内容视图
 */
@Composable
private fun ${PAGE_NAME}ContentView() {
    AppText(
        text = "${CN_NAME}",
        size = TextSize.TITLE_MEDIUM
    )
}

/**
 * ${CN_NAME}界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun ${PAGE_NAME}ScreenPreview() {
    AppTheme {
        ${PAGE_NAME}Screen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}

/**
 * ${CN_NAME}界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun ${PAGE_NAME}ScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        ${PAGE_NAME}Screen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}
```

## 变量自动推导（Android Studio设置）

为简化变量输入，建议在模板编辑器中添加以下表达式：
- PAGE_NAME_LOWERCASE = `${PAGE_NAME.toLowerCase()}`
- PAGE_NAME_UPPERCASE = `${PAGE_NAME.toUpperCase().replace(" ", "_")}`

## 使用示例

创建"关于我们"页面时：
- 文件名：AboutScreen
- PAGE_NAME：About
- CN_NAME：关于我们 