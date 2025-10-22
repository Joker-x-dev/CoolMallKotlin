package com.joker.coolmall.core.ui.component.goods

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.component.SpaceBetweenRow
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingLarge
import com.joker.coolmall.core.designsystem.theme.SpacePaddingSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.model.entity.Cart
import com.joker.coolmall.core.model.entity.CartGoodsSpec
import com.joker.coolmall.core.model.preview.previewCart
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.stepper.QuantityStepper
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.PriceText
import com.joker.coolmall.core.ui.component.text.TextType

/**
 * 商品卡片组件
 *
 * 包含商品标题和多个规格的商品条目
 *
 * @param modifier 修饰符
 * @param data 购物车数据
 * @param deletingSpecIds 正在删除的规格ID集合
 * @param onGoodsClick 商品点击回调
 * @param onSpecClick 规格点击回调
 * @param onQuantityChanged 数量变更回调，参数为商品ID和新数量
 * @param enableQuantityStepper 是否启用数量调节器
 * @param itemSelectSlot 商品选择框插槽生成器，参数为商品数据
 * @param itemActionSlot 商品操作区域插槽生成器，参数为商品数据
 * @author Joker.X
 */
@Composable
fun OrderGoodsCard(
    modifier: Modifier = Modifier,
    data: Cart,
    deletingSpecIds: Set<Long> = emptySet(),
    onGoodsClick: (Long) -> Unit = {},
    onSpecClick: (Long) -> Unit = {},
    onQuantityChanged: (Long, Int) -> Unit = { _, _ -> },
    enableQuantityStepper: Boolean = true,
    itemSelectSlot: (@Composable (CartGoodsSpec) -> Unit)? = null,
    itemActionSlot: (@Composable (CartGoodsSpec) -> Unit)? = null
) {
    Card(modifier = modifier) {
        // 商品标题
        AppListItem(
            title = data.goodsName,
            onClick = { onGoodsClick(data.goodsId) }
        )

        SpaceVerticalMedium()

        // 商品规格项列表
        data.spec.forEach { item ->
            val isSpecDeleting = deletingSpecIds.contains(item.id)

            AnimatedVisibility(
                visible = !isSpecDeleting,
                exit = slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            ) {
                OrderGoodsItem(
                    data = item,
                    onSpecClick = onSpecClick,
                    onQuantityChanged = { newCount -> onQuantityChanged(item.id, newCount) },
                    enableQuantityStepper = enableQuantityStepper,
                    selectSlot = itemSelectSlot?.let { { it(item) } },
                    actionSlot = itemActionSlot?.let { { it(item) } },
                    goodsId = data.goodsId,
                    goodsName = data.goodsName,
                    goodsMainPic = data.goodsMainPic
                )
            }
        }
    }
}

/**
 * 订单商品条目组件
 *
 * 该组件可以用于购物车、确认订单和订单详情等多个场景
 *
 * @param modifier 组件修饰符
 * @param data 规格数据
 * @param goodsId 商品 id
 * @param goodsName 商品名称
 * @param goodsMainPic 商品主图
 * @param onSpecClick 规格点击回调
 * @param onQuantityChanged 数量变更回调
 * @param enableQuantityStepper 是否启用数量调节器
 * @param selectSlot 选择框插槽，在购物车场景需要传入，订单场景不需要
 * @param actionSlot 操作插槽，可以自定义右侧操作区域
 * @author Joker.X
 */
@Composable
fun OrderGoodsItem(
    modifier: Modifier = Modifier,
    data: CartGoodsSpec,
    goodsId: Long,
    goodsName: String,
    goodsMainPic: String,
    onSpecClick: (Long) -> Unit = {},
    onQuantityChanged: (Int) -> Unit = {},
    enableQuantityStepper: Boolean = true,
    selectSlot: (@Composable () -> Unit)? = null,
    actionSlot: (@Composable () -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = if (selectSlot == null) SpacePaddingLarge else 0.dp,
                end = SpacePaddingLarge,
                bottom = SpacePaddingLarge
            )
    ) {
        // 选择框插槽（购物车场景）
        selectSlot?.let {
            Box(modifier = Modifier.padding(start = SpacePaddingLarge)) {
                it()
            }
            SpaceHorizontalMedium()
        }

        // 商品图片
        NetWorkImage(
            model = data.images?.firstOrNull() ?: goodsMainPic,
            size = 90.dp,
            showBackground = true,
            cornerShape = ShapeSmall
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
                    .clickable { onSpecClick(data.id) }
                    .padding(horizontal = SpacePaddingSmall, vertical = SpaceVerticalXSmall)
            ) {
                AppText(
                    text = data.name,
                    style = MaterialTheme.typography.bodySmall,
                    type = TextType.SECONDARY
                )
            }

            SpaceVerticalMedium()

            // 价格和数量/操作区域
            SpaceBetweenRow {
                // 价格
                PriceText(data.price)

                // 数量控制器或自定义操作区域
                if (actionSlot != null) {
                    actionSlot()
                } else if (enableQuantityStepper) {
                    QuantityStepper(
                        quantity = data.count,
                        onQuantityChanged = onQuantityChanged
                    )
                } else {
                    AppText(
                        text = "x${data.count}",
                        type = TextType.SECONDARY
                    )
                }
            }
        }
    }
}

/**
 * 订单商品条目预览
 *
 * 展示商品条目的基本样式，不包含选择框，启用数量调节器
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun OrderGoodsItemPreview() {
    AppTheme {
        val spec = CartGoodsSpec(
            id = 1L,
            goodsId = 1L,
            name = "雪岩白 12GB+256GB",
            price = 249900,
            count = 2,
            stock = 100,
            images = listOf("https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png")
        )

        OrderGoodsItem(
            data = spec,
            goodsId = 1L,
            goodsName = "Redmi K80",
            goodsMainPic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png",
            onSpecClick = {},
            onQuantityChanged = {}
        )
    }
}

/**
 * 带选择框的订单商品条目预览
 *
 * 展示包含选择框的商品条目样式，适用于购物车场景
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun OrderGoodsItemWithCheckboxPreview() {
    AppTheme {
        val spec = CartGoodsSpec(
            id = 1L,
            goodsId = 1L,
            name = "雪岩白 12GB+256GB",
            price = 249900,
            count = 2,
            stock = 100,
            images = listOf("https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png")
        )

        OrderGoodsItem(
            data = spec,
            goodsId = 1L,
            goodsName = "Redmi K80",
            goodsMainPic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png",
            onSpecClick = {},
            onQuantityChanged = {},
            selectSlot = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                ) {
                    Text(
                        text = "✓",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        )
    }
}

/**
 * 不带数量调节器的订单商品条目预览
 *
 * 展示禁用数量调节器的商品条目样式，适用于订单确认和订单详情场景
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun OrderGoodsItemNoStepperPreview() {
    AppTheme {
        val spec = CartGoodsSpec(
            id = 1L,
            goodsId = 1L,
            name = "雪岩白 12GB+256GB",
            price = 249900,
            count = 2,
            stock = 100,
            images = null
        )

        OrderGoodsItem(
            data = spec,
            goodsId = 1L,
            goodsName = "Redmi K80",
            goodsMainPic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png",
            onSpecClick = {},
            enableQuantityStepper = false
        )
    }
}

/**
 * 订单商品卡片预览
 *
 * 展示包含多个商品规格的商品卡片，适用于浅色主题
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun OrderGoodsCardPreview() {
    AppTheme {
        OrderGoodsCard(data = previewCart)
    }
}

/**
 * 深色模式下的订单商品卡片预览
 *
 * 展示深色主题下的商品卡片样式
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun OrderGoodsCardDarkPreview() {
    AppTheme(darkTheme = true) {
        OrderGoodsCard(data = previewCart)
    }
}