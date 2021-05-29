package io.viewpoint.moviedatabase.platform.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.databinding.ActivityMainBinding
import io.viewpoint.moviedatabase.platform.navigation.BottomNavigator

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val navController: NavController by lazy {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            ?: throw IllegalStateException("the container MUST contain a fragment at least one")
        navHostFragment.findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this

        navController.apply {
            navigatorProvider.addNavigator(
                BottomNavigator(
                    R.id.fragment_container,
                    supportFragmentManager
                )
            )
            // set a graph at code not XML, because add a custom navigator
            setGraph(R.navigation.bottom_navigation)

            binding.bottomNavigation.setupWithNavController(this)
        }

        savedInstanceState?.getInt(KEY_SELECTED_TAB)
            ?.let {
                MainTab.from(it)
            }
            ?.itemId
            ?.let {
                binding.bottomNavigation.selectedItemId = it
            }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SELECTED_TAB, binding.bottomNavigation.selectedItemId)
    }

    companion object {
        private const val KEY_SELECTED_TAB = "selectedTab"
    }
}
