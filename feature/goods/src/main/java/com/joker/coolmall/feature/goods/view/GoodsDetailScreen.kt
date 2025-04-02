package com.joker.coolmall.feature.goods.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.BorderLight
import com.joker.coolmall.core.designsystem.theme.ColorDanger
import com.joker.coolmall.core.designsystem.theme.DisplayLarge
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.ShapeCircle
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceDivider
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingXSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.ui.component.card.AppCard
import com.joker.coolmall.core.ui.component.loading.PageLoading
import com.joker.coolmall.core.ui.component.swiper.WeSwiper
import com.joker.coolmall.feature.goods.R
import com.joker.coolmall.feature.goods.viewmodel.GoodsDetailUiState
import com.joker.coolmall.feature.goods.viewmodel.GoodsDetailViewModel

/**
 * 商品详情页面路由入口
 */
@Composable
internal fun GoodsDetailRoute(
    viewModel: GoodsDetailViewModel = hiltViewModel()
) {
    // 从ViewModel收集UI状态
    val uiState by viewModel.uiState.collectAsState()

    GoodsDetailScreen(
        uiState = uiState,
        onBackClick = { viewModel.navigateBack() }
    )
}

/**
 * 商品详情页面UI
 * @param uiState 商品详情UI状态
 * @param onBackClick 返回按钮点击回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GoodsDetailScreen(
    uiState: GoodsDetailUiState,
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("商品详情") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: 分享功能 */ }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "分享"
                        )
                    }
                }
            )
        },
        bottomBar = {
            // 底部操作栏
            GoodsActionBar()
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isLoading) {
                // 显示加载中状态
                PageLoading()
            } else if (uiState.error != null) {
                // 显示错误状态
                Text(
                    text = "加载失败: ${uiState.error}",
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                // 显示商品详情
                GoodsDetailContent(
                    title = uiState.goodsName,
                    subTitle = uiState.goodsSubTitle,
                    price = uiState.goodsPrice,
                    images = uiState.goodsPics,
                    content = uiState.goodsContent
                )
            }
        }
    }
}

/**
 * 商品详情内容
 */
@Composable
private fun GoodsDetailContent(
    title: String,
    subTitle: String,
    price: String,
    images: List<String>,
    content: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 主要内容区域
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // 轮播图
            GoodsBanner(images)

            Column(
                modifier = Modifier.padding(SpacePaddingMedium)
            ) {

                // 基本信息
                GoodsInfoCard(title, subTitle, price)

                SpaceVerticalMedium()

                // 配送信息
                GoodsDeliveryCard()

                SpaceVerticalMedium()

                // 图文详情
                GoodsDetailCard(content)
            }
        }
    }
}

/**
 * 商品轮播图
 */
@Composable
private fun GoodsBanner(images: List<String>) {
    // 轮播图数据列表
    val bannerUrls = remember(images) { images }

    // 轮播图页面状态管理
    val state = rememberPagerState { bannerUrls.size }

    WeSwiper(
        state = state,
        options = bannerUrls,
        // 设置圆角裁剪
        modifier = Modifier,
        autoplay = false
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
                .aspectRatio(1f)
                .scale(animatedScale)
        )
    }
}

/**
 * 商品信息卡片
 */
@Composable
private fun GoodsInfoCard(title: String, subTitle: String, price: String) {
    AppCard {
        // 价格和已售标签行
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 价格
            Text(
                text = price,
                style = DisplayLarge,
                color = ColorDanger,
                fontWeight = FontWeight.Bold
            )

            // 已售标签
            SoldCountTag(count = 99)
        }

        // 优惠券列表
        CouponList()

        SpaceVerticalMedium()

        // 标题
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        SpaceVerticalXSmall()

        // 副标题
        Text(
            text = subTitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        SpaceVerticalMedium()

        // 规格选择
        SpecSelection()
    }
}

/**
 * 已售数量标签
 */
@Composable
private fun SoldCountTag(count: Int) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(
                color = Primary,
                shape = ShapeCircle
            )
            .padding(horizontal = SpacePaddingSmall, vertical = SpacePaddingXSmall)
    ) {
        Text(
            text = "已售 $count",
            style = MaterialTheme.typography.labelSmall,
            color = Color.White
        )
    }
}

/**
 * 优惠券列表
 */
@Composable
private fun CouponList() {
    Spacer(modifier = Modifier.height(SpaceVerticalSmall))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(SpaceVerticalSmall)
    ) {
        CouponTag("满188减88元")
        CouponTag("满100减9元")
    }
}

/**
 * 优惠券标签
 */
@Composable
private fun CouponTag(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(
                width = SpaceDivider,
                color = ColorDanger,
                shape = RoundedCornerShape(SpaceVerticalXSmall)
            )
            .padding(horizontal = SpacePaddingSmall, vertical = SpaceVerticalXSmall)
    ) {
        Icon(
            imageVector = Icons.Outlined.Settings,
            contentDescription = null,
            tint = ColorDanger,
            modifier = Modifier.size(SpaceVerticalMedium)
        )
        Spacer(modifier = Modifier.width(SpaceVerticalXSmall))
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = ColorDanger
        )
    }
}

/**
 * 规格选择
 */
@Composable
private fun SpecSelection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(ShapeSmall)
            .border(
                width = 1.2.dp,
                color = BorderLight,
                shape = ShapeSmall
            )
            .clickable { /* TODO: 打开规格选择 */ }
            .padding(SpacePaddingMedium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = "规格",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.size(SpaceVerticalLarge)
            )

            SpaceHorizontalSmall()

            Text(
                text = "选择规格",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            modifier = Modifier.size(SpaceVerticalLarge)
        )
    }
}

/**
 * 配送信息卡片
 */
@Composable
private fun GoodsDeliveryCard() {
    AppCard(lineTitle = "发货与服务") {

        SpaceVerticalMedium()

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "送至",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            SpaceVerticalMedium()

            Text(
                text = "广州市 天河区",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        SpaceVerticalSmall()

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "服务",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            SpaceVerticalMedium()

            Text(
                text = "7天无理由退货·运费险·48小时发货",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

/**
 * 商品详情卡片
 */
@Composable
private fun GoodsDetailCard(content: String) {
    AppCard(lineTitle = R.string.goods_detail) {

        SpaceVerticalMedium()

        // 详情图片列表
        GoodsDetailImages()
    }
}

/**
 * 商品详情图片列表
 * 从HTML内容中提取的图片URL
 */
@Composable
private fun GoodsDetailImages() {
    // 提取的详情图片URL列表 - 实际项目中应该从HTML内容中解析
    val detailImages = listOf(
        "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F5c161af71062402d8dc7e3193e62d8f5_d1.png",
        "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Fea304cef45b846d2b7fc4e7fbef6d103_d2.jpg",
        "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ff3d17dae77d144b9aa828537f96d04e4_d3.jpg",
        "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F99710ccacd5443518a9b97386d028b5c_d4.jpg",
        "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Fa180b572f52142d5811dcf4e18c27a95_d5.jpg",
        "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ff5bab785f9d04ac38b35e10a1b63486e_d6.jpg",
        "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F19f52075481c44a789dcf648e3f8a7aa_d7.jpg"
    )

    Column {
        detailImages.forEach { imageUrl ->
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = SpaceVerticalXSmall)
            )
        }
    }
}

/**
 * 底部操作栏
 */
@Composable
private fun GoodsActionBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = SpaceHorizontalMedium, vertical = SpaceVerticalSmall)
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.Center
    ) {
        // 客服按钮

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "客服",
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "客服",
                fontSize = 10.sp
            )
        }

        SpaceHorizontalLarge()

        // 购物车按钮
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "购物车",
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "购物车",
                fontSize = 10.sp
            )
        }

        SpaceHorizontalLarge()

        // 加入购物车按钮
        Button(
            onClick = { /* TODO: 加入购物车 */ },
            modifier = Modifier
                .weight(1f)
                .height(42.dp),
            shape = RoundedCornerShape(
                topStart = SpaceVerticalLarge,
                bottomStart = SpaceVerticalLarge
            )
        ) {
            Text("加入购物车")
        }

        // 立即购买按钮
        Button(
            onClick = { /* TODO: 立即购买 */ },
            modifier = Modifier
                .weight(1f)
                .height(42.dp),
            shape = RoundedCornerShape(topEnd = SpaceVerticalLarge, bottomEnd = SpaceVerticalLarge),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text("立即购买")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GoodsDetailScreenPreview() {
    AppTheme {
        GoodsDetailScreen(
            uiState = GoodsDetailUiState(
                isLoading = false,
                goodsId = "1",
                goodsName = "Redmi 14C",
                goodsSubTitle = "【持久续航】5160mAh 大电池",
                goodsPrice = "¥499",
                goodsMainPic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ffcd84baf3d3a4b49b35a03aaf783281e_%E7%BA%A2%E7%B1%B3%2014c.png",
                goodsPics = listOf(
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F32051f923ded432c82ef5934451a601b_b2.jpg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F88bf37e8c9ce42968067cbf3d717f613_b3.jpg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F605b0249e73a4fe185c0a075ee85c7a3_b4.jpeg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ffb3679b641214f9b8af929cc58d1fe87_b5.jpeg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Fd1cbc7c3e2e04aa28ed27b6913dbe05b_b6.jpeg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F3c081339d951490b8d232477d9249ec2_b7.jpeg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ff3b7302aa7944f7caad225fb32652999_b8.jpeg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F54a05d34d02141ee8c05a129a7cb3555_b9.jpeg"
                ),
                goodsContent = "<p><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F5c161af71062402d8dc7e3193e62d8f5_d1.png\" alt=\"\" data-href=\"\" style=\"width: 100%;\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Fea304cef45b846d2b7fc4e7fbef6d103_d2.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p><p><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ff3d17dae77d144b9aa828537f96d04e4_d3.jpg\" alt=\"\" data-href=\"\" style=\"width: 100%;\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F99710ccacd5443518a9b97386d028b5c_d4.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Fa180b572f52142d5811dcf4e18c27a95_d5.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ff5bab785f9d04ac38b35e10a1b63486e_d6.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p><p><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F19f52075481c44a789dcf648e3f8a7aa_d7.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p>"
            )
        )
    }
}