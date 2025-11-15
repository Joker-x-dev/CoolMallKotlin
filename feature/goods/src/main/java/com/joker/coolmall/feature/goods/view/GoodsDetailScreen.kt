package com.joker.coolmall.feature.goods.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.component.SpaceBetweenRow
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ArrowRightIcon
import com.joker.coolmall.core.designsystem.theme.ColorDanger
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.RadiusMedium
import com.joker.coolmall.core.designsystem.theme.ShapeCircle
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceDivider
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingXSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.model.entity.Comment
import com.joker.coolmall.core.model.entity.Coupon
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.model.entity.GoodsDetail
import com.joker.coolmall.core.model.entity.GoodsSpec
import com.joker.coolmall.core.model.entity.SelectedGoods
import com.joker.coolmall.core.model.preview.previewGoods
import com.joker.coolmall.core.model.preview.previewMyCoupons
import com.joker.coolmall.core.ui.component.button.AppButtonBordered
import com.joker.coolmall.core.ui.component.button.AppButtonFixed
import com.joker.coolmall.core.ui.component.button.ButtonShape
import com.joker.coolmall.core.ui.component.button.ButtonSize
import com.joker.coolmall.core.ui.component.button.ButtonStyle
import com.joker.coolmall.core.ui.component.button.ButtonType
import com.joker.coolmall.core.ui.component.card.AppCard
import com.joker.coolmall.core.ui.component.divider.WeDivider
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.modal.CouponModal
import com.joker.coolmall.core.ui.component.modal.SpecSelectModal
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.swiper.WeSwiper
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.PriceText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.title.TitleWithLine
import com.joker.coolmall.feature.goods.R
import com.joker.coolmall.feature.goods.component.CommentItem
import com.joker.coolmall.feature.goods.viewmodel.GoodsDetailViewModel
import kotlinx.coroutines.flow.collectLatest
import com.joker.coolmall.core.ui.R as CoreUiR

/**
 * 商品详情页面路由入口
 *
 * @param viewModel 商品详情 ViewModel
 * @author Joker.X
 */
@Composable
internal fun GoodsDetailRoute(
    viewModel: GoodsDetailViewModel = hiltViewModel()
) {
    // UI状态
    val uiState by viewModel.uiState.collectAsState()
    // 规格选择弹窗状态
    val specModalVisible by viewModel.specModalVisible.collectAsState()
    // 规格列表状态
    val specsModalUiState by viewModel.specsModalUiState.collectAsState()
    // 选中的规格
    val selectedSpec by viewModel.selectedSpec.collectAsState()
    // 动画状态
    val hasAnimated by viewModel.hasAnimated.collectAsState()
    // 优惠券弹窗状态
    val couponModalVisible by viewModel.couponModalVisible.collectAsState()

    GoodsDetailScreen(
        uiState = uiState,
        onBackClick = viewModel::navigateBack,
        onRetry = viewModel::retryRequest,
        specModalVisible = specModalVisible,
        specsModalUiState = specsModalUiState,
        selectedSpec = selectedSpec,
        onSpecSelected = viewModel::selectSpec,
        onShowSpecModal = viewModel::showSpecModal,
        onHideSpecModal = viewModel::hideSpecModal,
        onSpecModalExpanded = viewModel::onSpecModalExpanded,
        onAddToCart = viewModel::addToCart,
        onBuyNow = viewModel::buyNow,
        onSpecRetry = viewModel::loadGoodsSpecs,
        hasAnimated = hasAnimated,
        onTriggerAnimation = viewModel::triggerAnimation,
        couponModalVisible = couponModalVisible,
        onShowCouponModal = viewModel::showCouponModal,
        onHideCouponModal = viewModel::hideCouponModal,
        onCouponReceive = viewModel::receiveCoupon,
        onCommentClick = viewModel::toGoodsCommentPage,
        onCsClick = viewModel::toCsPage,
        onCartClick = viewModel::toCartPage
    )
}

/**
 * 商品详情页面UI
 *
 * @param uiState 商品详情UI状态，包含商品数据的网络请求状态
 * @param onBackClick 返回按钮点击回调，点击后返回上一页
 * @param onRetry 商品详情加载失败时的重试回调
 * @param onSpecRetry 规格列表加载失败时的重试回调
 * @param specModalVisible 规格选择弹窗是否可见
 * @param specsModalUiState 规格列表的网络请求状态
 * @param selectedSpec 当前选中的规格信息
 * @param onSpecSelected 规格选择回调，当用户选择一个规格时触发
 * @param onShowSpecModal 显示规格选择弹窗的回调
 * @param onHideSpecModal 隐藏规格选择弹窗的回调
 * @param onSpecModalExpanded 规格弹窗展开完成回调，用于在动画完成后加载数据
 * @param onAddToCart 加入购物车回调，参数为包含商品和规格信息的购物车对象
 * @param onBuyNow 立即购买回调，参数为包含商品和规格信息的购物车对象
 * @param hasAnimated 是否已播放动画
 * @param onTriggerAnimation 触发动画回调
 * @param couponModalVisible 优惠券弹窗是否可见
 * @param onShowCouponModal 显示优惠券弹窗回调
 * @param onHideCouponModal 隐藏优惠券弹窗回调
 * @param onCouponReceive 领取优惠券回调
 * @param onCommentClick 跳转到评论页面的回调，点击评论相关区域时触发
 * @param onCsClick 跳转到客服页面的回调
 * @param onCartClick 跳转到购物车页面的回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GoodsDetailScreen(
    uiState: BaseNetWorkUiState<GoodsDetail> = BaseNetWorkUiState.Loading,
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {},
    onSpecRetry: () -> Unit = {},
    specModalVisible: Boolean = false,
    specsModalUiState: BaseNetWorkUiState<List<GoodsSpec>> = BaseNetWorkUiState.Loading,
    selectedSpec: GoodsSpec? = null,
    onSpecSelected: (GoodsSpec) -> Unit = {},
    onShowSpecModal: () -> Unit = {},
    onHideSpecModal: () -> Unit = {},
    onSpecModalExpanded: () -> Unit = {},
    onAddToCart: (SelectedGoods) -> Unit = {},
    onBuyNow: (SelectedGoods) -> Unit = {},
    hasAnimated: Boolean = false,
    onTriggerAnimation: () -> Unit = {},
    couponModalVisible: Boolean = false,
    onShowCouponModal: () -> Unit = {},
    onHideCouponModal: () -> Unit = {},
    onCouponReceive: (Coupon) -> Unit = {},
    onCommentClick: () -> Unit = {},
    onCsClick: () -> Unit = {},
    onCartClick: () -> Unit = {}
) {
    Scaffold(
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.statusBars)
    ) { paddingValues ->
        BaseNetWorkView(
            uiState = uiState,
            padding = paddingValues,
            onRetry = onRetry
        ) { goodsDetail ->
            GoodsDetailContentView(
                data = goodsDetail,
                coupons = goodsDetail.coupon,
                comments = goodsDetail.comment,
                onBackClick = onBackClick,
                paddingValues = paddingValues,
                selectedSpec = selectedSpec,
                onShowSpecModal = onShowSpecModal,
                hasAnimated = hasAnimated,
                onTriggerAnimation = onTriggerAnimation,
                onShowCouponModal = onShowCouponModal,
                onCommentClick = onCommentClick,
                onCsClick = onCsClick,
                onCartClick = onCartClick
            )

            // 规格选择底部弹出层
            SpecSelectModal(
                visible = specModalVisible,
                onDismiss = onHideSpecModal,
                goods = goodsDetail.goodsInfo,
                uiState = specsModalUiState,
                onSpecSelected = onSpecSelected,
                onAddToCart = onAddToCart,
                onBuyNow = onBuyNow,
                onRetry = onSpecRetry,
                selectedSpec = selectedSpec,
                onExpanded = onSpecModalExpanded
            )

            // 优惠券弹出层
            CouponModal(
                visible = couponModalVisible,
                onDismiss = onHideCouponModal,
                coupons = goodsDetail.coupon,
                onCouponAction = { couponId ->
                    // 根据ID找到对应的优惠券并调用领取方法
                    val coupon = goodsDetail.coupon.find { it.id == couponId }
                    coupon?.let { onCouponReceive(it) }
                }
            )
        }
    }
}

/**
 * 商品详情主内容
 *
 * @param data 商品详情数据对象
 * @param coupons 优惠券列表
 * @param comments 评论列表
 * @param onBackClick 返回按钮点击回调
 * @param paddingValues 页面内边距值，用于适配系统UI（如状态栏、导航栏）
 * @param selectedSpec 当前选中的商品规格，若为null则表示未选择规格
 * @param onShowSpecModal 显示规格选择弹窗的回调函数
 * @param hasAnimated 是否已播放动画
 * @param onTriggerAnimation 触发动画回调
 * @param onShowCouponModal 显示优惠券弹窗回调
 * @param onCommentClick 跳转到评论页面的回调函数
 * @param onCsClick 跳转到客服页面的回调
 * @param onCartClick 跳转到购物车页面的回调
 * @author Joker.X
 */
@Composable
private fun GoodsDetailContentView(
    data: GoodsDetail,
    coupons: List<Coupon> = emptyList(),
    comments: List<Comment> = emptyList(),
    onBackClick: () -> Unit,
    paddingValues: PaddingValues,
    selectedSpec: GoodsSpec? = null,
    onShowSpecModal: () -> Unit = {},
    hasAnimated: Boolean = false,
    onTriggerAnimation: () -> Unit = {},
    onShowCouponModal: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onCsClick: () -> Unit = {},
    onCartClick: () -> Unit = {}
) {
    // 主内容容器
    var topBarAlpha by remember { mutableIntStateOf(0) }

    // 启动动画
    LaunchedEffect(Unit) {
        onTriggerAnimation()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        // 内容区域
        GoodsDetailContentWithScroll(
            data = data.goodsInfo,
            coupons = coupons,
            comments = comments,
            selectedSpec = selectedSpec,
            onTopBarAlphaChanged = { topBarAlpha = it },
            onShowSpecModal = onShowSpecModal,
            onShowCouponModal = onShowCouponModal,
            onCommentClick = onCommentClick
        )

        // 导航栏浮动在顶部
        GoodsDetailTopBar(
            onBackClick = onBackClick,
            onShareClick = { /* TODO: 分享功能 */ },
            topBarAlpha = topBarAlpha,
            hasAnimated = hasAnimated,
            modifier = Modifier.zIndex(1f)
        )

        // 底部操作栏
        GoodsActionBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            onAddToCartClick = onShowSpecModal,
            onBuyNowClick = onShowSpecModal,
            hasAnimated = hasAnimated,
            onCsClick = onCsClick,
            onCartClick = onCartClick
        )
    }
}

/**
 * 顶部导航栏
 *
 * @param modifier 应用于顶部导航栏的Modifier
 * @param onBackClick 返回按钮点击回调
 * @param onShareClick 分享按钮点击回调
 * @param topBarAlpha 顶部导航栏的透明度值(0-255)，用于实现滚动时的渐变效果
 * @param hasAnimated 是否已播放动画
 * @author Joker.X
 */
@Composable
private fun GoodsDetailTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    topBarAlpha: Int = 0,
    hasAnimated: Boolean = false
) {
    // 按钮缩放动画
    val buttonScale by animateFloatAsState(
        targetValue = if (hasAnimated) 1f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "button_scale"
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                if (isSystemInDarkTheme())
                    Color(0, 0, 0, topBarAlpha)
                else
                    Color(255, 255, 255, topBarAlpha)
            )
            .statusBarsPadding()
            .padding(horizontal = SpacePaddingMedium, vertical = SpacePaddingSmall)
    ) {
        CircleIconButton(
            icon = com.joker.coolmall.core.designsystem.R.drawable.ic_left,
            onClick = onBackClick,
            iconSize = 28.dp,
            scale = buttonScale
        )

        Spacer(modifier = Modifier.weight(1f))

        CircleIconButton(
            icon = R.drawable.ic_share_triangle,
            onClick = onShareClick,
            scale = buttonScale
        )
    }
}

/**
 * 圆形图标按钮
 *
 * @param modifier 应用于按钮的Modifier
 * @param icon 图标资源ID
 * @param onClick 按钮点击回调
 * @param iconSize 图标大小
 * @param scale 缩放比例
 * @author Joker.X
 */
@Composable
private fun CircleIconButton(
    modifier: Modifier = Modifier,
    icon: Int,
    onClick: () -> Unit = {},
    iconSize: Dp = 20.dp,
    scale: Float = 1f
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(38.dp)
            .scale(scale)
            .clip(CircleShape)
            .clickable { onClick() }
            .background(Color.Black.copy(alpha = 0.3f))
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(iconSize)
        )
    }
}

/**
 * 带滚动功能的商品详情内容
 *
 * @param data 商品详情数据对象
 * @param coupons 优惠券列表
 * @param comments 评论列表
 * @param selectedSpec 当前选中的商品规格，若为null则表示未选择规格
 * @param onTopBarAlphaChanged 顶部导航栏透明度变化回调，参数为新的透明度值
 * @param onShowSpecModal 显示规格选择弹窗的回调函数
 * @param onShowCouponModal 显示优惠券弹窗回调
 * @param onCommentClick 跳转到评论页面的回调函数
 * @author Joker.X
 */
@Composable
private fun GoodsDetailContentWithScroll(
    data: Goods,
    coupons: List<Coupon> = emptyList(),
    comments: List<Comment> = emptyList(),
    selectedSpec: GoodsSpec? = null,
    onTopBarAlphaChanged: (Int) -> Unit,
    onShowSpecModal: () -> Unit,
    onShowCouponModal: () -> Unit = {},
    onCommentClick: () -> Unit = {}
) {
    val lazyListState = rememberLazyListState()

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            val firstVisibleIndex = lazyListState.firstVisibleItemIndex
            val firstVisibleOffset = lazyListState.firstVisibleItemScrollOffset

            if (firstVisibleIndex > 0) {
                255
            } else {
                firstVisibleOffset.coerceIn(0, 255)
            }
        }.collectLatest { alpha ->
            onTopBarAlphaChanged(alpha)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState
    ) {
        // 轮播图直接放在顶部，没有状态栏的padding
        item {
            GoodsBanner(data.pics!!)
        }

        // 基本信息
        item {
            Column(modifier = Modifier.padding(SpacePaddingMedium)) {
                GoodsInfoCard(data, coupons, selectedSpec, onShowSpecModal, onShowCouponModal)
            }
        }

        // 配送信息
        item {
            Column(modifier = Modifier.padding(horizontal = SpacePaddingMedium)) {
                GoodsDeliveryCard()
            }
        }

        item {
            SpaceVerticalMedium()
        }

        // 评论列表
        if (comments.isNotEmpty()) {
            item {
                Column(modifier = Modifier.padding(horizontal = SpacePaddingMedium)) {
                    GoodsCommentsCard(comments = comments, onCommentClick = onCommentClick)
                }
            }

            item {
                SpaceVerticalMedium()
            }
        }

        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SpacePaddingMedium),
                shape = RoundedCornerShape(topStart = RadiusMedium, topEnd = RadiusMedium),
            ) {
                AppListItem(
                    title = "",
                    showArrow = false,
                    leadingContent = {
                        TitleWithLine(text = stringResource(id = R.string.goods_detail))
                    }
                )
            }
        }

        // 图文详情
        data.contentPics!!.forEachIndexed { index, pic ->
            val isLastItem = index == data.contentPics!!.size - 1
            item {
                NetWorkImage(
                    model = pic,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp)
                        .padding(horizontal = SpacePaddingMedium)
                        .then(
                            if (isLastItem) {
                                // 最后一张图片设置下圆角
                                Modifier.clip(
                                    RoundedCornerShape(
                                        bottomStart = RadiusMedium,
                                        bottomEnd = RadiusMedium
                                    )
                                )
                            } else {
                                Modifier
                            }
                        )
                )
            }
        }

        // 底部安全区域（为底部操作栏留出空间）
        item {
            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

/**
 * 商品轮播图
 *
 * @param images 图片URL列表，用于轮播展示
 * @author Joker.X
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

        NetWorkImage(
            model = item,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .scale(animatedScale)
        )
    }
}

/**
 * 商品信息卡片
 *
 * @param data 商品详情数据对象
 * @param coupons 优惠券列表
 * @param selectedSpec 当前选中的商品规格，若为null则表示未选择规格
 * @param onShowSpecModal 显示规格选择弹窗的回调函数
 * @param onShowCouponModal 显示优惠券弹窗回调
 * @author Joker.X
 */
@Composable
private fun GoodsInfoCard(
    data: Goods,
    coupons: List<Coupon> = emptyList(),
    selectedSpec: GoodsSpec? = null,
    onShowSpecModal: () -> Unit,
    onShowCouponModal: () -> Unit = {}
) {
    AppCard {
        // 价格和已售标签行
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 价格 - 如果有选中规格则显示规格价格，否则显示商品原价
            PriceText(
                selectedSpec?.price ?: data.price,
                integerTextSize = TextSize.DISPLAY_LARGE
            )

            // 已售标签
            SoldCountTag(count = data.sold)
        }

        // 优惠券列表
        CouponList(coupons, onShowCouponModal)

        SpaceVerticalMedium()

        // 标题
        Text(
            text = data.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        SpaceVerticalXSmall()

        data.subTitle.let {
            // 副标题
            Text(
                text = data.subTitle!!,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            SpaceVerticalMedium()
        }

        // 规格选择
        SpecSelection(
            selectedSpec = selectedSpec,
            onClick = onShowSpecModal
        )
    }
}

/**
 * 已售数量标签
 *
 * @param count 已售出的商品数量
 * @author Joker.X
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
            text = stringResource(R.string.sold_count, count),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White
        )
    }
}

/**
 * 优惠券列表
 *
 * 展示可用的优惠券信息列表
 *
 * @param coupons 优惠券列表
 * @param onShowCouponModal 显示优惠券弹窗回调
 * @author Joker.X
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CouponList(
    coupons: List<Coupon> = emptyList(),
    onShowCouponModal: () -> Unit = {}
) {
    if (coupons.isNotEmpty()) {
        SpaceVerticalSmall()
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SpaceHorizontalSmall),
            verticalArrangement = Arrangement.spacedBy(SpaceVerticalSmall)
        ) {
            coupons.forEach { coupon -> CouponTag(coupon, onShowCouponModal) }
        }
    }
}

/**
 * 优惠券标签
 *
 * @param coupon 优惠券对象，包含满减金额信息
 * @param onShowCouponModal 显示优惠券弹窗回调
 * @author Joker.X
 */
@Composable
private fun CouponTag(
    coupon: Coupon,
    onShowCouponModal: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(
                width = SpaceDivider,
                color = ColorDanger,
                shape = RoundedCornerShape(SpaceVerticalXSmall)
            )
            .clickable { onShowCouponModal() }
            .padding(horizontal = SpacePaddingSmall, vertical = SpaceVerticalXSmall)
    ) {
        Icon(
            painter = painterResource(id = CoreUiR.drawable.ic_coupon),
            contentDescription = null,
            tint = ColorDanger,
            modifier = Modifier.size(SpaceVerticalMedium)
        )
        Spacer(modifier = Modifier.width(SpaceVerticalXSmall))
        Text(
            text = stringResource(
                R.string.coupon_text,
                coupon.condition!!.fullAmount.toInt(),
                coupon.amount.toInt()
            ),
            style = MaterialTheme.typography.labelSmall,
            color = ColorDanger
        )
    }
}

/**
 * 规格选择
 *
 * @param selectedSpec 当前选中的商品规格，若为null则表示未选择规格
 * @param onClick 点击规格选择区域的回调，用于打开规格选择弹窗
 * @author Joker.X
 */
@Composable
private fun SpecSelection(
    selectedSpec: GoodsSpec? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(ShapeSmall)
            .border(
                width = 1.2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = ShapeSmall
            )
            .clickable { onClick() }
            .padding(SpacePaddingMedium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cube),
                contentDescription = stringResource(R.string.spec),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.size(20.dp)
            )

            SpaceHorizontalXSmall()

            // 根据是否选中规格显示不同的文本
            Text(
                text = if (selectedSpec != null) {
                    stringResource(R.string.selected_spec, selectedSpec.name)
                } else {
                    stringResource(R.string.select_spec)
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }

        ArrowRightIcon(
            modifier = Modifier.size(SpaceVerticalLarge),
            size = 16.dp,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
        )
    }
}

/**
 * 商品评论卡片
 *
 * @param comments 评论列表
 * @param onCommentClick 点击评论区域的回调，用于跳转到评论页面
 * @author Joker.X
 */
@Composable
private fun GoodsCommentsCard(
    comments: List<Comment>,
    onCommentClick: () -> Unit = {}
) {
    Card {
        Column {
            AppListItem(
                title = "",
                trailingText = stringResource(R.string.view_all),
                leadingContent = {
                    TitleWithLine(text = stringResource(R.string.goods_reviews))
                },
                onClick = onCommentClick
            )

            comments.forEachIndexed { index, comment ->
                CommentItem(
                    comment = comment,
                    onClick = onCommentClick
                )
                // 添加分割线，最后一个评论项不添加
                if (index < comments.size - 1) {
                    WeDivider()
                }
            }
        }
    }
}

/**
 * 配送信息卡片
 *
 * 显示商品的配送相关信息，包括送货地址和服务承诺
 *
 * @author Joker.X
 */
@Composable
private fun GoodsDeliveryCard() {
    Card {
        AppListItem(
            title = "",
            showArrow = false,
            leadingContent = {
                TitleWithLine(text = stringResource(R.string.shipping_and_service))
            }
        )

        AppListItem(
            title = stringResource(R.string.shipping),
            showArrow = false,
            trailingText = stringResource(R.string.shipping_location)
        )

        AppListItem(
            title = stringResource(R.string.service),
            showArrow = false,
            showDivider = false,
            trailingText = stringResource(R.string.service_details)
        )
    }
}

/**
 * 底部操作栏
 *
 * @param modifier 应用于底部操作栏的Modifier
 * @param onAddToCartClick 加入购物车按钮点击回调
 * @param onBuyNowClick 立即购买按钮点击回调
 * @param hasAnimated 是否已经播放过动画
 * @param onCsClick 跳转到客服页面的回调
 * @param onCartClick 跳转到购物车页面的回调
 * @author Joker.X
 */
@Composable
private fun GoodsActionBar(
    modifier: Modifier = Modifier,
    onAddToCartClick: () -> Unit = {},
    onBuyNowClick: () -> Unit = {},
    hasAnimated: Boolean = false,
    onCsClick: () -> Unit = {},
    onCartClick: () -> Unit = {}
) {
    // 底部操作栏从底部升起的动画
    val bottomBarOffset by animateOffsetAsState(
        targetValue = if (hasAnimated) Offset(0f, 0f) else Offset(0f, 200f),
        animationSpec = tween(durationMillis = 800),
        label = "bottom_bar_offset"
    )
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .offset { IntOffset(bottomBarOffset.x.toInt(), bottomBarOffset.y.toInt()) },
        shadowElevation = 4.dp
    ) {
        SpaceBetweenRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = SpaceVerticalSmall)
                .navigationBarsPadding(),
        ) {
            Row(
                modifier = Modifier.padding(start = SpaceHorizontalSmall)
            ) {
                // 客服按钮
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .width(40.dp)
                        .clip(ShapeSmall)
                        .clickable { onCsClick() }
                        .padding(vertical = SpaceVerticalXSmall)
                ) {
                    CommonIcon(
                        resId = R.drawable.ic_customer_service,
                        size = 20.dp
                    )

                    AppText(
                        text = stringResource(R.string.customer_service),
                        size = TextSize.BODY_SMALL
                    )
                }

                // 购物车按钮
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(40.dp)
                        .clip(ShapeSmall)
                        .clickable { onCartClick() }
                        .padding(vertical = SpaceVerticalXSmall)
                ) {
                    CommonIcon(
                        resId = R.drawable.ic_cart,
                        size = 20.dp
                    )
                    AppText(
                        text = stringResource(R.string.shopping_cart),
                        size = TextSize.BODY_SMALL
                    )
                }
            }

            Row(
                modifier = Modifier.padding(end = SpaceHorizontalLarge)
            ) {
                // 加入购物车按钮（边框样式）
                AppButtonBordered(
                    text = stringResource(R.string.add_to_cart),
                    onClick = onAddToCartClick,
                    type = ButtonType.LINK,
                    shape = ButtonShape.SQUARE,
                    size = ButtonSize.MINI,
                )

                SpaceHorizontalLarge()

                // 立即购买按钮（渐变背景）
                AppButtonFixed(
                    text = stringResource(R.string.buy_now),
                    onClick = onBuyNowClick,
                    size = ButtonSize.MINI,
                    style = ButtonStyle.GRADIENT,
                    shape = ButtonShape.SQUARE
                )
            }
        }
    }

}

/**
 * 商品详情界面浅色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun GoodsDetailScreenPreview() {
    AppTheme {
        GoodsDetailScreen(
            uiState = BaseNetWorkUiState.Success(
                data = GoodsDetail(
                    goodsInfo = previewGoods,
                    coupon = previewMyCoupons
                )
            )
        )
    }
}

/**
 * 商品详情界面深色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun GoodsDetailScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        GoodsDetailScreen(
            uiState = BaseNetWorkUiState.Success(
                data = GoodsDetail(
                    goodsInfo = previewGoods,
                    coupon = previewMyCoupons
                )
            )
        )
    }
}