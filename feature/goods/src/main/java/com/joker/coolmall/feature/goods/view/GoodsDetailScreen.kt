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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ColorDanger
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.ShapeCircle
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceDivider
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingLarge
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingXSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.model.entity.GoodsSpec
import com.joker.coolmall.core.model.entity.SelectedGoods
import com.joker.coolmall.core.ui.component.button.AppButtonBordered
import com.joker.coolmall.core.ui.component.button.AppButtonFixed
import com.joker.coolmall.core.ui.component.button.ButtonShape
import com.joker.coolmall.core.ui.component.button.ButtonSize
import com.joker.coolmall.core.ui.component.button.ButtonStyle
import com.joker.coolmall.core.ui.component.button.ButtonType
import com.joker.coolmall.core.ui.component.card.AppCard
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.modal.SpecSelectModal
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.swiper.WeSwiper
import com.joker.coolmall.core.ui.component.text.PriceText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.title.TitleWithLine
import com.joker.coolmall.feature.goods.R
import com.joker.coolmall.feature.goods.viewmodel.GoodsDetailViewModel
import kotlinx.coroutines.flow.collectLatest

/**
 * 商品详情页面路由入口
 */
@Composable
internal fun GoodsDetailRoute(
    viewModel: GoodsDetailViewModel = hiltViewModel()
) {
    // 从ViewModel收集UI状态
    val uiState by viewModel.uiState.collectAsState()
    // 收集规格选择弹窗状态
    val specModalVisible by viewModel.specModalVisible.collectAsState()
    // 收集规格列表状态
    val specsModalUiState by viewModel.specsModalUiState.collectAsState()
    // 收集选中的规格
    val selectedSpec by viewModel.selectedSpec.collectAsState()
    // 收集动画状态
    val hasAnimated by viewModel.hasAnimated.collectAsState()

    GoodsDetailScreen(
        uiState = uiState,
        onBackClick = { viewModel.navigateBack() },
        onRetry = viewModel::retryRequest,
        specModalVisible = specModalVisible,
        specsModalUiState = specsModalUiState,
        selectedSpec = selectedSpec,
        onSpecSelected = viewModel::selectSpec,
        onShowSpecModal = viewModel::showSpecModal,
        onHideSpecModal = viewModel::hideSpecModal,
        onAddToCart = viewModel::addToCart,
        onBuyNow = viewModel::buyNow,
        onSpecRetry = viewModel::loadGoodsSpecs,
        hasAnimated = hasAnimated,
        onTriggerAnimation = viewModel::triggerAnimation
    )
}

/**
 * 商品详情页面UI
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
 * @param onAddToCart 加入购物车回调，参数为包含商品和规格信息的购物车对象
 * @param onBuyNow 立即购买回调，参数为包含商品和规格信息的购物车对象
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GoodsDetailScreen(
    uiState: BaseNetWorkUiState<Goods> = BaseNetWorkUiState.Loading,
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {},
    onSpecRetry: () -> Unit = {},
    specModalVisible: Boolean = false,
    specsModalUiState: BaseNetWorkUiState<List<GoodsSpec>> = BaseNetWorkUiState.Loading,
    selectedSpec: GoodsSpec? = null,
    onSpecSelected: (GoodsSpec) -> Unit = {},
    onShowSpecModal: () -> Unit = {},
    onHideSpecModal: () -> Unit = {},
    onAddToCart: (SelectedGoods) -> Unit = {},
    onBuyNow: (SelectedGoods) -> Unit = {},
    hasAnimated: Boolean = false,
    onTriggerAnimation: () -> Unit = {}
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
        ) { goods ->
            GoodsDetailContentView(
                data = goods,
                onBackClick = onBackClick,
                paddingValues = paddingValues,
                selectedSpec = selectedSpec,
                onShowSpecModal = onShowSpecModal,
                hasAnimated = hasAnimated,
                onTriggerAnimation = onTriggerAnimation
            )

            // 规格选择底部弹出层
            SpecSelectModal(
                visible = specModalVisible,
                onDismiss = onHideSpecModal,
                goods = goods,
                uiState = specsModalUiState,
                onSpecSelected = onSpecSelected,
                onAddToCart = onAddToCart,
                onBuyNow = onBuyNow,
                onRetry = onSpecRetry,
                selectedSpec = selectedSpec
            )
        }
    }
}

/**
 * 商品详情主内容
 *
 * @param data 商品详情数据对象
 * @param onBackClick 返回按钮点击回调
 * @param paddingValues 页面内边距值，用于适配系统UI（如状态栏、导航栏）
 * @param selectedSpec 当前选中的商品规格，若为null则表示未选择规格
 * @param onShowSpecModal 显示规格选择弹窗的回调函数
 */
@Composable
private fun GoodsDetailContentView(
    data: Goods,
    onBackClick: () -> Unit,
    paddingValues: PaddingValues,
    selectedSpec: GoodsSpec? = null,
    onShowSpecModal: () -> Unit = {},
    hasAnimated: Boolean = false,
    onTriggerAnimation: () -> Unit = {}
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
            data = data,
            selectedSpec = selectedSpec,
            onTopBarAlphaChanged = { topBarAlpha = it },
            onShowSpecModal = onShowSpecModal
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
            hasAnimated = hasAnimated
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
            icon = com.joker.coolmall.core.designsystem.R.drawable.ic_arrow_left,
            onClick = onBackClick,
            iconSize = 28.dp,
            scale = buttonScale
        )

        Spacer(modifier = Modifier.weight(1f))

        CircleIconButton(
            icon = R.drawable.ic_share_fill,
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
 * @param selectedSpec 当前选中的商品规格，若为null则表示未选择规格
 * @param onTopBarAlphaChanged 顶部导航栏透明度变化回调，参数为新的透明度值
 * @param onShowSpecModal 显示规格选择弹窗的回调函数
 */
@Composable
private fun GoodsDetailContentWithScroll(
    data: Goods,
    selectedSpec: GoodsSpec? = null,
    onTopBarAlphaChanged: (Int) -> Unit,
    onShowSpecModal: () -> Unit
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(scrollState) {
        snapshotFlow {
            scrollState.value
        }.collectLatest { scrollY ->
            var alpha = scrollY
            if (alpha > 255) {
                alpha = 255
            }
            onTopBarAlphaChanged(alpha)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // 轮播图直接放在顶部，没有状态栏的padding
        data.pics.let {
            GoodsBanner(data.pics!!)
        }

        Column(
            modifier = Modifier.padding(SpacePaddingMedium)
        ) {
            // 基本信息
            GoodsInfoCard(data, selectedSpec, onShowSpecModal)

            SpaceVerticalMedium()

            // 配送信息
            GoodsDeliveryCard()

            SpaceVerticalMedium()

            // 图文详情
            if (data.contentPics != null) {
                GoodsDetailCard(data.contentPics!!)
            }

            // 底部安全区域（为底部操作栏留出空间）
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

/**
 * 商品轮播图
 *
 * @param images 图片URL列表，用于轮播展示
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
 * @param selectedSpec 当前选中的商品规格，若为null则表示未选择规格
 * @param onShowSpecModal 显示规格选择弹窗的回调函数
 */
@Composable
private fun GoodsInfoCard(
    data: Goods,
    selectedSpec: GoodsSpec? = null,
    onShowSpecModal: () -> Unit
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
        CouponList()

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
 *
 * 展示可用的优惠券信息列表
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
 *
 * @param text 优惠券文本内容，如"满100减10元"
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
            painter = painterResource(id = R.drawable.ic_coupon),
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
 *
 * @param selectedSpec 当前选中的商品规格，若为null则表示未选择规格
 * @param onClick 点击规格选择区域的回调，用于打开规格选择弹窗
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
                painter = painterResource(id = R.drawable.ic_spec),
                contentDescription = "规格",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.size(SpaceVerticalLarge)
            )

            SpaceHorizontalSmall()

            // 根据是否选中规格显示不同的文本
            Text(
                text = if (selectedSpec != null) "已选：${selectedSpec.name}" else "选择规格",
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
 *
 * 显示商品的配送相关信息，包括送货地址和服务承诺
 */
@Composable
private fun GoodsDeliveryCard() {
    Card {
        AppListItem(
            title = "",
            showArrow = false,
            leadingContent = {
                TitleWithLine(text = "发货与服务")
            }
        )

        AppListItem(
            title = "送至",
            showArrow = false,
            trailingText = "广州市 天河区"
        )

        AppListItem(
            title = "服务",
            showArrow = false,
            showDivider = false,
            trailingText = "7天无理由退货·运费险·48小时发货"
        )
    }
}

/**
 * 商品详情卡片
 *
 * @param contentPics 商品详情图片列表
 */
@Composable
private fun GoodsDetailCard(contentPics: List<String>) {
    Card {
        AppListItem(
            title = "",
            showArrow = false,
            leadingContent = {
                TitleWithLine(text = stringResource(id = R.string.goods_detail))
            }
        )

        // 循环图片
        contentPics.forEachIndexed { index, pic ->
            NetWorkImage(
                model = pic,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

/**
 * 底部操作栏
 *
 * @param modifier 应用于底部操作栏的Modifier
 * @param onAddToCartClick 加入购物车按钮点击回调
 * @param onBuyNowClick 立即购买按钮点击回调
 * @param hasAnimated 是否已经播放过动画
 */
@Composable
private fun GoodsActionBar(
    modifier: Modifier = Modifier,
    onAddToCartClick: () -> Unit = {},
    onBuyNowClick: () -> Unit = {},
    hasAnimated: Boolean = false
) {
    // 底部操作栏从底部升起的动画
    val bottomBarOffset by animateOffsetAsState(
        targetValue = if (hasAnimated) Offset(0f, 0f) else Offset(0f, 200f),
        animationSpec = tween(durationMillis = 800),
        label = "bottom_bar_offset"
    )
    val outlineColor = MaterialTheme.colorScheme.outline
    Row(
        modifier = modifier
            .fillMaxWidth()
            .offset { IntOffset(bottomBarOffset.x.toInt(), bottomBarOffset.y.toInt()) }
            // 绘制上边框
            .drawWithContent {
                drawContent() // 先绘制内容
                drawLine(
                    color = outlineColor,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = SpaceDivider.toPx()
                )
            }
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = SpacePaddingLarge, vertical = SpaceVerticalSmall)
            .navigationBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            // 客服按钮
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(end = SpaceHorizontalSmall)
            ) {
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .size(24.dp)
                        .clip(ShapeCircle)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(0.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_customer_service),
                        contentDescription = "客服"
                    )
                }

                Text(
                    text = "客服",
                    fontSize = 10.sp
                )
            }

            SpaceHorizontalLarge()

            // 购物车按钮
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(end = SpaceHorizontalSmall)
            ) {
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .size(24.dp)
                        .clip(ShapeCircle)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(0.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cart),
                        contentDescription = "购物车",
                    )
                }

                Text(
                    text = "购物车",
                    fontSize = 10.sp
                )
            }
        }

        Row {
            // 加入购物车按钮（边框样式）
            AppButtonBordered(
                text = "加入购物车",
                onClick = onAddToCartClick,
                type = ButtonType.LINK,
                shape = ButtonShape.SQUARE,
                size = ButtonSize.MINI,
            )

            SpaceHorizontalLarge()

            // 立即购买按钮（渐变背景）
            AppButtonFixed(
                text = "立即购买",
                onClick = onBuyNowClick,
                size = ButtonSize.MINI,
                style = ButtonStyle.GRADIENT,
                shape = ButtonShape.SQUARE
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GoodsDetailScreenPreview() {
    AppTheme {
        GoodsDetailScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Goods(
                    id = 1,
                    createTime = "2025-03-29 00:17:15",
                    updateTime = "2025-03-29 23:07:48",
                    typeId = 11,
                    title = "Redmi 14C",
                    subTitle = "【持久续航】5160mAh 大电池",
                    mainPic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ffcd84baf3d3a4b49b35a03aaf783281e_%E7%BA%A2%E7%B1%B3%2014c.png",
                    pics = listOf(
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
                    price = 499,
                    sold = 0,
                    content = "<p><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F5c161af71062402d8dc7e3193e62d8f5_d1.png\" alt=\"\" data-href=\"\" style=\"width: 100%;\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Fea304cef45b846d2b7fc4e7fbef6d103_d2.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p><p><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ff3d17dae77d144b9aa828537f96d04e4_d3.jpg\" alt=\"\" data-href=\"\" style=\"width: 100%;\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F99710ccacd5443518a9b97386d028b5c_d4.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Fa180b572f52142d5811dcf4e18c27a95_d5.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ff5bab785f9d04ac38b35e10a1b63486e_d6.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p><p><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F19f52075481c44a789dcf648e3f8a7aa_d7.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p>",
                    status = 1,
                    sortNum = 0,
                    specs = null
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GoodsDetailScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        GoodsDetailScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Goods(
                    id = 1,
                    createTime = "2025-03-29 00:17:15",
                    updateTime = "2025-03-29 23:07:48",
                    typeId = 11,
                    title = "Redmi 14C",
                    subTitle = "【持久续航】5160mAh 大电池",
                    mainPic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ffcd84baf3d3a4b49b35a03aaf783281e_%E7%BA%A2%E7%B1%B3%2014c.png",
                    pics = listOf(
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
                    price = 499,
                    sold = 0,
                    content = "<p><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F5c161af71062402d8dc7e3193e62d8f5_d1.png\" alt=\"\" data-href=\"\" style=\"width: 100%;\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Fea304cef45b846d2b7fc4e7fbef6d103_d2.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p><p><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ff3d17dae77d144b9aa828537f96d04e4_d3.jpg\" alt=\"\" data-href=\"\" style=\"width: 100%;\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F99710ccacd5443518a9b97386d028b5c_d4.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Fa180b572f52142d5811dcf4e18c27a95_d5.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ff5bab785f9d04ac38b35e10a1b63486e_d6.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p><p><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F19f52075481c44a789dcf648e3f8a7aa_d7.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p>",
                    status = 1,
                    sortNum = 0,
                    specs = null
                )
            )
        )
    }
}