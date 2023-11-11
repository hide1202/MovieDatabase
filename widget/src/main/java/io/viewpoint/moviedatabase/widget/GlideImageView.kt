package io.viewpoint.moviedatabase.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import io.viewpoint.moviedatabase.extensions.GlideBorderTransformation
import timber.log.Timber

class GlideImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    private var loadedImageUri: String? = null

    private val fallbackImage: Drawable?

    private val isCircle: Boolean

    private val borderWidth: Int

    @ColorInt
    var borderColor: Int

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.GlideImageView)

        isCircle = a.getBoolean(R.styleable.GlideImageView_isCircle, false)
        fallbackImage = a.getDrawable(R.styleable.GlideImageView_fallbackImage)
        borderWidth = a.getDimensionPixelSize(R.styleable.GlideImageView_borderWidth, 0)
        borderColor = a.getColor(R.styleable.GlideImageView_borderColor, 0)

        val imageUrl = a.getString(R.styleable.GlideImageView_imageUrl)
        if (imageUrl != null) {
            loadImage(imageUrl)
        }

        a.recycle()
    }

    fun loadImage(url: String?) {
        Timber.d("image url : %s", url)
        if (loadedImageUri != null && loadedImageUri == url) {
            return
        }

        Glide.with(this)
            .load(url)
            .let {
                if (borderWidth != 0 && borderColor != 0) {
                    it.apply(
                        RequestOptions.bitmapTransform(
                            GlideBorderTransformation(borderWidth, borderColor)
                        )
                    )
                } else it
            }
            .let {
                if (isCircle) it.circleCrop()
                else it
            }
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean = false.also {
                    loadedImageUri = url
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean = false.also {
                    loadedImageUri = url
                }
            })
            .fallback(fallbackImage)
            .error(fallbackImage)
            .placeholder(fallbackImage)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(this)
    }
}

@BindingAdapter("imageUrl")
fun GlideImageView.setImageUrl(url: String?) {
    loadImage(url)
}