package io.viewpoint.moviedatabase.platform.util

import android.content.res.Resources
import android.graphics.Rect
import android.view.View

class LocalizedRect(
    val start: Int,
    val top: Int,
    val end: Int,
    val bottom: Int
) {
    fun toRect(): Rect =
        if (Resources.getSystem().configuration?.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
            Rect(end, top, start, bottom)
        } else {
            Rect(start, top, end, bottom)
        }
}