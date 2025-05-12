package com.joker.coolmall.feature.order.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.component.SpaceBetweenRow
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingXSmall
import com.joker.coolmall.core.model.Address
import com.joker.coolmall.core.model.Cart
import com.joker.coolmall.core.model.CartGoodsSpec
import com.joker.coolmall.core.ui.component.address.AddressCard
import com.joker.coolmall.core.ui.component.button.AppButtonFixed
import com.joker.coolmall.core.ui.component.button.ButtonShape
import com.joker.coolmall.core.ui.component.button.ButtonSize
import com.joker.coolmall.core.ui.component.button.ButtonStyle
import com.joker.coolmall.core.ui.component.card.AppCard
import com.joker.coolmall.core.ui.component.goods.OrderGoodsCard
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.title.TitleWithLine
import com.joker.coolmall.feature.order.viewmodel.OrderConfirmViewModel

/**
 * 确认订单路由
 *
 * @param viewModel 确认订单ViewModel
 */
@Composable
internal fun OrderConfirmRoute(
    viewModel: OrderConfirmViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    OrderConfirmScreen(
        uiState = uiState,
        onRetry = viewModel::retryRequest,

    )
}

/**
 * 确认订单页面
 *
 * @param onRetry 重试请求回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrderConfirmScreen(
    uiState: BaseNetWorkUiState<Any> = BaseNetWorkUiState.Loading,
    onRetry: () -> Unit = {}
) {
    // 订单总金额（按UI图所示，单位为分）
    val totalPrice = 749800  // 7498.00元

    AppScaffold(
//        title = R.string.order_confirm,
        bottomBar = {
            OrderBottomBar(
                totalPrice = totalPrice,
                onSubmitClick = { /* 提交订单 */ }
            )
        }
    ) {
        BaseNetWorkView(
            uiState = uiState,
            onRetry = onRetry
        ) {
            OrderConfirmContentView(totalPrice)
        }
    }
}

/**
 * 确认订单内容视图
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrderConfirmContentView(totalPrice: Int = 749800) {
    // 订单备注状态
    var remark by remember { mutableStateOf("") }

    VerticalList(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        // 地址选择卡片
        AddressCard(
            address = Address(
                id = 1,
                province = "广东省",
                city = "广州市",
                district = "白云区",
                address = "XXXX街道 XXX 号",
                contact = "小明",
                phone = "188****8888",
                isDefault = true
            ),
            onClick = { /* 地址点击回调 */ },
            addressSelected = true
        )

        // 商品列表
        val cartItems = remember {
            listOf(
                Cart().apply {
                    goodsId = 1L
                    goodsName = "Redmi 14C"
                    goodsMainPic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png"
                    spec = listOf(
                        CartGoodsSpec(
                            id = 1L,
                            goodsId = 1L,
                            name = "星岩黑 4GB+64GB",
                            price = 49900,
                            count = 1,
                            stock = 200,
                            images = listOf("https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png")
                        )
                    )
                },
                Cart().apply {
                    goodsId = 2L
                    goodsName = "Xiaomi 15 Ultra"
                    goodsMainPic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png"
                    spec = listOf(
                        CartGoodsSpec(
                            id = 2L,
                            goodsId = 2L,
                            name = "经典黑银 16GB+512GB",
                            price = 699900,
                            count = 1,
                            stock = 30,
                            images = listOf("https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png")
                        )
                    )
                }
            )
        }

        // 订单商品卡片
        cartItems.forEach { cart ->
            OrderGoodsCard(
                data = cart,
                enableQuantityStepper = false, // 确认订单页面不需要调整数量
                onGoodsClick = { /* 商品点击事件 */ },
                onSpecClick = { /* 规格点击事件 */ }
            )
        }

        // 价格明细卡片
        Card {
            TitleWithLine(
                text = "价格明细",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SpacePaddingMedium)
                    .padding(top = SpacePaddingMedium, bottom = SpacePaddingXSmall)
            )
            AppListItem(
                title = "商品总价",
                trailingText = "¥${totalPrice / 100.0}0",
                showArrow = false
            )

            AppListItem(
                title = "优惠券",
                trailingText = "无可用",
                showArrow = true,
                onClick = { /* 选择优惠券 */ }
            )

            AppListItem(
                title = "运费",
                trailingText = "¥0.00",
                showArrow = false,
                showDivider = false
            )
        }

        // 订单备注卡片
        AppCard(lineTitle = "订单备注") {
            OutlinedTextField(
                value = remark,
                onValueChange = { remark = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                label = { Text("订单备注") },
                placeholder = { Text("选填，付款后商家可见") },
                shape = ShapeMedium,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                minLines = 3,
                maxLines = 5
            )
        }
    }
}

/**
 * 订单底部操作栏
 *
 * @param totalPrice 订单总金额（单位：分）
 * @param onSubmitClick 提交订单点击回调
 */
@Composable
private fun OrderBottomBar(
    totalPrice: Int,
    onSubmitClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shadowElevation = 4.dp,
    ) {

        SpaceBetweenRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpacePaddingMedium)
                .navigationBarsPadding()
        ) {

            AppText(
                text = "¥${totalPrice / 100.0}0",
                color = MaterialTheme.colorScheme.error,
                size = TextSize.TITLE_LARGE
            )

            AppButtonFixed(
                text = "提交订单",
                onClick = {},
                size = ButtonSize.MINI,
                style = ButtonStyle.GRADIENT,
                shape = ButtonShape.SQUARE
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun OrderConfirmScreenPreview() {
    AppTheme {
        OrderConfirmScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun OrderConfirmScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        OrderConfirmScreen(
            uiState = BaseNetWorkUiState.Success(
                data = Any()
            )
        )
    }
}