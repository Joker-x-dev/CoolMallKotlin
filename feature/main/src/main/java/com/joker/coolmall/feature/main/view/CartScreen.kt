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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joker.coolmall.core.designsystem.theme.ArrowRightIcon
import com.joker.coolmall.core.designsystem.theme.CoolIcon
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.SpaceDivider
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
import com.joker.coolmall.core.ui.component.divider.WeDivider
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.feature.main.R
import com.joker.coolmall.feature.main.component.CommonScaffold

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
    var selectedCount by remember { mutableStateOf(4) } // 示例数据
    var totalPrice by remember { mutableStateOf(12997) } // 示例数据，单位为分

    // 示例购物车数据 - 按商品分组
    val groupedCartItems = remember {
        mapOf(
            "Redmi K80" to listOf(
                CartItemSpec(
                    id = "1",
                    spec = "雪岩白 12GB+256GB",
                    price = 249900, // 单位为分
                    count = 2,
                    selected = true,
                    imageUrl = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png"
                ),
                CartItemSpec(
                    id = "2",
                    spec = "雪岩白 16GB+1TB",
                    price = 359900, // 单位为分
                    count = 1,
                    selected = true,
                    imageUrl = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png"
                )
            ),
            "Redmi 14C" to listOf(
                CartItemSpec(
                    id = "3",
                    spec = "冰川银 4GB+64GB",
                    price = 49900, // 单位为分
                    count = 1,
                    selected = true,
                    imageUrl = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png"
                )
            ),
            "Xiaomi 15 Ultra" to listOf(
                CartItemSpec(
                    id = "4",
                    spec = "经典黑银 16GB+512GB",
                    price = 699900, // 单位为分
                    count = 1,
                    selected = false,
                    imageUrl = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png"
                ),
                CartItemSpec(
                    id = "5",
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
                groupedCartItems.forEach { (goodsTitle, specs) ->
                    item {
                        GoodsCard(
                            goodsTitle = goodsTitle,
                            specs = specs,
                            onGoodsClick = { /* 实现商品点击事件 */ },
                            onCheckChanged = { /* 实现选择状态改变事件 */ },
                            onDecrementCount = { /* 实现减少数量事件 */ },
                            onIncrementCount = { /* 实现增加数量事件 */ }
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
private fun GoodsCard(
    goodsTitle: String,
    specs: List<CartItemSpec>,
    onGoodsClick: (String) -> Unit,
    onCheckChanged: (CartItemSpec) -> Unit,
    onDecrementCount: (CartItemSpec) -> Unit,
    onIncrementCount: (CartItemSpec) -> Unit,
    modifier: Modifier = Modifier
) {
    Card {
        Column(modifier = Modifier.fillMaxWidth()) {
            // 商品标题行
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onGoodsClick(goodsTitle) }
                    .padding(horizontal = SpacePaddingLarge, vertical = SpacePaddingMedium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = goodsTitle,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                ArrowRightIcon()
            }

            WeDivider()

            SpaceVerticalMedium()

            // 商品规格项列表
            specs.forEach { spec ->
                CartItemRow(
                    spec = spec,
                    onCheckChanged = onCheckChanged,
                    onDecrementCount = onDecrementCount,
                    onIncrementCount = onIncrementCount
                )
            }
        }
    }
}

@Composable
private fun CartItemRow(
    spec: CartItemSpec,
    onCheckChanged: (CartItemSpec) -> Unit,
    onDecrementCount: (CartItemSpec) -> Unit,
    onIncrementCount: (CartItemSpec) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = SpacePaddingLarge, end = SpacePaddingLarge, bottom = SpacePaddingLarge)
    ) {
        // 选择框
        CoolIcon(
            resId = if (spec.selected) R.drawable.ic_checkbox_checked else R.drawable.ic_checkbox_unchecked,
            contentDescription = if (spec.selected) "已选择" else "未选择",
            modifier = Modifier
                .size(24.dp)
                .clickable { onCheckChanged(spec) },
            tint = if (spec.selected) Primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                alpha = 0.6f
            )
        )

        SpaceHorizontalMedium()

        // 商品图片
        NetWorkImage(
            model = spec.imageUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )

        SpaceHorizontalMedium()

        // 商品信息
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // 商品规格 - 使用卡片样式
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = SpacePaddingSmall, vertical = SpaceVerticalXSmall)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = spec.spec,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    SpaceHorizontalXSmall()
                    ArrowRightIcon(size = 16.dp, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            SpaceVerticalMedium()

            // 价格和数量
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // 价格
                Text(
                    text = "¥${spec.price / 100.0}0",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.weight(1f)
                )

                // 数量控制
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 减少按钮
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(24.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = CircleShape
                            )
                            .clickable { onDecrementCount(spec) }
                    ) {
                        Text(
                            text = "-",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    // 数量
                    Text(
                        text = "${spec.count}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(horizontal = SpacePaddingMedium)
                            .widthIn(min = 16.dp),
                        textAlign = TextAlign.Center
                    )

                    // 增加按钮
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(24.dp)
                            .background(
                                color = Primary,
                                shape = CircleShape
                            )
                            .clickable { onIncrementCount(spec) }
                    ) {
                        Text(
                            text = "+",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.surface
                        )
                    }
                }
            }
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
    val outlineColor = MaterialTheme.colorScheme.outline

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .drawWithContent {
                // 绘制顶部分割线
                drawLine(
                    color = outlineColor,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = SpaceDivider.toPx()
                )

                // 先绘制内容
                drawContent()

                // 绘制底部分割线
                drawLine(
                    color = outlineColor,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = SpaceDivider.toPx()
                )
            }
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = SpacePaddingLarge, vertical = SpaceVerticalSmall)
            .navigationBarsPadding()
    ) {
        // 全选按钮
        CoolIcon(
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

// 购物车商品规格数据类
data class CartItemSpec(
    val id: String,
    val spec: String,
    val price: Int, // 单位为分
    val count: Int,
    val selected: Boolean,
    val imageUrl: String
)

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    CartScreen()
} 