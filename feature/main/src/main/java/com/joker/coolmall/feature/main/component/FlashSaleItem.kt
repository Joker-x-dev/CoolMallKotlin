package com.joker.coolmall.feature.main.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.text.PriceText
import com.joker.coolmall.core.ui.component.text.TextSize

/**
 * 限时精选卡片项
 *
 * @param goods 商品数据
 * @param modifier 修饰符
 * @param onClick 点击回调
 * @author Joker.X
 */
@Composable
fun FlashSaleItem(
    goods: Goods,
    modifier: Modifier = Modifier,
    onClick: (Long) -> Unit = {}
) {
    Column(
        modifier = modifier
            .width(120.dp)
            .clip(ShapeSmall)
            .clickable(onClick = { onClick(goods.id) })
    ) {
        NetWorkImage(
            model = goods.mainPic,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(ShapeSmall)
        )
        SpaceVerticalXSmall()
        Text(
            text = goods.title,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        SpaceVerticalXSmall()
        PriceText(goods.price, integerTextSize = TextSize.BODY_LARGE)
    }
}