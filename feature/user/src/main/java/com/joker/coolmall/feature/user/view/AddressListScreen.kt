package com.joker.coolmall.feature.user.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.component.SpaceBetweenRow
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.ui.component.bottombar.AppBottomButton
import com.joker.coolmall.core.ui.component.divider.WeDivider
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.tag.Tag
import com.joker.coolmall.core.ui.component.tag.TagSize
import com.joker.coolmall.core.ui.component.tag.TagType
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
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
            AppBottomButton(
                text = stringResource(id = R.string.address_add_new),
                onClick = toAddressDetail
            )
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
        Column {
            // 地址主要部分
            SpaceBetweenRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpacePaddingMedium)
            ) {
                Column {
                    // 地址第一行
                    AppText(
                        text = "$province$city$district",
                        size = TextSize.TITLE_LARGE
                    )

                    // 地址第二行
                    AppText(
                        text = address,
                        type = TextType.SECONDARY,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                // 编辑按钮 使用 draw 图标资源
                IconButton(
                    onClick = { onEditClick() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit_fill),
                        contentDescription = "编辑",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }

            WeDivider()

            // 联系人信息行
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SpaceHorizontalLarge, vertical = SpaceVerticalMedium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppText(
                    text = name,
                    type = TextType.SECONDARY
                )

                SpaceHorizontalLarge()

                AppText(
                    text = phone,
                    type = TextType.SECONDARY
                )

                Spacer(modifier = Modifier.weight(1f))

                // 默认地址标签
                if (isDefault) {
                    Tag(
                        text = "默认地址",
                        type = TagType.PRIMARY,
                        size = TagSize.SMALL,
                        shape = ShapeMedium
                    )
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

@Composable
@Preview
internal fun AddressListScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        AddressListScreen()
    }
}