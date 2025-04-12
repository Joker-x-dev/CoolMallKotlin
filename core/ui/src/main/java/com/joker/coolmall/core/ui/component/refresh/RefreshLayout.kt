package com.joker.coolmall.core.ui.component.refresh

import android.os.Bundle
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

// 滚动状态的保存键
private const val KEY_SCROLL_POSITION = "scroll_position"

// 触发加载更多的底部边距（像素）
private const val LOAD_MORE_THRESHOLD = 100

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
 * 4. 滚动状态保存 - 保存滚动位置，在配置更改或页面切换时恢复
 * 5. 自定义刷新状态 - 可以自定义刷新和加载的视觉反馈
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
 *     // 在这里放置你的内容，如Column
 *     Column {
 *         items.forEach { item ->
 *             ItemView(item)
 *         }
 *     }
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
    // 使用rememberSaveable保存滚动位置，在配置更改时恢复
    val scrollState = rememberSaveable { mutableStateOf<Bundle?>(null) }
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    // 使用AndroidView将Android原生视图包装到Compose中
    AndroidView(
        factory = { ctx ->
            // 创建并配置SmartRefreshLayout实例
            val refreshLayout = createRefreshLayout(
                ctx = ctx,
                enableRefresh = enableRefresh,
                enableLoadMore = enableLoadMore,
                onRefresh = onRefresh,
                onLoadMore = onLoadMore
            )

            // 创建并配置NestedScrollView作为内容容器
            val nestedScrollView = createNestedScrollView(
                ctx = ctx,
                refreshLayout = refreshLayout,
                scrollState = scrollState,
                content = content
            )

            // 构建视图层次结构并返回刷新布局
            refreshLayout.addView(
                nestedScrollView,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            refreshLayout
        },
        modifier = modifier,
        update = { layout ->
            // 更新刷新布局配置
            layout.setEnableRefresh(enableRefresh)
            layout.setEnableLoadMore(enableLoadMore)

            // 恢复滚动位置
            restoreScrollPosition(layout, scrollState)
        }
    )

    // 监听生命周期事件
    ObserveLifecycle(lifecycle)
}

/**
 * 创建并配置SmartRefreshLayout实例
 */
private fun createRefreshLayout(
    ctx: android.content.Context,
    enableRefresh: Boolean,
    enableLoadMore: Boolean,
    onRefresh: (finishRefresh: (Boolean) -> Unit) -> Unit,
    onLoadMore: (finishLoadMore: (Boolean, Boolean) -> Unit) -> Unit
): SmartRefreshLayout {
    return SmartRefreshLayout(ctx).apply {
        // 启用嵌套滚动支持
        setEnableNestedScroll(true)

        // 配置刷新头部，使用跟随内容的风格
        setRefreshHeader(ClassicsHeader(ctx).apply {
            spinnerStyle = SpinnerStyle.Translate
        })

        // 配置加载底部，使用跟随内容的风格
        setRefreshFooter(ClassicsFooter(ctx).apply {
            spinnerStyle = SpinnerStyle.Translate
        })

        // 配置刷新和加载功能
        setEnableRefresh(enableRefresh)
        setEnableLoadMore(enableLoadMore)

        // 优化滚动体验配置
        // 启用越界拖拽效果
        setEnableOverScrollDrag(true)
        // 启用越界拦截
        setEnableOverScrollBounce(true)
        // 内容不满一页时仍可上拉加载
        setEnableLoadMoreWhenContentNotFull(true)
        // 纯净滚动模式
        setEnablePureScrollMode(false)

        // 设置主色为透明
        setPrimaryColors(android.graphics.Color.TRANSPARENT)

        // 设置下拉刷新监听器
        setOnRefreshListener(object : OnRefreshListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                onRefresh { success ->
                    refreshLayout.finishRefresh(0, success, false)
                }
            }
        })

        // 设置上拉加载更多监听器
        setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                onLoadMore { success, hasMoreData ->
                    if (hasMoreData) {
                        refreshLayout.finishLoadMore(0, success, false)
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData()
                    }
                }
            }
        })
    }
}

/**
 * 创建并配置NestedScrollView作为内容容器
 */
private fun createNestedScrollView(
    ctx: android.content.Context,
    refreshLayout: SmartRefreshLayout,
    scrollState: androidx.compose.runtime.MutableState<Bundle?>,
    content: @Composable () -> Unit
): NestedScrollView {
    // 创建嵌套滚动视图
    val nestedScrollView = NestedScrollView(ctx).apply {
        // 启用平滑滚动
        isSmoothScrollingEnabled = true

        // 确保可以垂直滚动
        isVerticalScrollBarEnabled = true

        // 恢复保存的滚动位置
        scrollState.value?.let { bundle ->
            val position = bundle.getInt(KEY_SCROLL_POSITION, 0)
            post {
                scrollTo(0, position)
            }
        }

        // 添加滚动变化监听器
        setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            // 保存当前滚动位置
            val bundle = Bundle().apply {
                putInt(KEY_SCROLL_POSITION, scrollY)
            }
            scrollState.value = bundle

            // 检测是否滚动到底部，以触发加载更多
            checkAndTriggerLoadMore(this, scrollY, refreshLayout)
        })
    }

    // 创建ComposeView用于渲染Compose内容
    val composeView = ComposeView(ctx).apply {
        setContent {
            content()
        }
    }

    // 将ComposeView添加到NestedScrollView
    nestedScrollView.addView(
        composeView,
        ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    )

    return nestedScrollView
}

/**
 * 检测是否滚动到底部，以触发加载更多
 */
private fun checkAndTriggerLoadMore(
    scrollView: NestedScrollView,
    scrollY: Int,
    refreshLayout: SmartRefreshLayout
) {
    val childHeight = scrollView.getChildAt(0)?.measuredHeight ?: 0
    val viewHeight = scrollView.measuredHeight

    // 当滚动到距离底部LOAD_MORE_THRESHOLD像素时，触发加载更多
    if (!refreshLayout.isLoading && scrollY > 0 &&
        childHeight > 0 && viewHeight > 0 &&
        scrollY + viewHeight >= childHeight - LOAD_MORE_THRESHOLD
    ) {
        refreshLayout.autoLoadMore()
    }
}

/**
 * 恢复滚动位置
 */
private fun restoreScrollPosition(
    layout: SmartRefreshLayout,
    scrollState: androidx.compose.runtime.MutableState<Bundle?>
) {
    val nestedScrollView = layout.getChildAt(0) as? NestedScrollView
    scrollState.value?.let { bundle ->
        nestedScrollView?.let { scrollView ->
            val position = bundle.getInt(KEY_SCROLL_POSITION, 0)
            scrollView.post {
                scrollView.scrollTo(0, position)
            }
        }
    }
}

/**
 * 监听生命周期事件
 */
@Composable
private fun ObserveLifecycle(lifecycle: Lifecycle) {
    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    // 应用恢复前台时可添加额外逻辑
                }

                else -> {
                    // 其他生命周期事件不需特殊处理
                }
            }
        }

        // 添加观察者
        lifecycle.addObserver(observer)

        // 清理
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
} 