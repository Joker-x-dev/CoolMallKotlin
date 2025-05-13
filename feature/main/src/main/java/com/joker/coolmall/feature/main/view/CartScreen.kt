package com.joker.coolmall.feature.main.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingLarge
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.model.entity.Cart
import com.joker.coolmall.core.model.entity.CartGoodsSpec
import com.joker.coolmall.core.ui.component.appbar.CenterTopAppBar
import com.joker.coolmall.feature.main.R
import com.joker.coolmall.feature.main.component.CommonScaffold
import com.joker.coolmall.core.ui.component.goods.OrderGoodsCard

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

    // 示例购物车数据
    val cartItems = remember {
        listOf(
            Cart().apply {
                goodsId = 1L
                goodsName = "Redmi K80"
                goodsMainPic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png"
                spec = listOf(
                    CartGoodsSpec(
                        id = 1L,
                        goodsId = 1L,
                        name = "雪岩白 12GB+256GB",
                        price = 249900,
                        count = 2,
                        stock = 100,
                        images = listOf("https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png")
                    ),
                    CartGoodsSpec(
                        id = 2L,
                        goodsId = 1L,
                        name = "雪岩白 16GB+1TB",
                        price = 359900,
                        count = 1,
                        stock = 50,
                        images = null
                    )
                )
            },
            Cart().apply {
                goodsId = 2L
                goodsName = "Redmi 14C"
                goodsMainPic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png"
                spec = listOf(
                    CartGoodsSpec(
                        id = 3L,
                        goodsId = 2L,
                        name = "冰川银 4GB+64GB",
                        price = 49900,
                        count = 1,
                        stock = 200,
                        images = listOf("https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png")
                    )
                )
            },
            Cart().apply {
                goodsId = 3L
                goodsName = "Xiaomi 15 Ultra"
                goodsMainPic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png"
                spec = listOf(
                    CartGoodsSpec(
                        id = 4L,
                        goodsId = 3L,
                        name = "经典黑银 16GB+512GB",
                        price = 699900,
                        count = 1,
                        stock = 30,
                        images = listOf("https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png")
                    ),
                    CartGoodsSpec(
                        id = 5L,
                        goodsId = 3L,
                        name = "白色 16GB+512GB",
                        price = 699900,
                        count = 1,
                        stock = 25,
                        images = null
                    )
                )
            }
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
                // 遍历购物车商品数据
                cartItems.forEach { cart ->
                    item {
                        OrderGoodsCard(
                            data = cart,
                            onGoodsClick = { /* 实现商品点击事件 */ },
                            onSpecClick = { /* 规格点击事件 */ },
                            onQuantityChanged = { specId, newCount ->
                                /* 实现数量变更事件 */
                            },
                            itemSelectSlot = { spec ->
                                // 选择框 - 这里需要在实际应用中维护选择状态
                                val selected = (spec.id == 1L || spec.id == 2L || spec.id == 3L) // 示例：前三个商品被选中
                                CommonIcon(
                                    resId = if (selected) R.drawable.ic_checkbox_checked else R.drawable.ic_checkbox_unchecked,
                                    contentDescription = if (selected) "已选择" else "未选择",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable { /* 选择状态改变事件 */ },
                                    tint = if (selected) Primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(
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