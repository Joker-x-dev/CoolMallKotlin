package com.joker.coolmall.feature.main.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.ColorDanger
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.model.Goods
import com.joker.coolmall.core.ui.component.image.NetWorkImage

/**
 * 限时精选卡片项
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
        Text(
            text = "¥${goods.price}",
            style = MaterialTheme.typography.titleMedium,
            color = ColorDanger
        )
    }
}