package io.viewpoint.moviedatabase.platform.ui.main

import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.platform.ui.home.HomeFragment
import io.viewpoint.moviedatabase.platform.ui.search.MovieSearchFragment
import io.viewpoint.moviedatabase.platform.ui.setting.SettingFragment

enum class MainTab(
    val itemId: Int,
    val tag: String
) {
    HOME(R.id.home_menu, HomeFragment.TAG),
    MOVIE_SEARCH(R.id.movie_search_menu, MovieSearchFragment.TAG),
    SETTING(R.id.setting_menu, SettingFragment.TAG);

    companion object
}

fun MainTab.Companion.otherTab(exceptTag: String): Sequence<MainTab> =
    MainTab.values()
        .asSequence()
        .filter {
            it.tag != exceptTag
        }