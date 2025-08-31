
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.ui.R
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

/**
 * 评分组件 - 支持整星评分，带动画效果
 * @param value 当前评分值
 * @param count 星星总数，默认5个
 * @param onChange 评分变化回调
 * @param animationEnabled 是否启用动画效果，默认true
 */
@Composable
fun WeRate(
    value: Int,
    count: Int = 5,
    onChange: ((Int) -> Unit)? = null,
    animationEnabled: Boolean = true
) {
    var starWidth by remember { mutableIntStateOf(0) }
    var clickedIndex by remember { mutableIntStateOf(-1) }
    var animationTrigger by remember { mutableIntStateOf(0) }

    // 重置点击动画状态
    LaunchedEffect(clickedIndex) {
        if (clickedIndex >= 0) {
            delay(200)
            clickedIndex = -1
        }
    }

    Row(
        modifier = Modifier
            .pointerInput(starWidth) {
                onChange?.let {
                    detectHorizontalDragGestures { change, _ ->
                        val newValue = change.position
                            .calculateRateValue(starWidth, count)
                        onChange(newValue)
                    }
                }
            }
    ) {
        repeat(count) { index ->
            StarItem(
                isActive = index < value,
                isClicked = clickedIndex == index,
                animationEnabled = animationEnabled,
                animationDelay = if (animationEnabled) index * 50L else 0L,
                animationTrigger = animationTrigger,
                modifier = Modifier
                    .size(26.dp)
                    .onSizeChanged {
                        starWidth = it.width
                    }
                    .pointerInput(Unit) {
                        onChange?.let {
                            detectTapGestures {
                                clickedIndex = index
                                animationTrigger++
                                onChange(index + 1)
                            }
                        }
                    }
            )
        }
    }
}

/**
 * 星星图标组件 - 带动画效果
 * @param isActive 是否激活状态
 * @param isClicked 是否被点击
 * @param animationEnabled 是否启用动画
 * @param animationDelay 动画延迟时间
 * @param animationTrigger 动画触发器
 * @param modifier 修饰符
 */
@Composable
private fun StarItem(
    isActive: Boolean,
    isClicked: Boolean,
    animationEnabled: Boolean,
    animationDelay: Long,
    animationTrigger: Int,
    modifier: Modifier
) {
    // 缩放动画 - 点击时放大
    val scale by animateFloatAsState(
        targetValue = if (isClicked && animationEnabled) 1.3f else 1f,
        animationSpec = spring(
            dampingRatio = 0.6f,
            stiffness = 800f
        ),
        label = "star_scale"
    )

    // 颜色渐变动画
    val tintColor by animateColorAsState(
        targetValue = if (isActive) {
            Color(0xffFF6700)
        } else {
            MaterialTheme.colorScheme.outline
        },
        animationSpec = tween(
            durationMillis = if (animationEnabled) 300 else 0,
            delayMillis = if (animationEnabled) animationDelay.toInt() else 0
        ),
        label = "star_color"
    )

    // 星星填充动画 - 当评分变化时的弹性效果
    var scaleAnimation by remember { mutableStateOf(1f) }
    val animatedScale by animateFloatAsState(
        targetValue = scaleAnimation,
        animationSpec = spring(
            dampingRatio = 0.5f,
            stiffness = 600f
        ),
        label = "star_fill_animation"
    )

    // 监听激活状态变化，触发填充动画
    LaunchedEffect(isActive, animationTrigger) {
        if (isActive && animationEnabled) {
            delay(animationDelay)
            scaleAnimation = 1.2f
            delay(150)
            scaleAnimation = 1f
        }
    }

    Icon(
        painter = painterResource(
            id = if (isActive) {
                R.drawable.ic_star_fill
            } else {
                R.drawable.ic_star
            }
        ),
        contentDescription = "评分星星",
        tint = tintColor,
        modifier = modifier
            .scale(scale * animatedScale)
    )
}

/**
 * 根据触摸位置计算评分值
 * @param starWidth 单个星星的宽度
 * @param count 星星总数
 * @return 计算后的整数评分值
 */
private fun Offset.calculateRateValue(starWidth: Int, count: Int): Int {
    val newRating = (this.x / starWidth)
        .coerceIn(0f, count.toFloat())

    return newRating
        .roundToInt()
        .coerceIn(0, count)
}