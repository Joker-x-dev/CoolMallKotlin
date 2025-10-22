package com.joker.coolmall.core.ui.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.ColorDanger
import com.joker.coolmall.core.designsystem.theme.ColorPurple
import com.joker.coolmall.core.designsystem.theme.ColorSuccess
import com.joker.coolmall.core.designsystem.theme.ColorWarning
import com.joker.coolmall.core.designsystem.theme.GradientPrimaryEnd
import com.joker.coolmall.core.designsystem.theme.GradientPrimaryStart
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.TextWhite

/**
 * 按钮类型
 *
 * @author Joker.X
 */
enum class ButtonType {
    /**
     * 默认蓝色主题按钮
     */
    DEFAULT,

    /**
     * 成功绿色按钮
     */
    SUCCESS,

    /**
     * 警告黄色按钮
     */
    WARNING,

    /**
     * 危险红色按钮
     */
    DANGER,

    /**
     * 紫色按钮
     */
    PURPLE,

    /**
     * 链接按钮
     */
    LINK
}

/**
 * 按钮样式
 *
 * @author Joker.X
 */
enum class ButtonStyle {
    /**
     * 填充样式
     */
    FILLED,

    /**
     * 轮廓样式
     */
    OUTLINED,

    /**
     * 渐变样式
     */
    GRADIENT
}

/**
 * 按钮大小
 *
 * @author Joker.X
 */
enum class ButtonSize {
    /**
     * 中等尺寸按钮 - 高度48dp
     */
    MEDIUM,

    /**
     * 小尺寸按钮 - 高度40dp
     */
    SMALL,

    /**
     * 迷你尺寸按钮 - 高度32dp
     */
    MINI
}

/**
 * 按钮形状
 *
 * @author Joker.X
 */
enum class ButtonShape {
    /**
     * 方形按钮
     */
    SQUARE,

    /**
     * 圆形按钮
     */
    ROUND
}

/**
 * 基础按钮组件
 *
 * @param text 按钮文本
 * @param onClick 点击回调
 * @param modifier 修饰符
 * @param type 按钮类型，默认为DEFAULT
 * @param style 按钮样式，默认为FILLED
 * @param size 按钮大小，默认为MEDIUM
 * @param shape 按钮形状，默认为SQUARE
 * @param enabled 是否启用，默认为true
 * @param loading 是否显示加载状态，默认为false
 * @param fullWidth 是否占满宽度，默认为true
 * @author Joker.X
 */
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.DEFAULT,
    style: ButtonStyle = ButtonStyle.FILLED,
    size: ButtonSize = ButtonSize.MEDIUM,
    shape: ButtonShape = ButtonShape.ROUND,
    enabled: Boolean = true,
    loading: Boolean = false,
    fullWidth: Boolean = true
) {
    // 按钮颜色
    val buttonColor = when (type) {
        ButtonType.DEFAULT -> Primary
        ButtonType.SUCCESS -> ColorSuccess
        ButtonType.WARNING -> ColorWarning
        ButtonType.DANGER -> ColorDanger
        ButtonType.PURPLE -> ColorPurple
        ButtonType.LINK -> Primary
    }

    // 按钮高度
    val buttonHeight: Dp = when (size) {
        ButtonSize.MEDIUM -> 48.dp
        ButtonSize.SMALL -> 40.dp
        ButtonSize.MINI -> 34.dp
    }

    // 按钮形状
    val buttonShape = when (shape) {
        ButtonShape.SQUARE -> ShapeSmall
        ButtonShape.ROUND -> RoundedCornerShape(buttonHeight / 2)
    }

    val buttonModifier = if (fullWidth) {
        modifier
            .fillMaxWidth()
            .height(buttonHeight)
    } else {
        modifier.height(buttonHeight)
    }

    when (style) {
        ButtonStyle.FILLED -> {
            Button(
                onClick = onClick,
                enabled = enabled && !loading,
                shape = buttonShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor,
                    disabledContainerColor = buttonColor.copy(alpha = 0.5f)
                ),
                modifier = buttonModifier
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        color = TextWhite,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextWhite
                    )
                }
            }
        }

        ButtonStyle.OUTLINED -> {
            OutlinedButton(
                onClick = onClick,
                enabled = enabled && !loading,
                shape = buttonShape,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = buttonColor
                ),
                modifier = buttonModifier
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        color = buttonColor,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        ButtonStyle.GRADIENT -> {
            // 渐变起点和终点颜色
            val startColor = GradientPrimaryStart
            val endColor = GradientPrimaryEnd

            val gradientBrush = Brush.horizontalGradient(
                colors = listOf(startColor, endColor)
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = buttonModifier
                    .clip(buttonShape)
                    .background(
                        brush = gradientBrush,
                        alpha = if (enabled && !loading) 1f else 0.5f
                    )
                    .clickable(enabled = enabled && !loading) { onClick() }
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        color = TextWhite,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextWhite
                    )
                }
            }
        }
    }
}

/**
 * 自定义宽度的按钮组件
 *
 * @param text 按钮文本
 * @param onClick 点击回调
 * @param modifier 修饰符
 * @param type 按钮类型，默认为DEFAULT
 * @param style 按钮样式，默认为FILLED
 * @param size 按钮大小，默认为SMALL
 * @param shape 按钮形状，默认为ROUND
 * @param enabled 是否启用，默认为true
 * @param loading 是否显示加载状态，默认为false
 * @param contentPadding 内容内边距
 * @author Joker.X
 */
@Composable
fun AppButtonFixed(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.DEFAULT,
    style: ButtonStyle = ButtonStyle.FILLED,
    size: ButtonSize = ButtonSize.SMALL,
    shape: ButtonShape = ButtonShape.ROUND,
    enabled: Boolean = true,
    loading: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp)
) {
    // 按钮颜色
    val buttonColor = when (type) {
        ButtonType.DEFAULT -> Primary
        ButtonType.SUCCESS -> ColorSuccess
        ButtonType.WARNING -> ColorWarning
        ButtonType.DANGER -> ColorDanger
        ButtonType.PURPLE -> ColorPurple
        ButtonType.LINK -> Primary
    }

    // 按钮高度
    val buttonHeight: Dp = when (size) {
        ButtonSize.MEDIUM -> 48.dp
        ButtonSize.SMALL -> 40.dp
        ButtonSize.MINI -> 34.dp
    }

    // 按钮形状
    val buttonShape = when (shape) {
        ButtonShape.SQUARE -> ShapeSmall
        ButtonShape.ROUND -> RoundedCornerShape(buttonHeight / 2)
    }

    when (style) {
        ButtonStyle.FILLED -> {
            Button(
                onClick = onClick,
                enabled = enabled && !loading,
                shape = buttonShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor,
                    disabledContainerColor = buttonColor.copy(alpha = 0.5f)
                ),
                contentPadding = contentPadding,
                modifier = modifier.height(buttonHeight)
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        color = TextWhite,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextWhite,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        ButtonStyle.OUTLINED -> {
            OutlinedButton(
                onClick = onClick,
                enabled = enabled && !loading,
                shape = buttonShape,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = buttonColor
                ),
                contentPadding = contentPadding,
                modifier = modifier.height(buttonHeight)
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        color = buttonColor,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        ButtonStyle.GRADIENT -> {
            // 渐变起点和终点颜色
            val startColor = GradientPrimaryStart
            val endColor = GradientPrimaryEnd

            val gradientBrush = Brush.horizontalGradient(
                colors = listOf(startColor, endColor)
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .height(buttonHeight)
                    .clip(buttonShape)
                    .background(
                        brush = gradientBrush,
                        alpha = if (enabled && !loading) 1f else 0.5f
                    )
                    .clickable(enabled = enabled && !loading) { onClick() }
                    .padding(contentPadding)
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        color = TextWhite,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextWhite,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/**
 * 带边框的按钮组件
 *
 * @param text 按钮文本
 * @param onClick 点击回调
 * @param modifier 修饰符
 * @param type 按钮类型，默认为DEFAULT
 * @param size 按钮大小，默认为SMALL
 * @param shape 按钮形状，默认为SQUARE
 * @param enabled 是否启用，默认为true
 * @param loading 是否显示加载状态，默认为false
 * @param contentPadding 内容内边距
 * @param color 自定义颜色，优先级比type高
 * @param textStyle 按钮文本样式
 * @param height 自定义高度
 * @author Joker.X
 */
@Composable
fun AppButtonBordered(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.DEFAULT,
    size: ButtonSize = ButtonSize.SMALL,
    shape: ButtonShape = ButtonShape.ROUND,
    enabled: Boolean = true,
    loading: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    color: Color? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    height: Dp? = null
) {
    // 按钮颜色
    val buttonColor = color ?: when (type) {
        ButtonType.DEFAULT -> Primary
        ButtonType.SUCCESS -> ColorSuccess
        ButtonType.WARNING -> ColorWarning
        ButtonType.DANGER -> ColorDanger
        ButtonType.PURPLE -> ColorPurple
        ButtonType.LINK -> Primary
    }

    // 按钮高度
    val buttonHeight: Dp = height ?: when (size) {
        ButtonSize.MEDIUM -> 48.dp
        ButtonSize.SMALL -> 40.dp
        ButtonSize.MINI -> 34.dp
    }

    // 按钮形状
    val buttonShape = when (shape) {
        ButtonShape.SQUARE -> ShapeSmall
        ButtonShape.ROUND -> RoundedCornerShape(buttonHeight / 2)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(buttonHeight)
            .clip(buttonShape)
            .border(
                width = 1.dp,
                color = if (enabled && !loading) buttonColor else buttonColor.copy(alpha = 0.5f),
                shape = buttonShape
            )
            .clickable(enabled = enabled && !loading) { onClick() }
            .padding(contentPadding)
    ) {
        if (loading) {
            CircularProgressIndicator(
                color = buttonColor,
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                style = textStyle,
                color = if (enabled) buttonColor else buttonColor.copy(alpha = 0.5f),
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * 自定义按钮大小组件
 *
 * @param text 按钮文本
 * @param onClick 点击回调
 * @param modifier 修饰符
 * @param type 按钮类型，默认为DEFAULT
 * @param style 按钮样式，默认为FILLED
 * @param width 按钮宽度，默认为null
 * @param height 按钮高度，默认为null
 * @param shape 按钮形状，默认为SQUARE
 * @param enabled 是否启用，默认为true
 * @param loading 是否显示加载状态，默认为false
 * @param textStyle 按钮文本样式
 * @author Joker.X
 */
@Composable
fun AppButtonCustomSize(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.DEFAULT,
    style: ButtonStyle = ButtonStyle.FILLED,
    width: Dp? = null,
    height: Dp? = null,
    shape: ButtonShape = ButtonShape.ROUND,
    enabled: Boolean = true,
    loading: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    // 按钮颜色
    val buttonColor = when (type) {
        ButtonType.DEFAULT -> Primary
        ButtonType.SUCCESS -> ColorSuccess
        ButtonType.WARNING -> ColorWarning
        ButtonType.DANGER -> ColorDanger
        ButtonType.PURPLE -> ColorPurple
        ButtonType.LINK -> Primary
    }

    // 按钮形状
    val buttonShape = when (shape) {
        ButtonShape.SQUARE -> ShapeSmall
        ButtonShape.ROUND -> RoundedCornerShape(height ?: (40.dp / 2))
    }

    // 修饰符
    var buttonModifier = modifier
    if (width != null) {
        buttonModifier = buttonModifier.then(Modifier.width(width))
    }
    if (height != null) {
        buttonModifier = buttonModifier.then(Modifier.height(height))
    }

    when (style) {
        ButtonStyle.FILLED -> {
            Button(
                onClick = onClick,
                enabled = enabled && !loading,
                shape = buttonShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor,
                    disabledContainerColor = buttonColor.copy(alpha = 0.5f)
                ),
                modifier = buttonModifier
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        color = TextWhite,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = text,
                        style = textStyle,
                        color = TextWhite
                    )
                }
            }
        }

        ButtonStyle.OUTLINED -> {
            OutlinedButton(
                onClick = onClick,
                enabled = enabled && !loading,
                shape = buttonShape,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = buttonColor
                ),
                modifier = buttonModifier
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        color = buttonColor,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = text,
                        style = textStyle
                    )
                }
            }
        }

        ButtonStyle.GRADIENT -> {
            // 渐变起点和终点颜色
            val startColor = GradientPrimaryStart
            val endColor = GradientPrimaryEnd

            val gradientBrush = Brush.horizontalGradient(
                colors = listOf(startColor, endColor)
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = buttonModifier
                    .clip(buttonShape)
                    .background(
                        brush = gradientBrush,
                        alpha = if (enabled && !loading) 1f else 0.5f
                    )
                    .clickable(enabled = enabled && !loading) { onClick() }
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        color = TextWhite,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = text,
                        style = textStyle,
                        color = TextWhite
                    )
                }
            }
        }
    }
} 