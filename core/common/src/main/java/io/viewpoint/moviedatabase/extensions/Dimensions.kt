@file:JvmName("Dimensions")

package io.viewpoint.moviedatabase.extensions

import android.content.res.Resources
import android.util.TypedValue

@get:JvmName("dp")
val Float.dp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

@get:JvmName("dp")
val Int.dp: Int
    get() = toFloat().dp.toInt()