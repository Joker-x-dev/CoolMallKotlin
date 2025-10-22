package com.joker.coolmall.core.ui.component.swiper

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.Primary
import kotlinx.coroutines.delay

/**
 * 轮播组件 - 支持自动播放和自定义指示器
 *
 * @param options 轮播项数据列表
 * @param modifier 修饰符，用于自定义组件样式
 * @param state 分页状态，默认根据options大小创建
 * @param contentPadding 内容内边距，默认为0
 * @param pageSpacing 页面间距，默认为0
 * @param autoplay 是否自动播放，默认为true
 * @param interval 自动播放间隔时间（毫秒），默认3000ms
 * @param indicator 指示器组件，为null时不显示指示器
 * @param content 轮播项内容组件
 */
@Composable
fun <T> WeSwiper(
    options: List<T>,
    modifier: Modifier = Modifier,
    state: PagerState = rememberPagerState {
        options.size
    },
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageSpacing: Dp = 0.dp,
    autoplay: Boolean = true,
    interval: Long = 3000,
    indicator: (@Composable BoxScope.(count: Int, current: Int) -> Unit)? = { count, current ->
        WeSwiperDefaults.Indicator(
            count,
            current,
            Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-10).dp)
        )
    },
    content: @Composable (PagerScope.(Int, T) -> Unit)
) {
    if (autoplay) {
        LaunchedEffect(state.settledPage) {
            delay(interval)
            val targetPage = (state.currentPage + 1) % state.pageCount
            state.animateScrollToPage(targetPage)
        }
    }

    Box {
        HorizontalPager(
            state,
            modifier,
            contentPadding,
            pageSpacing = pageSpacing
        ) { index ->
            content(index, options[index])
        }

        if (indicator != null) {
            indicator(options.size, state.currentPage)
        }
    }
}

/**
 * WeSwiper默认配置对象
 */
object WeSwiperDefaults {
    /**
     * 默认指示器组件 - 横向进度条样式
     *
     * @param count 总页数
     * @param current 当前页索引
     * @param modifier 修饰符，用于自定义组件样式
     */
    @Composable
    fun Indicator(count: Int, current: Int, modifier: Modifier = Modifier) {
        Row(
            modifier
                .width((count * 16).dp)
                .height(3.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Primary.copy(0.2f))
        ) {
            val offsetX by animateDpAsState(
                targetValue = (current * 16).dp,
                label = "OffsetAnimation"
            )
            Box(
                Modifier
                    .width(16.dp)
                    .height(3.dp)
                    .offset(x = offsetX)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Primary.copy(0.6f))
            )
        }
    }
}