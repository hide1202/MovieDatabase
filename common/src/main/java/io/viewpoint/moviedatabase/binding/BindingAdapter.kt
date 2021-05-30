@file:JvmName("BindingAdapter")

package io.viewpoint.moviedatabase.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import io.viewpoint.moviedatabase.util.Flags
import io.viewpoint.moviedatabase.viewmodel.Command

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

@BindingAdapter("flag")
fun ImageView.setCountryFlag(isoCountryCode: String?) {
    setImageDrawable(
        isoCountryCode?.let {
            Flags.getDrawable(context, it)
        }
    )
}