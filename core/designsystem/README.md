# CoolMall 设计系统 (Design System)

CoolMall 应用的设计系统，提供统一的设计规范、主题系统和组件库，确保整个应用的视觉一致性和用户体验。

## 📋 目录

- [概述](#概述)
- [颜色系统](#颜色系统)
- [文字排版](#文字排版)
- [尺寸间距](#尺寸间距)
- [圆角规范](#圆角规范)
- [图标系统](#图标系统)
- [组件库](#组件库)
    - [Box 组件](#box-组件)
    - [Column 组件](#column-组件)
    - [Row 组件](#row-组件)
    - [LazyList 组件](#lazylist-组件)
    - [Scroll 组件](#scroll-组件)
- [主题系统](#主题系统)
- [使用示例](#使用示例)

## 🎯 概述

本设计系统基于 Material Design 3 构建，提供：

- 📐 **统一的设计规范**：颜色、字体、间距、圆角等
- 🎨 **深浅主题支持**：自动适配系统深色模式
- 🧩 **组件化设计**：预设常用布局组件
- 🔧 **易于使用**：简化的 API 设计

## 🎨 颜色系统

### 主色调

```kotlin
val Primary = Color(0xFF465CFF) // 品牌主色
```

### 功能色

```kotlin
val ColorDanger = Color(0xFFFF2B2B)   // 危险色/红色
val ColorWarning = Color(0xFFFFB703)  // 警告色/黄色
val ColorPurple = Color(0xFF6831FF)   // 紫色
val ColorSuccess = Color(0xFF09BE4F)  // 成功色/绿色
```

### 文字颜色

#### 浅色模式

```kotlin
val TextPrimaryLight = Color(0xFF181818)     // 主要文字
val TextSecondaryLight = Color(0xFF333333)   // 次要文字
val TextTertiaryLight = Color(0xFFB2B2B2)    // 三级文字
val TextQuaternaryLight = Color(0xFFCCCCCC)  // 四级文字
```

#### 深色模式

```kotlin
val TextPrimaryDark = Color(0xFFD1D1D1)      // 主要文字
val TextSecondaryDark = Color(0xFFA3A3A3)    // 次要文字
val TextTertiaryDark = Color(0xFF8D8D8D)     // 三级文字
val TextQuaternaryDark = Color(0xFF5E5E5E)   // 四级文字
```

### 背景色

#### 浅色模式

```kotlin
val BgGreyLight = Color(0xFFF1F4FA)    // 页面背景
val BgWhiteLight = Color(0xFFFFFFFF)   // 白色背景
val BgContentLight = Color(0xFFF8F8F8) // 内容模块背景
```

#### 深色模式

```kotlin
val BgGreyDark = Color(0xFF111111)     // 页面背景
val BgWhiteDark = Color(0xFF1B1B1B)    // 白色背景
val BgContentDark = Color(0xFF222222)  // 内容模块背景
```

### 使用方式

```kotlin
@Composable
fun MyComponent() {
    val textColors = appTextColors()
    Text(
        text = "标题",
        color = textColors.primary
    )
}
```

## ✏️ 文字排版

### 字体规格

| 样式            | 大小   | 行高   | 字重       | 用途          |
|---------------|------|------|----------|-------------|
| DisplayLarge  | 22sp | 31sp | SemiBold | 超大标题、启动页标题  |
| DisplayMedium | 18sp | 27sp | SemiBold | 页面大标题、模块主标题 |
| TitleLarge    | 16sp | 24sp | Bold     | 二级标题、导航栏标题  |
| TitleMedium   | 14sp | 22sp | Bold     | 分类名称、卡片标题   |
| BodyLarge     | 14sp | 22sp | Normal   | 正文内容、段落文字   |
| BodyMedium    | 12sp | 18sp | Normal   | 辅助文字、标签文字   |

### 使用示例

```kotlin
Text(
    text = "页面标题",
    style = MaterialTheme.typography.displayMedium
)

Text(
    text = "正文内容",
    style = MaterialTheme.typography.bodyLarge
)
```

## 📏 尺寸间距

### 垂直间距

```kotlin
val SpaceVerticalXXLarge = 32.dp  // 超大间距
val SpaceVerticalXLarge = 24.dp   // 特大间距
val SpaceVerticalLarge = 16.dp    // 大间距
val SpaceVerticalMedium = 12.dp   // 中等间距
val SpaceVerticalSmall = 8.dp     // 小间距
val SpaceVerticalXSmall = 4.dp    // 超小间距
```

### 水平间距

```kotlin
val SpaceHorizontalXXLarge = 32.dp  // 超大间距
val SpaceHorizontalXLarge = 24.dp   // 特大间距
val SpaceHorizontalLarge = 16.dp    // 大间距
val SpaceHorizontalMedium = 12.dp   // 中等间距
val SpaceHorizontalSmall = 8.dp     // 小间距
val SpaceHorizontalXSmall = 4.dp    // 超小间距
```

### 内边距

```kotlin
val SpacePaddingLarge = 16.dp   // 大内边距
val SpacePaddingMedium = 12.dp  // 中等内边距
val SpacePaddingSmall = 8.dp    // 小内边距
val SpacePaddingXSmall = 4.dp   // 超小内边距
```

### 间距组件

```kotlin
// 垂直间距
SpaceVerticalLarge()  // 16dp 垂直间距
SpaceVerticalMedium() // 12dp 垂直间距

// 水平间距
SpaceHorizontalLarge()  // 16dp 水平间距
SpaceHorizontalMedium() // 12dp 水平间距
```

## 🔘 圆角规范

```kotlin
val RadiusSmall = 8.dp        // 小圆角
val RadiusMedium = 12.dp      // 中圆角
val RadiusLarge = 16.dp       // 大圆角
val RadiusExtraLarge = 24.dp  // 超大圆角

val ShapeSmall = RoundedCornerShape(RadiusSmall)       // 常规卡片、按钮
val ShapeMedium = RoundedCornerShape(RadiusMedium)     // 中型容器、对话框
val ShapeLarge = RoundedCornerShape(RadiusLarge)       // 分类列表等组件
val ShapeExtraLarge = RoundedCornerShape(RadiusExtraLarge) // 大型卡片、底部弹窗
val ShapeCircle = RoundedCornerShape(percent = 50)     // 头像、图标按钮
```

## 🎯 图标系统

### 通用图标组件

```kotlin
// 使用资源ID
CommonIcon(
    resId = R.drawable.ic_arrow_left,
    size = 24.dp,
    tint = MaterialTheme.colorScheme.primary
)

// 使用ImageVector
CommonIcon(
    imageVector = Icons.Default.Home,
    size = 24.dp
)

// 使用Painter
CommonIcon(
    painter = painterResource(R.drawable.ic_logo),
    size = 24.dp
)
```

### 预定义图标

```kotlin
// Logo图标
LogoIcon(size = 24.dp)

// 返回箭头
ArrowLeftIcon(size = 28.dp)

// 右箭头
ArrowRightIcon(size = 24.dp)
```

## 🧩 组件库

### Box 组件

#### AppBox - 基础Box组件

```kotlin
AppBox(
    fillMaxWidth = true,
    padding = 16.dp,
    contentAlignment = Alignment.Center
) {
    Text("内容")
}
```

#### 预设对齐Box

```kotlin
// 居中Box
CenterBox {
    Text("居中内容")
}

// 顶部居中Box
TopCenterBox {
    Text("顶部居中")
}

// 底部居中Box
BottomCenterBox {
    Text("底部居中")
}

// 左侧居中Box
CenterStartBox {
    Text("左侧居中")
}

// 右侧居中Box
CenterEndBox {
    Text("右侧居中")
}
```

#### 特殊Box组件

```kotlin
// 全屏Box
FullScreenBox(
    contentAlignment = Alignment.Center,
    padding = PaddingValues(16.dp)
) {
    Text("全屏内容")
}

// 固定尺寸Box
FixedSizeBox(
    width = 100.dp,
    height = 100.dp
) {
    Text("固定尺寸")
}

// 圆角Box
RoundedBox(
    cornerRadius = 12.dp,
    backgroundColor = MaterialTheme.colorScheme.surface
) {
    Text("圆角内容")
}

// 边框Box
BorderBox(
    borderWidth = 1.dp,
    borderColor = MaterialTheme.colorScheme.outline
) {
    Text("边框内容")
}
```

#### 内边距Box

```kotlin
// 小内边距
SmallPaddingBox { Text("小间距") }

// 中等内边距
MediumPaddingBox { Text("中等间距") }

// 大内边距
LargePaddingBox { Text("大间距") }
```

### Column 组件

#### AppColumn - 基础Column组件

```kotlin
AppColumn(
    verticalArrangement = Arrangement.spacedBy(12.dp),
    horizontalAlignment = Alignment.Start
) {
    Text("项目 1")
    Text("项目 2")
    Text("项目 3")
}
```

#### 预设布局Column

```kotlin
// 全屏Column
FullScreenColumn(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Text("全屏垂直布局")
}

// 居中Column
CenterColumn {
    Text("居中布局")
}

// 顶部Column
TopColumn {
    Text("顶部对齐")
}

// 底部Column
BottomColumn {
    Text("底部对齐")
}

// 两端对齐Column
SpaceBetweenColumn {
    Text("顶部内容")
    Text("底部内容")
}

// 均匀分布Column
SpaceEvenlyColumn {
    Text("项目 1")
    Text("项目 2")
    Text("项目 3")
}
```

#### 对齐Column

```kotlin
// 左对齐
StartAlignColumn {
    Text("左对齐内容")
}

// 右对齐
EndAlignColumn {
    Text("右对齐内容")
}
```

#### 特殊Column

```kotlin
// 内容包裹Column
WrapColumn {
    Text("包裹内容")
}

// 垂直列表Column（带间距）
VerticalList {
    Text("列表项 1")
    Text("列表项 2")
    Text("列表项 3")
}

// 卡片内容列表
CardContentList {
    Text("卡片内容 1")
    Text("卡片内容 2")
}
```

#### 内边距Column

```kotlin
// 小内边距
SmallPaddingColumn { Text("小间距") }

// 中等内边距
MediumPaddingColumn { Text("中等间距") }

// 大内边距
LargePaddingColumn { Text("大间距") }
```

### Row 组件

#### AppRow - 基础Row组件

```kotlin
AppRow(
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    Icon(Icons.Default.Star, contentDescription = null)
    Text("星标")
}
```

#### 预设布局Row

```kotlin
// 居中Row
CenterRow {
    Text("居中内容")
}

// 两端对齐Row
SpaceBetweenRow {
    Text("左侧")
    Text("右侧")
}

// 均匀分布Row
SpaceEvenlyRow {
    Text("项目 1")
    Text("项目 2")
    Text("项目 3")
}

// 平均分布Row
SpaceAroundRow {
    Text("项目 1")
    Text("项目 2")
    Text("项目 3")
}

// 左对齐Row
StartRow {
    Text("左对齐内容")
}

// 右对齐Row
EndRow {
    Text("右对齐内容")
}
```

#### 特殊Row

```kotlin
// 内容包裹Row
WrapRow {
    Text("包裹内容")
}

// 水平列表Row（带间距）
HorizontalList {
    Text("项目 1")
    Text("项目 2")
    Text("项目 3")
}
```

#### 内边距Row

```kotlin
// 小内边距
SmallPaddingRow { Text("小间距") }

// 中等内边距
MediumPaddingRow { Text("中等间距") }

// 大内边距
LargePaddingRow { Text("大间距") }
```

### LazyList 组件

#### AppLazyColumn - 垂直懒加载列表

```kotlin
AppLazyColumn(
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
) {
    items(dataList) { item ->
        Text(item.title)
    }
}
```

#### 内边距LazyColumn

```kotlin
// 小内边距
SmallPaddingLazyColumn {
    items(dataList) { item ->
        Text(item.title)
    }
}

// 中等内边距
MediumPaddingLazyColumn {
    items(dataList) { item ->
        Text(item.title)
    }
}

// 大内边距
LargePaddingLazyColumn {
    items(dataList) { item ->
        Text(item.title)
    }
}
```

#### 垂直列表项目

```kotlin
VerticalListItems(
    itemSpacing = 8.dp,
    contentPadding = PaddingValues(16.dp)
) {
    items(dataList) { item ->
        Card {
            Text(
                text = item.title,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
```

#### AppLazyRow - 水平懒加载列表

```kotlin
AppLazyRow(
    contentPadding = PaddingValues(horizontal = 16.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
) {
    items(dataList) { item ->
        Card {
            Text(
                text = item.title,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
```

#### 内边距LazyRow

```kotlin
// 小内边距
SmallPaddingLazyRow {
    items(dataList) { item ->
        Text(item.title)
    }
}

// 中等内边距
MediumPaddingLazyRow {
    items(dataList) { item ->
        Text(item.title)
    }
}
```

### Scroll 组件

#### VerticalScroll - 垂直滚动

```kotlin
VerticalScroll(
    padding = 16.dp,
    fillMaxWidth = true
) {
    repeat(20) { index ->
        Text("项目 $index")
        SpaceVerticalMedium()
    }
}
```

#### 内边距VerticalScroll

```kotlin
// 小内边距
SmallPaddingVerticalScroll {
    Text("滚动内容")
}

// 中等内边距
MediumPaddingVerticalScroll {
    Text("滚动内容")
}

// 大内边距
LargePaddingVerticalScroll {
    Text("滚动内容")
}
```

#### HorizontalScroll - 水平滚动

```kotlin
HorizontalScroll(
    padding = 16.dp
) {
    repeat(10) { index ->
        Card(
            modifier = Modifier.size(100.dp)
        ) {
            Text("项目 $index")
        }
        SpaceHorizontalMedium()
    }
}
```

#### 内边距HorizontalScroll

```kotlin
// 小内边距
SmallPaddingHorizontalScroll {
    // 水平滚动内容
}

// 中等内边距
MediumPaddingHorizontalScroll {
    // 水平滚动内容
}
```

## 🎨 主题系统

### 应用主题

```kotlin
@Composable
fun MyApp() {
    AppTheme(
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = false
    ) {
        // 应用内容
    }
}
```

### 主题特性

- ✅ **自动深色模式**：跟随系统设置
- ✅ **Material 3**：基于最新设计规范
- ✅ **动态颜色**：支持 Android 12+ 动态颜色（可选）
- ✅ **一致性**：统一的颜色、字体、圆角规范

## 📱 使用示例

### 订单确认页面布局示例

```kotlin
@Composable
fun OrderConfirmScreen() {
    AppScaffold(
        title = R.string.order_confirm,
        useLargeTopBar = true,
        onBackClick = onBackClick,
        bottomBar = {
            OrderBottomBar(
                totalPrice = totalPrice,
                onSubmitClick = onSubmitOrderClick
            )
        }
    ) {
        VerticalList(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            // 地址选择卡片
            AddressCard(
                address = address,
                onClick = { /* 地址点击回调 */ },
                addressSelected = true
            )

            // 订单商品卡片
            cartList.forEach { cart ->
                OrderGoodsCard(
                    data = cart,
                    enableQuantityStepper = false,
                    onGoodsClick = { /* 商品点击事件 */ },
                    onSpecClick = { /* 规格点击事件 */ }
                )
            }

            // 价格明细卡片
            Card {
                AppListItem(
                    title = "",
                    showArrow = false,
                    leadingContent = {
                        TitleWithLine(text = "价格明细")
                    }
                )

                AppListItem(
                    title = "商品总价",
                    leadingIcon = R.drawable.ic_shop,
                    trailingContent = {
                        PriceText(
                            totalPrice,
                            integerTextSize = TextSize.BODY_LARGE,
                            decimalTextSize = TextSize.BODY_SMALL,
                            symbolTextSize = TextSize.BODY_SMALL,
                            type = TextType.PRIMARY
                        )
                    },
                    showArrow = false
                )

                AppListItem(
                    title = "优惠券",
                    leadingIcon = R.drawable.ic_coupon,
                    trailingText = "无可用",
                    showArrow = true,
                    onClick = { /* 选择优惠券 */ }
                )
            }
        }
    }
}
```

### 订单详情页面布局示例

```kotlin
@Composable
fun OrderDetailScreen() {
    AppScaffold(
        useLargeTopBar = true,
        title = titleResId,
        onBackClick = onBackClick,
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 4.dp,
            ) {
                EndRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SpacePaddingMedium)
                        .navigationBarsPadding()
                ) {
                    OrderButtons(
                        order = order,
                        onCancelClick = onCancelClick,
                        onPayClick = onPayClick,
                        onRefundClick = onRefundClick,
                        onConfirmClick = onConfirmClick,
                        onLogisticsClick = onLogisticsClick,
                        onCommentClick = onCommentClick,
                        onRebuyClick = onRebuyClick,
                    )
                }
            }
        }
    ) {
        VerticalList(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            AddressCard(
                address = data.address!!,
                onClick = { /* 地址点击回调 */ }
            )

            // 订单商品卡片
            cartList.forEach { cart ->
                OrderGoodsCard(
                    data = cart,
                    enableQuantityStepper = false,
                    onGoodsClick = { /* 商品点击事件 */ },
                    onSpecClick = { /* 规格点击事件 */ }
                )
            }

            // 价格明细卡片
            Card {
                AppListItem(
                    title = "",
                    showArrow = false,
                    leadingContent = {
                        TitleWithLine(text = "价格明细")
                    }
                )
                // ... 其他列表项
            }
        }
    }
}
```

### 简单卡片布局示例

```kotlin
@Composable
fun SimpleCard() {
    RoundedBox(
        cornerRadius = 12.dp,
        backgroundColor = MaterialTheme.colorScheme.surface
    ) {
        MediumPaddingColumn {
            Text(
                text = "卡片标题",
                style = MaterialTheme.typography.titleMedium
            )
            
            SpaceVerticalSmall()
            
            Text(
                text = "卡片内容描述",
                style = MaterialTheme.typography.bodyMedium,
                color = appTextColors().secondary
            )
            
            SpaceVerticalMedium()
            
            SpaceBetweenRow {
                Text(
                    text = "左侧信息",
                    style = MaterialTheme.typography.bodySmall,
                    color = appTextColors().tertiary
                )
                
                Text(
                    text = "右侧信息",
                    style = MaterialTheme.typography.bodySmall,
                    color = appTextColors().tertiary
                )
            }
        }
    }
}
```

## 🚀 最佳实践

### 1. 使用设计系统组件

```kotlin
// ✅ 推荐：使用设计系统组件
AppColumn(
    verticalArrangement = Arrangement.spacedBy(SpaceVerticalMedium)
) {
    Text("内容")
}

// ❌ 不推荐：直接使用原生组件
Column(
    modifier = Modifier.padding(12.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
) {
    Text("内容")
}
```

### 2. 使用主题颜色

```kotlin
// ✅ 推荐：使用主题颜色
val textColors = appTextColors()
Text(
    text = "标题",
    color = textColors.primary
)

// ❌ 不推荐：硬编码颜色
Text(
    text = "标题",
    color = Color(0xFF181818)
)
```

### 3. 使用预设间距

```kotlin
// ✅ 推荐：使用预设间距组件
Column {
    Text("内容 1")
    SpaceVerticalMedium()
    Text("内容 2")
}

// ❌ 不推荐：自定义间距
Column {
    Text("内容 1")
    Spacer(modifier = Modifier.height(12.dp))
    Text("内容 2")
}
```

### 4. 选择合适的组件

```kotlin
// ✅ 推荐：根据需求选择合适的组件
VerticalList {  // 自动带间距的垂直列表
    items.forEach { item ->
        ItemCard(item)
    }
}

// ✅ 也推荐：长列表使用LazyColumn
VerticalListItems {  // 懒加载列表
    items(longList) { item ->
        ItemCard(item)
    }
}
```

## 📚 更多资源

- [Material Design 3 官方文档](https://m3.material.io/)
- [Jetpack Compose 官方文档](https://developer.android.com/jetpack/compose)
- [设计系统最佳实践](https://www.designsystemsrepo.com/)

---

**注意**：本设计系统遵循 Material Design 3 规范，建议在使用时保持一致性，避免混用不同的设计风格。 