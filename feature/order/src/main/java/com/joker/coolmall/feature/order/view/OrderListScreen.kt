package com.joker.coolmall.feature.order.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.designsystem.component.CenterColumn
import com.joker.coolmall.core.designsystem.component.EndRow
import com.joker.coolmall.core.designsystem.component.HorizontalScroll
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.ui.component.button.AppButtonBordered
import com.joker.coolmall.core.ui.component.button.ButtonSize
import com.joker.coolmall.core.ui.component.button.ButtonType
import com.joker.coolmall.core.ui.component.divider.WeDivider
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.network.BaseNetWorkListView
import com.joker.coolmall.core.ui.component.refresh.RefreshLayout
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.PriceText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.feature.order.R
import com.joker.coolmall.feature.order.model.OrderStatus
import com.joker.coolmall.feature.order.viewmodel.OrderListViewModel

/**
 * 订单列表路由
 *
 * @param viewModel 订单列表ViewModel
 */
@Composable
internal fun OrderListRoute(
    viewModel: OrderListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val orderList by viewModel.listData.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val loadMoreState by viewModel.loadMoreState.collectAsState()
    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()

    OrderListScreen(
        uiState = uiState,
        orderList = orderList,
        isRefreshing = isRefreshing,
        loadMoreState = loadMoreState,
        selectedTabIndex = selectedTabIndex,
        onTabSelected = viewModel::updateSelectedTab,
        onBackClick = viewModel::navigateBack,
        onRetry = viewModel::retryRequest,
        onRefresh = viewModel::onRefresh,
        onLoadMore = viewModel::onLoadMore,
        shouldTriggerLoadMore = viewModel::shouldTriggerLoadMore
    )
}

/**
 * 订单列表页面
 *
 * @param uiState UI状态
 * @param orderList 订单列表数据
 * @param isRefreshing 是否正在刷新
 * @param loadMoreState 加载更多状态
 * @param selectedTabIndex 当前选中的标签
 * @param onTabSelected 标签选择回调
 * @param onBackClick 返回回调
 * @param onRefresh 刷新回调
 * @param onLoadMore 加载更多回调
 * @param onRetry 重试请求回调
 * @param shouldTriggerLoadMore 是否应触发加载更多的判断函数
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrderListScreen(
    uiState: BaseNetWorkListUiState = BaseNetWorkListUiState.Loading,
    orderList: List<Order> = emptyList(),
    isRefreshing: Boolean = false,
    loadMoreState: LoadMoreState = LoadMoreState.Success,
    selectedTabIndex: Int = 0,
    onTabSelected: (Int) -> Unit = {},
    onBackClick: () -> Unit = {},
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    onRetry: () -> Unit = {},
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean = { _, _ -> false }
) {
    AppScaffold(
        title = R.string.order_list,
        onBackClick = onBackClick
    ) {
        BaseNetWorkListView(
            uiState = uiState,
            onRetry = onRetry
        ) {
            OrderListContentView(
                orderList = orderList,
                isRefreshing = isRefreshing,
                loadMoreState = loadMoreState,
                selectedTabIndex = selectedTabIndex,
                onTabSelected = onTabSelected,
                onRefresh = onRefresh,
                onLoadMore = onLoadMore,
                shouldTriggerLoadMore = shouldTriggerLoadMore
            )
        }
    }
}

/**
 * 订单列表内容视图
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OrderListContentView(
    orderList: List<Order>,
    isRefreshing: Boolean,
    loadMoreState: LoadMoreState,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean
) {
    RefreshLayout(
        isRefreshing = isRefreshing,
        loadMoreState = loadMoreState,
        onRefresh = onRefresh,
        onLoadMore = onLoadMore,
        shouldTriggerLoadMore = shouldTriggerLoadMore,
    ) {
        // 添加固定标签栏
        stickyHeader {
            OrderTabs(selectedTabIndex, onTabSelected)
        }

        // 订单列表项
        items(orderList.size) { index ->
            OrderCard(
                modifier = Modifier.padding(horizontal = SpaceHorizontalMedium),
                order = orderList[index]
            )
        }
    }
}

/**
 * 订单标签栏
 */
@Composable
private fun OrderTabs(selectedIndex: Int, onTabSelected: (Int) -> Unit) {
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        edgePadding = 0.dp
    ) {
        OrderStatus.entries.forEachIndexed { index, status ->
            Tab(
                selected = selectedIndex == index,
                onClick = { onTabSelected(index) },
                text = {
                    AppText(
                        text = status.label,
                        type = if (selectedIndex == index) TextType.PRIMARY else TextType.SECONDARY
                    )
                }
            )
        }
    }
}

/**
 * 订单卡片组件
 */
@Composable
private fun OrderCard(
    modifier: Modifier = Modifier,
    order: Order,
    onDetailClick: () -> Unit = {},
    onPayClick: () -> Unit = {}
) {
    Card(modifier = modifier) {
        AppListItem(
            title = order.orderNum,
            showArrow = false,
            trailingText = when (order.status) {
                0 -> "待付款"
                1 -> "待发货"
                2 -> "待收货"
                3 -> "待评价"
                4 -> "交易完成"
                5 -> "退款中"
                6 -> "已退款"
                7 -> "已关闭"
                else -> ""
            }
        )

        // 订单商品列表
        Box(modifier = Modifier.fillMaxWidth()) {
            // 水平滚动的商品列表
            HorizontalScroll(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpacePaddingMedium)
                    .padding(end = 80.dp)
            ) {
                // 添加商品图片列表
                order.goodsList?.forEach { goods ->
                    NetWorkImage(
                        modifier = Modifier.padding(end = 8.dp),
                        model = goods.spec?.images?.firstOrNull() ?: goods.goodsInfo?.mainPic,
                        size = 80.dp,
                        showBackground = true,
                        cornerShape = ShapeSmall
                    )
                }
            }

            // 右侧价格和数量信息
            CenterColumn(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(80.dp)
                    .padding(end = SpacePaddingMedium)
            ) {
                PriceText(order.price, integerTextSize = TextSize.BODY_LARGE)
                SpaceVerticalXSmall()
                AppText(
                    text = "共 ${order.goodsList?.size ?: 0} 件",
                    size = TextSize.BODY_SMALL,
                    type = TextType.TERTIARY
                )
            }
        }

        WeDivider()

        EndRow(modifier = Modifier.padding(SpacePaddingMedium)) {
            AppButtonBordered(
                text = "查看详情",
                onClick = onDetailClick,
                type = ButtonType.LINK,
                size = ButtonSize.MINI
            )

            SpaceHorizontalSmall()

            // 只有待付款状态才显示去支付按钮
            if (order.status == 0) {
                AppButtonBordered(
                    text = "去支付",
                    onClick = onPayClick,
                    type = ButtonType.DANGER,
                    size = ButtonSize.MINI
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun OrderListScreenPreview() {
    AppTheme {
        /*OrderListScreen(
            uiState = BaseNetWorkUiState.Success(Unit),
            orderList = listOf(
                // 预览数据
            )
        )*/
    }
}

@Preview(showBackground = true)
@Composable
internal fun OrderListScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        /*OrderListScreen(
        )*/
    }
} 