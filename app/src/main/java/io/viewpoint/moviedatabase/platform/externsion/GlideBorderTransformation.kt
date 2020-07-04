package io.viewpoint.moviedatabase.platform.externsion

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.ColorInt
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class GlideBorderTransformation(
    private val borderWidth: Int,
    @ColorInt private val borderColor: Int
) : BitmapTransformation() {

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update("$ID/$borderWidth/$borderColor".toByteArray())
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val paint = Paint().apply {
            color = borderColor
            style = Paint.Style.STROKE
            strokeWidth = borderWidth.toFloat()
            isAntiAlias = true
        }

        val canvas = Canvas(toTransform)
        canvas.drawRect(
            0.0f,
            0.0f,
            toTransform.width.toFloat(),
            toTransform.height.toFloat(),
            paint
        )

        return toTransform
    }

    companion object {
        private const val ID =
            "io.viewpoint.moviedatabase.platform.externsion.GlideBorderTransformation"
    }
}