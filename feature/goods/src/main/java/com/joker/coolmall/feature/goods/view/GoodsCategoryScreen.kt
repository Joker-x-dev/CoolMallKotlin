package com.joker.coolmall.feature.goods.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.ShapeCircle
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.ui.component.appbar.SearchTopAppBar
import com.joker.coolmall.core.ui.component.goods.GoodsGridItem
import com.joker.coolmall.core.ui.component.network.BaseNetWorkListView
import com.joker.coolmall.core.ui.component.refresh.RefreshLayout
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.feature.goods.R
import com.joker.coolmall.feature.goods.viewmodel.GoodsCategoryViewModel

/**
 * 商品分类路由
 *
 * @param viewModel 商品分类 ViewModel
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun GoodsCategoryRoute(
    viewModel: GoodsCategoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listData by viewModel.listData.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val loadMoreState by viewModel.loadMoreState.collectAsState()

    var filtersVisible by remember { mutableStateOf(false) }

    SharedTransitionLayout {
        Box {
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
                onSearch = { searchText ->
                    // TODO: 实现搜索逻辑
                },
                filtersVisible = filtersVisible,
                onFiltersClick = { filtersVisible = true },
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
                    onDismiss = { filtersVisible = false }
                )
            }
        }
    }
}

/**
 * 商品分类界面
 *
 * @param uiState 收货地址列表UI状态
 * @param listData 收货地址列表数据
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
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
    filtersVisible: Boolean = false,
    onFiltersClick: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope
) {
    AppScaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                SearchTopAppBar(
                    onBackClick = onBackClick,
                    onSearch = onSearch
                )
                FilterBar(
                    filtersVisible = filtersVisible,
                    onFiltersClick = onFiltersClick,
                    sharedTransitionScope = sharedTransitionScope
                )
                SpaceVerticalSmall()
            }
        }
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
            )
        }
    }
}

/**
 * 商品分类内容视图
 */
@Composable
private fun GoodsCategoryContentView(
    data: List<Goods>,
    isRefreshing: Boolean,
    loadMoreState: LoadMoreState,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean,
) {
    RefreshLayout(
        isRefreshing = isRefreshing,
        loadMoreState = loadMoreState,
        onRefresh = onRefresh,
        onLoadMore = onLoadMore,
        shouldTriggerLoadMore = shouldTriggerLoadMore,
        isGrid = true,
        gridContent = {
            items(data.size) { index ->
                GoodsGridItem(goods = data[index])
            }
        }
    )
}

/**
 * 筛选栏
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun FilterBar(
    filtersVisible: Boolean,
    onFiltersClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope
) {
    Row(
//        horizontalArrangement = Arrangement.spacedBy(SpaceHorizontalLarge),
        modifier = Modifier
            .padding(horizontal = SpaceHorizontalMedium)
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
                            size = 18.dp,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                        AppText("筛选")
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(start = SpaceHorizontalLarge)
                .clip(ShapeCircle)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = SpaceHorizontalLarge)
                .height(32.dp),
            contentAlignment = Alignment.Center,
        ) {
            AppText("综合")
        }

        Box(
            modifier = Modifier
                .padding(start = SpaceHorizontalLarge)
                .clip(ShapeCircle)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = SpaceHorizontalLarge)
                .height(32.dp),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(28.dp)
            ) {
                AppText("销量")
                Column(
                    modifier = Modifier.padding(start = SpaceVerticalXSmall)
                ) {
                    CommonIcon(
                        resId = R.drawable.ic_up_triangle,
                        size = 8.dp,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                    )
                    CommonIcon(
                        resId = R.drawable.ic_down_triangle,
                        size = 8.dp,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(start = SpaceHorizontalLarge)
                .clip(ShapeCircle)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = SpaceHorizontalLarge)
                .height(32.dp),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AppText("价格")
                Column(
                    modifier = Modifier.padding(start = SpaceVerticalXSmall)
                ) {
                    CommonIcon(
                        resId = R.drawable.ic_up_triangle,
                        size = 8.dp,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                    )
                    CommonIcon(
                        resId = R.drawable.ic_down_triangle,
                        size = 8.dp,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                    )
                }
            }
        }
    }
}

/**
 * 筛选对话框
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun FilterDialog(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) {
                // capture click
            },
    ) {
        // 背景遮罩
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                ) {
                    onDismiss()
                },
        )

        with(sharedTransitionScope) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center)
                    .clip(MaterialTheme.shapes.medium)
                    .sharedBounds(
                        rememberSharedContentState("filter_button"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                        clipInOverlayDuringTransition = OverlayClip(MaterialTheme.shapes.medium),
                    )
                    .background(MaterialTheme.colorScheme.background)
                    .padding(24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                    ) { /* 阻止点击穿透 */ },
            ) {
                // 标题栏
                Row(
                    modifier = Modifier.padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppText(
                        text = "筛选",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.weight(1f)
                    )

                    Box(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .clickable { onDismiss() }
                            .padding(8.dp)
                    ) {
                        /*CommonIcon(
                            resId = R.drawable.ic_close,
                            size = 24.dp,
                            tint = MaterialTheme.colorScheme.onSurface
                        )*/
                        Text(
                            text = "取消",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                // 筛选内容
                Column {
                    AppText(
                        text = "排序方式",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // 排序选项
                    val sortOptions = listOf("综合", "销量", "价格")
                    sortOptions.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(MaterialTheme.shapes.small)
                                .clickable { /* TODO: 处理排序选择 */ }
                                .padding(vertical = 8.dp, horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AppText(
                                text = option,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    SpaceVerticalSmall()

                    AppText(
                        text = "价格区间",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // 价格选项
                    val priceOptions = listOf("不限", "0-50", "50-100", "100-200", "200以上")
                    priceOptions.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(MaterialTheme.shapes.small)
                                .clickable { /* TODO: 处理价格选择 */ }
                                .padding(vertical = 8.dp, horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AppText(
                                text = option,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * 商品分类界面浅色主题预览
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