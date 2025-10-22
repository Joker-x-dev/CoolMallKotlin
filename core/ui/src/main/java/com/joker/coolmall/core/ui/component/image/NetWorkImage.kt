package com.joker.coolmall.core.ui.component.image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.joker.coolmall.core.ui.component.loading.WeLoading
import com.joker.coolmall.core.ui.R as UiR
import com.joker.coolmall.core.util.R as CoreUtilR

/**
 * 带加载状态的网络图片组件
 *
 * 该组件是对Coil图片加载库的封装，提供了加载中、加载失败、加载成功三种状态的UI显示。
 * 使用SubcomposeAsyncImage可以根据不同的加载状态自定义不同的UI内容。
 *
 * @param model 图片资源（URL、URI、File、Drawable资源ID等）
 * @param modifier 应用于整个组件的修饰符
 * @param size 图片大小，设置宽高相等的尺寸。如果为null，则不设置大小（使用modifier中的约束）
 * @param contentScale 图片的内容缩放模式，默认为Fit（适应）。可选值包括：
 *   - Crop：裁剪模式，保持宽高比并填充整个容器，超出部分会被裁剪
 *   - Fit：适应模式，保持宽高比并完整显示图片，可能会有空白区域
 *   - FillBounds：填充模式，拉伸图片以完全填充容器，可能会变形
 *   - Inside：内部模式，类似Fit，但图片尺寸不会超过原始尺寸
 *   - None：无缩放，保持图片原始尺寸
 * @param loadingColor 加载动画的颜色，默认为未指定（使用主题色）
 * @param errorColor 错误图标的颜色，默认为灰色
 * @param onErrorClick 图片加载失败时点击错误图标的回调，如果为null则不显示可点击的图标
 * @param showBackground 是否显示背景颜色，默认为false（背景透明）
 * @param backgroundColor 背景颜色，默认为MaterialTheme.colorScheme.surfaceVariant
 * @param cornerShape 圆角形状，默认为null（无圆角）
 * @author Joker.X
 */
@Composable
fun NetWorkImage(
    model: Any?,
    modifier: Modifier = Modifier,
    size: Dp? = null,
    contentScale: ContentScale = ContentScale.Crop,
    loadingColor: Color = Color.Unspecified,
    errorColor: Color = Color.Gray,
    onErrorClick: (() -> Unit)? = null,
    showBackground: Boolean = false,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    cornerShape: Shape? = null
) {
    // 应用大小修饰符（如果提供了size参数）
    val finalModifier = if (size != null) {
        modifier.size(size)
    } else {
        modifier
    }

    // 构建修饰符，应用背景色和圆角（如果需要）
    val contentModifier = Modifier.fillMaxSize()
        .let { mod ->
            // 如果需要显示背景色，添加背景修饰符
            if (showBackground) mod.background(backgroundColor) else mod
        }
        .let { mod ->
            // 如果需要圆角，添加裁剪修饰符
            cornerShape?.let { shape -> mod.clip(shape) } ?: mod
        }

    // 使用SubcomposeAsyncImage代替AsyncImage，可以自定义不同状态的UI
    SubcomposeAsyncImage(
        model = model,
        contentDescription = null,
        modifier = finalModifier.let { mod ->
            // 如果需要圆角，添加裁剪修饰符到外层
            cornerShape?.let { shape -> mod.clip(shape) } ?: mod
        },
        contentScale = contentScale
    ) {
        // 根据图片加载状态显示不同的UI
        when (painter.state) {
            // 加载中状态
            is AsyncImagePainter.State.Loading -> {
                Box(
                    modifier = contentModifier,
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
                    modifier = contentModifier,
                    contentAlignment = Alignment.Center // 居中显示错误图标
                ) {
                    // 如果提供了onErrorClick回调，则显示可点击的图标按钮
                    if (onErrorClick != null) {
                        IconButton(onClick = onErrorClick) {
                            Icon(
                                painter = painterResource(id = CoreUtilR.drawable.ic_error),
                                contentDescription = stringResource(id = UiR.string.image_load_error),
                                tint = errorColor,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else {
                        // 否则显示普通图标
                        Icon(
                            painter = painterResource(id = CoreUtilR.drawable.ic_error),
                            contentDescription = stringResource(id = UiR.string.image_load_error),
                            tint = errorColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            // 加载成功状态
            is AsyncImagePainter.State.Success -> {
                // 如果需要背景，先绘制背景
                if (showBackground) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(backgroundColor)
                    )
                }
                // 直接显示图片内容
                SubcomposeAsyncImageContent()
            }

            // 空状态（model为null时）
            is AsyncImagePainter.State.Empty -> {
                // 显示背景（如果启用）
                if (showBackground) {
                    Box(modifier = contentModifier)
                }
            }
        }
    }
}