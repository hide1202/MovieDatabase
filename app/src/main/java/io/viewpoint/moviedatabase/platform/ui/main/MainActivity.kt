package io.viewpoint.moviedatabase.platform.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navHostController = rememberNavController()
            var selectedItem by remember {
                mutableStateOf(MainTab.HOME)
            }

            LaunchedEffect(Unit) {
                navHostController.addOnDestinationChangedListener { _, dest, _ ->
                    val route = dest.route ?: return@addOnDestinationChangedListener
                    val tab = MainTab.findTag(route) ?: return@addOnDestinationChangedListener
                    if (selectedItem != tab) {
                        selectedItem = tab
                    }
                }
            }

            MovieDatabaseRoute(
                navController = navHostController,
                selectedTab = selectedItem,
                onTabSelected = {
                    selectedItem = it

                    navHostController.navigate(it.tag)
                },
            )
        }
    }

    companion object {
        private const val KEY_SELECTED_TAB = "selectedTab"
    }
}
