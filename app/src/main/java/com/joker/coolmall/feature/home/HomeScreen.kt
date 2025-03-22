package com.joker.coolmall.feature.home

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.joker.coolmall.R
import com.joker.coolmall.core.designsystem.theme.ColorDanger
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.ui.component.swiper.WeSwiper

/**
 * 首页路由入口
 */
@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeScreen(
        onNavigateToGoodsDetail = viewModel::navigateToGoodsDetail
    )
}

/**
 * 首页UI - 参考SpotifyHome实现
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    onNavigateToGoodsDetail: (String) -> Unit = {}
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
            // 所有内容放在一个垂直滚动的Column中
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(SpaceHorizontalLarge)
            ) {

                // 轮播图
                Banner()

                Spacer(modifier = Modifier.height(SpaceVerticalLarge))

                // 分类
                Category()

                Spacer(modifier = Modifier.height(SpaceVerticalLarge))

                // 限时精选
                FlashSale()

                Spacer(modifier = Modifier.height(SpaceVerticalLarge))

                // 推荐商品标题
                Text(
                    text = "推荐商品",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = SpaceVerticalSmall)
                )

                // 商品列表 - 使用Row+Column布局
                ProductsGrid(onNavigateToGoodsDetail = onNavigateToGoodsDetail)

            }
        }
    }
}

/**
 * 商品网格实现 - 使用Row+Column布局
 */
@Composable
private fun ProductsGrid(onNavigateToGoodsDetail: (String) -> Unit = {}) {
    val items = remember {
        listOf(
            Triple(
                "https://mp-cf9001d2-d2aa-44c0-b86b-b7c05262ef8a.cdn.bspapp.com/test/4651da21ff937120fef3e374fff6ca79.pic",
                "男童春秋套装2023新款儿童装",
                "¥98"
            ),
            Triple(
                "https://mp-cf9001d2-d2aa-44c0-b86b-b7c05262ef8a.cdn.bspapp.com/test/fd638da6cdcb64495e7b3269783a6fcf.pic",
                "iPhone 14 Pro Max 256GB 暗紫色",
                "¥8999"
            ),
            Triple(
                "https://mp-cf9001d2-d2aa-44c0-b86b-b7c05262ef8a.cdn.bspapp.com/test/4651da21ff937120fef3e374fff6ca79.pic",
                "华为 Mate60 Pro 12GB+512GB 雅川青",
                "¥8999"
            ),
            Triple(
                "https://mp-cf9001d2-d2aa-44c0-b86b-b7c05262ef8a.cdn.bspapp.com/test/fd638da6cdcb64495e7b3269783a6fcf.pic",
                "小米14 Pro 16GB+1TB 钛金属",
                "¥6999"
            ),
            Triple(
                "https://mp-cf9001d2-d2aa-44c0-b86b-b7c05262ef8a.cdn.bspapp.com/test/4651da21ff937120fef3e374fff6ca79.pic",
                "男童春秋套装2023新款儿童装",
                "¥98"
            ),
            Triple(
                "https://mp-cf9001d2-d2aa-44c0-b86b-b7c05262ef8a.cdn.bspapp.com/test/fd638da6cdcb64495e7b3269783a6fcf.pic",
                "iPhone 14 Pro Max 256GB 暗紫色",
                "¥8999"
            ),
            Triple(
                "https://mp-cf9001d2-d2aa-44c0-b86b-b7c05262ef8a.cdn.bspapp.com/test/4651da21ff937120fef3e374fff6ca79.pic",
                "华为 Mate60 Pro 12GB+512GB 雅川青",
                "¥8999"
            ),
            Triple(
                "https://mp-cf9001d2-d2aa-44c0-b86b-b7c05262ef8a.cdn.bspapp.com/test/fd638da6cdcb64495e7b3269783a6fcf.pic",
                "小米14 Pro 16GB+1TB 钛金属",
                "¥6999"
            ),
        )
    }

    // 将商品列表按每行2个进行分组
    val rows = items.chunked(2)

    // 使用Column+Row组合布局
    Column(
        verticalArrangement = Arrangement.spacedBy(SpaceVerticalSmall)
    ) {
        rows.forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(SpaceHorizontalSmall),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach { (image, name, price) ->
                    // 每个商品卡片
                    Card(
                        onClick = { onNavigateToGoodsDetail(name) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(SpaceHorizontalSmall)
                        ) {
                            AsyncImage(
                                model = image,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(6.dp))
                            )
                            SpaceVerticalXSmall()
                            Text(
                                text = name,
                                style = MaterialTheme.typography.bodyLarge,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            SpaceVerticalXSmall()
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = price,
                                    style = MaterialTheme.typography.displayMedium,
                                    color = ColorDanger
                                )
                                Text(
                                    text = "已售 ${(100..999).random()}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                            }
                        }
                    }
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
private fun Banner() {
    // 轮播图数据列表
    val banners = remember {
        listOf(
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/0b2bb13c396cc6205dd91da3a91a275a.jpg?f=webp&w=1080&h=540&bg=A8D4D5",
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/1ab1ffaaeb5ca4c02674e9f35b1fd17c.jpg?f=webp&w=1080&h=540&bg=59A5FD",
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/37bd342303515c7a1a54681599e319a1.jpg?f=webp&w=1080&h=540&bg=56B6A8",
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/a2b3ab270e5ae4c9e85d6607cdb97008.jpg?f=webp&w=1080&h=540&bg=DC85AF"
        )
    }

    // 轮播图页面状态管理
    val state = rememberPagerState { banners.size }

    WeSwiper(
        state = state,
        options = banners,
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
private fun Category() {
    Card {
        // 改用普通布局，避免使用LazyVerticalGrid
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceVerticalSmall)
        ) {
            // 第一行：前5个分类
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                for (index in 0 until 5) {
                    CategoryItem(index, Modifier.weight(1f))
                }
            }

            // 第二行：后5个分类
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                for (index in 5 until 10) {
                    CategoryItem(index, Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun CategoryItem(index: Int, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        AsyncImage(
            model = "https://mp-cf9001d2-d2aa-44c0-b86b-b7c05262ef8a.cdn.bspapp.com/test/4651da21ff937120fef3e374fff6ca79.pic",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(1f)
                .clip(CircleShape)
        )
        SpaceVerticalXSmall()
        Text(
            text = when (index) {
                0 -> "手机"
                1 -> "服饰"
                2 -> "电脑"
                3 -> "优惠券"
                4 -> "洗护"
                5 -> "摄影"
                6 -> "鞋子"
                7 -> "会员"
                8 -> "包包"
                else -> "网络"
            }
        )
    }
}

/**
 * 限时精选卡片 - 使用LazyRow
 */
@Composable
private fun FlashSale() {
    // 模拟商品数据
    val items = remember {
        listOf(
            Triple(
                "https://mp-cf9001d2-d2aa-44c0-b86b-b7c05262ef8a.cdn.bspapp.com/test/fd638da6cdcb64495e7b3269783a6fcf.pic",
                "iPhone 14 Pro",
                "¥8400"
            ),
            Triple(
                "https://mp-cf9001d2-d2aa-44c0-b86b-b7c05262ef8a.cdn.bspapp.com/test/fd638da6cdcb64495e7b3269783a6fcf.pic",
                "华为 Mate60 Pro",
                "¥8600"
            ),
            Triple(
                "https://mp-cf9001d2-d2aa-44c0-b86b-b7c05262ef8a.cdn.bspapp.com/test/fd638da6cdcb64495e7b3269783a6fcf.pic",
                "小米 13",
                "¥3999"
            ),
            Triple(
                "https://mp-cf9001d2-d2aa-44c0-b86b-b7c05262ef8a.cdn.bspapp.com/test/fd638da6cdcb64495e7b3269783a6fcf.pic",
                "Redmi Note 12",
                "¥1599"
            ),
            Triple(
                "https://mp-cf9001d2-d2aa-44c0-b86b-b7c05262ef8a.cdn.bspapp.com/test/fd638da6cdcb64495e7b3269783a6fcf.pic",
                "OPPO Find X6",
                "¥4999"
            )
        )
    }

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
                items(items) { (image, name, price) ->
                    Column(
                        modifier = Modifier.width(120.dp)
                    ) {
                        AsyncImage(
                            model = image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(6.dp))
                        )
                        SpaceVerticalXSmall()
                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        SpaceVerticalXSmall()
                        Text(
                            text = price,
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