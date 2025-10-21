package com.joker.coolmall.feature.main.view

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.LogoIcon
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingXSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.model.entity.Banner
import com.joker.coolmall.core.model.entity.Category
import com.joker.coolmall.core.model.entity.Coupon
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.model.entity.Home
import com.joker.coolmall.core.model.preview.previewAvailableCoupons
import com.joker.coolmall.core.model.preview.previewBannerList
import com.joker.coolmall.core.model.preview.previewCategoryList
import com.joker.coolmall.core.model.preview.previewGoodsList
import com.joker.coolmall.core.ui.component.card.AppCard
import com.joker.coolmall.core.ui.component.goods.GoodsGridItem
import com.joker.coolmall.core.ui.component.goods.GoodsListItem
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.modal.CouponModal
import com.joker.coolmall.core.ui.component.network.BaseNetWorkListView
import com.joker.coolmall.core.ui.component.refresh.RefreshLayout
import com.joker.coolmall.core.ui.component.swiper.WeSwiper
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.core.ui.component.title.TitleWithLine
import com.joker.coolmall.feature.main.R
import com.joker.coolmall.feature.main.component.CommonScaffold
import com.joker.coolmall.feature.main.component.FlashSaleItem
import com.joker.coolmall.feature.main.viewmodel.HomeViewModel
import com.joker.coolmall.core.ui.R as CoreUiR

/**
 * 首页路由入口
 *
 * @param sharedTransitionScope 共享转换作用域
 * @param animatedContentScope 动画内容作用域
 * @param viewModel 首页 ViewModel
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun HomeRoute(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    viewModel: HomeViewModel = hiltViewModel()
) {
    // 网络请求UI状态
    val uiState by viewModel.uiState.collectAsState()
    // 首页数据
    val pageData by viewModel.pageData.collectAsState()
    // 商品列表数据
    val listData by viewModel.listData.collectAsState()
    // 是否正在刷新
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    // 加载更多状态
    val loadMoreState by viewModel.loadMoreState.collectAsState()
    // 优惠券弹窗是否可见
    val couponModalVisible by viewModel.couponModalVisible.collectAsState()

    HomeScreen(
        uiState = uiState,
        pageData = pageData,
        listData = listData,
        isRefreshing = isRefreshing,
        loadMoreState = loadMoreState,
        couponModalVisible = couponModalVisible,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        onRefresh = viewModel::onRefresh,
        onLoadMore = viewModel::onLoadMore,
        shouldTriggerLoadMore = viewModel::shouldTriggerLoadMore,
        toGoodsSearch = viewModel::toGoodsSearch,
        toGoodsDetail = viewModel::toGoodsDetail,
        toGoodsCategory = viewModel::toGoodsCategoryPage,
        toFlashSalePage = viewModel::toFlashSalePage,
        toGitHubPage = viewModel::toGitHubPage,
        toAboutPage = viewModel::toAboutPage,
        onShowCouponModal = viewModel::showCouponModal,
        onHideCouponModal = viewModel::hideCouponModal,
        onCouponReceive = viewModel::receiveCoupon,
        onRetry = viewModel::loadHomeData
    )
}

/**
 * 首页UI
 *
 * @param uiState 网络请求UI状态
 * @param pageData 页面数据
 * @param listData 商品列表数据
 * @param isRefreshing 是否正在刷新
 * @param loadMoreState 加载更多状态
 * @param couponModalVisible 优惠券弹窗是否可见
 * @param sharedTransitionScope 共享转换作用域
 * @param animatedContentScope 动画内容作用域
 * @param onRefresh 下拉刷新回调
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 是否应该触发加载更多的判断函数
 * @param toGoodsSearch 跳转到商品搜索页
 * @param toGoodsDetail 跳转到商品详情页
 * @param toGoodsCategory 跳转到商品分类页
 * @param toFlashSalePage 跳转到限时精选页
 * @param toGitHubPage 跳转到GitHub页
 * @param toAboutPage 跳转到关于页
 * @param onShowCouponModal 显示优惠券弹窗回调
 * @param onHideCouponModal 隐藏优惠券弹窗回调
 * @param onCouponReceive 领取优惠券回调
 * @param onRetry 重试请求回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun HomeScreen(
    uiState: BaseNetWorkListUiState = BaseNetWorkListUiState.Loading,
    pageData: Home = Home(),
    listData: List<Goods> = emptyList(),
    isRefreshing: Boolean = false,
    loadMoreState: LoadMoreState = LoadMoreState.Success,
    couponModalVisible: Boolean = false,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean = { _, _ -> false },
    toGoodsSearch: () -> Unit = {},
    toGoodsDetail: (Long) -> Unit = {},
    toGoodsCategory: (Long) -> Unit = {},
    toFlashSalePage: () -> Unit = {},
    toGitHubPage: () -> Unit = {},
    toAboutPage: () -> Unit = {},
    onShowCouponModal: () -> Unit = {},
    onHideCouponModal: () -> Unit = {},
    onCouponReceive: (Coupon) -> Unit = {},
    onRetry: () -> Unit = {}
) {
    // 创建TopAppBar的滚动行为
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    CommonScaffold(
        topBar = {
            HomeTopAppBar(
                scrollBehavior = scrollBehavior,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope,
                toGoodsSearch = toGoodsSearch,
                toGitHubPage = toGitHubPage,
                toAboutPage = toAboutPage
            )
        },
        scrollBehavior = scrollBehavior
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
                scrollBehavior = scrollBehavior,
                onRefresh = onRefresh,
                onLoadMore = onLoadMore,
                shouldTriggerLoadMore = shouldTriggerLoadMore,
                toGoodsDetail = toGoodsDetail,
                toGoodsCategory = toGoodsCategory,
                toFlashSalePage = toFlashSalePage,
                toGitHubPage = toGitHubPage,
                onShowCouponModal = onShowCouponModal
            )
        }

        // 优惠券弹出层
        CouponModal(
            visible = couponModalVisible,
            onDismiss = onHideCouponModal,
            coupons = pageData.coupon ?: emptyList(),
            onCouponAction = { couponId ->
                // 根据ID找到对应的优惠券并调用领取方法
                val coupon = (pageData.coupon ?: emptyList()).find { it.id == couponId }
                coupon?.let { onCouponReceive(it) }
            }
        )
    }
}

/**
 * 首页内容视图
 *
 * @param data 首页数据
 * @param listData 商品列表数据
 * @param isRefreshing 是否正在刷新
 * @param loadMoreState 加载更多状态
 * @param scrollBehavior 顶部导航栏滚动行为，用于实现滑动折叠效果
 * @param onRefresh 下拉刷新回调
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 是否应该触发加载更多的判断函数
 * @param toGoodsDetail 跳转到商品详情页
 * @param toGoodsCategory 跳转到商品分类页
 * @param toFlashSalePage 跳转到限时精选页
 * @param toGitHubPage 跳转到GitHub页
 * @param onShowCouponModal 显示优惠券弹窗回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContentView(
    data: Home,
    listData: List<Goods>,
    isRefreshing: Boolean,
    loadMoreState: LoadMoreState,
    scrollBehavior: TopAppBarScrollBehavior,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean,
    toGoodsDetail: (Long) -> Unit,
    toGoodsCategory: (Long) -> Unit,
    toFlashSalePage: () -> Unit,
    toGitHubPage: () -> Unit,
    onShowCouponModal: () -> Unit = {}
) {

    RefreshLayout(
        isGrid = true,
        isRefreshing = isRefreshing,
        scrollBehavior = scrollBehavior,
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

            // 优惠券
            item(span = StaggeredGridItemSpan.FullLine) {
                data.coupon?.let { coupons ->
                    CouponSection(
                        coupons = coupons,
                        onShowCouponModal = onShowCouponModal
                    )
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
                        text = stringResource(R.string.recommend_goods),
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
                        text = stringResource(R.string.all_goods),
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
 *
 * @param banners 轮播图列表
 * @author Joker.X
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
 *
 * @param categories 分类列表
 * @param onCategoryClick 分类点击回调
 * @author Joker.X
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
 *
 * @param modifier 修饰符
 * @param category 分类数据
 * @param onClick 点击回调
 * @author Joker.X
 */
@Composable
private fun CategoryItem(
    modifier: Modifier = Modifier,
    category: Category,
    onClick: () -> Unit = {},
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
 *
 * @param goods 商品列表
 * @param toGoodsDetail 跳转到商品详情页的回调
 * @param toFlashSalePage 跳转到限时精选页面的回调
 * @author Joker.X
 */
@Composable
private fun FlashSale(
    goods: List<Goods>,
    toGoodsDetail: (Long) -> Unit,
    toFlashSalePage: () -> Unit
) {
    Card {
        AppListItem(
            title = stringResource(R.string.flash_sale),
            trailingText = stringResource(R.string.view_all),
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
 *
 * @param scrollBehavior 滚动行为
 * @param sharedTransitionScope 共享转换作用域
 * @param animatedContentScope 动画内容作用域
 * @param toGoodsSearch 跳转到商品搜索页
 * @param toGitHubPage 跳转到GitHub页
 * @param toAboutPage 跳转到关于页
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
private fun HomeTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    toGoodsSearch: () -> Unit,
    toGitHubPage: () -> Unit,
    toAboutPage: () -> Unit
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            LogoIcon(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { toAboutPage() }
                    .let { modifier ->
                        if (sharedTransitionScope != null && animatedContentScope != null) {
                            with(sharedTransitionScope) {
                                modifier.sharedElement(
                                    sharedContentState = rememberSharedContentState(key = "app_logo"),
                                    animatedVisibilityScope = animatedContentScope
                                )
                            }
                        } else {
                            modifier
                        }
                    },
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
                        contentDescription = stringResource(R.string.search),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.size(18.dp)
                    )

                    Text(
                        text = stringResource(R.string.search_goods),
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
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        )
    )
}

/**
 * 优惠券区域组件
 *
 * @param coupons 优惠券列表
 * @param onShowCouponModal 显示优惠券弹窗的回调
 * @author Joker.X
 */
@Composable
private fun CouponSection(
    coupons: List<Coupon>,
    onShowCouponModal: () -> Unit = {}
) {
    // 轮播图页面状态管理
    val state = rememberPagerState { coupons.size }

    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpacePaddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 左侧可滑动的优惠券内容
            Box(modifier = Modifier.weight(1f)) {
                WeSwiper(
                    state = state,
                    options = coupons,
                    autoplay = false,
                    indicator = { count, current -> }
                ) { index, coupon ->
                    CouponCard(coupon = coupon)
                }
            }

            // 竖分割线
            VerticalDivider(
                modifier = Modifier
                    .height(20.dp)
                    .padding(horizontal = 12.dp),
                thickness = 0.5.dp
            )

            // 右侧固定的领取按钮
            AppText(
                text = stringResource(R.string.receive),
                type = TextType.LINK,
                modifier = Modifier.clickable {
                    onShowCouponModal()
                }
            )
        }
    }
}

/**
 * 优惠券卡片组件
 *
 * @param coupon 优惠券数据
 * @author Joker.X
 */
@Composable
private fun CouponCard(coupon: Coupon) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(SpacePaddingXSmall)
        ) {
            CommonIcon(
                resId = CoreUiR.drawable.ic_coupon,
                size = 18.dp,
                tint = LocalContentColor.current.copy(alpha = 0.9f)
            )
        }

        AppText(
            text = coupon.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .padding(start = SpaceVerticalSmall)
        )
    }
}

/**
 * 首页界面浅色主题预览
 *
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            sharedTransitionScope = null,
            animatedContentScope = null,
            uiState = BaseNetWorkListUiState.Success,
            pageData = Home(
                banner = previewBannerList,
                coupon = previewAvailableCoupons,
                category = previewCategoryList,
                recommend = previewGoodsList,
                flashSale = previewGoodsList,
                goods = previewGoodsList
            ),
        )
    }
}