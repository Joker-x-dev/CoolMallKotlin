package com.joker.coolmall.core.ui.component.goods

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.joker.coolmall.core.designsystem.theme.ColorDanger
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.designsystem.theme.TextTertiaryLight
import com.joker.coolmall.core.model.entity.Goods

/**
 * 商品网格卡片
 */
@Composable
fun GoodsGridItem(
    modifier: Modifier = Modifier,
    goods: Goods,
    onClick: (Long) -> Unit = {},
) {
    Card(
        onClick = { onClick(goods.id) },
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(SpaceHorizontalSmall)
        ) {
            AsyncImage(
                model = goods.mainPic,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(6.dp))
            )
            SpaceVerticalXSmall()
            Text(
                text = goods.title,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (!goods.subTitle.isNullOrEmpty()) {
                Text(
                    text = goods.subTitle!!,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = TextTertiaryLight
                )
            }

            SpaceVerticalXSmall()
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "¥${goods.price}",
                    style = MaterialTheme.typography.displayMedium,
                    color = ColorDanger
                )
                Text(
                    text = "已售 ${goods.sold}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}