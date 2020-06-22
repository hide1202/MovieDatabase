@file:JvmName("BindingAdapter")

package io.viewpoint.moviedatabase.platform.ui.binding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("gone")
fun setGone(v: View, gone: Boolean) {
    v.visibility = if (gone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}