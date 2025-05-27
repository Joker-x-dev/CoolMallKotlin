package com.joker.coolmall.feature.cs.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.SpacePaddingSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingXSmall
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize

/**
 * 表情选择器组件
 *
 * @param onEmojiSelected 表情选择回调
 * @param modifier 修饰符
 */
@Composable
fun EmojiSelector(
    onEmojiSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // 模拟表情列表
    val emojis = listOf(
        "😀", "😃", "😄", "😁", "😆", "😅", "😂", "🤣", "😊", "😇",
        "🙂", "🙃", "😉", "😌", "😍", "🥰", "😘", "😗", "😙", "😚",
        "😋", "😛", "😝", "😜", "🤪", "🤨", "🧐", "🤓", "😎", "🤩",
        "🥳", "😏", "😒", "😞", "😔", "😟", "😕", "🙁", "☹️", "😣"
    )

    Surface(
        modifier = modifier,
        shadowElevation = 4.dp,
        shape = ShapeMedium
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(8),
            modifier = Modifier.padding(SpacePaddingSmall)
        ) {
            items(emojis.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(SpacePaddingXSmall)
                        .size(40.dp)
                        .clickable { onEmojiSelected(emojis[index]) },
                    contentAlignment = Alignment.Center
                ) {
                    AppText(
                        text = emojis[index],
                        size = TextSize.TITLE_LARGE
                    )
                }
            }
        }
    }
} 