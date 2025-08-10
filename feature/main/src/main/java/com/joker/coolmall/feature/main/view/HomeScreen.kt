package com.joker.coolmall.feature.main.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.designsystem.component.SpaceBetweenRow
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.LogoIcon
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.designsystem.theme.TextWhite
import com.joker.coolmall.core.model.entity.Banner
import com.joker.coolmall.core.model.entity.Category
import com.joker.coolmall.core.model.entity.Coupon
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.model.entity.Home
import com.joker.coolmall.core.ui.component.button.AppButton
import com.joker.coolmall.core.ui.component.button.ButtonSize
import com.joker.coolmall.core.ui.component.card.AppCard
import com.joker.coolmall.core.ui.component.divider.WeDivider
import com.joker.coolmall.core.ui.component.goods.GoodsGridItem
import com.joker.coolmall.core.ui.component.goods.GoodsListItem
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.network.BaseNetWorkListView
import com.joker.coolmall.core.ui.component.refresh.RefreshLayout
import com.joker.coolmall.core.ui.component.swiper.WeSwiper
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.PriceText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
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
    val pageData by viewModel.pageData.collectAsState()
    val listData by viewModel.listData.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val loadMoreState by viewModel.loadMoreState.collectAsState()

    HomeScreen(
        uiState = uiState,
        pageData = pageData,
        listData = listData,
        isRefreshing = isRefreshing,
        loadMoreState = loadMoreState,
        onRefresh = viewModel::onRefresh,
        onLoadMore = viewModel::onLoadMore,
        shouldTriggerLoadMore = viewModel::shouldTriggerLoadMore,
        toGoodsSearch = viewModel::toGoodsSearch,
        toGoodsDetail = viewModel::toGoodsDetail,
        toGoodsCategory = viewModel::toGoodsCategoryPage,
        toFlashSalePage = viewModel::toFlashSalePage,
        toGitHubPage = viewModel::toGitHubPage,
        toAboutPage = viewModel::toAboutPage,
        onRetry = viewModel::loadHomeData
    )
}

/**
 * 首页UI
 * @param uiState 网络请求UI状态
 * @param pageData 页面数据
 * @param listData 商品列表数据
 * @param isRefreshing 是否正在刷新
 * @param loadMoreState 加载更多状态
 * @param onRefresh 下拉刷新回调
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 是否应该触发加载更多的判断函数
 * @param toGoodsSearch 跳转到商品搜索页
 * @param toGoodsDetail 跳转到商品详情页
 * @param toGoodsCategory 跳转到商品分类页
 * @param toFlashSalePage 跳转到限时精选页
 * @param toGitHubPage 跳转到GitHub页
 * @param toAboutPage 跳转到关于页
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    uiState: BaseNetWorkListUiState = BaseNetWorkListUiState.Loading,
    pageData: Home = Home(),
    listData: List<Goods> = emptyList(),
    isRefreshing: Boolean = false,
    loadMoreState: LoadMoreState = LoadMoreState.Success,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean = { _, _ -> false },
    toGoodsSearch: () -> Unit = {},
    toGoodsDetail: (Long) -> Unit = {},
    toGoodsCategory: (Long) -> Unit = {},
    toFlashSalePage: () -> Unit = {},
    toGitHubPage: () -> Unit = {},
    toAboutPage: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    CommonScaffold(
        topBar = {
            HomeTopAppBar(
                toGoodsSearch = toGoodsSearch,
                toGitHubPage = toGitHubPage,
                toAboutPage = toAboutPage
            )
        }
    ) {
        BaseNetWorkListView(
            uiState = uiState,
            padding = it,
            onRetry = onRetry
        ) {
            HomeContentView(
                data = pageData,
                listData = listData,
                isRefreshing = isRefreshing,
                loadMoreState = loadMoreState,
                onRefresh = onRefresh,
                onLoadMore = onLoadMore,
                shouldTriggerLoadMore = shouldTriggerLoadMore,
                toGoodsDetail = toGoodsDetail,
                toGoodsCategory = toGoodsCategory,
                toFlashSalePage = toFlashSalePage,
                toGitHubPage = toGitHubPage
            )
        }
    }
}

/**
 * 主页内容
 * @param data 页面数据
 * @param listData 商品列表数据
 * @param isRefreshing 是否正在刷新
 * @param loadMoreState 加载更多状态
 * @param onRefresh 下拉刷新回调
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 是否应该触发加载更多的判断函数
 * @param toGoodsDetail 跳转到商品详情页
 * @param toGoodsCategory 跳转到商品分类页
 * @param toFlashSalePage 跳转到限时精选页
 * @param toGitHubPage 跳转到GitHub页
 */
@Composable
private fun HomeContentView(
    data: Home,
    listData: List<Goods>,
    isRefreshing: Boolean,
    loadMoreState: LoadMoreState,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean,
    toGoodsDetail: (Long) -> Unit,
    toGoodsCategory: (Long) -> Unit,
    toFlashSalePage: () -> Unit,
    toGitHubPage: () -> Unit
) {

    RefreshLayout(
        isGrid = true,
        isRefreshing = isRefreshing,
        loadMoreState = loadMoreState,
        onRefresh = onRefresh,
        onLoadMore = onLoadMore,
        shouldTriggerLoadMore = shouldTriggerLoadMore,
        gridContent = {

            // 轮播图
            item(span = StaggeredGridItemSpan.FullLine) {
                data.banner?.let { banners ->
                    Banner(banners)
                }
            }

            // 分类
            item(span = StaggeredGridItemSpan.FullLine) {
                data.category?.let { categories ->
                    Category(
                        categories = categories,
                        onCategoryClick = toGoodsCategory
                    )
                }
            }

            // 优惠券
            item(span = StaggeredGridItemSpan.FullLine) {
                data.coupon?.let { coupons ->
                    CouponSection(
                        coupons = coupons,
                        onCouponReceive = {}
                    )
                }
            }

            // 限时精选
            item(span = StaggeredGridItemSpan.FullLine) {
                data.flashSale?.let { flashSaleGoods ->
                    FlashSale(
                        goods = flashSaleGoods,
                        toGoodsDetail = toGoodsDetail,
                        toFlashSalePage = toFlashSalePage
                    )
                }
            }

            // 推荐商品标题
            item(span = StaggeredGridItemSpan.FullLine) {
                data.goods?.let {
                    TitleWithLine(
                        text = "推荐商品",
                        modifier = Modifier.padding(vertical = SpaceVerticalSmall)
                    )
                }
            }

            // 推荐商品列表
            item(span = StaggeredGridItemSpan.FullLine) {
                VerticalList(padding = 0.dp) {
                    data.recommend?.forEach { goods ->
                        GoodsListItem(
                            goods = goods,
                            onClick = { toGoodsDetail(goods.id) },
                        )
                    }
                }
            }

            // 全部商品标题
            item(span = StaggeredGridItemSpan.FullLine) {
                data.goods?.let {
                    TitleWithLine(
                        text = "全部商品",
                        modifier = Modifier.padding(vertical = SpaceVerticalSmall)
                    )
                }
            }

            // 全部商品列表
            listData.let { goods ->
                items(goods.size) { index ->
                    GoodsGridItem(goods = goods[index], onClick = {
                        toGoodsDetail(goods[index].id)
                    })
                }
            }
        }
    )
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
private fun Category(
    categories: List<Category>,
    onCategoryClick: (Long) -> Unit = {}
) {
    AppCard {
        // 每行5个进行分组
        val rows = categories.chunked(5)

        // 遍历每一行分类
        rows.forEach { rowCategories ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                rowCategories.forEach { category ->
                    CategoryItem(
                        category = category,
                        onClick = { onCategoryClick(category.id) },
                        modifier = Modifier
                            .weight(1f)
                            .clip(ShapeSmall)
                    )
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
private fun CategoryItem(
    category: Category,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp)
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
private fun FlashSale(
    goods: List<Goods>,
    toGoodsDetail: (Long) -> Unit,
    toFlashSalePage: () -> Unit
) {
    Card {
        AppListItem(
            title = "限时精选",
            trailingText = "查看全部",
            leadingIcon = R.drawable.ic_time,
            onClick = toFlashSalePage
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
 * @param toGoodsSearch 跳转到商品搜索页
 * @param toGitHubPage 跳转到GitHub页
 * @param toAboutPage 跳转到关于页
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopAppBar(
    toGoodsSearch: () -> Unit,
    toGitHubPage: () -> Unit,
    toAboutPage: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            LogoIcon(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { toAboutPage() },
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
                    .clickable { toGoodsSearch() }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = com.joker.coolmall.core.ui.R.drawable.ic_search),
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
                onClick = toGitHubPage,
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

/**
 * 优惠券区域组件
 */
@Composable
private fun CouponSection(
    coupons: List<Coupon>,
    onCouponReceive: (Long) -> Unit
) {
    // 如果没有优惠券数据，不显示
    if (coupons.isEmpty()) return

    // 轮播图页面状态管理
    val state = rememberPagerState { coupons.size }

    Card(modifier = Modifier.animateContentSize()) {
        WeSwiper(
            state = state,
            options = coupons,
            autoplay = false,
            modifier = Modifier.clip(ShapeMedium),
        ) { index, coupon ->

            CouponCard(
                coupon = coupon,
                onReceive = { onCouponReceive(it.id) },
            )
        }
    }
}

/**
 * 优惠券卡片组件
 */
@Composable
private fun CouponCard(
    coupon: Coupon,
    onReceive: (Coupon) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column {
        // 上半部分：优惠券主要信息
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpacePaddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 左侧优惠券图标
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(
                        brush = linearGradient(
                            colors = listOf(
                                Primary.copy(alpha = 0.9f),
                                Primary.copy(alpha = 0.7f)
                            )
                        ),
                        shape = ShapeSmall
                    ),
                contentAlignment = Alignment.Center
            ) {
                CommonIcon(
                    resId = R.drawable.ic_coupon_fill,
                    size = 24.dp,
                    tint = TextWhite,
                )
            }

            SpaceHorizontalMedium()

            // 中间信息区域
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // 优惠券标题
                AppText(
                    text = coupon.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                // 有效期
                AppText(
                    text = "有效期至 ${coupon.endTime}",
                    size = TextSize.BODY_MEDIUM,
                    type = TextType.TERTIARY
                )
            }

            SpaceHorizontalMedium()

            // 右侧金额和条件区域
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // 优惠券金额
                PriceText(price = coupon.amount.toInt())
                // 使用条件
                AppText(
                    text = "满${coupon.condition!!.fullAmount.toInt()}元可用",
                    size = TextSize.BODY_MEDIUM,
                    type = TextType.SECONDARY
                )
            }
        }

        // 分割线
        WeDivider()

        // 下半部分：说明和领取按钮
        SpaceBetweenRow(
            modifier = Modifier
                .padding(
                    horizontal = SpacePaddingMedium,
                    vertical = SpaceVerticalMedium
                )
        ) {
            // 左侧：可展开的说明
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { isExpanded = !isExpanded }
                ) {
                    AppText(
                        text = "使用说明",
                        size = TextSize.BODY_MEDIUM,
                        type = TextType.TERTIARY
                    )

                    SpaceHorizontalXSmall()

                    CommonIcon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        size = 16.dp,
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    )
                }

                // 展开的详细说明
                AnimatedVisibility(
                    visible = isExpanded,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    AppText(
                        text = coupon.description,
                        size = TextSize.BODY_MEDIUM,
                        type = TextType.TERTIARY
                    )
                }
            }

            // 右侧：领取按钮
            AppButton(
                text = "领取",
                onClick = {},
                fullWidth = false,
                size = ButtonSize.MINI
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen()
    }
}