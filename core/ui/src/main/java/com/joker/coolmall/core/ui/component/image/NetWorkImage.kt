package com.joker.coolmall.core.ui.component.image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.joker.coolmall.core.common.R
import com.joker.coolmall.core.ui.component.loading.WeLoading

/**
 * 带加载状态的网络图片组件
 *
 * 该组件是对Coil图片加载库的封装，提供了加载中、加载失败、加载成功三种状态的UI显示。
 * 使用SubcomposeAsyncImage可以根据不同的加载状态自定义不同的UI内容。
 *
 * @param model 图片资源（URL、URI、File、Drawable资源ID等）
 * @param modifier 应用于整个组件的修饰符
 * @param contentScale 图片的内容缩放模式，默认为Fit（适应）。可选值包括：
 *   - Crop：裁剪模式，保持宽高比并填充整个容器，超出部分会被裁剪
 *   - Fit：适应模式，保持宽高比并完整显示图片，可能会有空白区域
 *   - FillBounds：填充模式，拉伸图片以完全填充容器，可能会变形
 *   - Inside：内部模式，类似Fit，但图片尺寸不会超过原始尺寸
 *   - None：无缩放，保持图片原始尺寸
 * @param loadingColor 加载动画的颜色，默认为未指定（使用主题色）
 * @param errorColor 错误图标的颜色，默认为灰色
 * @param onErrorClick 图片加载失败时点击错误图标的回调，如果为null则不显示可点击的图标
 */
@Composable
fun NetWorkImage(
    model: Any?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    loadingColor: Color = Color.Unspecified,
    errorColor: Color = Color.Gray,
    onErrorClick: (() -> Unit)? = null
) {
    // 使用SubcomposeAsyncImage代替AsyncImage，可以自定义不同状态的UI
    SubcomposeAsyncImage(
        model = model,
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
    ) {
        // 根据图片加载状态显示不同的UI
        when (painter.state) {
            // 加载中状态
            is AsyncImagePainter.State.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    WeLoading(
                        size = 24.dp,
                        color = loadingColor
                    )
                }
            }

            // 加载失败状态
            is AsyncImagePainter.State.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center // 居中显示错误图标
                ) {
                    // 如果提供了onErrorClick回调，则显示可点击的图标按钮
                    if (onErrorClick != null) {
                        IconButton(onClick = onErrorClick) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_error),
                                contentDescription = "Error",
                                tint = errorColor,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else {
                        // 否则显示普通图标
                        Icon(
                            painter = painterResource(id = R.drawable.ic_error),
                            contentDescription = "Error",
                            tint = errorColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            // 加载成功状态
            is AsyncImagePainter.State.Success -> {
                // 显示成功加载的图片内容
                SubcomposeAsyncImageContent()
            }

            // 空状态（model为null时）
            is AsyncImagePainter.State.Empty -> {
                // 不显示任何内容
            }
        }
    }
}