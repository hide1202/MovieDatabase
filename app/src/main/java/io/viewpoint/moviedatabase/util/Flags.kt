package io.viewpoint.moviedatabase.util

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import java.util.*

object Flags {
    fun getDrawable(context: Context, isoCountryCode: String): Drawable? {
        val resources = context.resources
        val id = resources.getIdentifier(
            "flag_${isoCountryCode.toLowerCase(Locale.US)}",
            "drawable",
            context.packageName
        )
        return ResourcesCompat.getDrawable(resources, id, context.theme)
    }
}