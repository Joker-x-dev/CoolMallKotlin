<div align="center">

<img src="docs/images/graphs/logo.svg" width="120" alt="青商城Logo"/>

# 青商城

_🛍️ 基于 Kotlin 和 Jetpack Compose 的现代化电商应用_

<!-- 语言切换按钮 -->
<div align="center">
  <a href="README_EN.md">🌍 English</a>
</div>

[![GitHub](https://img.shields.io/badge/GitHub-CoolMallKotlin-blue?style=flat-square&logo=github)](https://github.com/Joker-x-dev/CoolMallKotlin)
[![Gitee](https://img.shields.io/badge/Gitee-CoolMallKotlin-red?style=flat-square&logo=gitee)](https://gitee.com/Joker-x-dev/CoolMallKotlin)
[![Demo](https://img.shields.io/badge/Demo-蒲公英下载-green?style=flat-square&logo=android)](https://www.pgyer.com/CoolMallKotlinProdRelease)
[![API](https://img.shields.io/badge/API-文档-orange?style=flat-square&logo=postman)](https://coolmall.apifox.cn)
[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/Joker-x-dev/CoolMallKotlin)

</div>

## 📖 项目简介

这是一个基于 Kotlin 和 Jetpack Compose 打造的开源电商学习项目，核心功能已基本完成。项目采用了 Google
推荐的应用架构和最佳实践，参考了 [Now in Android](https://github.com/android/nowinandroid)
的架构设计，旨在展示如何运用现代 Android
开发技术构建一个完整的电商应用。项目具备完整的电商业务流程，包括用户认证、商品展示、购物车、订单支付等核心功能，适合开发者学习参考现代
Android 开发技术。

作为热爱技术的个人开发者，我将工作之外半年多的时间都投入到这个项目中。每一个功能的实现、每一次代码的优化，都是我在闲暇时间精心打磨的成果。尽管进度可能不如专职团队那么快，而且某些功能的实现还不够完善，但我会持续改进，不断完善。如果你对
Android 开发感兴趣，无论是学习还是共同创造，都欢迎加入。

> 如果项目对您有帮助，请给个 Star 支持 ⭐ 这对我来说很重要，能给我带来长期更新维护的动力！

## 📱 项目预览

> 💡 **说明**：由于功能模块较多，截图不下，这里仅展示部分界面，可下载体验完整功能。

<table>
  <tr>
    <td><img src="docs/images/gif/1.gif" alt="青商城动画演示1"/></td>
    <td><img src="docs/images/gif/2.gif" alt="青商城动画演示2"/></td>
    <td><img src="docs/images/gif/3.gif" alt="青商城动画演示3"/></td>
    <td><img src="docs/images/gif/4.gif" alt="青商城动画演示4"/></td>
    <td><img src="docs/images/gif/5.gif" alt="青商城动画演示5"/></td>
  </tr>
  <tr>
    <td><img src="docs/images/gif/6.gif" alt="青商城动画演示6"/></td>
    <td><img src="docs/images/gif/7.gif" alt="青商城动画演示7"/></td>
    <td><img src="docs/images/gif/8.gif" alt="青商城动画演示8"/></td>
    <td><img src="docs/images/gif/9.gif" alt="青商城动画演示9"/></td>
    <td><img src="docs/images/gif/10.gif" alt="青商城动画演示10"/></td>
  </tr>
</table>

### ☀️ 浅色模式

<img src="docs/images/preview/Light 1.png" alt="青商城应用浅色模式预览1"/>
<img src="docs/images/preview/Light 2.png" alt="青商城应用浅色模式预览2"/>
<img src="docs/images/preview/Light 3.png" alt="青商城应用浅色模式预览3"/>
<img src="docs/images/preview/Light 4.png" alt="青商城应用浅色模式预览4"/>
<img src="docs/images/preview/Light 5.png" alt="青商城应用浅色模式预览5"/>

### 🌙 深色模式

<img src="docs/images/preview/Dark 1.png" alt="青商城应用深色模式预览1"/>
<img src="docs/images/preview/Dark 2.png" alt="青商城应用深色模式预览2"/>
<img src="docs/images/preview/Dark 3.png" alt="青商城应用深色模式预览3"/>
<img src="docs/images/preview/Dark 4.png" alt="青商城应用深色模式预览4"/>
<img src="docs/images/preview/Dark 5.png" alt="青商城应用深色模式预览5"/>

### 🌈 主题

<img src="docs/images/preview/Theme.png" alt="青商城应用主题预览"/>

### 🌐 国际化

<img src="docs/images/preview/i18n.png" alt="青商城应用国际化预览"/>

### 📍 项目地址

- **GitHub 地址**：[https://github.com/Joker-x-dev/CoolMallKotlin](https://github.com/Joker-x-dev/CoolMallKotlin)
- **Gitee 地址**：[https://gitee.com/Joker-x-dev/CoolMallKotlin](https://gitee.com/Joker-x-dev/CoolMallKotlin)

### Demo 下载

> 🌟 **提示**：客服聊天和意见反馈功能均为真实可用的，大家给我的留言我每一条都会去看，也会回复，所以不要说我是人机了....

- **Release 版本（推荐 4MB）**：[点击下载体验](https://www.pgyer.com/CoolMallKotlinProdRelease)
    - 为日常使用和体验优化的稳定版本，具有最佳性能和体积。


- **Debug 版本（开发者 19MB）**：[点击下载体验](https://www.pgyer.com/CoolMallKotlinDebug)
    - **注意**：Debug 版本的包名带有 `.debug` 后缀，与 Release 版本不同，可以共存安装。
    - **内置工具**：集成了 LeakCanary（内存泄漏检测）和 Chucker（网络请求监控）等调试工具。
    - **性能与体积**：由于开启了调试功能且未进行代码压缩，此版本性能低于 Release 版本，且 APK 体积更大。
    - **快捷访问**：长按桌面图标可快速访问 `Leaks`（内存泄漏）和 `Open Chucker`（网络监控）。为确保能接收到
      Chucker 的实时网络请求通知，建议开启应用的通知权限。


- **支持系统**：Android 6.0 及以上
- **更新说明**：预览版本会不定时更新，可能不会完全同步最新的代码变更

### 相关文档

- **青商城 API 接口文档**：[在线查看](https://coolmall.apifox.cn)
  - 接口文档会随着项目开发进度同步更新，主要包含各接口的请求参数和返回数据示例


- **脚手架文档**：[在线查看](https://compose.dusksnow.top)
  - 很多同学希望在青商城的基础上二次开发或基于相同技术栈快速启动业务，我额外准备了一个精简的单模块 Jetpack Compose 空脚手架「AndroidProject-Compose」，并配套了详细文档方便学习。
  - 它只保留基础能力与示例路由，拉仓库即可运行并按需填充业务逻辑，也便于学习架构拆分与模块化落地；青商城的大部分基础能力与文档说明在此同样适用，便于快速上手项目。

## 🛠️ 技术栈

### 核心技术

| 类别    | 技术选型                      | 版本号        | 说明                |
|-------|---------------------------|------------|-------------------|
| 编程语言  | Kotlin                    | 2.2.21     | 100% Kotlin 开发    |
| UI 框架 | Jetpack Compose           | 2025.11.01 | 声明式 UI 框架         |
| 架构模式  | MVVM + Clean Architecture | -          | MVVM + Clean 架构   |
| 依赖注入  | Hilt                      | 2.57.2     | 基于 Dagger 的依赖注入框架 |
| 异步处理  | Coroutines + Flow         | 1.9.0      | 协程 + Flow 响应式编程   |

### 功能模块

| 类别    | 技术选型                  | 版本号           | 说明               |
|-------|-----------------------|---------------|------------------|
| 导航    | Navigation Compose    | 2.9.6         | Compose 导航组件     |
| 数据序列化 | Kotlinx Serialization | 1.9.0         | JSON 序列化处理       |
| 网络请求  | Retrofit + OkHttp     | 3.0.0 + 5.3.2 | HTTP 客户端         |
| 图片加载  | Coil Compose          | 2.7.0         | 图片加载与缓存          |
| 动画效果  | Lottie Compose        | 6.7.1         | After Effects 动画 |
| 权限管理  | XXPermissions         | 28.0          | 动态权限申请           |

### 数据存储

| 类别   | 技术选型 | 版本号   | 说明         |
|------|------|-------|------------|
| 数据库  | Room | 2.8.4 | SQLite 数据库 |
| 本地存储 | MMKV | 2.2.4 | 高性能键值存储    |

### 开发工具

| 类别   | 技术选型          | 版本号    | 说明          |
|------|---------------|--------|-------------|
| 日志框架 | Timber        | 5.0.1  | 日志管理        |
| 网络调试 | Chucker       | 4.2.0  | 网络请求监控      |
| 内存检测 | LeakCanary    | 2.14   | 内存泄漏检测      |
| 权限管理 | XXPermissions | 26.5   | 动态权限申请      |
| 测试框架 | JUnit         | 4.13.2 | 单元测试 + UI测试 |

## 📚 资源与参考

- **资源说明**: 项目中的部分素材来自网络，仅用于学习交流
- **图标来源**: 项目使用的图标库来自[图鸟 Icon](https://github.com/tuniaoTech)

## ✨ 项目特点

- **现代化架构**: 采用模块化设计 + Clean Architecture，各功能模块高度解耦
- **声明式 UI**: 使用 Jetpack Compose 构建现代化用户界面
- **设计规范**: 严格遵循 Material Design 3 设计规范和最佳实践
- **主题适配**: 完整支持浅色/深色主题动态切换
- **国际化**: 支持中英文语言切换，便于全球化推广
- **响应式编程**: 基于 Coroutines + Flow 的响应式编程范式
- **完整的测试覆盖** `计划中`
- **大屏适配（平板/折叠屏）** `计划中`

## 📱 功能模块目录

> **状态说明：**
> - `已完成` - 功能页面已完整实现并可以正常使用
> - `待完善` - 功能页面基本实现，但还需要进一步优化和完善
> - `待优化` - 功能页面已实现，但需要性能优化或体验优化
> - `仅页面` - 只完成了页面UI，功能逻辑尚未实现
> - `待开发` - 功能页面尚未开发，陆续实现中

- **主模块 (main)**
    - 首页 (home) `已完成`
    - 分类 (category) `已完成`
    - 购物车 (cart) `已完成`
    - 我的 (me) `已完成`


- **认证模块 (auth)**
    - 登录主页 (login) `已完成`
    - 账号密码登录 (account-login) `已完成`
    - 注册页面 (register) `已完成`
    - 找回密码 (reset-password) `仅页面`
    - 短信登录 (sms-login) `已完成`


- **用户体系模块 (user)**
    - 个人中心 (profile) `仅页面`
    - 收货地址列表 (address-list) `已完成`
    - 收货地址详情 (address-detail) `已完成`
    - 用户足迹 (footprint) `已完成`


- **订单模块 (order)**
    - 订单列表 (list) `已完成`
    - 确认订单 (confirm) `已完成`
    - 订单详情 (detail) `已完成`
    - 订单支付 (pay) `已完成`
    - 退款申请 (refund) `已完成`
    - 订单评价 (comment) `已完成`
    - 订单物流 (logistics) `已完成`


- **商品模块 (goods)**
    - 商品搜索 (search) `已完成`
    - 商品详情 (detail) `已完成`
    - 商品评价 (comment) `已完成`
    - 商品分类页面 (category) `已完成`


- **营销模块 (market)**
    - 优惠券管理 (coupon) `已完成`


- **客服模块 (cs)**
    - 客服聊天 (chat) `待完善`


- **反馈模块 (feedback)**
    - 反馈列表 (list) `已完成`
    - 提交反馈 (submit) `已完成`


- **通用模块 (common)**
    - 关于我们 (about) `仅页面`
    - WebView 页面 (web) `已完成`
    - 应用设置 (settings) `仅页面`
    - 用户协议 (user-agreement) `已完成`
    - 隐私政策 (privacy-policy) `已完成`
    - 贡献者列表 (contributors) `已完成`


- **启动流程模块 (launch)**
    - 启动页 (splash) `已完成`
    - 引导页 (guide) `已完成`

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
│   ├── cs/               # 客服模块
│   ├── feedback/         # 反馈模块
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
- **质量优先**: 注重代码质量和用户体验，而非功能数量的堆砌
- **社区驱动**: 欢迎开发者参与贡献，共同打造优质的学习项目

## 🎯 当前开发重点

项目的核心电商业务流程已全面完成并稳定运行，包括用户认证、商品展示、购物车、订单支付等主要功能模块。当前开发重点已从功能实现转向质量提升和生态完善：

### 🔧 近期重点

1. **辅助功能完善**: 完成用户资料编辑、应用设置等辅助功能模块
2. **用户体验优化**: 细化交互动效，完善视觉反馈，提升整体使用体验
3. **性能深度优化**: 内存使用优化、启动速度提升、页面渲染性能调优

### 🚀 中长期规划

4. **代码质量提升**: 重构历史代码，提高可维护性，完善单元测试覆盖
5. **多端生态扩展**: 推进鸿蒙版本和 iOS 版本的开发计划
6. **社区建设**: 完善开发文档，建立贡献者指南，促进开源社区发展


## 👥 加入群聊

欢迎加入青商城开发者交流群，一起分享学习心得，讨论技术问题！

<div align="left">
  <img src="docs/images/group/qq.jpg" width="200" alt="QQ群二维码"/>
  <p>扫码或搜索群号加入 QQ 群</p>
</div>

## 🤝 参与贡献

这是一个开放的学习项目，欢迎所有对 Android 开发感兴趣的开发者参与贡献！

### 🎯 贡献方式

- **代码贡献**: 提交 Pull Request，完善功能实现或修复问题
- **问题反馈**: 通过 Issue 报告 Bug 或提出功能建议
- **文档优化**: 完善项目文档、添加使用说明或开发指南
- **设计支持**: 提供 UI/UX 设计建议或素材资源
- **测试协助**: 参与功能测试，提供使用反馈和改进建议

### 📋 贡献指南

- 提交代码前请确保遵循项目的编码规范
- 新功能开发建议先创建 Issue 讨论可行性
- 欢迎分享学习心得和技术总结

让我们一起打造一个优质的 Android 学习项目！ 🚀
