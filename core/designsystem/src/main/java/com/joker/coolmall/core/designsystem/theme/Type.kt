package com.joker.coolmall.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * 文字排版规范
 * 定义应用程序中使用的所有文字样式，包括大小、粗细、行高等
 * 设计规范中的px值转换为sp值（转换比例为1px = 0.5sp）
 */

/**
 * 文字颜色集合，包含适应深色和浅色模式的文字颜色
 * 使用此类可以在组件中根据当前主题获取适合的文字颜色
 *
 * @param primary 主要文字颜色，用于标题、重要内容
 * @param secondary 次要文字颜色，用于正文内容
 * @param tertiary 三级文字颜色，用于辅助说明
 * @param quaternary 四级文字颜色，用于次要信息
 * @param white 白色文字，用于深色背景上的文字
 * @param link 链接颜色，用于可点击文字
 */
@Stable
class AppTextColors(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val quaternary: Color,
    val white: Color,
    val link: Color
)

/**
 * 根据当前主题获取文字颜色集合
 * 在Composable中调用此函数获取基于当前主题的文字颜色
 *
 * 使用示例:
 * ```
 * val textColors = appTextColors()
 * Text(
 *     text = "标题",
 *     color = textColors.primary
 * )
 * ```
 *
 * @return 返回与当前主题匹配的AppTextColors实例
 * @author Joker.X
 */
@Composable
fun appTextColors(): AppTextColors {
    return if (isSystemInDarkTheme()) {
        AppTextColors(
            primary = TextPrimaryDark,
            secondary = TextSecondaryDark,
            tertiary = TextTertiaryDark,
            quaternary = TextQuaternaryDark,
            white = TextWhite,
            link = Primary // 使用主色作为链接色
        )
    } else {
        AppTextColors(
            primary = TextPrimaryLight,
            secondary = TextSecondaryLight,
            tertiary = TextTertiaryLight,
            quaternary = TextQuaternaryLight,
            white = TextWhite,
            link = Primary // 使用主色作为链接色
        )
    }
}

/**
 * 超大标题文字样式
 * 尺寸: 22sp (44px), 行高: 31sp (62px), 字重: 中粗体
 * 适用场景: 页面主标题、启动页标题、特大号标题文字
 */
val DisplayLarge = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.SemiBold,
    fontSize = 22.sp, // 44px
    lineHeight = 31.sp, // 62px
)

/**
 * 大标题文字样式
 * 尺寸: 18sp (36px), 行高: 27sp (54px), 字重: 中粗体
 * 适用场景: 页面大标题、模块主标题
 */
val DisplayMedium = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.SemiBold,
    fontSize = 18.sp, // 36px
    lineHeight = 27.sp, // 54px
)

/**
 * 二级标题文字样式
 * 尺寸: 16sp (32px), 行高: 24sp (48px), 字重: 粗体
 * 适用场景: 二级标题、导航栏标题、列表标题、段落标题
 */
val TitleLarge = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp, // 32px
    lineHeight = 24.sp, // 48px
)

/**
 * 类别名称文字样式
 * 尺寸: 14sp (28px), 行高: 22sp (44px), 字重: 粗体
 * 适用场景: 分类名称、卡片标题、强调文字
 */
val TitleMedium = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp, // 28px
    lineHeight = 22.sp, // 44px
)

/**
 * 正文内容文字样式
 * 尺寸: 14sp (28px), 行高: 22sp (44px), 字重: 常规体
 * 适用场景: 正文内容、段落文字、列表内容
 */
val BodyLarge = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp, // 28px
    lineHeight = 22.sp, // 44px
)

/**
 * 辅助文字样式
 * 尺寸: 12sp (24px), 行高: 18sp (36px), 字重: 常规体
 * 适用场景: 辅助文字、标签文字、底部导航栏文字、次要信息
 */
val BodyMedium = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp, // 24px
    lineHeight = 18.sp, // 36px
)

/**
 * Material Design 3 排版系统
 * 将自定义的文字样式映射到Material 3的Typography中
 */
val Typography = Typography(
    // 超大标题
    displayLarge = DisplayLarge,
    // 大标题
    displayMedium = DisplayMedium,
    // 二级标题
    titleLarge = TitleLarge,
    // 类别名称
    titleMedium = TitleMedium,
    // 正文内容
    bodyLarge = BodyLarge,
    // 辅助文字、标签文字
    bodyMedium = BodyMedium,
    // 其他必需的Material 3样式，但使用我们的字体规范
    displaySmall = DisplayMedium.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    headlineLarge = TitleLarge,
    headlineMedium = TitleLarge.copy(
        fontWeight = FontWeight.SemiBold
    ),
    headlineSmall = TitleMedium,
    titleSmall = TitleMedium.copy(
        fontWeight = FontWeight.Medium
    ),
    bodySmall = BodyMedium.copy(
        fontSize = 11.sp,
        lineHeight = 16.sp
    ),
    labelLarge = BodyMedium,
    labelMedium = BodyMedium.copy(
        fontWeight = FontWeight.Medium
    ),
    labelSmall = BodyMedium.copy(
        fontSize = 11.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Medium
    )
)