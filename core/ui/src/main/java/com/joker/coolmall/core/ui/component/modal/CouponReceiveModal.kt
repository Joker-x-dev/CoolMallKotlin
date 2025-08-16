package com.joker.coolmall.core.ui.component.modal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.component.WrapColumn
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.model.entity.Condition
import com.joker.coolmall.core.model.entity.Coupon
import com.joker.coolmall.core.model.preview.previewAvailableCoupons
import com.joker.coolmall.core.ui.component.coupon.CouponCard
import com.joker.coolmall.core.ui.component.coupon.CouponCardMode

/**
 * 优惠券领取底部弹出层
 *
 * @param visible 是否显示
 * @param onDismiss 关闭回调
 * @param coupons 优惠券列表
 * @param onCouponReceive 优惠券领取回调，参数为优惠券ID
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouponReceiveModal(
    visible: Boolean,
    onDismiss: () -> Unit,
    coupons: List<Coupon> = emptyList(),
    onCouponReceive: (Long) -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    BottomModal(
        visible = visible,
        onDismiss = onDismiss,
        title = "领取优惠券",
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        indicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
    ) {
        CouponReceiveModalContent(
            coupons = coupons,
            onCouponReceive = onCouponReceive
        )
    }
}

/**
 * 优惠券领取弹出层内容
 *
 * @param coupons 优惠券列表
 * @param onCouponReceive 优惠券领取回调
 */
@Composable
private fun CouponReceiveModalContent(
    coupons: List<Coupon>,
    onCouponReceive: (Long) -> Unit
) {
    WrapColumn {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 500.dp)
        ) {
            items(coupons) { coupon ->
                CouponCard(
                    coupon = coupon,
                    mode = CouponCardMode.RECEIVE,
                    onActionClick = { onCouponReceive(coupon.id) },
                    modifier = Modifier.padding(bottom = SpaceVerticalMedium)
                )
            }
        }
    }
}

/**
 * 优惠券领取弹出层预览
 */
@Preview(showBackground = true)
@Composable
private fun CouponReceiveModalPreview() {
    AppTheme {
        Column {
            CouponReceiveModalContent(
                coupons = previewAvailableCoupons,
                onCouponReceive = {}
            )
        }
    }
}