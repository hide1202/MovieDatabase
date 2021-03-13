package io.viewpoint.moviedatabase.platform.ui.main

import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.ui.setting.SettingFragment
import io.viewpoint.moviedatabase.ui.home.HomeFragment

enum class MainTab(
    val itemId: Int,
    val tag: String
) {
    HOME(R.id.home_menu, HomeFragment.TAG),
    MOVIE_SEARCH(R.id.movie_search_menu, io.viewpoint.moviedatabase.ui.search.MovieSearchFragment.TAG),
    SETTING(R.id.setting_menu, io.viewpoint.moviedatabase.ui.setting.SettingFragment.TAG);

    companion object
}

fun MainTab.Companion.otherTab(exceptTag: String): Sequence<MainTab> =
    MainTab.values()
        .asSequence()
        .filter {
            it.tag != exceptTag
        }