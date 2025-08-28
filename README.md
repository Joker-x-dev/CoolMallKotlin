<div align="center">

<img src="docs/images/graphs/logo.svg" width="120" alt="青商城Logo"/>

# 青商城

_🛍️ 基于 Kotlin 和 Jetpack Compose 的现代化电商应用_

[![GitHub](https://img.shields.io/badge/GitHub-CoolMallKotlin-blue?style=flat-square&logo=github)](https://github.com/Joker-x-dev/CoolMallKotlin)
[![Gitee](https://img.shields.io/badge/Gitee-CoolMallKotlin-red?style=flat-square&logo=gitee)](https://gitee.com/Joker-x-dev/CoolMallKotlin)
[![Demo](https://img.shields.io/badge/Demo-蒲公英下载-green?style=flat-square&logo=android)](https://www.pgyer.com/CoolMallKotlinProdRelease)
[![API](https://img.shields.io/badge/API-文档-orange?style=flat-square&logo=postman)](https://coolmall.apifox.cn)

</div>

## 📖 项目简介

这是一个基于 Kotlin 和 Jetpack Compose 打造的开源电商学习项目，正在逐步完善中。项目采用了 Google
推荐的应用架构和最佳实践，参考了 [Now in Android](https://github.com/android/nowinandroid)
的架构设计，旨在展示如何运用现代 Android 开发技术构建一个电商应用的学习案例，适合开发者学习参考，而非直接用于商业环境。

作为热爱技术的个人开发者，我将工作之外的时间都投入到这个项目中。每一个功能的实现、每一次代码的优化，都是我在闲暇时间精心打磨的成果。尽管进度可能不如专职团队那么快，而且某些功能的实现还不够完善，但我会持续改进，不断完善。如果你对
Android 开发感兴趣，无论是学习还是共同创造，都欢迎加入。

## 📱 项目预览

<img src="docs/images/preview1.png"  alt="青商城应用预览1"/>
<img src="docs/images/preview2.png"  alt="青商城应用预览2"/>
<img src="docs/images/preview3.png"  alt="青商城应用预览3"/>
<img src="docs/images/preview4.png"  alt="青商城应用预览4"/>

### 📍 项目地址

- **GitHub 地址**：[https://github.com/Joker-x-dev/CoolMallKotlin](https://github.com/Joker-x-dev/CoolMallKotlin)
- **Gitee 地址**：[https://gitee.com/Joker-x-dev/CoolMallKotlin](https://gitee.com/Joker-x-dev/CoolMallKotlin)

### Demo 下载

- **Release 版本 (推荐)**：[点击下载体验](https://www.pgyer.com/CoolMallKotlinProdRelease)
  - 这是为日常使用和体验优化的稳定版本，具有最佳性能。

- **Debug 版本 (开发者)**：[点击下载体验](https://www.pgyer.com/CoolMallKotlinDebug)
  - **注意**：Debug 版本的包名带有 `.debug` 后缀，与 Release 版本不同，可以共存安装。
  - **内置工具**：集成了 LeakCanary (内存泄漏检测) 和 Chucker (网络请求监控) 等调试工具。
  - **性能与体积**：由于开启了调试功能且未进行代码压缩，此版本性能会低于 Release 版本，且 APK 体积更大。
  - **快捷访问**：长按桌面图标可快速访问 `Leaks` (内存泄漏) 和 `Open Chucker` (网络监控)。为确保能接收到 Chucker 的实时网络请求通知，建议开启应用的通知权限。

- **支持系统**：Android 8.0 及以上
- **更新说明**：预览版本会不定时更新，可能不会完全同步最新的代码变更

### API 文档

- **接口文档**：[在线查看](https://coolmall.apifox.cn)
- **说明**：接口文档会随着项目开发进度同步更新，主要包含各接口的请求参数和返回数据示例

## 🛠️ 技术栈

### 核心技术

| 类别     | 技术选型                    | 版本号      | 说明                     |
|--------|-------------------------|----------|------------------------|
| 编程语言   | Kotlin                   | 2.2.0    | 100% Kotlin 开发         |
| UI 框架  | Jetpack Compose          | 2025.08.00 | 声明式 UI 框架             |
| 架构模式   | MVVM + Clean Architecture | -        | MVVM + Clean 架构        |
| 依赖注入   | Hilt                     | 2.57     | 基于 Dagger 的依赖注入框架     |
| 异步处理   | Coroutines + Flow        | 1.9.0    | 协程 + Flow 响应式编程       |

### 功能模块

| 类别     | 技术选型                    | 版本号      | 说明           |
|--------|-------------------------|----------|--------------|
| 导航     | Navigation Compose       | 2.9.3    | Compose 导航组件 |
| 数据序列化  | Kotlinx Serialization   | 1.9.0    | JSON 序列化处理   |
| 网络请求   | Retrofit + OkHttp        | 3.0.0 + 5.1.0 | HTTP 客户端     |
| 图片加载   | Coil Compose             | 2.7.0    | 图片加载与缓存      |
| 动画效果   | Lottie Compose           | 6.6.7    | After Effects 动画 |
| 权限管理   | XXPermissions            | 23.0     | 动态权限申请       |

### 数据存储

| 类别     | 技术选型    | 版本号    | 说明         |
|--------|---------|--------|------------|
| 数据库    | Room    | 2.7.2  | SQLite 数据库 |
| 本地存储   | MMKV    | 2.2.2  | 高性能键值存储    |

### 开发工具

| 类别     | 技术选型       | 版本号    | 说明        |
|--------|------------|--------|-----------|
| 日志框架   | Timber     | 5.0.1  | 日志管理      |
| 网络调试   | Chucker    | 4.2.0  | 网络请求监控    |
| 内存检测   | LeakCanary | 2.14   | 内存泄漏检测    |
| 测试框架   | JUnit      | 4.13.2 | 单元测试 + UI测试 |

## 📚 资源与参考

- **资源说明**: 项目中的部分素材来自网络，仅用于学习交流
- **图标来源**: 项目使用的图标库来自[图鸟 Icon](https://github.com/tuniaoTech)

## ✨ 项目特点

- 采用模块化架构设计，各功能模块高度解耦
- 使用 Jetpack Compose 构建现代化 UI
- 遵循 Material Design 3 设计规范
- 支持浅色/深色主题切换
- 支持中英文语言切换
- 采用响应式编程范式
- 完整的测试覆盖 `计划中`
- 大屏适配（平板/折叠屏）`计划中`

## 📱 功能模块目录

> **状态说明：**
> - `已完成` - 功能页面已完整实现并可以正常使用
> - `待完善` - 功能页面基本实现，但还需要进一步优化和完善
> - `待优化` - 功能页面已实现，但需要性能优化或体验优化
> - `仅页面` - 只完成了页面UI，功能逻辑尚未实现
> - `待开发` - 功能页面尚未开发，陆续实现中

- **主模块 (main)**
    - 首页 (home) `待完善`
    - 分类 (category) `已完成`
    - 购物车 (cart) `已完成`
    - 我的 (me) `待完善`

  
- **认证模块 (auth)**
    - 登录主页 (login) `已完成`
    - 账号密码登录 (account-login) `已完成`
    - 注册页面 (register) `已完成`
    - 找回密码 (reset-password) `仅页面`
    - 短信登录 (sms-login) `已完成`


- **用户体系模块 (user)**
    - 个人中心 (profile) `仅页面`
    - 设置模块 (settings) `待开发`
    - 收货地址列表 (address-list) `已完成`
    - 收货地址详情 (address-detail) `已完成`
    - 用户足迹 (footprint) `已完成`


- **订单模块 (order)**
    - 订单列表 (list) `已完成`
    - 确认订单 (confirm) `待完善`
    - 订单详情 (detail) `待完善`
    - 订单支付 (pay) `已完成`
    - 退款申请 (refund) `待开发`
    - 订单评价 (comment) `待开发`
    - 订单物流 (logistics) `待开发`


- **商品模块 (goods)**
    - 商品搜索 (search) `已完成`
    - 商品详情 (detail) `待完善`
    - 商品评价 (comment) `待开发`
    - 商品分类页面 (category) `已完成`


- **营销模块 (market)**
    - 优惠券管理 (coupon) `待优化`


- **客服模块 (cs)**
    - 客服聊天 (chat) `待优化`


- **反馈系统 (feedback)**
    - 投诉子模块 (complain) `待开发`
    - 反馈子模块 (feedback) `待开发`


- **通用模块 (common)**
    - 关于我们 (about) `仅页面`
    - WebView 页面 (web) `已完成`


- **启动流程模块 (launch)**
    - 启动页 (splash) `待开发`
    - 引导页 (guide) `待开发`

## 项目结构

```
├── app/                   # 应用入口模块
├── build-logic/          # 构建逻辑
├── core/                 # 核心模块
│   ├── common/           # 通用工具和扩展
│   ├── data/             # 数据层
│   ├── database/         # 数据库
│   ├── datastore/        # 数据存储
│   ├── designsystem/     # 设计系统
│   ├── model/            # 数据模型
│   ├── network/          # 网络层
│   ├── result/           # 结果处理
│   ├── ui/               # UI组件
│   └── util/             # 工具类
├── feature/              # 功能模块
│   ├── auth/             # 认证模块
│   ├── common/           # 公共模块
│   ├── goods/            # 商品模块
│   ├── launch/           # 启动模块
│   ├── main/             # 主模块
│   ├── market/           # 营销模块
│   ├── order/            # 订单模块
│   └── user/             # 用户模块
└── navigation/           # 导航模块
```

## 🚀 开发计划

这是一个纯粹由个人热情驱动的开源项目。作为一名全职开发者，我只能在工作之余的时间来维护它，每一行代码都凝聚着我下班后和周末的心血。尽管时间有限，我仍然希望通过这个项目创建一个完整的电商学习案例，它更适合作为学习参考而非商业应用，因为某些方面还未达到商业级水准。我的目标是为其他开发者提供一个学习现代
Android 开发技术的实践平台。

由于时间和精力的限制，项目的更新节奏可能不会很快，但我会坚持长期投入，一步一步地完善每个功能模块。如果你有兴趣参与贡献，无论是代码、设计还是文档方面，都将非常欢迎！

### 📱 Android 版本（当前）

- **技术栈**: Kotlin + Jetpack Compose + MVVM
- **架构特点**: 模块化设计 + Clean Architecture

### 🌟 鸿蒙版本（计划中）

- **技术栈**: ArkTS + ArkUI + MVVM
- **架构特点**: 模块化设计 + 原子化服务

### 🍎 iOS 版本（计划中）

- **技术栈**: Swift + SwiftUI + MVVM
- **架构特点**: 模块化设计 + 组件化开发

## 💡 开发理念

- **循序渐进**: 采用迭代式开发方式，每次专注于一个小功能点的完善
- **开放学习**: 及时分享开发过程中的经验和心得，帮助其他开发者学习
- **持续改进**: 根据实际使用反馈不断优化架构和代码设计

## 🎯 当前开发重点

项目的核心功能模块（用户认证、商品展示、购物车、订单支付等）已基本完成，整体业务流程已经跑通。当前开发重点已转向：

1. **细节打磨**: 优化用户体验，完善交互细节和视觉效果
2. **功能完善**: 补充和优化现有功能模块的边缘场景处理
3. **性能优化**: 提升应用性能，优化内存使用和响应速度
4. **代码质量**: 重构部分代码，提高代码可维护性和可读性

## 🤝 参与贡献

欢迎感兴趣的开发者参与项目开发，无论是提交 Issue、Pull Request 还是优化文档，都可以帮助项目变得更好！