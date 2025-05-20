package com.joker.coolmall.feature.main.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.LogoIcon
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.model.entity.Banner
import com.joker.coolmall.core.model.entity.Category
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.model.entity.Home
import com.joker.coolmall.core.ui.component.card.AppCard
import com.joker.coolmall.core.ui.component.goods.GoodsGridItem
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.swiper.WeSwiper
import com.joker.coolmall.core.ui.component.title.TitleWithLine
import com.joker.coolmall.feature.main.R
import com.joker.coolmall.feature.main.component.CommonScaffold
import com.joker.coolmall.feature.main.component.FlashSaleItem
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
        toGoodsDetail = viewModel::toGoodsDetail,
        onRetry = viewModel::retryRequest
    )
}

/**
 * 首页UI
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    uiState: BaseNetWorkUiState<Home> = BaseNetWorkUiState.Loading,
    toGoodsDetail: (Long) -> Unit = {},
    onRetry: () -> Unit = {}
) {
    CommonScaffold(
        topBar = { HomeTopAppBar() }
    ) {
        BaseNetWorkView(
            uiState = uiState,
            padding = it,
            onRetry = onRetry
        ) {
            HomeContentView(
                data = it,
                toGoodsDetail = toGoodsDetail
            )
        }
    }
}

/**
 * 主页内容
 */
@Composable
private fun HomeContentView(data: Home, toGoodsDetail: (Long) -> Unit) {
    // 创建一个主垂直滚动容器
    val scrollState = rememberScrollState()
    // 所有内容放在一个垂直滚动的Column中
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(SpacePaddingMedium)
    ) {

        // 轮播图
        data.banner.let {
            Banner(it!!)
            SpaceVerticalMedium()
        }

        // 分类
        data.category.let {
            Category(it!!)
            SpaceVerticalMedium()
        }

        // 限时精选
        data.flashSale.let {
            FlashSale(
                goods = it!!,
                toGoodsDetail = toGoodsDetail
            )
            SpaceVerticalMedium()
        }

        // 推荐商品标题和列表
        data.goods.let {
            TitleWithLine(
                text = "推荐商品",
                modifier = Modifier.padding(vertical = SpaceVerticalSmall)
            )
            // 商品列表 - 使用Row+Column布局
            ProductsGrid(
                goods = it!!,
                toGoodsDetail = toGoodsDetail
            )
        }
    }
}

/**
 * 商品网格实现
 */
@Composable
private fun ProductsGrid(goods: List<Goods>, toGoodsDetail: (Long) -> Unit) {
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
                        onClick = toGoodsDetail,
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

        NetWorkImage(
            model = item,
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
    AppCard {
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

/**
 * 分类项
 */
@Composable
private fun CategoryItem(category: Category, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        NetWorkImage(
            model = category.pic,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(1f)
                .clip(CircleShape)
        )
        SpaceVerticalXSmall()
        Text(text = category.name)
    }
}

/**
 * 限时精选卡片 - 使用LazyRow
 */
@Composable
private fun FlashSale(goods: List<Goods>, toGoodsDetail: (Long) -> Unit) {
    Card {
        AppListItem(
            title = "限时精选",
            trailingText = "查看全部",
            leadingIcon = R.drawable.ic_time,
        )

        // 商品列表 - 使用LazyRow
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(SpaceHorizontalSmall),
            modifier = Modifier.padding(SpacePaddingMedium)
        ) {
            items(goods.size) { index ->
                val goods = goods[index]
                FlashSaleItem(goods = goods, onClick = toGoodsDetail)
            }
        }
    }
}

/**
 * 首页顶部导航栏
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopAppBar() {
    TopAppBar(
        navigationIcon = {
            LogoIcon(
                modifier = Modifier.padding(horizontal = 8.dp),
                size = 34.dp
            )
        },
        title = {
            // 中间搜索框
            Card(
                shape = ShapeMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp)
                    .clip(ShapeMedium)
                    .clickable { }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "搜索",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.size(18.dp)
                    )

                    Text(
                        text = "搜索商品",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        },
        actions = {
            IconButton(
                onClick = { },
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(27.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_github),
                    contentDescription = null,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen()
    }
}