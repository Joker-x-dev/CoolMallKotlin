package com.joker.coolmall.feature.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
 * 首页UI
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    onNavigateToGoodsDetail: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("主页") })
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(SpaceHorizontalLarge),
        ) {
            Column {
                Banner()
                SpaceVerticalLarge()
                Category()
                SpaceVerticalLarge()
                FlashSale()
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
        // 功能按钮网格
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceVerticalSmall),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(10) { index ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = "https://mp-cf9001d2-d2aa-44c0-b86b-b7c05262ef8a.cdn.bspapp.com/test/4651da21ff937120fef3e374fff6ca79.pic",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
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
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

/**
 * 限时精选卡片
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
                "小米 13",
                "¥3999"
            ),
            Triple(
                "https://mp-cf9001d2-d2aa-44c0-b86b-b7c05262ef8a.cdn.bspapp.com/test/fd638da6cdcb64495e7b3269783a6fcf.pic",
                "小米 13",
                "¥3999"
            ),
            Triple(
                "https://mp-cf9001d2-d2aa-44c0-b86b-b7c05262ef8a.cdn.bspapp.com/test/fd638da6cdcb64495e7b3269783a6fcf.pic",
                "小米 13",
                "¥3999"
            ),
            Triple(
                "https://mp-cf9001d2-d2aa-44c0-b86b-b7c05262ef8a.cdn.bspapp.com/test/fd638da6cdcb64495e7b3269783a6fcf.pic",
                "小米 13",
                "¥3999"
            ),
            Triple(
                "https://mp-cf9001d2-d2aa-44c0-b86b-b7c05262ef8a.cdn.bspapp.com/test/fd638da6cdcb64495e7b3269783a6fcf.pic",
                "小米 13",
                "¥3999"
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

            // 商品列表
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(SpaceHorizontalSmall),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    horizontal = SpaceHorizontalSmall
                )
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
                            maxLines = 2
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