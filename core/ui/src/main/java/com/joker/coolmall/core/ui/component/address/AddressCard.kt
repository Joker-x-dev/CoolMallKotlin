package com.joker.coolmall.core.ui.component.address

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.component.SpaceBetweenRow
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ArrowRightIcon
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.model.entity.Address
import com.joker.coolmall.core.ui.R
import com.joker.coolmall.core.ui.component.divider.WeDivider
import com.joker.coolmall.core.ui.component.tag.Tag
import com.joker.coolmall.core.ui.component.tag.TagSize
import com.joker.coolmall.core.ui.component.tag.TagType
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType

/**
 * 通用收货地址卡片组件
 *
 * 该组件可用于收货地址列表、确认订单等多个场景
 *
 * @param modifier 修饰符
 * @param address 地址数据，为null时显示选择地址布局
 * @param onClick 卡片点击回调
 * @param actionSlot 右上角操作区域插槽，可根据场景定制
 * @param showBottomBar 是否显示底部栏，包含联系人和标签
 * @param addressSelected 在订单确认页标记为已选中的地址
 * @author Joker.X
 */
@Composable
fun AddressCard(
    modifier: Modifier = Modifier,
    address: Address? = null,
    onClick: () -> Unit = {},
    actionSlot: (@Composable () -> Unit)? = null,
    showBottomBar: Boolean = true,
    addressSelected: Boolean = false
) {
    Card(
        modifier = modifier
            .clip(ShapeMedium)
            .clickable { onClick() },
    ) {
        if (address != null) {
            // 有地址数据时显示正常布局
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
                            text = "${address.province}${address.city}${address.district}",
                            size = TextSize.TITLE_LARGE
                        )

                        // 地址第二行
                        AppText(
                            text = address.address,
                            type = TextType.SECONDARY,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    // 右侧操作区域插槽，传入null则显示默认的箭头图标
                    if (actionSlot != null) {
                        actionSlot()
                    } else if (addressSelected) {
                        // 订单确认页面选中时显示箭头
                        ArrowRightIcon()
                    }
                }

                // 底部联系人信息栏，可选显示
                if (showBottomBar) {
                    WeDivider()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = SpaceHorizontalLarge,
                                vertical = SpaceVerticalMedium
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AppText(
                            text = address.contact,
                            type = TextType.SECONDARY
                        )

                        SpaceHorizontalLarge()

                        AppText(
                            text = address.phone,
                            type = TextType.SECONDARY
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        // 默认地址标签
                        if (address.isDefault) {
                            Tag(
                                text = stringResource(id = R.string.address_default),
                                type = TagType.PRIMARY,
                                size = TagSize.SMALL,
                                shape = ShapeMedium
                            )
                        }
                    }
                }
            }
        } else {
            // 没有地址数据时显示选择地址布局
            Column {
                SpaceBetweenRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SpacePaddingMedium)
                ) {
                    AppText(
                        text = stringResource(id = R.string.address_not_selected),
                        size = TextSize.TITLE_LARGE
                    )
                    ArrowRightIcon()
                }

                WeDivider()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = SpaceHorizontalLarge,
                            vertical = SpaceVerticalMedium
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppText(
                        text = stringResource(id = R.string.address_add_hint),
                        type = TextType.SECONDARY
                    )
                }
            }
        }
    }
}

/**
 * 地址列表场景的编辑按钮
 *
 * @param onClick 点击回调
 * @param iconResId 图标资源ID
 * @author Joker.X
 */
@Composable
fun AddressActionButton(
    onClick: () -> Unit,
    iconResId: Int
) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        CommonIcon(
            resId = iconResId,
            tint = LocalContentColor.current.copy(alpha = 0.5f),
            size = 18.dp
        )
    }
}

/**
 * 收货地址卡片默认样式预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun AddressCardPreview() {
    AppTheme {
        AddressCard(
            address = Address(
                id = 1,
                province = "广东省",
                city = "深圳市",
                district = "南山区",
                address = "科技园南区T3栋 8楼",
                contact = "张三",
                phone = "13800138000",
                isDefault = true
            )
        )
    }
}

/**
 * 收货地址卡片在订单确认场景的预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun AddressCardOrderConfirmPreview() {
    AppTheme {
        AddressCard(
            address = Address(
                id = 1,
                province = "云南省",
                city = "曲靖市",
                district = "麒麟区",
                address = "南城门广场",
                contact = "张三",
                phone = "18888888888",
                isDefault = true
            ),
            addressSelected = true
        )
    }
}

/**
 * 收货地址卡片自定义操作区域预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun AddressCardWithActionsPreview() {
    AppTheme {
        AddressCard(
            address = Address(
                id = 1,
                province = "广东省",
                city = "深圳市",
                district = "南山区",
                address = "科技园南区T3栋 8楼",
                contact = "张三",
                phone = "13800138000",
                isDefault = true
            ),
            actionSlot = {
                Row(
                    horizontalArrangement = spacedBy(8.dp)
                ) {
                    // 模拟编辑按钮
                    AddressActionButton(
                        onClick = { },
                        iconResId = android.R.drawable.ic_menu_edit
                    )

                    // 模拟删除按钮
                    AddressActionButton(
                        onClick = { },
                        iconResId = android.R.drawable.ic_menu_delete
                    )
                }
            }
        )
    }
}

/**
 * 深色主题下的收货地址卡片预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun AddressCardDarkPreview() {
    AppTheme(darkTheme = true) {
        AddressCard(
            address = Address(
                id = 1,
                province = "广东省",
                city = "深圳市",
                district = "南山区",
                address = "科技园南区T3栋 8楼",
                contact = "张三",
                phone = "13800138000",
                isDefault = true
            )
        )
    }
}

/**
 * 空地址状态的收货地址卡片预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun AddressCardEmptyPreview() {
    AppTheme {
        AddressCard(
            address = null,
            onClick = { /* 点击选择地址 */ }
        )
    }
}

/**
 * 深色主题下空地址状态的收货地址卡片预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun AddressCardEmptyDarkPreview() {
    AppTheme(darkTheme = true) {
        AddressCard(
            address = null,
            onClick = { /* 点击选择地址 */ }
        )
    }
}