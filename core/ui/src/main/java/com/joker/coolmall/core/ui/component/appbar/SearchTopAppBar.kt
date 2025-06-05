package com.joker.coolmall.core.ui.component.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.component.CenterRow
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ArrowLeftIcon
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.ShapeCircle
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.ui.R
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextType

/**
 * 搜索顶部栏
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(
    onBackClick: () -> Unit,
    onSearch: (String) -> Unit,
    actions: @Composable () -> Unit = {}
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val performSearch = {
        onSearch(searchText)
        focusManager.clearFocus()
    }

    TopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .clip(ShapeCircle)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                CenterRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 4.dp)
                        .padding(vertical = SpaceVerticalXSmall),
                ) {
                    CommonIcon(
                        resId = R.drawable.ic_search,
                        size = 16.dp,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    BasicTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = SpaceHorizontalSmall),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = { performSearch() }
                        ),
                        decorationBox = { innerTextField ->
                            if (searchText.isEmpty()) {
                                AppText(
                                    text = "输入内容进行搜索",
                                    type = TextType.TERTIARY
                                )
                            }
                            innerTextField()
                        }
                    )

                    // 内部搜索按钮
                    Box(
                        modifier = Modifier
                            .clip(ShapeCircle)
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable { performSearch() }
                            .height(34.dp)
                            .padding(horizontal = SpaceHorizontalXLarge),
                        contentAlignment = Alignment.Center
                    ) {
                        AppText(
                            text = "搜索",
                            color = Color.White
                        )
                    }
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                ArrowLeftIcon()
            }
        },
        actions = {
            actions()
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SearchTopAppBarPreview() {
    AppTheme {
        SearchTopAppBar(
            onSearch = {},
            onBackClick = {}
        )
    }
}