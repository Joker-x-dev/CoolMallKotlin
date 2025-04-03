package com.joker.coolmall.feature.main.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.feature.main.component.BottomNavigationBar
import com.joker.coolmall.feature.main.model.TopLevelDestination
import com.joker.coolmall.feature.main.viewmodel.MainViewModel
import kotlinx.coroutines.launch

/**
 * 主界面路由入口
 */
@Composable
internal fun MainRoute() {
    val viewModel: MainViewModel = hiltViewModel()
    MainScreen(viewModel)
}

/**
 * 主界面
 * 包含底部导航栏和四个主要页面（首页、分类、购物车、我的）
 */
@Composable
internal fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    // 从ViewModel获取当前导航状态
    val currentDestination by viewModel.currentDestination.collectAsState()
    val currentPageIndex by viewModel.currentPageIndex.collectAsState()
    
    // 协程作用域
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 创建分页器状态
        val pageState = rememberPagerState(
            initialPage = currentPageIndex
        ) {
            TopLevelDestination.entries.size
        }

        // 水平分页器
        HorizontalPager(
            userScrollEnabled = false,
            state = pageState, 
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page: Int ->
            when (page) {
                0 -> HomeRoute()
                1 -> CategoryRoute()
                2 -> CartRoute()
                3 -> MeRoute()
            }
        }

        // 底部导航栏
        BottomNavigationBar(
            destinations = TopLevelDestination.entries,
            onNavigateToDestination = { index ->
                // 更新ViewModel中的状态
                viewModel.updateDestination(index)
                scope.launch {
                    pageState.scrollToPage(index)
                }
            },
            currentDestination = currentDestination,
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
