package io.viewpoint.moviedatabase.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class IconButton : AppCompatImageView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun setPressed(pressed: Boolean) {
        super.setPressed(pressed)
        alpha = if (pressed) 0.5f else 1.0f
    }
}