package com.joker.coolmall.feature.goods.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.ShapeCircle
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingXSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.ui.component.appbar.SearchTopAppBar
import com.joker.coolmall.core.ui.component.goods.GoodsGridItem
import com.joker.coolmall.core.ui.component.goods.GoodsListItem
import com.joker.coolmall.core.ui.component.network.BaseNetWorkListView
import com.joker.coolmall.core.ui.component.refresh.RefreshLayout
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.feature.goods.R
import com.joker.coolmall.feature.goods.component.FilterDialog
import com.joker.coolmall.feature.goods.model.SortState
import com.joker.coolmall.feature.goods.model.SortType
import com.joker.coolmall.feature.goods.viewmodel.GoodsCategoryViewModel
import com.joker.coolmall.core.ui.R as CoreUiR

/**
 * 商品分类路由
 *
 * @param viewModel 商品分类 ViewModel
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun GoodsCategoryRoute(
    viewModel: GoodsCategoryViewModel = hiltViewModel()
) {
    // 网络请求UI状态
    val uiState by viewModel.uiState.collectAsState()
    // 分类数据UI状态
    val categoryUiState by viewModel.categoryUiState.collectAsState()
    // 商品列表数据
    val listData by viewModel.listData.collectAsState()
    // 是否正在刷新
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    // 加载更多状态
    val loadMoreState by viewModel.loadMoreState.collectAsState()
    // 筛选弹窗是否可见
    val filtersVisible by viewModel.filtersVisible.collectAsState()
    // 选中的分类ID列表
    val selectedCategoryIds by viewModel.selectedCategoryIds.collectAsState()
    // 最低价格
    val minPrice by viewModel.minPrice.collectAsState()
    // 最高价格
    val maxPrice by viewModel.maxPrice.collectAsState()
    // 当前排序类型
    val currentSortType by viewModel.currentSortType.collectAsState()
    // 当前排序状态
    val currentSortState by viewModel.currentSortState.collectAsState()
    // 是否为网格布局
    val isGridLayout by viewModel.isGridLayout.collectAsState()

    SharedTransitionLayout {
        GoodsCategoryScreen(
            uiState = uiState,
            listData = listData,
            isRefreshing = isRefreshing,
            loadMoreState = loadMoreState,
            onRefresh = viewModel::onRefresh,
            onLoadMore = viewModel::onLoadMore,
            shouldTriggerLoadMore = viewModel::shouldTriggerLoadMore,
            onBackClick = viewModel::navigateBack,
            onRetry = viewModel::retryRequest,
            onSearch = viewModel::onSearch,
            initialSearchText = viewModel.getCurrentSearchKeyword(),
            filtersVisible = filtersVisible,
            onFiltersClick = viewModel::showFilters,
            currentSortType = currentSortType,
            currentSortState = currentSortState,
            onSortClick = viewModel::onSortClick,
            toGoodsDetail = viewModel::toGoodsDetailPage,
            isGridLayout = isGridLayout,
            onToggleLayout = viewModel::toggleLayoutMode,
            sharedTransitionScope = this@SharedTransitionLayout
        )

        AnimatedVisibility(
            visible = filtersVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            FilterDialog(
                sharedTransitionScope = this@SharedTransitionLayout,
                animatedVisibilityScope = this@AnimatedVisibility,
                onDismiss = viewModel::hideFilters,
                uiState = categoryUiState,
                selectedCategoryIds = selectedCategoryIds,
                minPrice = minPrice,
                maxPrice = maxPrice,
                onApplyFilters = viewModel::applyFilters,
                onResetFilters = viewModel::resetFilters,
            )
        }
    }
}

/**
 * 商品分类界面
 *
 * @param uiState 网络请求UI状态
 * @param listData 商品列表数据
 * @param isRefreshing 是否正在刷新
 * @param loadMoreState 加载更多状态
 * @param onRefresh 下拉刷新回调
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 是否应该触发加载更多的判断函数
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 * @param onSearch 搜索回调
 * @param initialSearchText 初始搜索文本
 * @param filtersVisible 筛选弹窗是否可见
 * @param onFiltersClick 筛选按钮点击回调
 * @param currentSortType 当前排序类型
 * @param currentSortState 当前排序状态
 * @param onSortClick 排序按钮点击回调
 * @param toGoodsDetail 跳转到商品详情回调
 * @param isGridLayout 是否为网格布局
 * @param onToggleLayout 切换布局模式回调
 * @param sharedTransitionScope 共享转场作用域
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun GoodsCategoryScreen(
    uiState: BaseNetWorkListUiState = BaseNetWorkListUiState.Loading,
    listData: List<Goods> = emptyList(),
    isRefreshing: Boolean = false,
    loadMoreState: LoadMoreState = LoadMoreState.Success,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean = { _, _ -> false },
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {},
    onSearch: (String) -> Unit = {},
    initialSearchText: String = "",
    filtersVisible: Boolean = false,
    onFiltersClick: () -> Unit = {},
    currentSortType: SortType = SortType.COMPREHENSIVE,
    currentSortState: SortState = SortState.NONE,
    onSortClick: (SortType) -> Unit = {},
    toGoodsDetail: (Long) -> Unit = {},
    isGridLayout: Boolean = true,
    onToggleLayout: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope
) {
    // 创建TopAppBar的滚动行为
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    AppScaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                SearchTopAppBar(
                    onBackClick = onBackClick,
                    onSearch = onSearch,
                    initialSearchText = initialSearchText,
                    scrollBehavior = scrollBehavior,
                    actions = {
                        LayoutToggleButton(
                            isGridLayout = isGridLayout,
                            onToggleLayout = onToggleLayout,
                            modifier = Modifier.padding(end = SpaceHorizontalSmall)
                        )
                    }
                )
                SpaceVerticalSmall()

                FilterBar(
                    filtersVisible = filtersVisible,
                    onFiltersClick = onFiltersClick,
                    currentSortType = currentSortType,
                    currentSortState = currentSortState,
                    onSortClick = onSortClick,
                    sharedTransitionScope = sharedTransitionScope,
                    scrollBehavior = scrollBehavior,
                    isGridLayout = isGridLayout,
                    onToggleLayout = onToggleLayout
                )
                SpaceVerticalSmall()
            }
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        BaseNetWorkListView(
            uiState = uiState,
            onRetry = onRetry
        ) {
            GoodsCategoryContentView(
                data = listData,
                isRefreshing = isRefreshing,
                loadMoreState = loadMoreState,
                onRefresh = onRefresh,
                onLoadMore = onLoadMore,
                shouldTriggerLoadMore = shouldTriggerLoadMore,
                toGoodsDetail = toGoodsDetail,
                isGridLayout = isGridLayout
            )
        }
    }
}

/**
 * 商品分类内容视图
 *
 * @param data 商品列表数据
 * @param isRefreshing 是否正在刷新
 * @param loadMoreState 加载更多状态
 * @param onRefresh 下拉刷新回调
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 是否应该触发加载更多的判断函数
 * @param toGoodsDetail 跳转到商品详情回调
 * @param isGridLayout 是否为网格布局
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GoodsCategoryContentView(
    data: List<Goods>,
    isRefreshing: Boolean,
    loadMoreState: LoadMoreState,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean,
    toGoodsDetail: (Long) -> Unit,
    isGridLayout: Boolean
) {
    RefreshLayout(
        isRefreshing = isRefreshing,
        loadMoreState = loadMoreState,
        onRefresh = onRefresh,
        onLoadMore = onLoadMore,
        shouldTriggerLoadMore = shouldTriggerLoadMore,
        isGrid = isGridLayout,
        gridContent = {
            items(data.size) { index ->
                GoodsGridItem(goods = data[index], onClick = {
                    toGoodsDetail(data[index].id)
                })
            }
        }
    ) {
        items(data.size) { index ->
            GoodsListItem(goods = data[index], onClick = {
                toGoodsDetail(data[index].id)
            })
        }
    }
}

/**
 * 筛选栏
 *
 * @param filtersVisible 筛选弹窗是否可见
 * @param onFiltersClick 筛选按钮点击回调
 * @param currentSortType 当前排序类型
 * @param currentSortState 当前排序状态
 * @param onSortClick 排序按钮点击回调
 * @param sharedTransitionScope 共享转场作用域
 * @param scrollBehavior 滚动行为
 * @param isGridLayout 是否为网格布局
 * @param onToggleLayout 切换布局模式回调
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun FilterBar(
    filtersVisible: Boolean,
    onFiltersClick: () -> Unit,
    currentSortType: SortType,
    currentSortState: SortState,
    onSortClick: (SortType) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    scrollBehavior: TopAppBarScrollBehavior,
    isGridLayout: Boolean,
    onToggleLayout: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = SpaceHorizontalMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {

        with(sharedTransitionScope) {
            AnimatedVisibility(visible = !filtersVisible) {
                Box(
                    modifier = Modifier
                        .clip(ShapeCircle)
                        .background(MaterialTheme.colorScheme.background)
                        .sharedBounds(
                            rememberSharedContentState("filter_button"),
                            animatedVisibilityScope = this@AnimatedVisibility,
                            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds
                        )
                        .clickable { onFiltersClick() }
                        .padding(horizontal = SpaceHorizontalLarge)
                        .height(32.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        CommonIcon(
                            resId = R.drawable.ic_filter,
                            size = 14.dp,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                        AppText(
                            stringResource(R.string.filter),
                            size = TextSize.BODY_MEDIUM
                        )
                    }
                }
            }
        }

        FilterButton(
            isSelected = currentSortType == SortType.COMPREHENSIVE,
            onClick = { onSortClick(SortType.COMPREHENSIVE) }
        ) {
            AppText(
                text = stringResource(R.string.comprehensive),
                size = TextSize.BODY_MEDIUM,
                color = if (currentSortType == SortType.COMPREHENSIVE)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurface
            )
        }

        SortButtonWithArrows(
            text = stringResource(R.string.sales),
            sortType = SortType.SALES,
            currentSortType = currentSortType,
            currentSortState = currentSortState,
            onSortClick = onSortClick
        )

        SortButtonWithArrows(
            text = stringResource(R.string.price),
            sortType = SortType.PRICE,
            currentSortType = currentSortType,
            currentSortState = currentSortState,
            onSortClick = onSortClick
        )

        Spacer(modifier = Modifier.weight(1f))

        AnimatedVisibility(
            visible = scrollBehavior.state.collapsedFraction >= 0.8f,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            LayoutToggleButton(
                isGridLayout = isGridLayout,
                onToggleLayout = onToggleLayout
            )
        }
    }
}

/**
 * 通用筛选按钮组件
 *
 * @param isSelected 是否选中
 * @param onClick 点击回调
 * @param content 内容
 * @author Joker.X
 */
@Composable
private fun FilterButton(
    isSelected: Boolean,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(start = SpaceHorizontalSmall)
            .clip(ShapeCircle)
            .background(
                if (isSelected)
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                else
                    MaterialTheme.colorScheme.background
            )
            .clickable { onClick() }
            .padding(horizontal = SpaceHorizontalLarge)
            .height(32.dp),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

/**
 * 带箭头的排序按钮组件
 *
 * @param text 按钮文本
 * @param sortType 排序类型
 * @param currentSortType 当前排序类型
 * @param currentSortState 当前排序状态
 * @param onSortClick 排序按钮点击回调
 * @author Joker.X
 */
@Composable
private fun SortButtonWithArrows(
    text: String,
    sortType: SortType,
    currentSortType: SortType,
    currentSortState: SortState,
    onSortClick: (SortType) -> Unit
) {
    val isSelected = currentSortType == sortType && currentSortState != SortState.NONE

    FilterButton(
        isSelected = isSelected,
        onClick = { onSortClick(sortType) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(32.dp)
        ) {
            AppText(
                text = text,
                size = TextSize.BODY_MEDIUM,
                color = if (isSelected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurface
            )
            SortArrows(
                sortType = sortType,
                currentSortType = currentSortType,
                currentSortState = currentSortState
            )
        }
    }
}

/**
 * 排序箭头组件
 *
 * @param sortType 排序类型
 * @param currentSortType 当前排序类型
 * @param currentSortState 当前排序状态
 * @author Joker.X
 */
@Composable
private fun SortArrows(
    sortType: SortType,
    currentSortType: SortType,
    currentSortState: SortState
) {
    Column(
        modifier = Modifier.padding(start = SpaceVerticalXSmall)
    ) {
        CommonIcon(
            resId = R.drawable.ic_up_triangle,
            size = 6.dp,
            tint = if (currentSortType == sortType && currentSortState == SortState.ASC)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
        )
        CommonIcon(
            resId = R.drawable.ic_down_triangle,
            size = 6.dp,
            tint = if (currentSortType == sortType && currentSortState == SortState.DESC)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
        )
    }
}

/**
 * 布局切换按钮
 *
 * @param isGridLayout 是否为网格布局
 * @param onToggleLayout 切换布局模式回调
 * @param modifier Modifier
 * @author Joker.X
 */
@Composable
private fun LayoutToggleButton(
    isGridLayout: Boolean,
    onToggleLayout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(ShapeCircle)
            .clickable { onToggleLayout() }
            .padding(SpacePaddingXSmall)
    ) {
        CommonIcon(
            resId = if (isGridLayout) CoreUiR.drawable.ic_menu_list else CoreUiR.drawable.ic_menu,
            size = 24.dp,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * 商品分类界面浅色主题预览
 *
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
internal fun GoodsCategoryScreenPreview() {
    AppTheme {
        SharedTransitionLayout {
            GoodsCategoryScreen(
                uiState = BaseNetWorkListUiState.Success,
                sharedTransitionScope = this
            )
        }
    }
}

/**
 * 商品分类界面深色主题预览
 *
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
internal fun GoodsCategoryScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        SharedTransitionLayout {
            GoodsCategoryScreen(
                uiState = BaseNetWorkListUiState.Success,
                sharedTransitionScope = this
            )
        }
    }
}