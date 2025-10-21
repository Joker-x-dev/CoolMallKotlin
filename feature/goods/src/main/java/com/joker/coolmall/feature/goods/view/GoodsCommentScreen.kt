package com.joker.coolmall.feature.goods.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.model.entity.Comment
import com.joker.coolmall.core.ui.component.network.BaseNetWorkListView
import com.joker.coolmall.core.ui.component.refresh.RefreshLayout
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.goods.R
import com.joker.coolmall.feature.goods.component.CommentCard
import com.joker.coolmall.feature.goods.viewmodel.GoodsCommentViewModel

/**
 * 商品评论路由
 *
 * @param viewModel 商品评论 ViewModel
 * @author Joker.X
 */
@Composable
internal fun GoodsCommentRoute(
    viewModel: GoodsCommentViewModel = hiltViewModel()
) {
    // 商品评论列表UI状态
    val uiState by viewModel.uiState.collectAsState()
    // 商品评论列表数据
    val listData by viewModel.listData.collectAsState()
    // 是否正在刷新
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    // 加载更多状态
    val loadMoreState by viewModel.loadMoreState.collectAsState()

    GoodsCommentScreen(
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
 * 商品评论界面
 *
 * @param uiState 商品评论列表UI状态
 * @param listData 商品评论列表数据
 * @param isRefreshing 是否正在刷新
 * @param loadMoreState 加载更多状态
 * @param onRefresh 下拉刷新回调
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 是否应触发加载更多的判断函数
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GoodsCommentScreen(
    uiState: BaseNetWorkListUiState = BaseNetWorkListUiState.Loading,
    listData: List<Comment> = emptyList(),
    isRefreshing: Boolean = false,
    loadMoreState: LoadMoreState = LoadMoreState.Success,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean = { _, _ -> false },
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    AppScaffold(
        title = R.string.goods_comment_title,
        onBackClick = onBackClick
    ) {
        BaseNetWorkListView(
            uiState = uiState,
            onRetry = onRetry
        ) {
            GoodsCommentContentView(
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
 * 商品评论内容视图
 *
 * @param data 商品评论列表数据
 * @param isRefreshing 是否正在刷新
 * @param loadMoreState 加载更多状态
 * @param onRefresh 下拉刷新回调
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 是否应触发加载更多的判断函数
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GoodsCommentContentView(
    data: List<Comment>,
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
        // 商品评论列表项
        items(data.size) { index ->
            CommentCard(
                comment = data[index]
            )
        }
    }
}

/**
 * 商品评论界面浅色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun GoodsCommentScreenPreview() {
    AppTheme {
        GoodsCommentScreen(
            uiState = BaseNetWorkListUiState.Success
        )
    }
}

/**
 * 商品评论界面深色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun GoodsCommentScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        GoodsCommentScreen(
            uiState = BaseNetWorkListUiState.Success
        )
    }
}