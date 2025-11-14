package com.joker.coolmall.navigation

/**
 * 通用的页面刷新结果 Key。
 *
 * 语义等价于以前的 "refresh" 布尔标记：
 * - true 表示上一个页面需要刷新数据
 * - false 或 null 表示不刷新
 *
 * 示例：
 * ```kotlin
 * // 子页面：操作成功后返回并通知上一个页面刷新
 * popBackStackWithResult(RefreshResultKey, true)
 *
 * // 上一个页面（ViewModel）：
 * fun observeRefresh(backStackEntry: NavBackStackEntry?) {
 *     observeRefreshState(backStackEntry, RefreshResultKey)
 * }
 * ```
 *
 * @author Joker.X
 */
object RefreshResultKey : NavigationResultKey<Boolean>
