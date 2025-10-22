package com.joker.coolmall.feature.main.view

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joker.coolmall.core.designsystem.component.SpaceBetweenRow
import com.joker.coolmall.core.designsystem.component.SpaceEvenlyRow
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ArrowRightIcon
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.ShapeLarge
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.model.entity.Footprint
import com.joker.coolmall.core.model.entity.OrderCount
import com.joker.coolmall.core.model.entity.User
import com.joker.coolmall.core.ui.component.badge.WeBadge
import com.joker.coolmall.core.ui.component.image.Avatar
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.feature.main.R
import com.joker.coolmall.feature.main.component.CommonScaffold
import com.joker.coolmall.feature.main.viewmodel.MeViewModel
import com.joker.coolmall.core.ui.R as CoreUiR

/**
 * 个人中心路由
 *
 * @param sharedTransitionScope 共享转换作用域
 * @param animatedContentScope 动画内容作用域
 * @param viewModel 个人中心 ViewModel
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun MeRoute(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    viewModel: MeViewModel = hiltViewModel(),
) {
    // 获取生命周期所有者
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    // 注册生命周期观察者
    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(viewModel)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(viewModel)
        }
    }

    // 收集登录状态
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
    // 收集用户信息
    val userInfo by viewModel.userInfo.collectAsStateWithLifecycle()
    // 收集足迹数据
    val recentFootprints by viewModel.recentFootprints.collectAsStateWithLifecycle()
    // 收集订单统计数据
    val orderCount by viewModel.orderCount.collectAsStateWithLifecycle()

    MeScreen(
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        isLoggedIn = isLoggedIn,
        userInfo = userInfo,
        orderCount = orderCount,
        recentFootprints = recentFootprints,
        onHeadClick = viewModel::onHeadClick,
        toAddressList = viewModel::toAddressListPage,
        toOrderList = viewModel::toOrderListPage,
        toOrderListByTab = viewModel::toOrderListPage,
        toUserFootprint = viewModel::toUserFootprintPage,
        toGoodsDetail = viewModel::toGoodsDetailPage,
        toChat = viewModel::toChatPage,
        toCoupon = viewModel::toCouponPage,
        toFeedback = viewModel::toFeedbackPage,
        toAbout = viewModel::toAboutPage,
        toSettings = viewModel::toSettingsPage,
    )
}

/**
 * 个人中心界面
 *
 * @param sharedTransitionScope 共享转换作用域
 * @param animatedContentScope 动画内容作用域
 * @param isLoggedIn 是否已登录
 * @param userInfo 用户信息
 * @param orderCount 订单统计数据
 * @param recentFootprints 最近足迹列表
 * @param onHeadClick 头像点击回调
 * @param toAddressList 跳转到地址列表
 * @param toOrderList 跳转到订单列表
 * @param toOrderListByTab 跳转到订单列表指定标签
 * @param toUserFootprint 跳转到用户足迹
 * @param toGoodsDetail 跳转到商品详情
 * @param toChat 跳转到在线客服
 * @param toCoupon 跳转到优惠券
 * @param toFeedback 跳转到意见反馈
 * @param toAbout 跳转到关于我们
 * @param toSettings 跳转到设置
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun MeScreen(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    isLoggedIn: Boolean = false,
    userInfo: User? = null,
    orderCount: OrderCount? = null,
    recentFootprints: List<Footprint> = emptyList(),
    onHeadClick: () -> Unit = {},
    toAddressList: () -> Unit = {},
    toOrderList: () -> Unit = {},
    toOrderListByTab: (Int) -> Unit = {},
    toUserFootprint: () -> Unit = {},
    toGoodsDetail: (Long) -> Unit = {},
    toChat: () -> Unit = {},
    toCoupon: () -> Unit = {},
    toFeedback: () -> Unit = {},
    toAbout: () -> Unit = {},
    toSettings: () -> Unit = {},
) {
    CommonScaffold(topBar = { }) { paddingValues ->
        MeContentView(
            isLoggedIn = isLoggedIn,
            userInfo = userInfo ?: User(),
            orderCount = orderCount ?: OrderCount(),
            recentFootprints = recentFootprints,
            onHeadClick = onHeadClick,
            toAddressList = toAddressList,
            toOrderList = toOrderList,
            toOrderListByTab = toOrderListByTab,
            toUserFootprint = toUserFootprint,
            toGoodsDetail = toGoodsDetail,
            toChat = toChat,
            toCoupon = toCoupon,
            toFeedback = toFeedback,
            toAbout = toAbout,
            toSettings = toSettings,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = animatedContentScope,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

/**
 * 个人中心内容视图
 *
 * @param sharedTransitionScope 共享转换作用域
 * @param animatedContentScope 动画内容作用域
 * @param isLoggedIn 是否已登录
 * @param userInfo 用户信息
 * @param orderCount 订单统计数据
 * @param recentFootprints 最近足迹列表
 * @param onHeadClick 头像点击回调
 * @param toAddressList 跳转到地址列表
 * @param toOrderList 跳转到订单列表
 * @param toOrderListByTab 跳转到订单列表指定标签
 * @param toUserFootprint 跳转到用户足迹
 * @param toGoodsDetail 跳转到商品详情
 * @param toChat 跳转到在线客服
 * @param toCoupon 跳转到优惠券
 * @param toFeedback 跳转到意见反馈
 * @param toAbout 跳转到关于我们
 * @param toSettings 跳转到设置
 * @param modifier 修饰符
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun MeContentView(
    isLoggedIn: Boolean,
    userInfo: User,
    orderCount: OrderCount,
    recentFootprints: List<Footprint>,
    onHeadClick: () -> Unit,
    toAddressList: () -> Unit,
    toOrderList: () -> Unit,
    toOrderListByTab: (Int) -> Unit,
    toUserFootprint: () -> Unit,
    toGoodsDetail: (Long) -> Unit,
    toChat: () -> Unit,
    toCoupon: () -> Unit,
    toFeedback: () -> Unit,
    toAbout: () -> Unit,
    toSettings: () -> Unit,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    modifier: Modifier = Modifier
) {
    VerticalList(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        SpaceVerticalMedium()

        // 用户信息区域
        UserInfoSection(
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = animatedContentScope,
            isLoggedIn = isLoggedIn,
            userInfo = userInfo,
            onHeadClick = onHeadClick
        )

        // 会员权益卡片
        MembershipCard()

        // 订单区域
        OrderSection(
            orderCount = orderCount,
            toOrderList = toOrderList,
            toOrderListByTab = toOrderListByTab
        )

        // 我的足迹
        if (recentFootprints.isNotEmpty()) {
            MyFootprintSection(
                footprints = recentFootprints,
                toUserFootprint = toUserFootprint,
                toGoodsDetail = toGoodsDetail
            )
        }

        // 功能菜单区域
        FunctionMenuSection(
            toAddressList = toAddressList,
            toChat = toChat,
            toCoupon = toCoupon,
            toFeedback = toFeedback,
            toAbout = toAbout,
            toSettings = toSettings,
        )
    }
}

/**
 * 用户信息区域
 *
 * @param sharedTransitionScope 共享转换作用域
 * @param animatedContentScope 动画内容作用域
 * @param isLoggedIn 是否已登录
 * @param userInfo 用户信息
 * @param onHeadClick 头像点击回调
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun UserInfoSection(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    isLoggedIn: Boolean,
    userInfo: User?,
    onHeadClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onHeadClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 头像
        Avatar(
            avatarUrl = userInfo?.avatarUrl,
            size = 72.dp,
            modifier = Modifier.let { modifier ->
                if (sharedTransitionScope != null && animatedContentScope != null) {
                    with(sharedTransitionScope) {
                        modifier.sharedElement(
                            sharedContentState = rememberSharedContentState(key = "user_avatar"),
                            animatedVisibilityScope = animatedContentScope
                        )
                    }
                } else {
                    modifier
                }
            },
        )

        SpaceHorizontalLarge()

        Column(
            modifier = Modifier.weight(1f)
        ) {
            // 用户名 - 根据登录状态显示
            AppText(
                text = (if (isLoggedIn && userInfo != null) userInfo.nickName else stringResource(R.string.not_logged_in)).toString(),
                size = TextSize.DISPLAY_MEDIUM
            )

            SpaceVerticalXSmall()

            // 手机号 - 根据登录状态显示
            AppText(
                text = if (isLoggedIn && userInfo != null && !userInfo.phone.isNullOrEmpty())
                    "${stringResource(R.string.phone_number)}: ${userInfo.phone}"
                else stringResource(R.string.click_to_login),
                size = TextSize.BODY_MEDIUM,
                type = TextType.TERTIARY
            )
        }

        // 箭头图标
        ArrowRightIcon(tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }

}

/**
 * 会员权益卡片
 *
 * @author Joker.X
 */
@Composable
private fun MembershipCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF242424) // 会员卡片保持深色背景
        )
    ) {
        SpaceBetweenRow(
            modifier = Modifier.padding(
                horizontal = SpaceHorizontalLarge,
                vertical = SpaceVerticalMedium
            )
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_vip_fill),
                    contentDescription = stringResource(R.string.member),
                    tint = Color(0xFFE0A472),
                    modifier = Modifier.size(20.dp)
                )
                SpaceHorizontalSmall()
                AppText(
                    text = stringResource(R.string.member_benefits),
                    color = Color(0xFFE0A472),
                    fontWeight = FontWeight.Bold
                )
            }
            AppText(
                text = stringResource(R.string.activate_now),
                size = TextSize.BODY_MEDIUM,
                color = Color(0xFFE0A472),
                modifier = Modifier
                    .border(1.dp, Color(0xFFE0A472), ShapeLarge)
                    .padding(horizontal = SpaceHorizontalMedium, vertical = 6.dp)
            )
        }
    }
}

/**
 * 订单区域
 *
 * @param orderCount 订单统计数据
 * @param toOrderList 跳转到订单列表
 * @param toOrderListByTab 跳转到订单列表指定标签
 * @author Joker.X
 */
@Composable
private fun OrderSection(
    orderCount: OrderCount? = null,
    toOrderList: () -> Unit = {},
    toOrderListByTab: (Int) -> Unit = {}
) {
    Card {
        // 标题行
        AppListItem(
            title = stringResource(R.string.my_orders),
            trailingText = stringResource(R.string.view_all),
            leadingIcon = R.drawable.ic_order_fill,
            leadingIconTint = Primary,
            onClick = toOrderList
        )
        // 订单状态图标
        SpaceEvenlyRow {
            OrderStatusItem(
                icon = R.drawable.ic_pay,
                label = stringResource(R.string.pending_payment),
                badgeCount = orderCount?.pendingPayment ?: 0,
                modifier = Modifier.weight(1f),
                onClick = { toOrderListByTab(1) } // 待付款对应的标签索引为1
            )

            OrderStatusItem(
                icon = R.drawable.ic_receipt,
                label = stringResource(R.string.pending_shipment),
                badgeCount = orderCount?.pendingShipment ?: 0,
                modifier = Modifier.weight(1f),
                onClick = { toOrderListByTab(2) } // 待发货对应的标签索引为2
            )

            OrderStatusItem(
                icon = R.drawable.ic_logistics,
                label = stringResource(R.string.pending_receipt),
                badgeCount = orderCount?.pendingReceive ?: 0,
                modifier = Modifier.weight(1f),
                onClick = { toOrderListByTab(3) } // 待收货对应的标签索引为3
            )

            OrderStatusItem(
                icon = R.drawable.ic_message,
                label = stringResource(R.string.pending_review),
                badgeCount = orderCount?.pendingReview ?: 0,
                modifier = Modifier.weight(1f),
                onClick = { toOrderListByTab(5) } // 待评价对应的标签索引为5
            )

            OrderStatusItem(
                icon = CoreUiR.drawable.ic_refund,
                label = stringResource(R.string.refund_after_sale),
                badgeCount = (orderCount?.refunding ?: 0) + (orderCount?.refunded ?: 0),
                modifier = Modifier.weight(1f),
                onClick = { toOrderListByTab(4) } // 售后对应的标签索引为4
            )
        }
    }
}

/**
 * 我的足迹区域
 *
 * @param footprints 足迹列表
 * @param toUserFootprint 跳转到足迹页面
 * @param toGoodsDetail 跳转到商品详情
 * @author Joker.X
 */
@Composable
private fun MyFootprintSection(
    footprints: List<Footprint> = emptyList(),
    toUserFootprint: () -> Unit = {},
    toGoodsDetail: (Long) -> Unit = {}
) {
    Card {
        AppListItem(
            title = stringResource(R.string.my_footprint),
            trailingText = stringResource(R.string.view_all),
            leadingIcon = R.drawable.ic_footprint_fill,
            leadingIconTint = Color(0xFFFF9800),
            onClick = toUserFootprint
        )
        // 水平滚动的产品列表
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(SpacePaddingMedium)
        ) {
            items(footprints) { footprint ->
                FootprintItem(
                    footprint = footprint,
                    onClick = { toGoodsDetail(footprint.goodsId) }
                )
            }
        }
    }
}

/**
 * 足迹项
 *
 * @param footprint 足迹数据
 * @param onClick 点击回调
 * @author Joker.X
 */
@Composable
private fun FootprintItem(
    footprint: Footprint,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(ShapeSmall)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
        NetWorkImage(
            model = footprint.goodsMainPic,
            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(ShapeSmall)
        )
    }
}

/**
 * 订单状态项
 *
 * @param modifier 修饰符
 * @param icon 图标资源
 * @param label 标签文字
 * @param badgeCount 徽章数量
 * @param onClick 点击回调
 * @author Joker.X
 */
@Composable
private fun OrderStatusItem(
    modifier: Modifier,
    icon: Int,
    label: String,
    badgeCount: Int = 0,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = SpaceVerticalMedium)
    ) {

        if (badgeCount > 0) {
            WeBadge(
                content = badgeCount.toString(),
                size = 16.dp,
                color = MaterialTheme.colorScheme.error,
                alignment = Alignment.TopEnd
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = label,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        } else {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        SpaceVerticalSmall()

        AppText(
            text = label,
            size = TextSize.BODY_MEDIUM
        )
    }
}

/**
 * 功能菜单区域
 *
 * @param toAddressList 跳转到地址列表
 * @param toChat 跳转到在线客服
 * @param toCoupon 跳转到优惠券
 * @param toFeedback 跳转到意见反馈
 * @param toAbout 跳转到关于我们
 * @param toSettings 跳转到设置
 * @author Joker.X
 */
@Composable
private fun FunctionMenuSection(
    toAddressList: () -> Unit,
    toChat: () -> Unit,
    toCoupon: () -> Unit,
    toFeedback: () -> Unit,
    toAbout: () -> Unit,
    toSettings: () -> Unit,
) {
    Card {
        AppListItem(
            title = stringResource(R.string.coupons),
            leadingIcon = R.drawable.ic_coupon_fill,
            leadingIconTint = Color(0xFF6A9BE6),
            verticalPadding = SpaceVerticalLarge,
            onClick = toCoupon
        )

        AppListItem(
            title = stringResource(R.string.shipping_address),
            leadingIcon = R.drawable.ic_location_fill,
            leadingIconTint = Color(0xFF66BB6A),
            verticalPadding = SpaceVerticalLarge,
            onClick = toAddressList
        )

        AppListItem(
            title = stringResource(R.string.online_customer_service),
            leadingIcon = R.drawable.ic_service_fill,
            leadingIconTint = Color(0xFFF87C7B),
            verticalPadding = SpaceVerticalLarge,
            onClick = toChat
        )

        AppListItem(
            title = stringResource(R.string.feedback),
            leadingIcon = R.drawable.ic_creative_fill,
            leadingIconTint = Color(0xFFF3AF76),
            verticalPadding = SpaceVerticalLarge,
            onClick = toFeedback
        )

        AppListItem(
            title = stringResource(R.string.about_us),
            leadingIcon = R.drawable.ic_tip_fill,
            leadingIconTint = Color(0xFF9179F1),
            verticalPadding = SpaceVerticalLarge,
            showDivider = false,
            onClick = toAbout
        )
    }

    // 设置选项单独放在一个卡片中
    Card {
        AppListItem(
            title = stringResource(R.string.settings),
            leadingIcon = R.drawable.ic_set_fill,
            leadingIconTint = Color(0xFF26A69A),
            verticalPadding = SpaceVerticalLarge,
            showDivider = false,
            onClick = toSettings
        )
    }
}

/**
 * 个人中心界面浅色主题预览
 *
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun MeScreenPreview() {
    AppTheme {
        MeScreen()
    }
}

/**
 * 个人中心界面深色主题预览
 *
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun MeScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        MeScreen()
    }
}