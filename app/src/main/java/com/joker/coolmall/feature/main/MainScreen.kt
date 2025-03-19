package com.joker.coolmall.feature.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.joker.coolmall.feature.cart.CartRoute
import com.joker.coolmall.feature.category.CategoryRoute
import com.joker.coolmall.feature.home.HomeRoute
import com.joker.coolmall.feature.main.component.BottomNavigationBar
import com.joker.coolmall.feature.me.MeRoute
import com.joker.coolmall.navigation.TopLevelDestination
import kotlinx.coroutines.launch

/**
 * 主界面路由入口
 */
@Composable
internal fun MainRoute() {
    MainScreen()
}

/**
 * 主界面
 * 包含底部导航栏和四个主要页面（首页、分类、购物车、我的）
 */
@Composable
internal fun MainScreen() {
    // 当前选中的界面名称
    var currentDestination by rememberSaveable {
        mutableStateOf(TopLevelDestination.HOME.route)
    }

    // 协程作用域
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 创建分页器状态
        val pageState = rememberPagerState {
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
            onNavigateToDestination = {
                currentDestination = TopLevelDestination.entries[it].route
                scope.launch {
                    pageState.scrollToPage(it)
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
