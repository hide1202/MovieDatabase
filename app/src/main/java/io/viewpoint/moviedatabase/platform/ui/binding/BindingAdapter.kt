@file:JvmName("BindingAdapter")

package io.viewpoint.moviedatabase.platform.ui.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import io.viewpoint.moviedatabase.R

@BindingAdapter("gone")
fun View.setGone(gone: Boolean) {
    visibility = if (gone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("adapter")
fun RecyclerView.setAdapter(adapter: RecyclerView.Adapter<*>?) {
    this.adapter = adapter
}

@BindingAdapter("verticalDividerDecoration")
fun RecyclerView.setVerticalDividerDecoration(isAdded: Boolean) {
    if (isAdded) {
        this.addItemDecoration(
            DividerItemDecoration(
                this.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }
}

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    if (url != null) {
        Glide.with(this)
            .load(url)
            .fallback(R.drawable.fallback_image)
            .error(R.drawable.fallback_image)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(this)
    } else {
        Glide.with(this)
            .clear(this)
    }
}