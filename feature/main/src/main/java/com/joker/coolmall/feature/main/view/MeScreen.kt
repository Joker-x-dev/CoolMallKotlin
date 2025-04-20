package com.joker.coolmall.feature.main.view

import User
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ArrowRightIcon
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.ui.component.divider.WeDivider
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.feature.main.R
import com.joker.coolmall.feature.main.component.CommonScaffold
import com.joker.coolmall.feature.main.viewmodel.MeViewModel

@Composable
internal fun MeRoute(
    viewModel: MeViewModel = hiltViewModel(),
) {
    // 收集登录状态
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
    // 收集用户信息
    val userInfo by viewModel.userInfo.collectAsStateWithLifecycle()

    MeScreen(
        isLoggedIn = isLoggedIn,
        userInfo = userInfo,
        toLogin = viewModel::toLoginPage,
        toAddressList = viewModel::toAddressListPage,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MeScreen(
    isLoggedIn: Boolean = false,
    userInfo: User? = null,
    toLogin: () -> Unit = {},
    toAddressList: () -> Unit = {},
) {
    CommonScaffold(topBar = { }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(SpacePaddingMedium)
        ) {
            // 用户信息区域
            UserInfoSection(
                isLoggedIn = isLoggedIn, userInfo = userInfo, toLogin = toLogin
            )
            SpaceVerticalMedium()

            // 会员权益卡片
            MembershipCard()
            SpaceVerticalMedium()

            // 订单区域
            OrderSection()
            SpaceVerticalMedium()

            // 我的足迹
            MyFootprintSection()
            SpaceVerticalMedium()

            // 功能菜单区域
            FunctionMenuSection(
                toAddressList = toAddressList
            )
            SpaceVerticalMedium()

        }
    }
}

/**
 * 用户信息区域
 */
@Composable
private fun UserInfoSection(
    isLoggedIn: Boolean, userInfo: User?, toLogin: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { toLogin() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 头像
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_user_fill),
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.Center),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        SpaceHorizontalLarge()

        Column(
            modifier = Modifier.weight(1f)
        ) {
            // 用户名 - 根据登录状态显示
            Text(
                text = (if (isLoggedIn && userInfo != null) userInfo.nickName else "未登录").toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            SpaceVerticalXSmall()

            // 手机号 - 根据登录状态显示
            Text(
                text = if (isLoggedIn && userInfo != null && !userInfo.phone.isNullOrEmpty()) "手机号: ${userInfo.phone}"
                else "点击登录账号",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
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
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF242424) // 会员卡片保持深色背景
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_vip),
                    contentDescription = "会员",
                    tint = Color(0xFFE0A472),
                    modifier = Modifier.size(20.dp)
                )
                SpaceHorizontalSmall()
                Text(
                    text = "会员立享5大权益!",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFE0A472), // 金色
                )
            }

            Text(
                text = "立即开通",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFE0A472), // 金色
                modifier = Modifier
                    .border(1.dp, Color(0xFFE0A472), RoundedCornerShape(16.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}

/**
 * 订单区域
 */
@Composable
private fun OrderSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        // 标题行
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* 查看全部 */ }
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_order_fill),
                    contentDescription = "订单",
                    tint = Primary,
                    modifier = Modifier.size(18.dp)
                )

                SpaceHorizontalSmall()

                Text(
                    text = "我的订单",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "查看全部订单",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                ArrowRightIcon(size = 16.dp)
            }
        }

        WeDivider()

        // 订单状态图标
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OrderStatusItem(
                icon = R.drawable.ic_wait_pay,
                label = "待付款",
                modifier = Modifier.weight(1f),
            )

            OrderStatusItem(
                icon = R.drawable.ic_wait_ship,
                label = "待发货",
                modifier = Modifier.weight(1f),
            )

            OrderStatusItem(
                icon = R.drawable.ic_wait_receive,
                label = "待收货",
                modifier = Modifier.weight(1f),
            )

            OrderStatusItem(
                icon = R.drawable.ic_wait_review,
                label = "待评价",
                modifier = Modifier.weight(1f),
            )

            OrderStatusItem(
                icon = R.drawable.ic_refund,
                label = "退款/售后",
                modifier = Modifier.weight(1f),
            )
        }
    }
}

/**
 * 我的足迹区域
 */
@Composable
private fun MyFootprintSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 标题和计数
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_footprint),
                        contentDescription = null,
                        tint = Color(0xFFFF9800),
                        modifier = Modifier.size(20.dp)
                    )

                    SpaceHorizontalSmall()

                    Text(
                        text = "我的足迹",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { /* 查看更多足迹 */ }) {
                    Text(
                        text = "6",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    ArrowRightIcon(size = 16.dp)
                }
            }

            // 水平滚动的产品列表
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val footprintItems = listOf(
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ffcd84baf3d3a4b49b35a03aaf783281e_%E7%BA%A2%E7%B1%B3%2014c.png",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ffcd84baf3d3a4b49b35a03aaf783281e_%E7%BA%A2%E7%B1%B3%2014c.png",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ffcd84baf3d3a4b49b35a03aaf783281e_%E7%BA%A2%E7%B1%B3%2014c.png",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ffcd84baf3d3a4b49b35a03aaf783281e_%E7%BA%A2%E7%B1%B3%2014c.png",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ffcd84baf3d3a4b49b35a03aaf783281e_%E7%BA%A2%E7%B1%B3%2014c.png",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ffcd84baf3d3a4b49b35a03aaf783281e_%E7%BA%A2%E7%B1%B3%2014c.png",
                )

                items(footprintItems) { imageUrl ->
                    FootprintItem(imageUrl = imageUrl)
                }
            }
        }
    }
}

/**
 * 足迹项
 */
@Composable
private fun FootprintItem(imageUrl: String) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(ShapeSmall)
            .clickable { /* 点击商品 */ }) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
        NetWorkImage(
            model = imageUrl,
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
    modifier: Modifier, icon: Int, label: String, badgeCount: Int = 0
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable(onClick = {})
            .padding(vertical = 16.dp)
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

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * 功能菜单区域
 */
@Composable
private fun FunctionMenuSection(
    toAddressList: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column {
            FunctionMenuItem(
                icon = R.drawable.ic_coupon_fill, title = "优惠券", iconTint = Color(0xFFFF9800)
            )

            // 收货人
            FunctionMenuItem(
                icon = R.drawable.ic_location_fill, title = "收货人", iconTint = Color(0xFF66BB6A),
                onClick = toAddressList
            )

            FunctionMenuItem(
                icon = R.drawable.ic_customer_service_fill,
                title = "客服",
                iconTint = Color(0xFFF87C7B),
                showDivider = false
            )

        }
    }

    SpaceVerticalMedium()

    // 设置选项单独放在一个卡片中
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        // 设置
        FunctionMenuItem(
            icon = R.drawable.ic_settings,
            title = "设置",
            iconTint = Color(0xFF26A69A),
            showDivider = false
        )
    }
}

/**
 * 功能菜单项
 */
@Composable
private fun FunctionMenuItem(
    icon: Int,
    title: String,
    trailingText: String = "",
    iconTint: Color = Color.Black,
    showDivider: Boolean = true,
    onClick: () -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(vertical = 16.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            // 图标
            Icon(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(20.dp),
                tint = iconTint
            )

            SpaceHorizontalMedium()

            // 标题
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.weight(1f))

            // 尾部文本
            if (trailingText.isNotEmpty()) {
                Text(
                    text = trailingText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                SpaceHorizontalXSmall()
            }

            // 右箭头
            ArrowRightIcon(size = 16.dp)
        }

        if (showDivider) {
            WeDivider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MeScreenPreview() {
    AppTheme {
        MeScreen()
    }
}