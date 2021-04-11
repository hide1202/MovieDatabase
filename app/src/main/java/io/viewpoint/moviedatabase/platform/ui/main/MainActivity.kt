package io.viewpoint.moviedatabase.platform.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.databinding.ActivityMainBinding
import io.viewpoint.moviedatabase.ui.home.HomeFragment
import io.viewpoint.moviedatabase.ui.search.MovieSearchFragment
import io.viewpoint.moviedatabase.ui.setting.SettingFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_menu -> replaceFragment<HomeFragment>(HomeFragment.TAG)
                R.id.movie_search_menu -> replaceFragment<MovieSearchFragment>(MovieSearchFragment.TAG)
                R.id.setting_menu -> replaceFragment<SettingFragment>(SettingFragment.TAG)
                else -> false
            }
        }
        binding.bottomNavigation.selectedItemId = savedInstanceState?.getInt(KEY_SELECTED_TAB)
            ?.let {
                MainTab.from(it)
            }
            ?.itemId
            ?: MainTab.HOME.itemId
    }

    private inline fun <reified T : Fragment> replaceFragment(tag: String): Boolean {
        val current = supportFragmentManager.findFragmentByTag(tag)
        val others = MainTab.otherTab(exceptTag = tag)
            .mapNotNull {
                supportFragmentManager.findFragmentByTag(it.tag)
            }

        supportFragmentManager.commit {
            if (current == null) {
                add<T>(R.id.fragment_container, tag)
            } else {
                show(current)
            }

            others.forEach {
                hide(it)
            }
        }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SELECTED_TAB, binding.bottomNavigation.selectedItemId)
    }

    companion object {
        private const val KEY_SELECTED_TAB = "selectedTab"
    }
}
