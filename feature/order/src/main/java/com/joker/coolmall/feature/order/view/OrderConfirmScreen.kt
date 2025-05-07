package com.joker.coolmall.feature.order.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.component.SpaceBetweenRow
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.model.Address
import com.joker.coolmall.core.ui.component.address.AddressCard
import com.joker.coolmall.core.ui.component.goods.OrderGoodsCard
import com.joker.coolmall.core.ui.component.goods.OrderGoodsItemData
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
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
        onRetry = viewModel::retryRequest
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
    AppScaffold() {
        BaseNetWorkView(
            uiState = uiState,
            onRetry = onRetry
        ) {
            OrderConfirmContentView()
        }
    }
}

/**
 * 确认订单内容视图
 */
@Composable
private fun OrderConfirmContentView() {
    VerticalList(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        // 地址选择卡片
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
            onClick = { /* 地址点击回调 */ },
            addressSelected = true
        )

        // 商品列表
        val orderItems = remember {
            listOf(
                OrderGoodsItemData(
                    id = "1",
                    title = "Redmi K80",
                    spec = "雪岩白 12GB+256GB",
                    price = 249900,
                    count = 2,
                    imageUrl = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png"
                ),
                OrderGoodsItemData(
                    id = "3",
                    title = "Redmi 14C",
                    spec = "冰川银 4GB+64GB",
                    price = 49900,
                    count = 1,
                    imageUrl = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png"
                )
            )
        }

        // 按商品标题分组
        val groupedItems = orderItems.groupBy { it.title }

        // 订单商品卡片
        groupedItems.forEach { (title, items) ->
            OrderGoodsCard(
                goodsTitle = title,
                items = items,
                enableQuantityStepper = false, // 确认订单页面不需要调整数量
                onGoodsClick = { /* 商品点击事件 */ },
                onSpecClick = { /* 规格点击事件 */ }
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