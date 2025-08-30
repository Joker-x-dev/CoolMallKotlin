package com.joker.coolmall.feature.order.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.designsystem.component.CenterColumn
import com.joker.coolmall.core.designsystem.component.EndRow
import com.joker.coolmall.core.designsystem.component.HorizontalScroll
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.model.entity.DictItem
import com.joker.coolmall.core.model.entity.Order
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
import com.joker.coolmall.core.ui.component.modal.DictSelectModal
import com.joker.coolmall.core.ui.component.dialog.WeDialog
import com.joker.coolmall.feature.order.component.OrderButtons
import com.joker.coolmall.feature.order.model.OrderStatus
import com.joker.coolmall.feature.order.model.OrderTabState
import com.joker.coolmall.feature.order.viewmodel.OrderListViewModel
import kotlinx.coroutines.launch

/**
 * 订单列表路由 - 顶层入口
 *
 * 负责收集ViewModel数据并传递给Screen层
 * @param viewModel 订单列表ViewModel，提供数据和事件处理，默认通过hiltViewModel()注入
 */
@Composable
internal fun OrderListRoute(
    viewModel: OrderListViewModel = hiltViewModel(),
    navController: NavController
) {
    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()
    val isAnimatingTabChange by viewModel.isAnimatingTabChange.collectAsState()
    
    // 收集取消订单相关状态
    val cancelModalVisible by viewModel.cancelModalVisible.collectAsState()
    val cancelReasonsModalUiState by viewModel.cancelReasonsModalUiState.collectAsState()
    val selectedCancelReason by viewModel.selectedCancelReason.collectAsState()
    
    // 收集确认收货弹窗状态
    val showConfirmDialog by viewModel.showConfirmDialog.collectAsState()

    // 注册页面刷新监听
    val backStackEntry = navController.currentBackStackEntry

    // 获取标签页状态提供者
    val tabStateProvider: @Composable (Int) -> OrderTabState = { index ->
        val uiState by viewModel.uiStates[index].collectAsState()
        val orderList by viewModel.listDataMap[index].collectAsState()
        val isRefreshing by viewModel.refreshingStates[index].collectAsState()
        val loadMoreState by viewModel.loadMoreStates[index].collectAsState()

        OrderTabState(
            uiState = uiState,
            orderList = orderList,
            isRefreshing = isRefreshing,
            loadMoreState = loadMoreState,
            onRetry = { viewModel.retryRequest(index) },
            onRefresh = { viewModel.onRefresh(index) },
            onLoadMore = { viewModel.onLoadMore(index) },
            shouldTriggerLoadMore = { lastIndex, totalCount ->
                viewModel.shouldTriggerLoadMore(lastIndex, totalCount, index)
            }
        )
    }

    OrderListScreen(
        toOrderDetail = viewModel::toOrderDetailPage,
        toPay = viewModel::toPaymentPage,
        toGoodsDetail = viewModel::toGoodsDetail,
        toOrderLogistics = viewModel::toOrderLogistics,
        toOrderRefund = viewModel::toOrderRefund,
        toOrderComment = viewModel::toOrderComment,
        cancelOrder = viewModel::cancelOrder,
        cancelModalVisible = cancelModalVisible,
        cancelReasonsModalUiState = cancelReasonsModalUiState,
        selectedCancelReason = selectedCancelReason,
        onCancelModalDismiss = viewModel::hideCancelModal,
        onCancelReasonSelected = viewModel::selectCancelReason,
        onConfirmCancel = { viewModel.confirmCancelOrder() },
        showConfirmDialog = showConfirmDialog,
        onConfirmDialogDismiss = viewModel::hideConfirmReceiveDialog,
        onConfirmReceive = viewModel::confirmReceiveOrder,
        onConfirmClick = viewModel::showConfirmReceiveDialog,
        selectedTabIndex = selectedTabIndex,
        isAnimatingTabChange = isAnimatingTabChange,
        onTabSelected = viewModel::updateSelectedTab,
        onTabByPageChanged = viewModel::updateTabByPage,
        onAnimationCompleted = viewModel::notifyAnimationCompleted,
        onBackClick = viewModel::navigateBack,
        tabStateProvider = tabStateProvider
    )

    // 只要backStackEntry不为null就注册监听
    LaunchedEffect(backStackEntry) {
        viewModel.observeRefreshState(backStackEntry)
    }
}

/**
 * 订单列表页面 - Screen层
 *
 * 包含AppScaffold和页面整体布局
 * 所有参数都提供默认值，方便预览
 *
 * @param toOrderDetail 跳转到订单详情页面
 * @param toPay 跳转到支付页面
 * @param selectedTabIndex 当前选中的标签索引，默认为0
 * @param isAnimatingTabChange 是否正在执行标签切换动画，默认为false
 * @param onTabSelected 标签被选择时的回调，参数为选中的标签索引
 * @param onTabByPageChanged 通过页面滑动切换标签时的回调，参数为新的标签索引
 * @param onAnimationCompleted 标签切换动画完成时的回调
 * @param onBackClick 返回按钮点击事件回调
 * @param tabStateProvider 标签页状态提供者函数，根据索引返回对应标签页的状态
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrderListScreen(
    toOrderDetail: (Long) -> Unit = {},
    toPay: (Order) -> Unit = {},
    toGoodsDetail: (Long) -> Unit = {},
    toOrderLogistics: (Long) -> Unit = {},
    toOrderRefund: (Long) -> Unit = {},
    toOrderComment: (Long) -> Unit = {},
    cancelOrder: (Long) -> Unit = {},
    cancelModalVisible: Boolean = false,
    cancelReasonsModalUiState: BaseNetWorkUiState<List<DictItem>> = BaseNetWorkUiState.Loading,
    selectedCancelReason: DictItem? = null,
    onCancelModalDismiss: () -> Unit = {},
    onCancelReasonSelected: (DictItem) -> Unit = {},
    onConfirmCancel: () -> Unit = {},
    showConfirmDialog: Boolean = false,
    onConfirmDialogDismiss: () -> Unit = {},
    onConfirmReceive: () -> Unit = {},
    onConfirmClick: (Long) -> Unit = {},
    selectedTabIndex: Int = 0,
    isAnimatingTabChange: Boolean = false,
    onTabSelected: (Int) -> Unit = {},
    onTabByPageChanged: (Int) -> Unit = {},
    onAnimationCompleted: () -> Unit = {},
    onBackClick: () -> Unit = {},
    tabStateProvider: @Composable (Int) -> OrderTabState = { _ ->
        OrderTabState(
            uiState = BaseNetWorkListUiState.Loading,
            orderList = emptyList(),
            isRefreshing = false,
            loadMoreState = LoadMoreState.PullToLoad,
            onRetry = {},
            onRefresh = {},
            onLoadMore = {},
            shouldTriggerLoadMore = { _, _ -> false }
        )
    }
) {
    AppScaffold(
        title = R.string.order_list,
        onBackClick = onBackClick
    ) {
        OrderListContentView(
            toOrderDetail = toOrderDetail,
            toPay = toPay,
            toGoodsDetail = toGoodsDetail,
            toOrderLogistics = toOrderLogistics,
            toOrderRefund = toOrderRefund,
            toOrderComment = toOrderComment,
            cancelOrder = cancelOrder,
            onConfirmClick = onConfirmClick,
            selectedTabIndex = selectedTabIndex,
            isAnimatingTabChange = isAnimatingTabChange,
            onTabSelected = onTabSelected,
            onTabByPageChanged = onTabByPageChanged,
            onAnimationCompleted = onAnimationCompleted,
            tabStateProvider = tabStateProvider
        )
    }
    
    // 取消订单弹窗
    DictSelectModal(
        visible = cancelModalVisible,
        title = "选择取消原因",
        uiState = cancelReasonsModalUiState,
        selectedItem = selectedCancelReason,
        onDismiss = onCancelModalDismiss,
        onItemSelected = onCancelReasonSelected,
        onConfirm = { reason ->
            onConfirmCancel()
        }
    )
    
    // 确认收货弹窗
    if (showConfirmDialog) {
        WeDialog(
            title = "确认收货",
            content = "确认收货后无法发起退款等售后申请，请谨慎操作",
            okText = "确认",
            cancelText = "取消",
            onOk = onConfirmReceive,
            onCancel = onConfirmDialogDismiss,
            onDismiss = onConfirmDialogDismiss
        )
    }
}

/**
 * 订单列表内容视图
 *
 * @param modifier Compose修饰符，用于设置组件样式和布局，默认为Modifier
 * @param toOrderDetail 跳转到订单详情
 * @param toPay 跳转到支付页面
 * @param selectedTabIndex 当前选中的标签页索引
 * @param isAnimatingTabChange 是否正在执行标签切换动画
 * @param onTabSelected 标签被点击选择时的回调，参数为选中的标签索引
 * @param onTabByPageChanged 通过页面滑动切换标签时的回调，参数为新的标签索引
 * @param onAnimationCompleted 标签切换动画完成时的回调
 * @param tabStateProvider 标签页状态提供者函数，根据索引返回对应标签页的状态
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OrderListContentView(
    modifier: Modifier = Modifier,
    toOrderDetail: (Long) -> Unit,
    toPay: (Order) -> Unit,
    toGoodsDetail: (Long) -> Unit,
    toOrderLogistics: (Long) -> Unit,
    toOrderRefund: (Long) -> Unit,
    toOrderComment: (Long) -> Unit,
    cancelOrder: (Long) -> Unit,
    onConfirmClick: (Long) -> Unit,
    selectedTabIndex: Int,
    isAnimatingTabChange: Boolean,
    onTabSelected: (Int) -> Unit,
    onTabByPageChanged: (Int) -> Unit,
    onAnimationCompleted: () -> Unit,
    tabStateProvider: @Composable (Int) -> OrderTabState
) {
    // 协程作用域
    val scope = rememberCoroutineScope()

    // 创建分页器状态
    val pageState = rememberPagerState(
        initialPage = selectedTabIndex
    ) {
        OrderStatus.entries.size
    }

    // 处理页面状态变化
    HandlePageStateChanges(
        pageState = pageState,
        selectedTabIndex = selectedTabIndex,
        isAnimatingTabChange = isAnimatingTabChange,
        onTabByPageChanged = onTabByPageChanged,
        onAnimationCompleted = onAnimationCompleted
    )

    Column(modifier = modifier) {
        // 标签栏
        OrderTabs(
            selectedIndex = selectedTabIndex,
            onTabSelected = { index ->
                onTabSelected(index)
                scope.launch {
                    pageState.animateScrollToPage(index)
                }
            }
        )

        // 水平分页器
        HorizontalPager(
            state = pageState,
            userScrollEnabled = true,
            modifier = Modifier.weight(1f)
        ) { page ->
            // 获取当前标签页的状态
            val tabState = tabStateProvider(page)

            // 使用 BaseNetWorkListView 包裹每个标签页
            BaseNetWorkListView(
                uiState = tabState.uiState,
                onRetry = tabState.onRetry
            ) {
                // 标签页的内容
        OrderTabContent(
             toOrderDetail = toOrderDetail,
             toPay = toPay,
             toGoodsDetail = toGoodsDetail,
             toOrderLogistics = toOrderLogistics,
             toOrderRefund = toOrderRefund,
             toOrderComment = toOrderComment,
             cancelOrder = cancelOrder,
             onConfirmClick = onConfirmClick,
             orderList = tabState.orderList,
             isRefreshing = tabState.isRefreshing,
             loadMoreState = tabState.loadMoreState,
             onRefresh = tabState.onRefresh,
             onLoadMore = tabState.onLoadMore,
             shouldTriggerLoadMore = tabState.shouldTriggerLoadMore
         )
            }
        }
    }
}

/**
 * 标签页内容
 *
 * @param toOrderDetail 跳转到订单详情
 * @param toPay 跳转到支付页面
 * @param orderList 订单列表数据
 * @param isRefreshing 是否正在刷新中
 * @param loadMoreState 加载更多的状态
 * @param onRefresh 刷新回调函数
 * @param onLoadMore 加载更多回调函数
 * @param shouldTriggerLoadMore 判断是否应触发加载更多的函数，参数为当前列表最后一项索引和总数
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrderTabContent(
    toOrderDetail: (Long) -> Unit,
    toPay: (Order) -> Unit,
    toGoodsDetail: (Long) -> Unit,
    toOrderLogistics: (Long) -> Unit,
    toOrderRefund: (Long) -> Unit,
    toOrderComment: (Long) -> Unit,
    cancelOrder: (Long) -> Unit,
    onConfirmClick: (Long) -> Unit,
    orderList: List<Order>,
    isRefreshing: Boolean,
    loadMoreState: LoadMoreState,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean
) {
    RefreshLayout(
        isRefreshing = isRefreshing,
        loadMoreState = loadMoreState,
        onRefresh = onRefresh,
        onLoadMore = onLoadMore,
        shouldTriggerLoadMore = shouldTriggerLoadMore
    ) {
        // 订单列表项
        items(orderList.size) { index ->
            val order = orderList[index]
            OrderCard(
                order = order,
                toOrderDetail = toOrderDetail,
                toPay = { toPay(order) },
                toGoodsDetail = { 
                    // 获取订单中第一个商品的ID进行跳转
                    order.goodsList?.firstOrNull()?.goodsId?.let { goodsId ->
                        toGoodsDetail(goodsId)
                    }
                },
                toLogistics = { toOrderLogistics(order.id) },
                toComment = { toOrderComment(order.id) },
                toRefund = { toOrderRefund(order.id) },
                onCancelClick = { cancelOrder(order.id) },
                onConfirmClick = { onConfirmClick(order.id) }
            )
        }
    }
}

/**
 * 订单卡片组件
 *
 * @param modifier Compose修饰符
 * @param order 订单数据对象，包含订单的所有信息
 * @param toOrderDetail 跳转到订单详情页面
 * @param toGoodsDetail 跳转到商品详情页面
 * @param toPay 跳转到支付页面
 * @param toLogistics 跳转到物流详情页面
 * @param toComment 跳转到评价页面
 * @param toRefund 跳转到退款/售后页面
 * @param onCancelClick 取消订单按钮点击回调
 * @param onConfirmClick 确认收货按钮点击回调
 */
@Composable
private fun OrderCard(
    modifier: Modifier = Modifier,
    order: Order,
    toOrderDetail: (Long) -> Unit = {},
    toGoodsDetail: () -> Unit = {},
    toPay: () -> Unit = {},
    toLogistics: () -> Unit = {},
    toComment: () -> Unit = {},
    toRefund: () -> Unit = {},
    onCancelClick: () -> Unit = {},
    onConfirmClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .clip(ShapeMedium)
            .clickable(
                onClick = {
                    toOrderDetail(order.id)
                }
            )) {
        AppListItem(
            title = order.orderNum,
            showArrow = false,
            onClick = {
                toOrderDetail(order.id)
            },
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

        EndRow(modifier = modifier.padding(SpacePaddingMedium)) {
            OrderButtons(
                order = order,
                onCancelClick = onCancelClick,
                onPayClick = toPay,
                onRefundClick = toRefund,
                onConfirmClick = onConfirmClick,
                onLogisticsClick = toLogistics,
                onCommentClick = toComment,
                onRebuyClick = toGoodsDetail
            )
        }
    }
}

/**
 * 订单标签栏
 *
 * @param selectedIndex 当前选中的标签索引
 * @param onTabSelected 标签被选择时的回调，参数为选中的标签索引
 */
@Composable
private fun OrderTabs(selectedIndex: Int, onTabSelected: (Int) -> Unit) {
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        edgePadding = 0.dp,
        divider = { WeDivider() }
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
 * 处理页面状态变化的副作用
 *
 * @param pageState 分页器状态，控制标签页的滑动
 * @param selectedTabIndex 当前选中的标签索引
 * @param isAnimatingTabChange 是否正在执行标签切换动画
 * @param onTabByPageChanged 通过页面滑动切换标签时的回调，参数为新的标签索引
 * @param onAnimationCompleted 标签切换动画完成时的回调
 */
@Composable
private fun HandlePageStateChanges(
    pageState: PagerState,
    selectedTabIndex: Int,
    isAnimatingTabChange: Boolean,
    onTabByPageChanged: (Int) -> Unit,
    onAnimationCompleted: () -> Unit
) {
    // 当标签选择变化时，自动滚动到相应页面
    LaunchedEffect(selectedTabIndex, isAnimatingTabChange) {
        if (isAnimatingTabChange && pageState.currentPage != selectedTabIndex) {
            pageState.animateScrollToPage(selectedTabIndex)
        }
    }

    // 监听分页器当前页面变化
    LaunchedEffect(pageState.currentPage) {
        // 当页面已经切换到新页面，立即更新导航状态
        if (!isAnimatingTabChange) {
            onTabByPageChanged(pageState.currentPage)
        }
    }

    // 监听滑动动画完成
    LaunchedEffect(pageState.isScrollInProgress) {
        if (!pageState.isScrollInProgress && isAnimatingTabChange) {
            // 当页面滑动动画结束，通知完成
            onAnimationCompleted()
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun OrderListScreenPreview() {
    AppTheme {
        OrderListScreen()
    }
}

@Preview(showBackground = true)
@Composable
internal fun OrderListScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        OrderListScreen()
    }
}