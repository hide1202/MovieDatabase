package io.viewpoint.moviedatabase.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.annotation.ColorInt
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest
import kotlin.math.absoluteValue


class GlideBorderTransformation(
    private val borderWidth: Int,
    @ColorInt private val borderColor: Int
) : BitmapTransformation() {
    private val borderPaint = Paint().apply {
        color = borderColor
        style = Paint.Style.STROKE
        strokeWidth = borderWidth.toFloat()
        isAntiAlias = true
    }
    private val rect = RectF()

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update("$ID/$borderWidth/$borderColor".toByteArray())
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val halfBorderWidth = borderWidth / 2.0f

        rect.set(
            0.0f,
            0.0f,
            toTransform.width.toFloat(),
            toTransform.height.toFloat()
        )
        rect.inset(halfBorderWidth, halfBorderWidth)

        // if scaleType is centerCrop, it is different between length of output and length of bitmap
        val extraWidth = outWidth - toTransform.width
        val extraHeight = outHeight - toTransform.height
        rect.inset(
            if (extraWidth < 0) extraWidth.absoluteValue / 2.0f else 0.0f,
            if (extraHeight < 0) extraHeight.absoluteValue / 2.0f else 0.0f
        )

        val canvas = Canvas(toTransform)
        canvas.drawRect(rect, borderPaint)

        return toTransform
    }

    companion object {
        private const val ID =
            "io.viewpoint.moviedatabase.extensions.GlideBorderTransformation"
    }
}