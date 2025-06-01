package com.joker.coolmall.feature.goods.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.designsystem.component.SpaceEvenlyRow
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.ShapeCircle
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXLarge
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
@Composable
internal fun GoodsCategoryRoute(
    viewModel: GoodsCategoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listData by viewModel.listData.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val loadMoreState by viewModel.loadMoreState.collectAsState()

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
        }
    )
}

/**
 * 商品分类界面
 *
 * @param uiState 收货地址列表UI状态
 * @param listData 收货地址列表数据
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
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
    onSearch: (String) -> Unit = {}
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
                SpaceEvenlyRow {
                    Box(
                        modifier = Modifier
                            .clip(ShapeCircle)
                            .background(MaterialTheme.colorScheme.background)
                            .clickable { }
                            .height(34.dp)
                            .padding(horizontal = SpaceHorizontalXLarge),
                        contentAlignment = Alignment.Center,
                    ) {
                        AppText("综合")
                    }

                    Box(
                        modifier = Modifier
                            .clip(ShapeCircle)
                            .background(MaterialTheme.colorScheme.background)
                            .clickable { }
                            .height(34.dp)
                            .padding(horizontal = SpaceHorizontalXLarge),
                        contentAlignment = Alignment.Center,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
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
                            .clip(ShapeCircle)
                            .background(MaterialTheme.colorScheme.background)
                            .clickable { }
                            .height(34.dp)
                            .padding(horizontal = SpaceHorizontalXLarge),
                        contentAlignment = Alignment.Center,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
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

                    Box(
                        modifier = Modifier
                            .clip(ShapeCircle)
                            .background(MaterialTheme.colorScheme.background)
                            .clickable { }
                            .height(34.dp)
                            .padding(horizontal = SpaceHorizontalXLarge),
                        contentAlignment = Alignment.Center,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            AppText("筛选")
                            CommonIcon(
//                                modifier = Modifier.padding(start = SpaceVerticalXSmall),
                                resId = R.drawable.ic_filter,
                                size = 16.dp,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
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
 * 商品分类界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun GoodsCategoryScreenPreview() {
    AppTheme {
        GoodsCategoryScreen(
            uiState = BaseNetWorkListUiState.Success
        )
    }
}

/**
 * 商品分类界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun GoodsCategoryScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        GoodsCategoryScreen(
            uiState = BaseNetWorkListUiState.Success
        )
    }
}