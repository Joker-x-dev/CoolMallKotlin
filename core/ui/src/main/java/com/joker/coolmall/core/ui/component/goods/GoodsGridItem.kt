package com.joker.coolmall.core.ui.component.goods

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.designsystem.theme.TextTertiaryLight
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.model.preview.previewGoods
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.text.PriceText

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
            NetWorkImage(
                model = goods.mainPic,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp)
                    .clip(ShapeSmall)
            )
            SpaceVerticalXSmall()
            Text(
                text = goods.title,
                style = MaterialTheme.typography.bodyLarge,
            )
            if (!goods.subTitle.isNullOrEmpty()) {
                Text(
                    text = goods.subTitle!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiaryLight,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            SpaceVerticalXSmall()
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                PriceText(goods.price)
                Text(
                    text = "已售 ${goods.sold}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GoodsGridItemPreview() {
    AppTheme {
        GoodsGridItem(
            goods = previewGoods
        )
    }
}