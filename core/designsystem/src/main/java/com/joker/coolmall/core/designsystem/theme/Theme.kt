package com.joker.coolmall.core.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

/**
 * 应用主题系统
 * 整合颜色、排版、形状等设计系统，定义深色和浅色主题
 */

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDefault,
    secondary = ColorPurple,
    tertiary = ColorSuccess,
    background = BgGreyDark,
    surface = BgWhiteDark,
    error = ColorDanger,
    onPrimary = TextWhite,
    onSecondary = TextWhite,
    onTertiary = TextWhite,
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark,
    onError = TextWhite,
    outline = BorderDark,
    surfaceVariant = BgContentDark,
    surfaceTint = BgColorDark,
    surfaceContainer = BgWhiteDark,
    surfaceContainerHigh = BgWhiteDark,
    surfaceContainerHighest = BgWhiteDark
)

/**
 * 浅色主题配色方案
 * 定义MaterialTheme中浅色模式下的各种颜色
 */
private val LightColorScheme = lightColorScheme(
    primary = PrimaryDefault,
    secondary = ColorPurple,
    tertiary = ColorSuccess,
    background = BgGreyLight,
    surface = BgWhiteLight,
    error = ColorDanger,
    onPrimary = TextWhite,
    onSecondary = TextWhite,
    onTertiary = TextWhite,
    onBackground = TextPrimaryLight,
    onSurface = TextPrimaryLight,
    onError = TextWhite,
    outline = BorderLight,
    surfaceVariant = BgContentLight,
    surfaceTint = PrimaryDefault,
    surfaceContainer = BgWhiteLight,
    surfaceContainerHigh = BgWhiteLight,
    surfaceContainerHighest = BgWhiteLight
)

/**
 * 应用主题 Composable 函数
 * 根据系统设置决定使用深色或浅色主题，并应用所有设计系统元素
 *
 * @param darkTheme 是否使用深色主题，默认跟随系统设置
 * @param dynamicColor 是否使用动态颜色（Android 12+特性），默认关闭
 * @param content 需要应用主题的内容
 * @author Joker.X
 */
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    themeColor: Color = PrimaryDefault,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme.copy(
            primary = themeColor,
            surfaceTint = themeColor
        )

        else -> LightColorScheme.copy(
            primary = themeColor,
            surfaceTint = themeColor
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}
