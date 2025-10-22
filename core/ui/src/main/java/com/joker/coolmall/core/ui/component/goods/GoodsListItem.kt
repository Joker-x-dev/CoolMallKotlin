package com.joker.coolmall.core.ui.component.goods

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
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
import com.joker.coolmall.core.ui.R
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.text.PriceText

/**
 * 商品列表卡片
 *
 * @param modifier 修饰符
 * @param goods 商品数据
 * @param onClick 点击回调
 * @author Joker.X
 */
@Composable
fun GoodsListItem(
    modifier: Modifier = Modifier,
    goods: Goods,
    onClick: (Long) -> Unit = {},
) {
    Card(
        onClick = { onClick(goods.id) },
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceHorizontalSmall),
            horizontalArrangement = Arrangement.spacedBy(SpaceHorizontalSmall)
        ) {
            // 商品图片
            NetWorkImage(
                model = goods.mainPic,
                size = 100.dp,
                modifier = Modifier
                    .clip(ShapeSmall)
            )

            // 商品信息
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                // 标题和副标题
                Column {
                    Text(
                        text = goods.title,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (!goods.subTitle.isNullOrEmpty()) {
                        SpaceVerticalXSmall()
                        Text(
                            text = goods.subTitle!!,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = TextTertiaryLight
                        )
                    }
                }

                // 价格和销量
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PriceText(goods.price)
                    Text(
                        text = stringResource(id = R.string.goods_sold, goods.sold),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

/**
 * 商品列表卡片预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun GoodsListItemPreview() {
    AppTheme {
        GoodsListItem(
            goods = previewGoods
        )
    }
}