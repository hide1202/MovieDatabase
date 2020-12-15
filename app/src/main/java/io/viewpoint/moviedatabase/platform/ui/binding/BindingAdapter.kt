@file:JvmName("BindingAdapter")

package io.viewpoint.moviedatabase.platform.ui.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.platform.externsion.GlideBorderTransformation
import io.viewpoint.moviedatabase.util.Flags
import io.viewpoint.moviedatabase.viewmodel.Command
import timber.log.Timber

@BindingAdapter("gone")
fun View.setGone(gone: Boolean) {
    visibility = if (gone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("command")
fun View.setCommand(command: Command) = setOnClickListener {
    command.action()
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

@BindingAdapter("imageUrl", "circle", "borderWidth", "borderColor", requireAll = false)
fun ImageView.setImageUrl(
    url: String?,
    circle: Boolean?,
    borderWidth: Int?,
    borderColor: Int?
) {
    Timber.d("image url : %s", url)
    Glide.with(this)
        .load(url)
        .let {
            if (borderWidth != null && borderColor != null) {
                it.apply(
                    RequestOptions.bitmapTransform(
                        GlideBorderTransformation(borderWidth, borderColor)
                    )
                )
            } else it
        }
        .let {
            if (circle == true) it.circleCrop()
            else it
        }
        .fallback(R.drawable.fallback_image)
        .error(R.drawable.fallback_image)
        .placeholder(R.drawable.fallback_image)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

@BindingAdapter("flag")
fun ImageView.setCountryFlag(isoCountryCode: String?) {
    setImageDrawable(
        isoCountryCode?.let {
            Flags.getDrawable(context, it)
        }
    )
}