package com.joker.coolmall.feature.main.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ArrowRightIcon
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingLarge
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.ui.component.appbar.CenterTopAppBar
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.feature.main.R
import com.joker.coolmall.feature.main.component.CommonScaffold
import com.joker.coolmall.core.ui.component.goods.OrderGoodsCard
import com.joker.coolmall.core.ui.component.goods.OrderGoodsItemData

@Composable
internal fun CartRoute() {
    CartScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CartScreen() {
    // 购物车状态
    var isCheckAll by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var selectedCount by remember { mutableIntStateOf(4) } // 示例数据
    var totalPrice by remember { mutableIntStateOf(12997) } // 示例数据，单位为分

    // 示例购物车数据 - 按商品分组
    val groupedCartItems = remember {
        mapOf(
            "Redmi K80" to listOf(
                OrderGoodsItemData(
                    id = "1",
                    title = "Redmi K80",
                    spec = "雪岩白 12GB+256GB",
                    price = 249900, // 单位为分
                    count = 2,
                    selected = true,
                    imageUrl = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png"
                ),
                OrderGoodsItemData(
                    id = "2",
                    title = "Redmi K80",
                    spec = "雪岩白 16GB+1TB",
                    price = 359900, // 单位为分
                    count = 1,
                    selected = true,
                    imageUrl = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png"
                )
            ),
            "Redmi 14C" to listOf(
                OrderGoodsItemData(
                    id = "3",
                    title = "Redmi 14C",
                    spec = "冰川银 4GB+64GB",
                    price = 49900, // 单位为分
                    count = 1,
                    selected = true,
                    imageUrl = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png"
                )
            ),
            "Xiaomi 15 Ultra" to listOf(
                OrderGoodsItemData(
                    id = "4",
                    title = "Xiaomi 15 Ultra",
                    spec = "经典黑银 16GB+512GB",
                    price = 699900, // 单位为分
                    count = 1,
                    selected = false,
                    imageUrl = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png"
                ),
                OrderGoodsItemData(
                    id = "5",
                    title = "Xiaomi 15 Ultra",
                    spec = "白色 16GB+512GB",
                    price = 699900, // 单位为分
                    count = 1,
                    selected = false,
                    imageUrl = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png"
                )
            )
        )
    }

    CommonScaffold(
        topBar = {
            CenterTopAppBar(
                title = R.string.cart,
                showBackIcon = false,
                actions = {
                    TextButton(onClick = { isEditing = !isEditing }) {
                        Text(
                            text = stringResource(id = if (isEditing) R.string.complete else R.string.edit),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 购物车商品列表
            LazyColumn(
                contentPadding = PaddingValues(SpacePaddingMedium),
                verticalArrangement = Arrangement.spacedBy(SpaceVerticalMedium),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp) // 为底部栏留出空间
            ) {
                // 遍历分组后的商品数据
                groupedCartItems.forEach { (goodsTitle, items) ->
                    item {
                        OrderGoodsCard(
                            goodsTitle = goodsTitle,
                            items = items,
                            onGoodsClick = { /* 实现商品点击事件 */ },
                            onSpecClick = { /* 规格点击事件 */ },
                            onQuantityChanged = { itemId, newCount ->
                                /* 实现数量变更事件 */
                            },
                            itemSelectSlot = { item ->
                                // 选择框
                                CommonIcon(
                                    resId = if (item.selected) R.drawable.ic_checkbox_checked else R.drawable.ic_checkbox_unchecked,
                                    contentDescription = if (item.selected) "已选择" else "未选择",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable { /* 选择状态改变事件 */ },
                                    tint = if (item.selected) Primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                        alpha = 0.6f
                                    )
                                )
                            }
                        )
                    }
                }
            }

            // 底部结算栏
            CartBottomBar(
                isCheckAll = isCheckAll,
                isEditing = isEditing,
                selectedCount = selectedCount,
                totalPrice = totalPrice,
                onCheckAllChanged = { isCheckAll = !isCheckAll },
                onDeleteClick = { /* 实现删除事件 */ },
                onSettleClick = { /* 实现结算事件 */ },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun CartBottomBar(
    isCheckAll: Boolean,
    isEditing: Boolean,
    selectedCount: Int,
    totalPrice: Int, // 单位为分
    onCheckAllChanged: () -> Unit,
    onDeleteClick: () -> Unit,
    onSettleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        tonalElevation = 2.dp,
        shadowElevation = 2.dp,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = SpacePaddingLarge, vertical = SpaceVerticalSmall)
        ) {
            // 全选按钮
            CommonIcon(
                resId = if (isCheckAll) R.drawable.ic_checkbox_checked else R.drawable.ic_checkbox_unchecked,
                contentDescription = stringResource(id = R.string.select_all),
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onCheckAllChanged() },
                tint = if (isCheckAll) Primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                    alpha = 0.6f
                )
            )

            SpaceHorizontalSmall()

            Text(
                text = stringResource(id = R.string.select_all),
                style = MaterialTheme.typography.bodyMedium
            )

            // 使用正确的方式添加权重空间
            Spacer(modifier = Modifier.weight(1f))

            if (isEditing) {
                // 编辑模式 - 显示删除按钮 - 设计稿样式
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(34.dp)
                        .widthIn(min = 90.dp)

                        .border(
                            width = 1.dp,
                            color = Color(0xFFDDDDDD),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clickable(enabled = selectedCount > 0) { onDeleteClick() }
                        .padding(horizontal = SpacePaddingLarge)
                ) {
                    Text(
                        text = stringResource(id = R.string.delete),
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selectedCount > 0) MaterialTheme.colorScheme.onSurface else Color(
                            0xFFCCCCCC
                        )
                    )
                }
            } else {
                // 正常模式 - 显示价格和结算按钮
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.total),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "¥${totalPrice / 100.0}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )

                    SpaceHorizontalMedium()

                    // 结算按钮 - 设计稿样式
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(34.dp)
                            .widthIn(min = 90.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color(0xFF4F44FF), Color(0xFF7A3CFF))
                                ),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable(enabled = selectedCount > 0) { onSettleClick() }
                            .padding(horizontal = SpacePaddingLarge)
                    ) {
                        Text(
                            text = if (selectedCount == 0)
                                stringResource(id = R.string.settle_account)
                            else
                                stringResource(id = R.string.settle_account_count, selectedCount),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    AppTheme {
        CartScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        CartScreen()
    }
}