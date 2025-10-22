package com.joker.coolmall.core.ui.component.modal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.component.WrapColumn
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.model.entity.Cart
import com.joker.coolmall.core.model.preview.previewCartList
import com.joker.coolmall.core.ui.component.button.AppButtonFixed
import com.joker.coolmall.core.ui.component.button.ButtonSize
import com.joker.coolmall.core.ui.component.button.ButtonType
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType

/**
 * 订单商品弹出层组件
 * 用于订单中多个商品的评论或再次购买功能
 *
 * @author Joker.X
 */

/**
 * 订单商品弹出层
 *
 * @param visible 是否显示弹出层
 * @param title 弹出层标题
 * @param buttonText 操作按钮文本
 * @param cartList 商品列表
 * @param onDismiss 关闭弹出层回调
 * @param onItemButtonClick 商品操作按钮点击回调，参数为商品ID
 * @param modifier 修饰符
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderGoodsModal(
    visible: Boolean,
    title: String,
    buttonText: String,
    cartList: List<Cart>,
    onDismiss: () -> Unit,
    onItemButtonClick: (goodsId: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    BottomModal(
        visible = visible,
        title = title,
        onDismiss = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        indicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
    ) {
        OrderGoodsModalContent(
            cartList = cartList,
            buttonText = buttonText,
            onItemButtonClick = onItemButtonClick
        )
    }
}

/**
 * 订单商品弹出层内容
 *
 * @param cartList 商品列表
 * @param buttonText 操作按钮文本
 * @param onItemButtonClick 商品操作按钮点击回调
 * @author Joker.X
 */
@Composable
private fun OrderGoodsModalContent(
    cartList: List<Cart>,
    buttonText: String,
    onItemButtonClick: (goodsId: Long) -> Unit
) {
    WrapColumn {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 500.dp),
            verticalArrangement = Arrangement.spacedBy(SpaceVerticalMedium)
        ) {
            items(cartList) { cart ->
                OrderGoodsItem(
                    cart = cart,
                    buttonText = buttonText,
                    onButtonClick = { onItemButtonClick(cart.goodsId) }
                )
            }
        }
    }
}

/**
 * 订单商品条目组件
 * 左侧显示商品图片和名称，右侧显示操作按钮
 *
 * @param cart 购物车数据
 * @param buttonText 按钮文本
 * @param onButtonClick 按钮点击回调
 * @param modifier 修饰符
 * @author Joker.X
 */
@Composable
private fun OrderGoodsItem(
    cart: Cart,
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(SpacePaddingMedium),
            horizontalArrangement = Arrangement.spacedBy(SpaceHorizontalMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 商品图片
            NetWorkImage(
                model = cart.goodsMainPic,
                size = 60.dp,
                cornerShape = ShapeSmall,
                showBackground = true
            )

            // 商品名称
            AppText(
                text = cart.goodsName,
                size = TextSize.BODY_LARGE,
                type = TextType.PRIMARY,
                modifier = Modifier.weight(1f)
            )

            // 操作按钮
            AppButtonFixed(
                text = buttonText,
                onClick = onButtonClick,
                type = ButtonType.DEFAULT,
                size = ButtonSize.MINI,
            )
        }
    }
}

/**
 * 订单商品弹出层预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun OrderGoodsModalPreview() {
    AppTheme {
        OrderGoodsModalContent(
            cartList = previewCartList,
            buttonText = "再次购买",
            onItemButtonClick = { }
        )
    }
}