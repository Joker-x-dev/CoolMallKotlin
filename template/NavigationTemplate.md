# Navigation 模板

## 模板代码

```kotlin
package com.joker.coolmall.feature.order.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.order.view.OrderConfirmRoute
import com.joker.coolmall.navigation.routes.OrderRoutes

/**
 * 确认订单页面导航
 */
fun NavGraphBuilder.orderConfirmScreen() {
    composable(route = OrderRoutes.ORDER_CONFIRM) {
        OrderConfirmRoute()
    }
}
```