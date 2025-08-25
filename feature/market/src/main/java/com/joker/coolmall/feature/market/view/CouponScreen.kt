package com.joker.coolmall.feature.market.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.model.entity.Coupon
import com.joker.coolmall.core.model.preview.previewMyCoupons
import com.joker.coolmall.core.ui.component.coupon.CouponCard
import com.joker.coolmall.core.ui.component.coupon.CouponCardMode
import com.joker.coolmall.core.ui.component.network.BaseNetWorkListView
import com.joker.coolmall.core.ui.component.refresh.RefreshLayout
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.market.viewmodel.CouponViewModel

/**
 * 我的优惠券路由
 *
 * @param viewModel 我的优惠券 ViewModel
 */
@Composable
internal fun CouponRoute(
    viewModel: CouponViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listData by viewModel.listData.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val loadMoreState by viewModel.loadMoreState.collectAsState()

    CouponScreen(
        uiState = uiState,
        listData = listData,
        isRefreshing = isRefreshing,
        loadMoreState = loadMoreState,
        onRefresh = viewModel::onRefresh,
        onLoadMore = viewModel::onLoadMore,
        shouldTriggerLoadMore = viewModel::shouldTriggerLoadMore,
        onBackClick = viewModel::navigateBack,
        onRetry = viewModel::retryRequest
    )
}

/**
 * 我的优惠券界面
 *
 * @param uiState UI状态
 * @param listData 优惠券列表数据
 * @param isRefreshing 是否正在刷新
 * @param loadMoreState 加载更多状态
 * @param onRefresh 刷新回调
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 是否应触发加载更多的判断函数
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CouponScreen(
    uiState: BaseNetWorkListUiState = BaseNetWorkListUiState.Loading,
    listData: List<Coupon> = emptyList(),
    isRefreshing: Boolean = false,
    loadMoreState: LoadMoreState = LoadMoreState.Success,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean = { _, _ -> false },
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    AppScaffold(
        title = com.joker.coolmall.feature.market.R.string.coupon_title,
        onBackClick = onBackClick
    ) {
        BaseNetWorkListView(
            uiState = uiState,
            onRetry = onRetry
        ) {
            CouponContentView(
                data = listData,
                isRefreshing = isRefreshing,
                loadMoreState = loadMoreState,
                onRefresh = onRefresh,
                onLoadMore = onLoadMore,
                shouldTriggerLoadMore = shouldTriggerLoadMore
            )
        }
    }
}

/**
 * 我的优惠券内容视图
 *
 * @param data 优惠券列表数据
 * @param isRefreshing 是否正在刷新
 * @param loadMoreState 加载更多状态
 * @param onRefresh 刷新回调
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 是否应触发加载更多的判断函数
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CouponContentView(
    data: List<Coupon>,
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
        shouldTriggerLoadMore = shouldTriggerLoadMore,
    ) {
        // 优惠券列表项
        items(data.size) { index ->
            CouponCard(
                coupon = data[index],
                mode = CouponCardMode.VIEW
            )
        }
    }
}

/**
 * 我的优惠券界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun CouponScreenPreview() {
    AppTheme {
        CouponScreen(
            uiState = BaseNetWorkListUiState.Success,
            listData = previewMyCoupons
        )
    }
}

/**
 * 我的优惠券界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun CouponScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        CouponScreen(
            uiState = BaseNetWorkListUiState.Success,
            listData = previewMyCoupons
        )
    }
}