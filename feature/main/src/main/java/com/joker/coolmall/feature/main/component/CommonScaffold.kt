package com.joker.coolmall.feature.main.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll

/**
 * 通用脚手架组件
 *
 * @param topBar 顶部栏
 * @param bottomBar 底部栏
 * @param scrollBehavior 滚动行为
 * @param content 内容
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CommonScaffold(
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
        // 排除底部导航栏边距
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars),
        modifier = scrollBehavior?.nestedScrollConnection?.let {
            Modifier.nestedScroll(it)
        } ?: Modifier
    ) { paddingValues ->
        content(paddingValues)
    }
}