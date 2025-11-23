package com.joker.coolmall.core.ui.component.refresh

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.tween
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.MutatorMutex
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * 记住刷新状态
 *
 * @param coroutineScope 协程作用域
 * @return [RefreshState] 实例
 * @author Joker.X
 */
@Composable
fun rememberRefreshState(
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): RefreshState {
    return remember {
        RefreshState(coroutineScope)
    }
}

/**
 * 刷新状态管理类
 *
 * 负责管理下拉刷新的状态、动画和嵌套滚动逻辑。
 *
 * @param coroutineScope 协程作用域，用于执行动画和状态更新
 * @author Joker.X
 */
@Stable
class RefreshState(private val coroutineScope: CoroutineScope) {
    /**
     * 刷新头部的触发高度阈值
     */
    var headerHeight = 0f

    /**
     * 是否启用刷新
     */
    var enableRefresh = true

    /**
     * 是否正在刷新中
     */
    var isRefreshing by mutableStateOf(false)

    /**
     * 是否正在结束刷新（显示"刷新完成"状态）
     */
    var isFinishing by mutableStateOf(false)

    /**
     * 动画是否结束
     */
    var animateIsOver by mutableStateOf(true)

    /**
     * 内部使用的动画状态，用于控制指示器的偏移量
     */
    private val _indicatorOffset = Animatable(0f)

    /**
     * 互斥锁，用于防止多个动画或手势冲突
     */
    private val mutatorMutex = MutatorMutex()

    /**
     * 当前指示器的偏移量（下拉距离）
     */
    val indicatorOffset: Float
        get() = _indicatorOffset.value

    /**
     * 判断是否处于加载或动画状态
     */
    fun isLoading() = !animateIsOver || isRefreshing

    /**
     * 动画滚动到指定偏移量
     *
     * @param offset 目标偏移量
     * @param durationMillis 动画时长
     * @author Joker.X
     */
    suspend fun animateOffsetTo(
        offset: Float,
        durationMillis: Int = AnimationConstants.DefaultDurationMillis
    ) {
        mutatorMutex.mutate {
            _indicatorOffset.animateTo(offset, animationSpec = tween(durationMillis)) {
                if (this.value == 0f) {
                    animateIsOver = true
                }
            }
        }
    }

    /**
     * 立即跳转到指定偏移量
     *
     * @param offset 目标偏移量
     * @author Joker.X
     */
    suspend fun snapOffsetTo(offset: Float) {
        mutatorMutex.mutate(MutatePriority.UserInput) {
            _indicatorOffset.snapTo(offset)
        }
    }

    /**
     * 消费滚动距离，更新偏移量
     *
     * @param needConsumedY 需要消费的Y轴距离
     * @author Joker.X
     */
    private fun consumed(needConsumedY: Float) {
        if (needConsumedY == 0f) return
        coroutineScope.launch {
            snapOffsetTo(indicatorOffset + needConsumedY)
        }
    }

    /**
     * 嵌套滚动连接器，处理下拉手势
     */
    internal val connection = object : NestedScrollConnection {
        /**
         * 预滚动事件处理
         *
         * 在子视图消费滚动事件之前触发。用于处理上滑收起刷新头部的逻辑。
         *
         * @param available 剩余可用的滚动偏移量
         * @param source 滚动来源
         * @return 消费掉的滚动偏移量
         * @author Joker.X
         */
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            return when {
                enableRefresh && available.y < 0 -> {
                    // 处理上滑（收起）
                    val canConsumed = (available.y * 0.5f).coerceAtLeast(0 - indicatorOffset)
                    consumed(canConsumed)
                    available.copy(x = 0f, y = canConsumed / 0.5f)
                }

                else -> Offset.Zero
            }
        }

        /**
         * 后滚动事件处理
         *
         * 在子视图消费滚动事件之后触发。用于处理下拉显示刷新头部的逻辑。
         *
         * @param consumed 子视图已经消费的滚动偏移量
         * @param available 剩余可用的滚动偏移量
         * @param source 滚动来源
         * @return 消费掉的滚动偏移量
         * @author Joker.X
         */
        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            return when {
                enableRefresh && available.y > 0 -> {
                    // 处理下拉
                    val canConsumed = available.y * 0.5f
                    consumed(canConsumed)
                    if (source == NestedScrollSource.SideEffect && indicatorOffset > headerHeight) {
                        throw CancellationException()
                    }
                    available.copy(x = 0f, y = canConsumed / 0.5f)
                }

                else -> Offset.Zero
            }
        }

        /**
         * 预惯性滑动事件处理
         *
         * 在手指松开准备进行惯性滑动时触发。用于判断是否触发刷新。
         *
         * @param available 剩余可用的速度
         * @return 消费掉的速度
         * @author Joker.X
         */
        override suspend fun onPreFling(available: Velocity): Velocity {
            // 手指松开时，如果超过阈值，触发刷新
            if (indicatorOffset >= headerHeight) {
                if (!isLoading()) {
                    isRefreshing = true
                    animateOffsetTo(headerHeight)
                    return available
                }
            }
            return super.onPreFling(available)
        }

        /**
         * 后惯性滑动事件处理
         *
         * 在惯性滑动结束后触发。用于处理未触发刷新时的回弹动画。
         *
         * @param consumed 已消费的速度
         * @param available 剩余可用的速度
         * @return 消费掉的速度
         * @author Joker.X
         */
        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            // 手指松开后的惯性处理
            if (indicatorOffset > 0) {
                if (isRefreshing && indicatorOffset > headerHeight) {
                    animateOffsetTo(headerHeight)
                } else if (!isRefreshing) {
                    animateOffsetTo(0f)
                }
                return available
            }
            return super.onPostFling(consumed, available)
        }
    }
}
