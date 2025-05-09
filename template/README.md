# Android Studio 文件模板使用指南

## 模板说明

本目录包含三个 Markdown 文件，存放了用于 Android Studio File and Code Templates 的代码模板：

1. **ScreenTemplate.md** - 页面 UI 组件模板
2. **ViewModelTemplate.md** - ViewModel 模板
3. **NavigationTemplate.md** - 页面导航模板

这些模板遵循项目的架构设计和命名规范，可以快速创建标准化的组件。

## 设置步骤

### 1. 打开 Android Studio 模板配置

- 菜单: `File > Settings` (Windows/Linux) 或 `Android Studio > Preferences` (macOS)
- 搜索: `File and Code Templates`
- 选择: `Files` 选项卡

### 2. 创建自定义模板

#### Screen 模板
1. 点击 "+" 按钮
2. 名称填写: `Compose Screen`
3. 扩展名填写: `kt`
4. 将 `ScreenTemplate.md` 中的代码部分复制到编辑区
5. 点击 `Edit Variables` 设置变量表达式
6. 点击 "OK" 保存

#### ViewModel 模板
1. 点击 "+" 按钮
2. 名称填写: `Compose ViewModel`
3. 扩展名填写: `kt`
4. 将 `ViewModelTemplate.md` 中的代码部分复制到编辑区
5. 点击 "OK" 保存

#### Navigation 模板
1. 点击 "+" 按钮
2. 名称填写: `Compose Navigation`
3. 扩展名填写: `kt`
4. 将 `NavigationTemplate.md` 中的代码部分复制到编辑区
5. 点击 `Edit Variables` 设置变量表达式
6. 点击 "OK" 保存

### 3. 变量表达式配置

为了简化输入过程，建议在模板编辑器的 `Edit Variables` 中添加以下变量表达式：

**Screen 模板变量**:
- PAGE_NAME_LOWERCASE = `${PAGE_NAME.toLowerCase()}`
- PAGE_NAME_UPPERCASE = `${PAGE_NAME.toUpperCase().replace(" ", "_")}`

**Navigation 模板变量**:
- 无需额外变量，直接使用内联表达式

## 使用方法

### 创建新页面

1. **创建 Screen**:
   - 右键点击视图目录 (如 `feature/common/view`)
   - 选择 `New > Compose Screen`
   - 输入文件名 (如 `AboutScreen`)
   - 输入 PAGE_NAME (如 `About`)
   - 输入 CN_NAME (如 `关于我们`)

2. **创建 ViewModel**:
   - 右键点击 viewmodel 目录
   - 选择 `New > Compose ViewModel`
   - 输入文件名 (如 `AboutViewModel`)
   - 输入 CN_NAME (如 `关于我们`)

3. **创建导航**:
   - 右键点击 navigation 目录
   - 选择 `New > Compose Navigation`
   - 输入文件名 (如 `AboutNavigation`)
   - 输入 NAME (如 `About`)
   - 输入 CN_NAME (如 `关于我们`)

## 简化变量输入的优化建议

为避免填写过多变量，可以尝试以下优化：

1. **利用 Groovy 表达式自动推导变量**:
   - FEATURE_NAME 可以从包名自动提取
   - 所有大小写转换使用内联表达式

2. **合并模板**:
   - 考虑创建一个组合模板，一次生成所有三个文件

## 示例预览

### 示例1：创建"关于我们"页面

- **Screen 文件**:
  - 文件名: `AboutScreen.kt`
  - 类和方法: `AboutRoute`, `AboutScreen`, `AboutContentView` 等

- **ViewModel 文件**:
  - 文件名: `AboutViewModel.kt`
  - 类: `AboutViewModel`

- **Navigation 文件**:
  - 文件名: `AboutNavigation.kt`
  - 函数: `aboutScreen()` 