package io.viewpoint.moviedatabase.platform.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.viewpoint.moviedatabase.R

enum class MainTab(
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int,
    val tag: String
) {
    HOME(
        titleResId = R.string.menu_home,
        iconResId = R.drawable.ic_home,
        tag = "main_tab_home",
    ),
    MOVIE_SEARCH(
        titleResId = R.string.menu_search,
        iconResId = R.drawable.ic_search,
        tag = "main_tab_search",
    ),
    SETTING(
        titleResId = R.string.menu_setting,
        iconResId = R.drawable.ic_settings,
        tag = "main_tab_setting",
    );

    companion object {
        fun findTag(tag: String): MainTab? {
            return values().firstOrNull { it.tag == tag }
        }
    }
}

fun MainTab.Companion.otherTab(exceptTag: String): Sequence<MainTab> =
    MainTab.values()
        .asSequence()
        .filter {
            it.tag != exceptTag
        }