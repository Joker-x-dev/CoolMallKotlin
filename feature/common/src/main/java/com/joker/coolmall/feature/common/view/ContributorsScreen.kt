package com.joker.coolmall.feature.common.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.model.entity.UserContributor
import com.joker.coolmall.core.ui.component.network.BaseNetWorkListView
import com.joker.coolmall.core.ui.component.refresh.RefreshLayout
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.common.R
import com.joker.coolmall.feature.common.component.UserContributorCard
import com.joker.coolmall.feature.common.viewmodel.ContributorsViewModel

/**
 * 贡献者列表路由
 *
 * @param viewModel 贡献者列表 ViewModel
 * @author Joker.X
 */
@Composable
internal fun ContributorsRoute(
    viewModel: ContributorsViewModel = hiltViewModel()
) {
    // 从ViewModel收集UI状态
    val uiState by viewModel.uiState.collectAsState()
    // 收集列表数据
    val listData by viewModel.listData.collectAsState()
    // 收集刷新状态
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    // 收集加载更多状态
    val loadMoreState by viewModel.loadMoreState.collectAsState()

    ContributorsScreen(
        uiState = uiState,
        listData = listData,
        isRefreshing = isRefreshing,
        loadMoreState = loadMoreState,
        onRefresh = viewModel::onRefresh,
        onLoadMore = viewModel::onLoadMore,
        shouldTriggerLoadMore = viewModel::shouldTriggerLoadMore,
        onBackClick = viewModel::navigateBack,
        onRetry = viewModel::retryRequest,
        onContributorClick = viewModel::onContributorClick
    )
}

/**
 * 贡献者列表页面
 *
 * @param uiState UI状态
 * @param listData 列表数据
 * @param isRefreshing 是否正在刷新
 * @param loadMoreState 加载更多状态
 * @param onRefresh 刷新事件
 * @param onLoadMore 加载更多事件
 * @param shouldTriggerLoadMore 是否应该触发加载更多
 * @param onBackClick 返回点击事件
 * @param onRetry 重试点击事件
 * @param onContributorClick 贡献者点击事件
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ContributorsScreen(
    uiState: BaseNetWorkListUiState = BaseNetWorkListUiState.Loading,
    listData: List<UserContributor> = emptyList(),
    isRefreshing: Boolean = false,
    loadMoreState: LoadMoreState = LoadMoreState.Success,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    shouldTriggerLoadMore: (Int, Int) -> Boolean = { _, _ -> false },
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {},
    onContributorClick: (UserContributor) -> Unit = {}
) {
    AppScaffold(
        title = R.string.contributors_title,
        onBackClick = onBackClick
    ) {
        BaseNetWorkListView(
            uiState = uiState,
            onRetry = onRetry
        ) {
            ContributorsContentView(
                data = listData,
                isRefreshing = isRefreshing,
                loadMoreState = loadMoreState,
                onRefresh = onRefresh,
                onLoadMore = onLoadMore,
                shouldTriggerLoadMore = shouldTriggerLoadMore,
                onContributorClick = onContributorClick
            )
        }
    }
}

/**
 * 贡献者列表内容视图
 *
 * @param data 贡献者列表数据
 * @param isRefreshing 是否正在刷新
 * @param loadMoreState 加载更多状态
 * @param onRefresh 下拉刷新回调
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 是否应触发加载更多的判断函数
 * @param onContributorClick 贡献者点击事件
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContributorsContentView(
    data: List<UserContributor> = emptyList(),
    isRefreshing: Boolean = false,
    loadMoreState: LoadMoreState = LoadMoreState.Success,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean = { _, _ -> false },
    onContributorClick: (UserContributor) -> Unit = {}
) {
    RefreshLayout(
        isRefreshing = isRefreshing,
        loadMoreState = loadMoreState,
        onRefresh = onRefresh,
        onLoadMore = onLoadMore,
        shouldTriggerLoadMore = shouldTriggerLoadMore
    ) {
        // 贡献者列表项
        items(data.size) { index ->
            UserContributorCard(
                contributor = data[index],
                onClick = onContributorClick
            )
        }
    }
}

/**
 * 贡献者列表界面浅色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun ContributorsScreenPreview() {
    AppTheme {
        ContributorsScreen()
    }
}

/**
 * 贡献者列表界面深色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun ContributorsScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        ContributorsScreen()
    }
}