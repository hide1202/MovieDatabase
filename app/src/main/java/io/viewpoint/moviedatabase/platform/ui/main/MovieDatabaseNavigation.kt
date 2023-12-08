package io.viewpoint.moviedatabase.platform.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun MovieDatabaseNavigation(
    selectedItem: MainTab,
    onSelected: (MainTab) -> Unit
) {
    Column {
        NavigationBar {
            MainTab.values().forEach {
                NavigationBarItem(
                    icon = {
                        Image(
                            painter = painterResource(id = it.iconResId),
                            contentDescription = null,
                        )
                    },
                    label = { Text(text = stringResource(id = it.titleResId)) },
                    selected = selectedItem == it,
                    onClick = {
                        onSelected(it)
                    }
                )
            }
        }
    }
}