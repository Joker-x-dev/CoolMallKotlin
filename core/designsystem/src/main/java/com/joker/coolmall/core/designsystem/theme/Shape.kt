package com.joker.coolmall.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * 圆角规范
 * 设计规范中的px值转换为dp值（转换比例为1px = 0.5dp）
 */

/**
 * 小圆角：8dp (16px)
 * 适用场景：常规卡片、按钮等小型UI元素的圆角
 */
val ShapeSmall = RoundedCornerShape(8.dp) // 小圆角 16px

/**
 * 中圆角：12dp (24px)
 * 适用场景：中型容器、对话框等组件的圆角
 */
val ShapeMedium = RoundedCornerShape(12.dp) // 中圆角 24px

/**
 * 大圆角：24dp (48px)
 * 适用场景：大型卡片、底部弹窗等较大UI元素的圆角
 */
val ShapeLarge = RoundedCornerShape(24.dp) // 大圆角 48px

/**
 * 圆形
 * 适用场景：头像、图标按钮等需要完全圆形的组件
 */
val ShapeCircle = RoundedCornerShape(percent = 50)

/**
 * Material3 Shapes配置
 * 将自定义圆角规范应用于Material3设计系统
 */
val AppShapes = Shapes(
    small = ShapeSmall,
    medium = ShapeMedium,
    large = ShapeLarge,
    extraLarge = ShapeLarge
)
