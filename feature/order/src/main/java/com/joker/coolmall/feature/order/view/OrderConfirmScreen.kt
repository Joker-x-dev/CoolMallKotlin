package com.joker.coolmall.feature.order.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.joker.coolmall.core.model.entity.Address
import com.joker.coolmall.core.model.entity.Cart
import com.joker.coolmall.core.model.preview.previewAddress
import com.joker.coolmall.core.model.preview.previewCartList
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
import com.joker.coolmall.core.ui.component.text.PriceText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.core.ui.component.title.TitleWithLine
import com.joker.coolmall.feature.order.R
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
    val remark by viewModel.remark.collectAsState()

    OrderConfirmScreen(
        uiState = uiState,
        onRetry = viewModel::retryRequest,
        onBackClick = viewModel::navigateBack,
        cartList = viewModel.cartList,
        remark = remark,
        onRemarkChange = viewModel::updateRemark,
        onSubmitOrderClick = viewModel::onSubmitOrderClick
    )
}

/**
 * 确认订单页面
 *
 * @param onRetry 重试请求回调
 * @param cartList 购物车列表
 * @param remark 订单备注
 * @param onRemarkChange 订单备注变更回调
 * @param onSubmitOrderClick 提交订单点击回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrderConfirmScreen(
    uiState: BaseNetWorkUiState<Address?> = BaseNetWorkUiState.Loading,
    onRetry: () -> Unit = {},
    onBackClick: () -> Unit = {},
    cartList: List<Cart> = emptyList(),
    remark: String = "",
    onRemarkChange: (String) -> Unit = {},
    onSubmitOrderClick: () -> Unit = {}
) {
    val totalPrice = androidx.compose.runtime.remember(cartList) {
        cartList.sumOf { cart ->
            cart.spec.sumOf { spec ->
                spec.price * spec.count
            }
        }
    }

    AppScaffold(
        title = R.string.order_confirm,
        useLargeTopBar = true,
        onBackClick = onBackClick,
        bottomBar = {
            if (uiState is BaseNetWorkUiState.Success) {
                OrderBottomBar(
                    totalPrice = totalPrice,
                    onSubmitClick = onSubmitOrderClick
                )
            }
        }
    ) {
        BaseNetWorkView(
            uiState = uiState,
            onRetry = onRetry
        ) {
            OrderConfirmContentView(
                address = it,
                totalPrice = totalPrice,
                cartList = cartList,
                remark = remark,
                onRemarkChange = onRemarkChange
            )
        }
    }
}

/**
 * 确认订单内容视图
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrderConfirmContentView(
    address: Address?,
    totalPrice: Int,
    cartList: List<Cart>,
    remark: String,
    onRemarkChange: (String) -> Unit
) {
    VerticalList(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        // 地址选择卡片
        AddressCard(
            address = address,
            onClick = { /* 地址点击回调 */ },
            addressSelected = true
        )

        // 订单商品卡片
        cartList.forEach { cart ->
            OrderGoodsCard(
                data = cart,
                enableQuantityStepper = false, // 确认订单页面不需要调整数量
                onGoodsClick = { /* 商品点击事件 */ },
                onSpecClick = { /* 规格点击事件 */ }
            )
        }

        // 价格明细卡片
        Card {
            AppListItem(
                title = "",
                showArrow = false,
                leadingContent = {
                    TitleWithLine(text = "价格明细")
                }
            )

            AppListItem(
                title = "商品总价",
                leadingIcon = R.drawable.ic_shop,
                trailingContent = {
                    PriceText(
                        totalPrice, integerTextSize = TextSize.BODY_LARGE,
                        decimalTextSize = TextSize.BODY_SMALL,
                        symbolTextSize = TextSize.BODY_SMALL,
                        type = TextType.PRIMARY
                    )
                },
                showArrow = false
            )

            AppListItem(
                title = "优惠券",
                leadingIcon = R.drawable.ic_coupon,
                trailingText = "无可用",
                showArrow = true,
                onClick = { /* 选择优惠券 */ }
            )

            AppListItem(
                title = "合计",
                leadingIcon = R.drawable.ic_bankcard,
                trailingContent = {
                    PriceText(
                        totalPrice, integerTextSize = TextSize.BODY_LARGE,
                        decimalTextSize = TextSize.BODY_SMALL,
                        symbolTextSize = TextSize.BODY_SMALL,
                        type = TextType.PRIMARY
                    )
                },
                showArrow = false,
                showDivider = false
            )
        }

        // 订单备注卡片
        AppCard(lineTitle = "订单备注") {
            OutlinedTextField(
                value = remark,
                onValueChange = onRemarkChange,
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

            PriceText(
                totalPrice,
                integerTextSize = TextSize.DISPLAY_LARGE,
                decimalTextSize = TextSize.TITLE_MEDIUM,
                symbolTextSize = TextSize.TITLE_MEDIUM,
            )

            AppButtonFixed(
                text = "提交订单",
                onClick = onSubmitClick,
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
                data = previewAddress
            ),
            cartList = previewCartList
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun OrderConfirmScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        OrderConfirmScreen(
            uiState = BaseNetWorkUiState.Success(
                data = previewAddress
            ),
            cartList = previewCartList
        )
    }
}