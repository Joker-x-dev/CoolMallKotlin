package com.joker.coolmall.navigation

/**
 * 通用的页面刷新结果 Key。
 *
 * 使用公共的 [RefreshResult] 作为返回类型：
 * - refresh = true 表示上一个页面需要刷新数据
 * - refresh = false 或 null 表示不刷新
 *
 * 示例：
 * ```kotlin
 * // 子页面：操作成功后返回并通知上一个页面刷新
 * popBackStackWithResult(RefreshResultKey, RefreshResult(refresh = true))
 *
 * // 上一个页面（ViewModel）：
 * fun observeRefresh() {
 *     observeRefreshState(RefreshResultKey)
 * }
 * ```
 *
 * @author Joker.X
 */
object RefreshResultKey : NavigationResultKey<RefreshResult>
