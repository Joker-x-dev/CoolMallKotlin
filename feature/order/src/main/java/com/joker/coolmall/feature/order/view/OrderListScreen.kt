package com.joker.coolmall.feature.order.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.component.CenterColumn
import com.joker.coolmall.core.designsystem.component.EndRow
import com.joker.coolmall.core.designsystem.component.HorizontalScroll
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.ui.component.button.AppButtonBordered
import com.joker.coolmall.core.ui.component.button.ButtonSize
import com.joker.coolmall.core.ui.component.button.ButtonType
import com.joker.coolmall.core.ui.component.divider.WeDivider
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.refresh.RefreshLayout
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.PriceText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
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
    OrderListScreen(
        uiState = uiState,
        onBackClick = viewModel::navigateBack,
        onRetry = viewModel::retryRequest,
    )
}

/**
 * 订单列表页面
 *
 * @param uiState UI状态
 * @param onBackClick 返回回调
 * @param onRefresh 刷新回调
 * @param onLoadMore 加载更多回调
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrderListScreen(
    uiState: BaseNetWorkUiState<NetworkPageData<Order>> = BaseNetWorkUiState.Loading,
    onBackClick: () -> Unit = {},
    onRefresh: (finishRefresh: (Boolean) -> Unit) -> Unit = { it(true) },
    onLoadMore: (finishLoadMore: (Boolean, Boolean) -> Unit) -> Unit = { it(true, true) },
    onRetry: () -> Unit = {}
) {
    AppScaffold(
        onBackClick = onBackClick,
    ) {
        BaseNetWorkView(
            uiState = uiState,
            onRetry = onRetry
        ) { data ->
            OrderListContentView(
                data = data.list!!,
                onRefresh = onRefresh,
                onLoadMore = onLoadMore
            )
        }
    }
}

/**
 * 订单列表内容视图
 */
@Composable
private fun OrderListContentView(
    data: List<Order>,
    onRefresh: (finishRefresh: (Boolean) -> Unit) -> Unit = { it(true) },
    onLoadMore: (finishLoadMore: (Boolean, Boolean) -> Unit) -> Unit = { it(true, true) }
) {
    // 当前选中的标签
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 标签栏
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f),
            edgePadding = 0.dp,
        ) {
            OrderStatus.entries.forEachIndexed { index, status ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        AppText(
                            text = status.label,
                            type = if (selectedTabIndex == index) {
                                TextType.PRIMARY
                            } else {
                                TextType.SECONDARY
                            }
                        )
                    }
                )
            }
        }

        RefreshLayout(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            onRefresh = onRefresh,
            onLoadMore = onLoadMore
        ) {
            // 订单列表
            VerticalList(
                modifier = Modifier.fillMaxWidth()
            ) {
                data.forEach { order ->
                    OrderCard(order = order)
                }
            }
        }
    }
}

/**
 * 订单卡片组件
 */
@Composable
private fun OrderCard(
    order: Order,
    onDetailClick: () -> Unit = {},
    onPayClick: () -> Unit = {}
) {
    Card {
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
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
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

        EndRow(
            modifier = Modifier.padding(SpacePaddingMedium)
        ) {
            AppButtonBordered(
                text = "查看详情",
                onClick = onDetailClick,
                type = ButtonType.LINK,
                size = ButtonSize.MINI,
            )

            SpaceHorizontalSmall()

            // 只有待付款状态才显示去支付按钮
            if (order.status == 0) {
                AppButtonBordered(
                    text = "去支付",
                    onClick = onPayClick,
                    type = ButtonType.DANGER,
                    size = ButtonSize.MINI,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun OrderListScreenPreview() {
    AppTheme {
        OrderListScreen(
            uiState = BaseNetWorkUiState.Loading
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun OrderListScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        OrderListScreen(
            uiState = BaseNetWorkUiState.Loading
        )
    }
} 