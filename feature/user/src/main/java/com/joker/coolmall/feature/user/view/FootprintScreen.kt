package com.joker.coolmall.feature.user.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.model.entity.Footprint
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.ui.component.empty.EmptyData
import com.joker.coolmall.core.ui.component.goods.GoodsGridItem
import com.joker.coolmall.core.ui.component.loading.PageLoading
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.user.R
import com.joker.coolmall.feature.user.state.FootprintUiState
import com.joker.coolmall.feature.user.viewmodel.FootprintViewModel

/**
 * 用户足迹路由
 *
 * @param viewModel 用户足迹ViewModel
 * @author Joker.X
 */
@Composable
internal fun FootprintRoute(
    viewModel: FootprintViewModel = hiltViewModel()
) {
    // 收集页面UI状态
    val uiState by viewModel.uiState.collectAsState()
    // 收集足迹数据
    val footprints by viewModel.footprints.collectAsStateWithLifecycle()
    // 收集足迹总数
    val footprintCount by viewModel.footprintCount.collectAsStateWithLifecycle()

    FootprintScreen(
        uiState = uiState,
        footprints = footprints,
        footprintCount = footprintCount,
        onBackClick = viewModel::navigateBack,
        onFootprintClick = viewModel::toGoodsDetail,
        onClearAll = viewModel::clearAllFootprints
    )
}

/**
 * 用户足迹界面
 *
 * @param uiState 页面UI状态
 * @param footprints 足迹列表
 * @param footprintCount 足迹总数
 * @param onBackClick 返回上一页回调
 * @param onFootprintClick 点击足迹项回调
 * @param onClearAll 清空所有足迹回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FootprintScreen(
    uiState: FootprintUiState = FootprintUiState.Loading,
    footprints: List<Footprint> = emptyList(),
    footprintCount: Int = 0,
    onBackClick: () -> Unit = {},
    onFootprintClick: (Long) -> Unit = {},
    onClearAll: () -> Unit = {}
) {
    AppScaffold(
        title = R.string.footprint_title,
        onBackClick = onBackClick,
        topBarActions = {
            if (footprints.isNotEmpty()) {
                TextButton(onClick = onClearAll) {
                    Text(
                        text = stringResource(R.string.clear_with_count, footprintCount),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    ) {
        AnimatedContent(
            targetState = uiState,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith
                        fadeOut(animationSpec = tween(300))
            }
        ) { state ->
            when (state) {
                is FootprintUiState.Loading -> PageLoading()
                is FootprintUiState.Empty -> EmptyData()
                is FootprintUiState.Success -> {
                    FootprintContentView(
                        footprints = footprints,
                        onFootprintClick = onFootprintClick,
                    )
                }
            }
        }
    }
}

/**
 * 足迹内容视图
 *
 * @param footprints 足迹列表
 * @param onFootprintClick 点击足迹项回调
 * @param modifier 修饰符
 * @author Joker.X
 */
@Composable
private fun FootprintContentView(
    footprints: List<Footprint>,
    onFootprintClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(SpacePaddingMedium),
        horizontalArrangement = Arrangement.spacedBy(SpacePaddingMedium),
        verticalItemSpacing = SpacePaddingMedium
    ) {
        items(footprints.size) { index ->
            GoodsGridItem(
                goods = footprints[index].toGoods(),
                onClick = onFootprintClick
            )
        }
    }
}

/**
 * 将Footprint转换为Goods对象，用于GoodsGridItem组件
 *
 * @return Goods对象
 * @author Joker.X
 */
private fun Footprint.toGoods(): Goods {
    return Goods(
        id = this.goodsId,
        title = this.goodsName,
        subTitle = this.goodsSubTitle,
        mainPic = this.goodsMainPic,
        price = this.goodsPrice,
        sold = this.goodsSold
    )
}

/**
 * 我的足迹界面浅色主题预览
 *
 * @author Joker.X
 */
@Composable
@Preview(showBackground = true)
fun FootprintScreenPreview() {
    AppTheme {
        FootprintScreen()
    }
}

/**
 * 我的足迹界面深色主题预览
 *
 * @author Joker.X
 */
@Composable
@Preview(showBackground = true)
fun FootprintScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        FootprintScreen()
    }
}