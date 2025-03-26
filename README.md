# CoolMall - 现代化电商应用(持续更新中)

基于 Kotlin 和 Jetpack Compose 构建的开源电商应用，采用 Google 推荐的 Android 应用架构和最佳实践。项目参考了 [Now in Android](https://github.com/android/nowinandroid) 的架构设计，致力于打造一个功能完整、性能优异的现代化电商平台。

## 技术栈

- **编程语言**: 100% Kotlin
- **UI框架**: Jetpack Compose
- **架构模式**: MVVM + Clean Architecture
- **依赖注入**: Hilt
- **异步处理**: Kotlin Coroutines + Flow
- **导航**: Jetpack Navigation
- **数据持久化**: Room
- **网络请求**: Retrofit + OkHttp
- **图片加载**: Coil
- **单元测试**: JUnit + Mockk
- **UI测试**: Compose UI Testing

## 项目特点

- 采用模块化架构设计，各功能模块高度解耦
- 使用 Jetpack Compose 构建现代化 UI
- 遵循 Material Design 3 设计规范
- 支持浅色/深色主题
- 采用响应式编程范式
- 完整的测试覆盖
- 持续集成与部署

## 功能模块目录(预期实现，后续可能会有差异)

- **主模块 (main)**
  - 首页 (home)
  - 分类 (category)
  - 购物车 (cart)
  - 我的 (me)

- **认证模块 (auth)**
  - 登录主页 (home)
  - 账号登录 (login)
  - 注册页面 (register)
  - 找回密码 (reset)
  - 短信登录 (sms)

- **用户体系模块 (user)**
  - 个人中心 (profile)
  - 设置模块 (settings)
  - 收货地址模块 (address)
  - 用户足迹 (footprint)

- **订单模块 (order)**
  - 订单列表 (list)
  - 确认订单 (confirm)
  - 订单详情 (detail)

- **商品模块 (goods)**
  - 商品搜索 (search)
  - 商品详情 (detail)
  - 评价系统 (comment)
  - 商品分类页面 (category)

- **营销模块 (market)**
  - 优惠券管理 (coupon)

- **客服模块 (cs)**
  - 客服聊天 (chat)

- **反馈系统 (feedback)**
  - 投诉子模块 (complain)
  - 反馈子模块 (feedback)

- **公共信息模块 (common)**
  - 关于我们 (about)
  - WebView 页面 (webview)

- **启动流程模块 (launch)**
  - 启动页 (splash)
  - 引导页 (guide)

## 项目结构

```
├── app/                   # 应用入口模块
├── build-logic/          # 构建逻辑
├── core/                 # 核心模块
│   ├── common/           # 通用工具和扩展
│   ├── data/             # 数据层
│   ├── designsystem/     # 设计系统
│   ├── model/            # 数据模型
│   ├── network/          # 网络层
│   └── ui/               # UI组件
├── feature/              # 功能模块
│   ├── auth/             # 认证模块
│   ├── goods/            # 商品模块
│   ├── main/             # 主模块
│   ├── market/           # 营销模块
│   ├── order/            # 订单模块
│   └── user/             # 用户模块
└── navigation/           # 导航模块
```

## 开发计划

项目目前处于积极开发阶段，功能模块将逐步完善。我们采用迭代式开发方式，优先实现核心功能，后续会持续添加更多特性。

### 开发时间线

1. **Android Kotlin 版本 (进行中)**
   - 预计开发周期：1-3个月
   - 完成核心功能开发和架构设计
   - 建立最佳实践和开发规范

2. **iOS 原生版本 (计划中)**
   - 计划在 Android 版本完成后启动
   - 基于 SwiftUI 构建现代化 UI
   - 复用 Android 版本的架构设计和业务逻辑

3. **HarmonyOS 原生版本 (计划中)**
   - 计划在 Android 版本完成后启动
   - 采用 ArkUI 构建原生界面
   - 参考 Android 版本的架构设计

### 当前开发重点

1. 完善核心购物流程
2. 优化用户认证体验
3. 实现商品搜索和筛选
4. 订单管理系统