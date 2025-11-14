package com.joker.coolmall.feature.main.view

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.feature.main.component.BottomNavigationBar
import com.joker.coolmall.feature.main.model.TopLevelDestination
import com.joker.coolmall.feature.main.viewmodel.MainViewModel
import kotlinx.coroutines.launch

/**
 * 主界面路由入口
 *
 * @param sharedTransitionScope 共享转场作用域
 * @param animatedContentScope 动画内容作用域
 * @param viewModel 主界面ViewModel
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun MainRoute(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: MainViewModel = hiltViewModel()
) {
    // 当前页面索引
    val currentPageIndex by viewModel.currentPageIndex.collectAsState()

    MainScreen(
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        currentPageIndex = currentPageIndex,
        onPageChanged = viewModel::updatePageIndex,
        onNavigationItemSelected = viewModel::updateDestination
    )
}

/**
 * 主界面
 * 包含底部导航栏和四个主要页面（首页、分类、购物车、我的）
 *
 * @param sharedTransitionScope 共享转场作用域
 * @param animatedContentScope 动画内容作用域
 * @param currentPageIndex 当前页面索引
 * @param onPageChanged 页面改变回调
 * @param onNavigationItemSelected 导航项选中回调
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun MainScreen(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    currentPageIndex: Int = 0,
    onPageChanged: (Int) -> Unit = {},
    onNavigationItemSelected: (Int) -> Unit = {}
) {
    // 协程作用域
    val scope = rememberCoroutineScope()

    // 创建分页器状态
    val pageState = rememberPagerState(
        initialPage = currentPageIndex
    ) {
        TopLevelDestination.entries.size
    }

    // 监听分页器当前页面变化
    LaunchedEffect(pageState.currentPage) {
        onPageChanged(pageState.currentPage)
    }

    Scaffold(
        // 排除顶部导航栏边距
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.statusBars),
        bottomBar = {
            BottomNavigationBar(
                destinations = TopLevelDestination.entries,
                onNavigateToDestination = { index ->
                    // 通知选择了新的导航项
                    onNavigationItemSelected(index)
                    scope.launch {
                        pageState.scrollToPage(index)
                    }
                },
                currentPageIndex = currentPageIndex,
                modifier = Modifier
            )
        }
    ) { paddingValues ->
        MainScreenContentView(
            pageState = pageState,
            paddingValues = paddingValues,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = animatedContentScope
        )
    }
}

/**
 * 主界面内容视图
 *
 * @param pageState 分页器状态
 * @param paddingValues 内边距
 * @param sharedTransitionScope 共享转场作用域
 * @param animatedContentScope 动画内容作用域
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun MainScreenContentView(
    pageState: PagerState,
    paddingValues: PaddingValues,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
) {
    HorizontalPager(
        state = pageState,
        modifier = Modifier
            .padding(paddingValues)
    ) { page: Int ->
        when (page) {
            0 -> HomeRoute(
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope
            )

            1 -> CategoryRoute()
            2 -> CartRoute()
            3 -> MeRoute(
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope
            )
        }
    }
}

/**
 * 主界面浅色主题预览
 *
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}
