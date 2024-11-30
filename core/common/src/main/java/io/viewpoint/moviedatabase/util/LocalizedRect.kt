package io.viewpoint.moviedatabase.util

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
        toRect(Resources.getSystem().configuration?.layoutDirection == View.LAYOUT_DIRECTION_RTL)

    fun toRect(isRtl: Boolean): Rect =
        if (isRtl) {
            Rect(end, top, start, bottom)
        } else {
            Rect(start, top, end, bottom)
        }
}