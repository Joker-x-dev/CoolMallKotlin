package com.joker.coolmall.feature.main.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.ui.component.appbar.CenterTopAppBar
import com.joker.coolmall.core.ui.component.empty.EmptyNetwork
import com.joker.coolmall.core.ui.component.loading.PageLoading
import com.joker.coolmall.core.ui.component.refresh.RefreshLayout
import com.joker.coolmall.feature.main.R
import com.joker.coolmall.feature.main.component.CommonScaffold
import com.joker.coolmall.feature.main.state.CategoryUiState
import com.joker.coolmall.feature.main.viewmodel.CategoryViewModel

/**
 * 分类页面路由入口
 */
@Composable
internal fun CategoryRoute(
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    CategoryScreen(
        uiState = uiState,
        onRefresh = viewModel::refreshData,
        onLoadMore = viewModel::loadMoreData,
        onRetry = viewModel::loadInitialData
    )
}

/**
 * 分类页面UI
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CategoryScreen(
    uiState: CategoryUiState = CategoryUiState.Loading,
    onRefresh: ((Boolean) -> Unit) -> Unit = {},
    onLoadMore: ((Boolean, Boolean) -> Unit) -> Unit = {},
    onRetry: () -> Unit = {}
) {
    CommonScaffold(
        topBar = {
            CenterTopAppBar(R.string.category, showBackIcon = false)
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            // 主内容布局
            when (uiState) {
                is CategoryUiState.Loading -> PageLoading()
                is CategoryUiState.Error -> EmptyNetwork(onRetryClick = onRetry)
                is CategoryUiState.Success -> CategoryContent(
                    items = uiState.data,
                    onRefresh = onRefresh,
                    onLoadMore = onLoadMore
                )
            }
        }
    }
}

/**
 * 分类内容区
 */
@Composable
private fun CategoryContent(
    items: List<String>,
    onRefresh: ((Boolean) -> Unit) -> Unit,
    onLoadMore: ((Boolean, Boolean) -> Unit) -> Unit
) {
    RefreshLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(SpacePaddingMedium),
        onRefresh = onRefresh,
        onLoadMore = onLoadMore
    ) {
        // 直接使用Column显示列表，配合NestedScrollView
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(SpaceVerticalMedium)
        ) {
            items.forEach { item ->
                CategoryItem(title = item)
            }
        }
    }
}

/**
 * 分类项
 */
@Composable
private fun CategoryItem(title: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {
    CategoryScreen()
} 