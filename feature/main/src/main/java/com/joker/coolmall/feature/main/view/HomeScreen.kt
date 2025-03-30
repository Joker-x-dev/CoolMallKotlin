package com.joker.coolmall.feature.main.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.joker.coolmall.core.designsystem.theme.ColorDanger
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.model.Banner
import com.joker.coolmall.core.model.Category
import com.joker.coolmall.core.model.Goods
import com.joker.coolmall.core.ui.component.empty.EmptyNetwork
import com.joker.coolmall.core.ui.component.goods.GoodsGridItem
import com.joker.coolmall.core.ui.component.loading.PageLoading
import com.joker.coolmall.core.ui.component.swiper.WeSwiper
import com.joker.coolmall.core.ui.component.title.TitleWithLine
import com.joker.coolmall.feature.main.R
import com.joker.coolmall.feature.main.state.HomeUiState
import com.joker.coolmall.feature.main.viewmodel.HomeViewModel

/**
 * 首页路由入口
 */
@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    HomeScreen(
        uiState = uiState,
        onNavigateToGoodsDetail = viewModel::navigateToGoodsDetail,
        onRetry = viewModel::getHomeData
    )
}

/**
 * 首页UI
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    uiState: HomeUiState = HomeUiState.Loading,
    onNavigateToGoodsDetail: (String) -> Unit = {},
    onRetry: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("主页") })
        },
        //排除底部导航栏边距
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)

    ) { paddingValues ->

        // 创建一个主垂直滚动容器
        val scrollState = rememberScrollState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState) {
                is HomeUiState.Loading -> PageLoading()
                is HomeUiState.Error -> EmptyNetwork(onRetryClick = onRetry)
                is HomeUiState.Success -> {
                    // 所有内容放在一个垂直滚动的Column中
                    Column(
                        modifier = Modifier
                            .verticalScroll(scrollState)
                            .padding(SpaceHorizontalLarge)
                    ) {

                        // 轮播图
                        uiState.data.banner.let {
                            Banner(it!!)
                            SpaceVerticalLarge()
                        }

                        // 分类
                        uiState.data.category.let {
                            Category(it!!)
                            SpaceVerticalLarge()
                        }

                        // 限时精选
                        uiState.data.flashSale.let {
                            FlashSale(it!!)
                            SpaceVerticalLarge()
                        }

                        // 推荐商品标题和列表
                        uiState.data.goods.let {
                            TitleWithLine(
                                text = "推荐商品",
                                modifier = Modifier.padding(vertical = SpaceVerticalSmall)
                            )
                            // 商品列表 - 使用Row+Column布局
                            ProductsGrid(
                                goods = it!!,
                                onNavigateToGoodsDetail = onNavigateToGoodsDetail
                            )
                        }

                    }
                }
            }
        }

    }
}

/**
 * 商品网格实现
 */
@Composable
private fun ProductsGrid(goods: List<Goods>, onNavigateToGoodsDetail: (String) -> Unit = {}) {
    // 将商品列表按每行2个进行分组
    val rows = goods.chunked(2)

    // 使用Column+Row组合布局
    Column(
        verticalArrangement = Arrangement.spacedBy(SpaceVerticalSmall)
    ) {
        rows.forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(SpaceHorizontalSmall),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach { goods ->
                    GoodsGridItem(
                        goods = goods,
                        onClick = onNavigateToGoodsDetail,
                        modifier = Modifier.weight(1f)
                    )
                }

                // 如果一行只有一个商品，添加空白占位
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

/**
 * 顶部轮播图
 */
@Composable
private fun Banner(banners: List<Banner>) {
    // 轮播图数据列表
    val bannerUrls = remember(banners) {
        banners.map { it.pic }
    }

    // 轮播图页面状态管理
    val state = rememberPagerState { bannerUrls.size }

    WeSwiper(
        state = state,
        options = bannerUrls,
        // 设置圆角裁剪
        modifier = Modifier.clip(ShapeMedium),
    ) { index, item ->
        // 根据当前页面和模式设置缩放动画
        val animatedScale by animateFloatAsState(
            targetValue = 1f,
            label = ""
        )

        // 使用AsyncImage加载网络图片
        AsyncImage(
            model = item,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 1f)
                .scale(animatedScale)
                .clip(RoundedCornerShape(6.dp))
        )
    }
}

/**
 * 分类
 */
@Composable
private fun Category(categories: List<Category>) {
    Card {
        // 改用普通布局，避免使用LazyVerticalGrid
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceVerticalSmall)
        ) {
            // 将分类列表限制为前10个并按每行5个进行分组
            val rows = categories.take(10).chunked(5)

            // 遍历每一行分类
            rows.forEach { rowCategories ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowCategories.forEach { category ->
                        CategoryItem(category, Modifier.weight(1f))
                    }

                    // 如果一行不满5个，添加空白占位
                    repeat(5 - rowCategories.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryItem(category: Category, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        AsyncImage(
            model = category.pic,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(1f)
                .clip(CircleShape)
        )
        SpaceVerticalXSmall()
        Text(
            text = category.name
        )
    }
}

/**
 * 限时精选卡片 - 使用LazyRow
 */
@Composable
private fun FlashSale(goods: List<Goods>) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceHorizontalLarge)
        ) {
            // 标题栏
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = SpaceVerticalXSmall),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clock),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                    SpaceHorizontalSmall()
                    Text(
                        text = "限时精选",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            SpaceVerticalSmall()

            // 商品列表 - 使用LazyRow
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(SpaceHorizontalSmall),
                contentPadding = PaddingValues(horizontal = SpaceHorizontalSmall)
            ) {
                items(goods.size) { index ->
                    val goods = goods[index]
                    Column(
                        modifier = Modifier.width(120.dp)
                    ) {
                        AsyncImage(
                            model = goods.mainPic,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(6.dp))
                        )
                        SpaceVerticalXSmall()
                        Text(
                            text = goods.title,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        SpaceVerticalXSmall()
                        Text(
                            text = "¥${goods.price}",
                            style = MaterialTheme.typography.titleMedium,
                            color = ColorDanger
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}