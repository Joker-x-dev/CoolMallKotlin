package com.joker.coolmall.feature.main.view

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.feature.main.component.BottomNavigationBar
import com.joker.coolmall.feature.main.model.TopLevelDestination
import com.joker.coolmall.feature.main.viewmodel.MainViewModel
import kotlinx.coroutines.launch

/**
 * 主界面路由入口
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun MainRoute(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: MainViewModel = hiltViewModel()
) {
    // 从ViewModel获取当前导航状态
    val currentDestination by viewModel.currentDestination.collectAsState()
    val currentPageIndex by viewModel.currentPageIndex.collectAsState()
    val isAnimatingPageChange by viewModel.isAnimatingPageChange.collectAsState()

    MainScreen(
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        currentDestination = currentDestination,
        currentPageIndex = currentPageIndex,
        isAnimatingPageChange = isAnimatingPageChange,
        onPageChanged = viewModel::updatePageIndex,
        onNavigationItemSelected = viewModel::updateDestination,
        onPageOffsetChanged = viewModel::previewPageChange,
        onAnimationCompleted = viewModel::notifyAnimationCompleted
    )
}

/**
 * 主界面
 * 包含底部导航栏和四个主要页面（首页、分类、购物车、我的）
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun MainScreen(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    currentDestination: String = TopLevelDestination.HOME.route,
    currentPageIndex: Int = 0,
    isAnimatingPageChange: Boolean = false,
    onPageChanged: (Int) -> Unit = {},
    onNavigationItemSelected: (Int) -> Unit = {},
    onPageOffsetChanged: (Int) -> Unit = {},
    onAnimationCompleted: () -> Unit = {}
) {
    // 协程作用域
    val scope = rememberCoroutineScope()

    // 创建分页器状态
    val pageState = rememberPagerState(
        initialPage = currentPageIndex
    ) {
        TopLevelDestination.entries.size
    }

    // 处理页面状态变化
    HandlePageStateChanges(
        pageState = pageState,
        isAnimatingPageChange = isAnimatingPageChange,
        onPageChanged = onPageChanged,
        onPageOffsetChanged = onPageOffsetChanged,
        onAnimationCompleted = onAnimationCompleted
    )

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
                    // 执行页面滚动动画
                    scope.launch {
                        pageState.animateScrollToPage(index)
                    }
                },
                currentDestination = currentDestination,
                modifier = Modifier
            )
        }
    ) { paddingValues ->
        // 水平分页器
        HorizontalPager(
            userScrollEnabled = true,
            state = pageState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) { page: Int ->
            when (page) {
                0 -> HomeRoute()
                1 -> CategoryRoute()
                2 -> CartRoute()
                3 -> MeRoute(
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope
                )
            }
        }
    }
}

/**
 * 处理页面状态变化的副作用
 */
@Composable
private fun HandlePageStateChanges(
    pageState: PagerState,
    isAnimatingPageChange: Boolean,
    onPageChanged: (Int) -> Unit,
    onPageOffsetChanged: (Int) -> Unit,
    onAnimationCompleted: () -> Unit
) {
    // 监听分页器当前页面变化
    LaunchedEffect(pageState.currentPage) {
        // 当页面已经切换到新页面，立即更新导航状态
        if (!isAnimatingPageChange) {
            onPageChanged(pageState.currentPage)
        }
    }

    // 监听页面偏移变化，提前预测用户滑动的目标页面
    LaunchedEffect(pageState.currentPageOffsetFraction) {
        // 只处理用户手势滑动，而不是程序触发的动画
        if (!isAnimatingPageChange && pageState.isScrollInProgress) {
            val currentOffset = pageState.currentPageOffsetFraction
            // 如果滑动超过一半，提前更新UI状态
            if (kotlin.math.abs(currentOffset) > 0.5f) {
                val targetPage = if (currentOffset > 0) {
                    pageState.currentPage + 1
                } else if (currentOffset < 0) {
                    pageState.currentPage - 1
                } else {
                    pageState.currentPage
                }
                // 确保目标页面在有效范围内
                if (targetPage in 0 until TopLevelDestination.entries.size) {
                    // 更新UI但不改变动画状态
                    onPageOffsetChanged(targetPage)
                }
            } else if (kotlin.math.abs(currentOffset) < 0.1f) {
                // 如果滑动很小，回到当前页面
                onPageOffsetChanged(pageState.currentPage)
            }
        }
    }

    // 监听滑动动画完成
    LaunchedEffect(pageState.isScrollInProgress) {
        if (!pageState.isScrollInProgress && isAnimatingPageChange) {
            // 当页面滑动动画结束，通知完成
            onAnimationCompleted()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
//        MainScreen()
    }
}
