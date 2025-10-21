package com.joker.coolmall.feature.goods.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.ShapeCircle
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.model.entity.GoodsSearchKeyword
import com.joker.coolmall.core.model.entity.SearchHistory
import com.joker.coolmall.core.model.preview.previewGoodsSearchKeywordList
import com.joker.coolmall.core.ui.component.appbar.SearchTopAppBar
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.feature.goods.R
import com.joker.coolmall.feature.goods.viewmodel.GoodsSearchViewModel

/**
 * 商品搜索路由
 *
 * @param viewModel 商品搜索 ViewModel
 * @author Joker.X
 */
@Composable
internal fun GoodsSearchRoute(
    viewModel: GoodsSearchViewModel = hiltViewModel()
) {
    // UI状态
    val uiState by viewModel.uiState.collectAsState()
    // 搜索历史列表
    val searchHistoryList by viewModel.searchHistoryList.collectAsState()

    GoodsSearchScreen(
        uiState = uiState,
        searchHistoryList = searchHistoryList,
        onBackClick = viewModel::navigateBack,
        onRetry = viewModel::retryRequest,
        onSearch = viewModel::onSearch,
        onKeywordClick = viewModel::onKeywordClick,
        onSearchHistoryClick = viewModel::onSearchHistoryClick,
        onClearSearchHistory = viewModel::clearSearchHistory
    )
}

/**
 * 商品搜索界面
 *
 * @param uiState UI状态
 * @param searchHistoryList 搜索历史列表
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 * @param onSearch 搜索回调
 * @param onKeywordClick 关键词点击回调
 * @param onSearchHistoryClick 搜索历史点击回调
 * @param onClearSearchHistory 清空搜索历史回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GoodsSearchScreen(
    uiState: BaseNetWorkUiState<List<GoodsSearchKeyword>> = BaseNetWorkUiState.Loading,
    searchHistoryList: List<SearchHistory> = emptyList(),
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {},
    onSearch: (String) -> Unit = {},
    onKeywordClick: (String) -> Unit = {},
    onSearchHistoryClick: (SearchHistory) -> Unit = {},
    onClearSearchHistory: () -> Unit = {}
) {
    AppScaffold(
        topBar = {
            SearchTopAppBar(
                onBackClick = onBackClick,
                onSearch = onSearch
            )
        }
    ) {
        BaseNetWorkView(
            uiState = uiState,
            onRetry = onRetry
        ) { keywordList ->
            GoodsSearchContentView(
                keywordList = keywordList,
                searchHistoryList = searchHistoryList,
                onKeywordClick = onKeywordClick,
                onSearchHistoryClick = onSearchHistoryClick,
                onClearSearchHistory = onClearSearchHistory
            )
        }
    }
}

/**
 * 商品搜索内容视图
 *
 * @param keywordList 推荐关键词列表
 * @param searchHistoryList 搜索历史列表
 * @param onKeywordClick 关键词点击回调
 * @param onSearchHistoryClick 搜索历史点击回调
 * @param onClearSearchHistory 清空搜索历史回调
 * @author Joker.X
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GoodsSearchContentView(
    keywordList: List<GoodsSearchKeyword>,
    searchHistoryList: List<SearchHistory>,
    onKeywordClick: (String) -> Unit,
    onSearchHistoryClick: (SearchHistory) -> Unit,
    onClearSearchHistory: () -> Unit
) {
    VerticalList(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {

        // 搜索历史部分
        if (searchHistoryList.isNotEmpty()) {
            SearchHistorySection(
                searchHistoryList = searchHistoryList,
                onSearchHistoryClick = onSearchHistoryClick,
                onClearSearchHistory = onClearSearchHistory
            )

            SpaceVerticalXSmall()
        }

        // 推荐搜索部分
        RecommendSearchSection(
            keywordList = keywordList,
            onKeywordClick = onKeywordClick
        )
    }
}

/**
 * 搜索历史部分
 *
 * @param searchHistoryList 搜索历史列表
 * @param onSearchHistoryClick 搜索历史点击回调
 * @param onClearSearchHistory 清空搜索历史回调
 * @author Joker.X
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SearchHistorySection(
    searchHistoryList: List<SearchHistory>,
    onSearchHistoryClick: (SearchHistory) -> Unit,
    onClearSearchHistory: () -> Unit
) {
    Column {
        AppListItem(
            "",
            leadingContent = {
                AppText(stringResource(R.string.search_history), size = TextSize.TITLE_LARGE)
            },
            showArrow = false,
            showDivider = false,
            horizontalPadding = 0.dp,
            verticalPadding = 0.dp,
            trailingContent = {
                CommonIcon(
                    resId = R.drawable.ic_delete,
                    size = 18.dp,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.clickable { onClearSearchHistory() }
                )
            }
        )

        SpaceVerticalSmall()

        // 搜索历史标签
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(SpaceHorizontalSmall),
            verticalArrangement = Arrangement.spacedBy(SpaceVerticalSmall)
        ) {
            searchHistoryList.forEach { searchHistory ->
                SearchHistoryTag(
                    text = searchHistory.keyword,
                    onClick = { onSearchHistoryClick(searchHistory) }
                )
            }
        }
    }
}

/**
 * 推荐搜索部分
 *
 * @param keywordList 推荐关键词列表
 * @param onKeywordClick 关键词点击回调
 * @author Joker.X
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RecommendSearchSection(
    keywordList: List<GoodsSearchKeyword>,
    onKeywordClick: (String) -> Unit
) {
    Column {
        AppText(stringResource(R.string.recommended_search), size = TextSize.TITLE_LARGE)

        SpaceVerticalSmall()

        // 推荐关键词标签
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(SpaceHorizontalSmall),
            verticalArrangement = Arrangement.spacedBy(SpaceVerticalSmall)
        ) {
            keywordList.forEach { keyword ->
                SearchHistoryTag(
                    text = keyword.name,
                    onClick = { onKeywordClick(keyword.name) }
                )
            }
        }
    }
}

/**
 * 搜索历史标签
 *
 * @param text 标签文本
 * @param onClick 点击回调
 * @author Joker.X
 */
@Composable
private fun SearchHistoryTag(
    text: String,
    onClick: () -> Unit
) {
    Card(
        shape = ShapeCircle,
        modifier = Modifier
            .clip(ShapeCircle)
            .clickable { onClick() }
    ) {
        AppText(
            text = text,
            modifier = Modifier.padding(
                horizontal = SpaceHorizontalLarge,
                vertical = SpaceVerticalSmall
            )
        )
    }
}

/**
 * 商品搜索界面浅色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun GoodsSearchScreenPreview() {
    AppTheme {
        GoodsSearchScreen(
            uiState = BaseNetWorkUiState.Success(
                data = previewGoodsSearchKeywordList
            ),
            searchHistoryList = listOf(
                SearchHistory(keyword = "小米手机", searchTime = System.currentTimeMillis()),
                SearchHistory(keyword = "苹果", searchTime = System.currentTimeMillis()),
                SearchHistory(keyword = "三星手机", searchTime = System.currentTimeMillis())
            )
        )
    }
}

/**
 * 商品搜索界面深色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun GoodsSearchScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        GoodsSearchScreen(
            uiState = BaseNetWorkUiState.Success(
                data = previewGoodsSearchKeywordList
            ),
            searchHistoryList = listOf(
                SearchHistory(keyword = "小米手机", searchTime = System.currentTimeMillis()),
                SearchHistory(keyword = "苹果", searchTime = System.currentTimeMillis()),
                SearchHistory(keyword = "三星手机", searchTime = System.currentTimeMillis())
            )
        )
    }
}