package com.joker.coolmall.core.ui.component.refresh

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.widget.NestedScrollView
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

/**
 * 支持下拉刷新和上拉加载更多的布局组件
 *
 * 该组件基于SmartRefreshLayout封装，支持下拉刷新和上拉加载更多功能，
 * 可以与任何Compose布局组合使用。
 *
 * 主要功能：
 * 1. 下拉刷新 - 用户可以通过向下拉动页面触发刷新操作
 * 2. 上拉加载更多 - 用户滑动到底部时可以触发加载更多数据
 * 3. 嵌套滚动支持 - 使用NestedScrollView确保内容可以流畅滚动
 * 4. 自定义刷新状态 - 可以自定义刷新和加载的视觉反馈
 *
 * 使用方法：
 * ```
 * RefreshLayout(
 *     onRefresh = { finishRefresh ->
 *         // 在这里执行刷新操作
 *         loadData { success ->
 *             // 完成后调用finishRefresh
 *             finishRefresh(success)
 *         }
 *     },
 *     onLoadMore = { finishLoadMore ->
 *         // 在这里执行加载更多操作
 *         loadMoreData { success, hasMore ->
 *             // 完成后调用finishLoadMore
 *             finishLoadMore(success, hasMore)
 *         }
 *     }
 * ) {
 *     // 在这里放置你的内容
 *     YourContent()
 * }
 * ```
 *
 * @param modifier 应用于外层容器的修饰符
 * @param enableRefresh 是否启用下拉刷新功能，默认为true
 * @param enableLoadMore 是否启用上拉加载更多功能，默认为true
 * @param onRefresh 下拉刷新的回调函数，接收一个函数参数用于完成刷新操作
 *                 参数是(Boolean) -> Unit类型，传入true表示刷新成功，false表示失败
 * @param onLoadMore 上拉加载更多的回调函数，接收一个函数参数用于完成加载更多操作
 *                  参数是(Boolean, Boolean) -> Unit类型，第一个Boolean表示加载是否成功，
 *                  第二个Boolean表示是否还有更多数据
 * @param content 内容区域的Composable函数，这里放置你想要显示的主要内容
 */
@Composable
fun RefreshLayout(
    modifier: Modifier = Modifier,
    enableRefresh: Boolean = true,
    enableLoadMore: Boolean = true,
    onRefresh: (finishRefresh: (Boolean) -> Unit) -> Unit = { it(true) },
    onLoadMore: (finishLoadMore: (Boolean, Boolean) -> Unit) -> Unit = { it(true, true) },
    content: @Composable () -> Unit
) {
    // 使用AndroidView将Android原生视图包装到Compose中
    // 这允许我们在Compose中使用SmartRefreshLayout，这是一个Android原生库
    AndroidView(
        factory = { ctx ->
            // 创建SmartRefreshLayout实例
            // SmartRefreshLayout是一个强大的下拉刷新和上拉加载组件
            val refreshLayout = SmartRefreshLayout(ctx).apply {
                // 设置刷新头部，ClassicsHeader提供了经典的下拉刷新动画和文字提示
                setRefreshHeader(ClassicsHeader(ctx))
                
                // 设置加载底部，ClassicsFooter提供了经典的上拉加载动画和文字提示
                setRefreshFooter(ClassicsFooter(ctx))
                
                // 配置是否启用下拉刷新功能
                setEnableRefresh(enableRefresh)
                
                // 配置是否启用上拉加载更多功能
                setEnableLoadMore(enableLoadMore)
                
                // 设置主色为透明，这样不会影响背景色
                setPrimaryColors(android.graphics.Color.TRANSPARENT)

                // 禁用自动加载更多，由我们的滚动监听器控制
                // 这样可以更精确地控制何时触发加载更多
                setEnableAutoLoadMore(false)

                // 设置下拉刷新的监听器
                setOnRefreshListener(object : OnRefreshListener {
                    override fun onRefresh(refreshLayout: RefreshLayout) {
                        // 当用户下拉刷新时，调用onRefresh回调
                        // 并提供一个函数用于完成刷新操作
                        onRefresh { success ->
                            // 完成刷新动作，传入参数：
                            // 0: 延迟毫秒数，0表示立即结束
                            // success: 刷新是否成功
                            // false: 是否没有更多数据，这里在刷新操作中不相关
                            refreshLayout.finishRefresh(0, success, false)
                        }
                    }
                })

                // 设置上拉加载更多的监听器
                setOnLoadMoreListener(object : OnLoadMoreListener {
                    override fun onLoadMore(refreshLayout: RefreshLayout) {
                        // 当触发加载更多时，调用onLoadMore回调
                        // 并提供一个函数用于完成加载更多操作
                        onLoadMore { success, hasMoreData ->
                            // 根据是否有更多数据选择不同的结束方式
                            if (hasMoreData) {
                                // 有更多数据，正常结束加载更多
                                // 参数1: 0毫秒延迟，立即结束
                                // 参数2: 加载是否成功
                                // 参数3: 没有更多数据为false
                                refreshLayout.finishLoadMore(0, success, false)
                            } else {
                                // 没有更多数据，显示无更多数据状态
                                refreshLayout.finishLoadMoreWithNoMoreData()
                            }
                        }
                    }
                })
            }

            // 创建NestedScrollView作为内容容器
            // NestedScrollView允许我们在多层滚动视图中正确处理滚动事件
            val nestedScrollView = NestedScrollView(ctx).apply {
                // 启用平滑滚动，使滚动更加流畅
                isSmoothScrollingEnabled = true
                
                // 添加滚动变化监听器，用于检测何时滚动到底部
                setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                    // 检测是否滚动到了底部
                    // v.getChildAt(0).measuredHeight: 内容的总高度
                    // v.measuredHeight: 可见区域的高度
                    // scrollY: 已经滚动的距离
                    // 当scrollY + 可见区域高度 >= 内容总高度时，表示滚动到了底部
                    if (!refreshLayout.isLoading && scrollY > 0 &&
                        (v.getChildAt(0).measuredHeight - v.measuredHeight) <= scrollY
                    ) {
                        // 触发加载更多操作
                        refreshLayout.autoLoadMore()
                    }
                })
            }

            // 创建ComposeView用于在Android View中渲染Compose内容
            val composeView = ComposeView(ctx).apply {
                // 设置Compose内容
                setContent {
                    // 直接渲染传入的content内容
                    content()
                }
            }

            // 构建视图层次结构
            // 先将ComposeView添加到NestedScrollView中
            nestedScrollView.addView(
                composeView, 
                // 设置ComposeView填满整个父容器
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )

            // 再将NestedScrollView添加到SmartRefreshLayout中
            refreshLayout.addView(
                nestedScrollView, 
                // 设置NestedScrollView填满整个父容器
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )

            // 返回最外层的SmartRefreshLayout作为AndroidView的根视图
            refreshLayout
        },
        // 应用传入的modifier到AndroidView
        modifier = modifier,
        // update回调用于处理视图更新
        update = { layout ->
            // 当组件重组时，更新刷新布局的配置
            // 这确保enableRefresh和enableLoadMore属性的变化能够立即生效
            layout.setEnableRefresh(enableRefresh)
            layout.setEnableLoadMore(enableLoadMore)
        }
    )
} 