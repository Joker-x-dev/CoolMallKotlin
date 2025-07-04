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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.joker.coolmall.core.model.entity.User
import com.joker.coolmall.core.ui.component.image.Avatar
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.feature.main.R
import com.joker.coolmall.feature.main.component.CommonScaffold
import com.joker.coolmall.feature.main.viewmodel.MeViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun MeRoute(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    viewModel: MeViewModel = hiltViewModel(),
) {
    // 收集登录状态
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
    // 收集用户信息
    val userInfo by viewModel.userInfo.collectAsStateWithLifecycle()
    // 收集足迹数据
    val recentFootprints by viewModel.recentFootprints.collectAsStateWithLifecycle()

    MeScreen(
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        isLoggedIn = isLoggedIn,
        userInfo = userInfo,
        recentFootprints = recentFootprints,
        onHeadClick = viewModel::onHeadClick,
        toAddressList = viewModel::toAddressListPage,
        toOrderList = viewModel::toOrderListPage,
        toOrderListByTab = viewModel::toOrderListPage,
        toUserFootprint = viewModel::toUserFootprintPage,
        toGoodsDetail = viewModel::toGoodsDetailPage,
        toChat = viewModel::toChatPage,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun MeScreen(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    isLoggedIn: Boolean = false,
    userInfo: User? = null,
    recentFootprints: List<Footprint> = emptyList(),
    onHeadClick: () -> Unit = {},
    toAddressList: () -> Unit = {},
    toOrderList: () -> Unit = {},
    toOrderListByTab: (Int) -> Unit = {},
    toUserFootprint: () -> Unit = {},
    toGoodsDetail: (Long) -> Unit = {},
    toChat: () -> Unit = {}
) {
    CommonScaffold(topBar = { }) {
        VerticalList(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            // 用户信息区域
            UserInfoSection(
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope,
                isLoggedIn = isLoggedIn, userInfo = userInfo, onHeadClick = onHeadClick
            )

            // 会员权益卡片
            MembershipCard()

            // 订单区域
            OrderSection(
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
                toChat = toChat
            )

        }
    }
}

/**
 * 用户信息区域
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
                text = (if (isLoggedIn && userInfo != null) userInfo.nickName else "未登录").toString(),
                size = TextSize.DISPLAY_MEDIUM
            )

            SpaceVerticalXSmall()

            // 手机号 - 根据登录状态显示
            AppText(
                text = if (isLoggedIn && userInfo != null && !userInfo.phone.isNullOrEmpty()) "手机号: ${userInfo.phone}"
                else "点击登录账号",
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
                    contentDescription = "会员",
                    tint = Color(0xFFE0A472),
                    modifier = Modifier.size(20.dp)
                )
                SpaceHorizontalSmall()
                AppText(
                    text = "会员立享5大权益!",
                    color = Color(0xFFE0A472),
                    fontWeight = FontWeight.Bold
                )
            }
            AppText(
                text = "立即开通",
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
 */
@Composable
private fun OrderSection(
    toOrderList: () -> Unit = {},
    toOrderListByTab: (Int) -> Unit = {}
) {
    Card {
        // 标题行
        AppListItem(
            title = "我的订单",
            trailingText = "查看全部订单",
            leadingIcon = R.drawable.ic_order_fill,
            leadingIconTint = Primary,
            onClick = toOrderList
        )
        // 订单状态图标
        SpaceEvenlyRow {
            OrderStatusItem(
                icon = R.drawable.ic_pay,
                label = "待付款",
                modifier = Modifier.weight(1f),
                onClick = { toOrderListByTab(1) } // 待付款对应的标签索引为1
            )

            OrderStatusItem(
                icon = R.drawable.ic_receipt,
                label = "待发货",
                modifier = Modifier.weight(1f),
                onClick = { toOrderListByTab(2) } // 待发货对应的标签索引为2
            )

            OrderStatusItem(
                icon = R.drawable.ic_logistics,
                label = "待收货",
                modifier = Modifier.weight(1f),
                onClick = { toOrderListByTab(3) } // 待收货对应的标签索引为3
            )

            OrderStatusItem(
                icon = R.drawable.ic_message,
                label = "待评价",
                modifier = Modifier.weight(1f),
                onClick = { toOrderListByTab(5) } // 待评价对应的标签索引为5
            )

            OrderStatusItem(
                icon = R.drawable.ic_refund,
                label = "退款/售后",
                modifier = Modifier.weight(1f),
                onClick = { toOrderListByTab(4) } // 售后对应的标签索引为4
            )
        }
    }
}

/**
 * 我的足迹区域
 */
@Composable
private fun MyFootprintSection(
    footprints: List<Footprint> = emptyList(),
    toUserFootprint: () -> Unit = {},
    toGoodsDetail: (Long) -> Unit = {}
) {
    Card {
        AppListItem(
            title = "我的足迹",
            trailingText = if (footprints.isNotEmpty()) "查看全部" else "暂无足迹",
            leadingIcon = R.drawable.ic_footprint_fill,
            leadingIconTint = Color(0xFFFF9800),
            onClick = toUserFootprint
        )

        if (footprints.isNotEmpty()) {
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
        } else {
            // 空状态提示
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceVerticalLarge),
                contentAlignment = Alignment.Center
            ) {
                AppText(
                    text = "暂无浏览记录",
                    type = TextType.TERTIARY,
                    size = TextSize.BODY_MEDIUM
                )
            }
        }
    }
}

/**
 * 足迹项
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
        Box {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )

            // 如果有角标数字，则显示
            if (badgeCount > 0) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.error)
                        .offset(x = 10.dp, y = (-6).dp)
                        .align(Alignment.TopEnd), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = badgeCount.toString(),
                        color = MaterialTheme.colorScheme.onError,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
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
 */
@Composable
private fun FunctionMenuSection(
    toAddressList: () -> Unit,
    toChat: () -> Unit
) {
    Card {
        AppListItem(
            title = "优惠券",
            leadingIcon = R.drawable.ic_coupon_fill,
            leadingIconTint = Color(0xFFFF9800),
            verticalPadding = SpaceVerticalLarge
        )

        AppListItem(
            title = "收货人",
            leadingIcon = R.drawable.ic_location_fill,
            leadingIconTint = Color(0xFF66BB6A),
            verticalPadding = SpaceVerticalLarge,
            onClick = toAddressList
        )

        AppListItem(
            title = "客服",
            leadingIcon = R.drawable.ic_service_fill,
            leadingIconTint = Color(0xFFF87C7B),
            verticalPadding = SpaceVerticalLarge,
            showDivider = false,
            onClick = toChat
        )
    }

    // 设置选项单独放在一个卡片中
    Card {
        AppListItem(
            title = "设置",
            leadingIcon = R.drawable.ic_set_fill,
            leadingIconTint = Color(0xFF26A69A),
            verticalPadding = SpaceVerticalLarge,
            showDivider = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MeScreenPreview() {
    AppTheme {
//        MeScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun MeScreenPreviewDark() {
    AppTheme(darkTheme = true) {
//        MeScreen()
    }
}