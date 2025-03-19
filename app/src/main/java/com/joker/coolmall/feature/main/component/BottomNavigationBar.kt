package com.joker.coolmall.feature.main.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.navigation.TopLevelDestination

/**
 * 底部导航栏
 */
@Composable
fun BottomNavigationBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (Int) -> Unit,
    currentDestination: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
    ) {
        destinations.forEachIndexed { index, destination ->
            val selected = destination.route == currentDestination

            var isPressed by remember { mutableStateOf(false) }
            val scale by animateFloatAsState(
                targetValue = if (isPressed) 1.3f else 1f,
                label = "ButtonScale${index}"
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .scale(scale)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                isPressed = true
                                tryAwaitRelease()
                                isPressed = false
                            },
                            onTap = {
                                onNavigateToDestination(index)
                            }
                        )
                    }
                    .padding(vertical = SpaceHorizontalSmall)
            ) {
                Image(
                    painter = painterResource(
                        id = if (selected)
                            destination.selectedIconId
                        else
                            destination.unselectedIconId
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )

                SpaceVerticalXSmall()

                Text(
                    text = stringResource(id = destination.titleTextId),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = if (selected) 0.7f else 0.5f
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyNavigationBarPreview() {
    BottomNavigationBar(
        destinations = TopLevelDestination.entries,
        onNavigateToDestination = {},
        currentDestination = TopLevelDestination.HOME.route
    )
}