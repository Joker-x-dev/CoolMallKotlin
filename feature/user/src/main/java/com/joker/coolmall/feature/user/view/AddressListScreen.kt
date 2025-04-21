package com.joker.coolmall.feature.user.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.ShapeCircle
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingXSmall
import com.joker.coolmall.core.designsystem.theme.TitleLarge
import com.joker.coolmall.core.ui.component.divider.WeDivider
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.feature.user.R
import com.joker.coolmall.feature.user.viewmodel.AddressListViewModel

/**
 * 收货地址列表路由
 *
 * @param viewModel 收货地址列表ViewModel
 */
@Composable
internal fun AddressListRoute(
    viewModel: AddressListViewModel = hiltViewModel()
) {
    AddressListScreen(
        toAddressDetail = viewModel::toAddressDetailPage,
        toAddressDetailEdit = viewModel::toAddressDetailEditPage,
        onBackClick = viewModel::navigateBack
    )
}

/**
 * 收货地址列表界面
 *
 * @param toAddressDetail 导航到收货地址详情（新增模式）
 * @param toAddressDetailEdit 导航到收货地址详情（编辑模式）
 * @param onBackClick 返回上一页回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddressListScreen(
    toAddressDetail: () -> Unit = {},
    toAddressDetailEdit: (Long) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    AppScaffold(
        title = R.string.address_list_title,
        onBackClick = onBackClick,
        bottomBar = {
            // 底部添加按钮
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                shadowElevation = 4.dp,
            ) {

                Button(
                    onClick = toAddressDetail,
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(SpacePaddingMedium)
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.address_add_new),
                        style = TitleLarge
                    )
                }
            }
        }
    ) {
        // 地址列表
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = SpacePaddingMedium),
            verticalArrangement = Arrangement.spacedBy(SpacePaddingMedium)
        ) {

            // 地址项
            AddressItem(
                province = "云南省",
                city = "曲靖市",
                district = "麒麟区",
                address = "曲靖南城门",
                name = "Joker",
                phone = "188****8888",
                isDefault = true,
                onEditClick = { toAddressDetailEdit(101) }
            )

            AddressItem(
                province = "北京市",
                city = "市辖区",
                district = "朝阳区",
                address = "天安门广场",
                name = "小明",
                phone = "186****6666",
                isDefault = false,
                onEditClick = { toAddressDetailEdit(102) }
            )

            AddressItem(
                province = "广东省",
                city = "广州市",
                district = "天河区",
                address = "天河路385号太平洋数码广场",
                name = "张三",
                phone = "133****3333",
                isDefault = false,
                onEditClick = { toAddressDetailEdit(103) }
            )

            AddressItem(
                province = "浙江省",
                city = "杭州市",
                district = "西湖区",
                address = "西湖风景区",
                name = "李四",
                phone = "177****7777",
                isDefault = false,
                onEditClick = { toAddressDetailEdit(104) }
            )
        }
    }
}

/**
 * 默认地址标签
 */
@Composable
private fun DefaultAddressTag() {
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
            text = "默认地址",
            style = MaterialTheme.typography.labelSmall,
            color = Color.White
        )
    }
}

/**
 * 设为默认地址标签
 */
@Composable
private fun SetDefaultAddressTag(onClick: () -> Unit = {}) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFF5F5F5))
            .clickable(onClick = onClick)
            .padding(horizontal = SpacePaddingSmall, vertical = SpacePaddingXSmall)
    ) {
        Text(
            text = stringResource(id = R.string.set_default_address),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
    }
}

/**
 * 地址项卡片
 */
@Composable
private fun AddressItem(
    province: String,
    city: String,
    district: String,
    address: String,
    name: String,
    phone: String,
    isDefault: Boolean = false,
    onEditClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SpacePaddingMedium)
            .clickable { },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // 地址主要部分
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpacePaddingMedium)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // 地址第一行
                    Text(
                        text = "$province$city$district",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    // 地址第二行
                    Text(
                        text = address,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        color = Color.DarkGray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                // 编辑按钮
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "编辑",
                    tint = Color.Gray,
                    modifier = Modifier
                        .padding(start = 8.dp, top = 4.dp)
                        .size(20.dp)
                        .clickable { onEditClick() }
                )
            }

            WeDivider()

            // 联系人信息行
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = phone,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                )

                Spacer(modifier = Modifier.weight(1f))

                // 默认地址标签
                if (isDefault) {
                    DefaultAddressTag()
                } else {
                    SetDefaultAddressTag()
                }
            }
        }
    }
}

@Composable
@Preview
internal fun AddressListScreenPreview() {
    AppTheme {
        AddressListScreen()
    }
}