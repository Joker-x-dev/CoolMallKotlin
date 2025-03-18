import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val MAIN_ROUTE = "main"

fun NavController.navigateToMain() {
    navigate(MAIN_ROUTE) {
        launchSingleTop = true
        popUpTo(MAIN_ROUTE)
    }
}

fun NavGraphBuilder.mainScreen(
) {
    composable(MAIN_ROUTE) {
        MainRoute()
    }
}