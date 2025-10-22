package com.joker.coolmall.feature.launch.view

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.component.CenterColumn
import com.joker.coolmall.core.designsystem.component.CenterRow
import com.joker.coolmall.core.designsystem.component.FullScreenColumn
import com.joker.coolmall.core.designsystem.component.SpaceBetweenRow
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXXLarge
import com.joker.coolmall.core.designsystem.theme.SpacePaddingLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXXLarge
import com.joker.coolmall.core.ui.component.button.AppButton
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.feature.launch.R
import com.joker.coolmall.feature.launch.model.GuidePageData
import com.joker.coolmall.feature.launch.viewmodel.GuideViewModel
import kotlinx.coroutines.launch

/**
 * 引导页路由
 *
 * @param viewModel 引导页 ViewModel
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun GuideRoute(
    viewModel: GuideViewModel = hiltViewModel()
) {
    // 协程作用域
    val scope = rememberCoroutineScope()

    // 从ViewModel获取状态
    val currentPageIndex by viewModel.currentPageIndex.collectAsState()

    GuideScreen(
        guidePages = viewModel.guidePages,
        currentPageIndex = currentPageIndex,
        isLastPage = viewModel.isLastPage(),
        onPageChanged = viewModel::updatePageIndex,
        onNextClick = { pagerState ->
            if (viewModel.handleNextClick()) {
                // 需要切换到下一页
                scope.launch {
                    val nextPage = viewModel.getNextPageIndex()
                    pagerState.animateScrollToPage(nextPage)
                }
            }
        },
        onSkipClick = viewModel::skipGuide
    )
}

/**
 * 引导页界面
 *
 * @param guidePages 引导页数据列表
 * @param currentPageIndex 当前页面索引
 * @param isLastPage 是否为最后一页
 * @param onPageChanged 页面变化回调
 * @param onNextClick 下一页按钮点击回调
 * @param onSkipClick 跳过按钮点击回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GuideScreen(
    guidePages: List<GuidePageData> = emptyList(),
    currentPageIndex: Int = 0,
    isLastPage: Boolean = false,
    onPageChanged: (Int) -> Unit = {},
    onNextClick: (androidx.compose.foundation.pager.PagerState) -> Unit = {},
    onSkipClick: () -> Unit = {}
) {
    // 创建分页器状态
    val pagerState = rememberPagerState(
        initialPage = currentPageIndex
    ) {
        guidePages.size
    }

    // 监听页面变化
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onPageChanged(page)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->
        GuideContentView(
            guidePages = guidePages,
            currentPageIndex = currentPageIndex,
            isLastPage = isLastPage,
            pagerState = pagerState,
            onNextClick = onNextClick,
            onSkipClick = onSkipClick,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

/**
 * 引导页内容视图
 *
 * @param guidePages 引导页数据列表
 * @param currentPageIndex 当前页面索引
 * @param isLastPage 是否为最后一页
 * @param pagerState 分页器状态
 * @param onNextClick 下一页按钮点击回调
 * @param onSkipClick 跳过按钮点击回调
 * @param modifier 修饰符
 * @author Joker.X
 */
@Composable
private fun GuideContentView(
    guidePages: List<GuidePageData>,
    currentPageIndex: Int,
    isLastPage: Boolean,
    pagerState: androidx.compose.foundation.pager.PagerState,
    onNextClick: (androidx.compose.foundation.pager.PagerState) -> Unit,
    onSkipClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // 顶部跳过按钮
        SpaceBetweenRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(1.dp))
            TextButton(
                onClick = onSkipClick,
                modifier = Modifier.padding(SpacePaddingLarge)
            ) {
                AppText(
                    text = stringResource(R.string.guide_skip),
                    type = TextType.TERTIARY,
                    size = TextSize.BODY_MEDIUM
                )
            }
        }

        // 主要内容区域
        FullScreenColumn(
            modifier = Modifier,
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpaceVerticalXXLarge()
            SpaceVerticalLarge()

            // 水平分页器
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                if (page < guidePages.size) {
                    GuidePageContent(
                        pageData = guidePages[page],
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            SpaceVerticalXXLarge()

            // 页面指示器
            PageIndicator(
                pageCount = guidePages.size,
                currentPage = currentPageIndex,
                modifier = Modifier.padding(vertical = SpaceVerticalLarge)
            )

            // 下一页按钮 - 带宽度变化动画
            val buttonText =
                if (isLastPage) stringResource(R.string.guide_start_experience) else stringResource(
                    R.string.guide_continue
                )
            val animatedButtonWidth by animateDpAsState(
                targetValue = if (isLastPage) 160.dp else 120.dp,
                animationSpec = tween(300),
                label = "button_width"
            )

            AppButton(
                text = buttonText,
                onClick = { onNextClick(pagerState) },
                fullWidth = false,
                modifier = Modifier.width(animatedButtonWidth)
            )

            SpaceVerticalXXLarge()
        }
    }
}

/**
 * 引导页页面内容
 *
 * @param pageData 页面数据
 * @param modifier 修饰符
 * @author Joker.X
 */
@Composable
private fun GuidePageContent(
    pageData: GuidePageData,
    modifier: Modifier = Modifier
) {
    CenterColumn(modifier = modifier) {

        Image(
            painter = painterResource(id = pageData.image),
            contentDescription = "Guide",
            modifier = Modifier
                .size(240.dp)
        )

        SpaceVerticalXXLarge()

        // 标题 - 添加动画效果
        AppText(
            text = pageData.title,
            size = TextSize.DISPLAY_MEDIUM,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        SpaceVerticalXLarge()

        // 描述
        AppText(
            text = pageData.description,
            type = TextType.SECONDARY,
            size = TextSize.BODY_LARGE,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            modifier = Modifier.padding(horizontal = SpaceHorizontalXXLarge)
        )
    }
}

/**
 * 页面指示器
 *
 * @param pageCount 总页数
 * @param currentPage 当前页面索引
 * @param modifier 修饰符
 * @author Joker.X
 */
@Composable
private fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    CenterRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = SpacePaddingLarge),
        fillMaxWidth = false
    ) {
        repeat(pageCount) { index ->
            val isSelected = index == currentPage
            val animatedWidth by animateDpAsState(
                targetValue = if (isSelected) 24.dp else 8.dp,
                animationSpec = tween(300),
                label = "indicator_width"
            )
            val animatedAlpha by animateFloatAsState(
                targetValue = if (isSelected) 1f else 0.4f,
                animationSpec = tween(300),
                label = "indicator_alpha"
            )

            Box(
                modifier = Modifier
                    .width(animatedWidth)
                    .height(8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = animatedAlpha),
                        shape = RoundedCornerShape(4.dp)
                    )
            )

            if (index < pageCount - 1) {
                Box(modifier = Modifier.size(width = SpaceHorizontalMedium, height = 1.dp))
            }
        }
    }
}


/**
 * 引导页界面浅色主题预览
 *
 * @author Joker.X
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
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun GuideScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        GuideScreen()
    }
}