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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.Primary
import kotlinx.coroutines.delay

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

object WeSwiperDefaults {
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