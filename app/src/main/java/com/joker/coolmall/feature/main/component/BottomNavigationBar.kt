package com.joker.coolmall.feature.main.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
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
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        destinations.forEachIndexed { index, destination ->
            val selected = destination.route == currentDestination
            val scale by animateFloatAsState(targetValue = if (selected) 1.2f else 1.0f)

            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(index) },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = if (selected) {
                                destination.selectedIconId
                            } else {
                                destination.unselectedIconId
                            }
                        ),
                        tint = Color.Unspecified,
                        contentDescription = null,
                        modifier = Modifier.scale(scale)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = destination.titleTextId),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = if (selected) 12.sp else 10.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = if (selected) 0.7f else 0.5f
                        )
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    indicatorColor = MaterialTheme.colorScheme.surface
                )
            )
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