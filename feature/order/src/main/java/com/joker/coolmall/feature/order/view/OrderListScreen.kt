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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
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
import com.joker.coolmall.core.ui.component.dialog.WeDialog
import com.joker.coolmall.core.ui.component.divider.WeDivider
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.modal.DictSelectModal
import com.joker.coolmall.core.ui.component.modal.OrderGoodsModal
import com.joker.coolmall.core.ui.component.network.BaseNetWorkListView
import com.joker.coolmall.core.ui.component.refresh.RefreshLayout
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.PriceText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.feature.order.R
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
 * @param navController 导航控制器
 * @author Joker.X
 */
@Composable
internal fun OrderListRoute(
    viewModel: OrderListViewModel = hiltViewModel(),
    navController: NavController
) {
    // 当前选中的标签索引
    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()
    // 是否正在进行标签切换动画
    val isAnimatingTabChange by viewModel.isAnimatingTabChange.collectAsState()

    // 收集取消订单相关状态
    // 取消订单弹窗显示状态
    val cancelModalVisible by viewModel.cancelModalVisible.collectAsState()
    // 取消原因弹出层UI状态
    val cancelReasonsModalUiState by viewModel.cancelReasonsModalUiState.collectAsState()
    // 选中的取消原因
    val selectedCancelReason by viewModel.selectedCancelReason.collectAsState()

    // 收集确认收货弹窗状态
    // 确认收货弹窗显示状态
    val showConfirmDialog by viewModel.showConfirmDialog.collectAsState()

    // 收集再次购买和商品评论弹窗状态
    // 再次购买弹窗显示状态
    val rebuyModalVisible by viewModel.rebuyModalVisible.collectAsState()
    // 商品评论弹窗显示状态
    val commentModalVisible by viewModel.commentModalVisible.collectAsState()
    // 再次购买的购物车列表
    val rebuyCartList by viewModel.rebuyCartList.collectAsState()
    // 再次购买的当前订单
    val rebuyCurrentOrder: Order? by viewModel.rebuyCurrentOrder.collectAsState()
    // 商品评论的购物车列表
    val commentCartList by viewModel.commentCartList.collectAsState()
    // 商品评论的当前订单
    val commentCurrentOrder: Order? by viewModel.commentCurrentOrder.collectAsState()

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
        toGoodsDetail = viewModel::handleRebuy,
        toOrderLogistics = viewModel::toOrderLogistics,
        toOrderRefund = viewModel::toOrderRefund,
        toOrderComment = viewModel::handleOrderComment,
        cancelOrder = viewModel::cancelOrder,
        cancelModalVisible = cancelModalVisible,
        cancelReasonsModalUiState = cancelReasonsModalUiState,
        selectedCancelReason = selectedCancelReason,
        onCancelModalDismiss = viewModel::hideCancelModal,
        onCancelModalExpanded = viewModel::onCancelModalExpanded,
        onCancelReasonSelected = viewModel::selectCancelReason,
        onConfirmCancel = { viewModel.confirmCancelOrder() },
        showConfirmDialog = showConfirmDialog,
        onConfirmDialogDismiss = viewModel::hideConfirmReceiveDialog,
        onConfirmReceive = viewModel::confirmReceiveOrder,
        onConfirmClick = viewModel::showConfirmReceiveDialog,
        rebuyModalVisible = rebuyModalVisible,
        commentModalVisible = commentModalVisible,
        rebuyCartList = rebuyCartList,
        rebuyCurrentOrder = rebuyCurrentOrder,
        commentCartList = commentCartList,
        commentCurrentOrder = commentCurrentOrder,
        onRebuyModalDismiss = viewModel::hideRebuyModal,
        onCommentModalDismiss = viewModel::hideCommentModal,
        onRebuyGoodsSelected = viewModel::toGoodsDetailForRebuy,
        onCommentGoodsSelected = { orderId, goodsId ->
            viewModel.toOrderCommentForGoods(goodsId)
        },
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
 * @param toGoodsDetail 跳转到商品详情页面（再次购买）
 * @param toOrderLogistics 跳转到订单物流页面
 * @param toOrderRefund 跳转到退款申请页面
 * @param toOrderComment 跳转到订单评价页面
 * @param cancelOrder 取消订单
 * @param cancelModalVisible 取消订单弹窗显示状态
 * @param cancelReasonsModalUiState 取消原因弹出层UI状态
 * @param selectedCancelReason 选中的取消原因
 * @param onCancelModalDismiss 取消订单弹窗关闭回调
 * @param onCancelModalExpanded 取消订单弹窗展开完成回调
 * @param onCancelReasonSelected 取消原因选中回调
 * @param onConfirmCancel 确认取消订单回调
 * @param showConfirmDialog 确认收货弹窗显示状态
 * @param onConfirmDialogDismiss 确认收货弹窗关闭回调
 * @param onConfirmReceive 确认收货回调
 * @param onConfirmClick 确认收货按钮点击回调
 * @param rebuyModalVisible 再次购买弹窗显示状态
 * @param commentModalVisible 商品评论弹窗显示状态
 * @param rebuyCartList 再次购买的购物车列表
 * @param rebuyCurrentOrder 再次购买的当前订单
 * @param commentCartList 商品评论的购物车列表
 * @param commentCurrentOrder 商品评论的当前订单
 * @param onRebuyModalDismiss 再次购买弹窗关闭回调
 * @param onCommentModalDismiss 商品评论弹窗关闭回调
 * @param onRebuyGoodsSelected 再次购买商品选中回调
 * @param onCommentGoodsSelected 商品评论商品选中回调
 * @param selectedTabIndex 当前选中的标签索引，默认为0
 * @param isAnimatingTabChange 是否正在执行标签切换动画，默认为false
 * @param onTabSelected 标签被选择时的回调，参数为选中的标签索引
 * @param onTabByPageChanged 通过页面滑动切换标签时的回调，参数为新的标签索引
 * @param onAnimationCompleted 标签切换动画完成时的回调
 * @param onBackClick 返回按钮点击事件回调
 * @param tabStateProvider 标签页状态提供者函数，根据索引返回对应标签页的状态
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrderListScreen(
    toOrderDetail: (Long) -> Unit = {},
    toPay: (Order) -> Unit = {},
    toGoodsDetail: (Order) -> Unit = {},
    toOrderLogistics: (Long) -> Unit = {},
    toOrderRefund: (Long) -> Unit = {},
    toOrderComment: (Order) -> Unit = {},
    cancelOrder: (Long) -> Unit = {},
    cancelModalVisible: Boolean = false,
    cancelReasonsModalUiState: BaseNetWorkUiState<List<DictItem>> = BaseNetWorkUiState.Loading,
    selectedCancelReason: DictItem? = null,
    onCancelModalDismiss: () -> Unit = {},
    onCancelModalExpanded: () -> Unit = {},
    onCancelReasonSelected: (DictItem) -> Unit = {},
    onConfirmCancel: () -> Unit = {},
    showConfirmDialog: Boolean = false,
    onConfirmDialogDismiss: () -> Unit = {},
    onConfirmReceive: () -> Unit = {},
    onConfirmClick: (Long) -> Unit = {},
    rebuyModalVisible: Boolean = false,
    commentModalVisible: Boolean = false,
    rebuyCartList: List<com.joker.coolmall.core.model.entity.Cart> = emptyList(),
    rebuyCurrentOrder: Order? = null,
    commentCartList: List<com.joker.coolmall.core.model.entity.Cart> = emptyList(),
    commentCurrentOrder: Order? = null,
    onRebuyModalDismiss: () -> Unit = {},
    onCommentModalDismiss: () -> Unit = {},
    onRebuyGoodsSelected: (Long) -> Unit = {},
    onCommentGoodsSelected: (Long, Long) -> Unit = { _, _ -> },
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
        title = R.string.select_cancel_reason,
        uiState = cancelReasonsModalUiState,
        selectedItem = selectedCancelReason,
        onDismiss = onCancelModalDismiss,
        onItemSelected = onCancelReasonSelected,
        onConfirm = { reason ->
            onConfirmCancel()
        },
        onExpanded = onCancelModalExpanded
    )

    // 确认收货弹窗
    if (showConfirmDialog) {
        WeDialog(
            title = stringResource(R.string.confirm_receipt),
            content = stringResource(R.string.confirm_receipt_message),
            okText = stringResource(R.string.confirm),
            cancelText = stringResource(R.string.cancel),
            onOk = onConfirmReceive,
            onCancel = onConfirmDialogDismiss,
            onDismiss = onConfirmDialogDismiss
        )
    }

    // 再次购买弹窗
    OrderGoodsModal(
        visible = rebuyModalVisible,
        title = stringResource(R.string.select_goods_to_buy),
        buttonText = stringResource(R.string.rebuy),
        cartList = rebuyCartList,
        onDismiss = onRebuyModalDismiss,
        onItemButtonClick = onRebuyGoodsSelected
    )

    // 商品评论弹窗
    OrderGoodsModal(
        visible = commentModalVisible,
        title = stringResource(R.string.select_goods_to_comment),
        buttonText = stringResource(R.string.go_to_comment),
        cartList = commentCartList,
        onDismiss = onCommentModalDismiss,
        onItemButtonClick = { goodsId ->
            commentCurrentOrder?.let { order ->
                onCommentGoodsSelected(order.id, goodsId)
            }
        }
    )
}

/**
 * 订单列表内容视图
 *
 * @param modifier Compose修饰符，用于设置组件样式和布局，默认为Modifier
 * @param toOrderDetail 跳转到订单详情
 * @param toPay 跳转到支付页面
 * @param toGoodsDetail 跳转到商品详情页面（再次购买）
 * @param toOrderLogistics 跳转到订单物流页面
 * @param toOrderRefund 跳转到退款申请页面
 * @param toOrderComment 跳转到订单评价页面
 * @param cancelOrder 取消订单
 * @param onConfirmClick 确认收货按钮点击回调
 * @param selectedTabIndex 当前选中的标签页索引
 * @param isAnimatingTabChange 是否正在执行标签切换动画
 * @param onTabSelected 标签被点击选择时的回调，参数为选中的标签索引
 * @param onTabByPageChanged 通过页面滑动切换标签时的回调，参数为新的标签索引
 * @param onAnimationCompleted 标签切换动画完成时的回调
 * @param tabStateProvider 标签页状态提供者函数，根据索引返回对应标签页的状态
 * @author Joker.X
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OrderListContentView(
    modifier: Modifier = Modifier,
    toOrderDetail: (Long) -> Unit,
    toPay: (Order) -> Unit,
    toGoodsDetail: (Order) -> Unit,
    toOrderLogistics: (Long) -> Unit,
    toOrderRefund: (Long) -> Unit,
    toOrderComment: (Order) -> Unit,
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
 * @param toGoodsDetail 跳转到商品详情页面（再次购买）
 * @param toOrderLogistics 跳转到订单物流页面
 * @param toOrderRefund 跳转到退款申请页面
 * @param toOrderComment 跳转到订单评价页面
 * @param cancelOrder 取消订单
 * @param onConfirmClick 确认收货按钮点击回调
 * @param orderList 订单列表数据
 * @param isRefreshing 是否正在刷新中
 * @param loadMoreState 加载更多的状态
 * @param onRefresh 刷新回调函数
 * @param onLoadMore 加载更多回调函数
 * @param shouldTriggerLoadMore 判断是否应触发加载更多的函数，参数为当前列表最后一项索引和总数
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrderTabContent(
    toOrderDetail: (Long) -> Unit,
    toPay: (Order) -> Unit,
    toGoodsDetail: (Order) -> Unit,
    toOrderLogistics: (Long) -> Unit,
    toOrderRefund: (Long) -> Unit,
    toOrderComment: (Order) -> Unit,
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
                    // 使用 ViewModel 的 handleRebuy 方法处理再次购买逻辑
                    toGoodsDetail(order)
                },
                toLogistics = { toOrderLogistics(order.id) },
                toComment = { toOrderComment(order) },
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
 * @author Joker.X
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
            trailingText = stringResource(
                when (order.status) {
                    0 -> R.string.order_status_unpaid
                    1 -> R.string.order_status_unshipped
                    2 -> R.string.order_status_unreceived
                    3 -> R.string.order_status_unevaluated
                    4 -> R.string.order_status_completed
                    5 -> R.string.order_status_refunding
                    6 -> R.string.order_status_refunded
                    7 -> R.string.order_status_closed
                    else -> R.string.order_status_unknown
                }
            )
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
                    text = stringResource(R.string.total_items, order.goodsList?.size ?: 0),
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
 * @author Joker.X
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
                        text = stringResource(status.labelRes),
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
 * @author Joker.X
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

/**
 * 订单列表界面浅色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun OrderListScreenPreview() {
    AppTheme {
        OrderListScreen()
    }
}

/**
 * 订单列表界面深色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun OrderListScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        OrderListScreen()
    }
}