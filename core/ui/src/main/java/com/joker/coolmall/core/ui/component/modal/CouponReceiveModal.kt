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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.component.WrapColumn
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.model.entity.Coupon
import com.joker.coolmall.core.model.preview.previewAvailableCoupons
import com.joker.coolmall.core.ui.R
import com.joker.coolmall.core.ui.component.coupon.CouponCard
import com.joker.coolmall.core.ui.component.coupon.CouponCardMode

/**
 * 优惠券领取弹出层
 *
 * @param visible 是否显示
 * @param onDismiss 关闭回调
 * @param coupons 优惠券列表
 * @param title 弹出层标题，默认为空（使用字符串资源）
 * @param mode 优惠券卡片模式，默认为RECEIVE
 * @param onCouponAction 优惠券操作回调，参数为优惠券ID
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouponModal(
    visible: Boolean,
    onDismiss: () -> Unit,
    coupons: List<Coupon> = emptyList(),
    title: String = "",
    mode: CouponCardMode = CouponCardMode.RECEIVE,
    onCouponAction: (Long) -> Unit = {}
) {
    val finalTitle = title.ifEmpty { stringResource(id = R.string.coupon_modal_title) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    BottomModal(
        visible = visible,
        onDismiss = onDismiss,
        title = finalTitle,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        indicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
    ) {
        CouponModalContent(
            coupons = coupons,
            mode = mode,
            onCouponAction = onCouponAction
        )
    }
}

/**
 * 优惠券弹出层内容
 *
 * @param coupons 优惠券列表
 * @param mode 优惠券卡片模式
 * @param onCouponAction 优惠券操作回调
 * @author Joker.X
 */
@Composable
private fun CouponModalContent(
    coupons: List<Coupon>,
    mode: CouponCardMode,
    onCouponAction: (Long) -> Unit
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
                    mode = mode,
                    onActionClick = { onCouponAction(coupon.id) },
                    modifier = Modifier.padding(bottom = SpaceVerticalMedium)
                )
            }
        }
    }
}

/**
 * 优惠券领取弹出层预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun CouponModalPreview() {
    AppTheme {
        Column {
            CouponModalContent(
                coupons = previewAvailableCoupons,
                mode = CouponCardMode.RECEIVE,
                onCouponAction = {}
            )
        }
    }
}