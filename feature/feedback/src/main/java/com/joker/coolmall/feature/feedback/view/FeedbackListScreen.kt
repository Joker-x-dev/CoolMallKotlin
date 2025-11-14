package com.joker.coolmall.feature.feedback.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.model.entity.Feedback
import com.joker.coolmall.core.ui.component.bottombar.AppBottomButton
import com.joker.coolmall.core.ui.component.network.BaseNetWorkListView
import com.joker.coolmall.core.ui.component.refresh.RefreshLayout
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.feedback.R
import com.joker.coolmall.feature.feedback.component.FeedbackCard
import com.joker.coolmall.feature.feedback.viewmodel.FeedbackListViewModel

/**
 * 反馈列表路由
 *
 * @param viewModel 反馈列表 ViewModel
 * @author Joker.X
 */
@Composable
internal fun FeedbackListRoute(
    viewModel: FeedbackListViewModel = hiltViewModel(),
    navController: NavHostController
) {
    // 反馈列表UI状态
    val uiState by viewModel.uiState.collectAsState()
    // 反馈列表数据
    val listData by viewModel.listData.collectAsState()
    // 是否正在刷新
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    // 加载更多状态
    val loadMoreState by viewModel.loadMoreState.collectAsState()

    // 注册返回刷新监听（使用 BaseNetWorkListViewModel 内置的 observeRefreshState）
    val backStackEntry = navController.currentBackStackEntry
    LaunchedEffect(backStackEntry) {
        viewModel.observeRefreshState(backStackEntry)
    }

    FeedbackListScreen(
        uiState = uiState,
        listData = listData,
        isRefreshing = isRefreshing,
        loadMoreState = loadMoreState,
        onRefresh = viewModel::onRefresh,
        onLoadMore = viewModel::onLoadMore,
        shouldTriggerLoadMore = viewModel::shouldTriggerLoadMore,
        onBackClick = viewModel::navigateBack,
        onRetry = viewModel::retryRequest,
        onSubmitClick = viewModel::toFeedbackSubmitPage,
        getFeedbackTypeName = viewModel::getFeedbackTypeName
    )
}

/**
 * 反馈列表界面
 *
 * @param uiState 反馈列表UI状态
 * @param listData 反馈列表数据
 * @param isRefreshing 是否正在刷新
 * @param loadMoreState 加载更多状态
 * @param onRefresh 下拉刷新回调
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 是否应触发加载更多的判断函数
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 * @param onSubmitClick 提交反馈按钮回调
 * @param getFeedbackTypeName 获取反馈类型名称的方法
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FeedbackListScreen(
    uiState: BaseNetWorkListUiState = BaseNetWorkListUiState.Loading,
    listData: List<Feedback> = emptyList(),
    isRefreshing: Boolean = false,
    loadMoreState: LoadMoreState = LoadMoreState.Success,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean = { _, _ -> false },
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {},
    onSubmitClick: () -> Unit = {},
    getFeedbackTypeName: (Int?) -> String = { it.toString() }
) {
    AppScaffold(
        title = R.string.feedback_list,
        onBackClick = onBackClick,
        bottomBar = {
            if (uiState != BaseNetWorkListUiState.Loading && uiState != BaseNetWorkListUiState.Error) {
                AppBottomButton(
                    text = stringResource(R.string.feedback_submit),
                    onClick = onSubmitClick
                )
            }
        }
    ) {
        BaseNetWorkListView(
            uiState = uiState,
            onRetry = onRetry
        ) {
            FeedbackListContentView(
                data = listData,
                isRefreshing = isRefreshing,
                loadMoreState = loadMoreState,
                onRefresh = onRefresh,
                onLoadMore = onLoadMore,
                shouldTriggerLoadMore = shouldTriggerLoadMore,
                getFeedbackTypeName = getFeedbackTypeName
            )
        }
    }
}

/**
 * 反馈列表内容视图
 *
 * @param data 反馈列表数据
 * @param isRefreshing 是否正在刷新
 * @param loadMoreState 加载更多状态
 * @param onRefresh 下拉刷新回调
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 是否应触发加载更多的判断函数
 * @param getFeedbackTypeName 获取反馈类型名称的方法
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FeedbackListContentView(
    data: List<Feedback>,
    isRefreshing: Boolean,
    loadMoreState: LoadMoreState,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean,
    getFeedbackTypeName: (Int?) -> String
) {
    RefreshLayout(
        isRefreshing = isRefreshing,
        loadMoreState = loadMoreState,
        onRefresh = onRefresh,
        onLoadMore = onLoadMore,
        shouldTriggerLoadMore = shouldTriggerLoadMore
    ) {
        // 反馈列表项
        items(data.size) { index ->
            FeedbackCard(
                feedback = data[index],
                typeName = getFeedbackTypeName(data[index].type)
            )
        }
    }
}

/**
 * 反馈列表界面浅色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun FeedbackListScreenPreview() {
    AppTheme {
        FeedbackListScreen(
            uiState = BaseNetWorkListUiState.Success,
            listData = listOf(
                Feedback(
                    id = 1,
                    type = 1,
                    content = "希望能够增加夜间模式功能",
                    contact = "user@example.com",
                    images = null,
                    status = 0,
                    createTime = "2025-08-15 10:30:00",
                    updateTime = "2025-08-15 10:30:00"
                )
            )
        )
    }
}

/**
 * 反馈列表界面深色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun FeedbackListScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        FeedbackListScreen(
            uiState = BaseNetWorkListUiState.Success,
            listData = listOf(
                Feedback(
                    id = 2,
                    type = 0,
                    content = "登录时偶尔会出现验证码刷新失败的问题",
                    contact = "13800138000",
                    images = null,
                    status = 1,
                    createTime = "2025-08-15 10:30:00",
                    updateTime = "2025-08-15 10:30:00"
                )
            )
        )
    }
}