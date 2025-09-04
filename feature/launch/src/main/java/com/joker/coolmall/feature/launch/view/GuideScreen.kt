/**
 * 引导页界面
 *
 * @author Joker.X
 */
package com.joker.coolmall.feature.launch.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.feature.launch.viewmodel.GuideViewModel

/**
 * 引导页路由
 *
 * @param viewModel 引导页 ViewModel
 */
@Composable
internal fun GuideRoute(
    viewModel: GuideViewModel = hiltViewModel()
) {
    GuideScreen(
        onBackClick = viewModel::navigateBack,
    )
}

/**
 * 引导页界面
 *
 * @param onBackClick 返回按钮回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GuideScreen(
    onBackClick: () -> Unit = {},
) {
    AppScaffold(
        onBackClick = onBackClick
    ) {
        GuideContentView()
    }
}

/**
 * 引导页内容视图
 */
@Composable
private fun GuideContentView() {
    AppText(
        text = "引导页",
        size = TextSize.TITLE_MEDIUM
    )
}

/**
 * 引导页界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun GuideScreenPreview() {
    AppTheme {
        GuideScreen()
    }
}

/**
 * 引导页界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun GuideScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        GuideScreen()
    }
}