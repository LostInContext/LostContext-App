package com.lostincontext.commons.images


import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.lostincontext.R
import com.lostincontext.utils.getColorSafe

/**
 * Allows a protection gradient on top of an image so that it can be displayed with some white content
 * on top
 */
class ImageGradientTransformation(val context: Context) : BitmapTransformation(context) {

    val resources: Resources = context.resources
    val theme: Resources.Theme = context.theme
    val paint: Paint = Paint()
    val gradient = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                                    intArrayOf(resources.getColorSafe(R.color.image_protection,
                                                                      theme),
                                               Color.TRANSPARENT))

    val actionBarSize: Int

    init {

        val a = context.obtainStyledAttributes(TypedValue().data,
                                               intArrayOf(R.attr.actionBarSize))
        actionBarSize = a.getDimensionPixelSize(0, 0)

        a.recycle()

    }

    override fun transform(pool: BitmapPool,
                           toTransform: Bitmap,
                           outWidth: Int,
                           outHeight: Int): Bitmap? {

        val result = getBitmapForTransformation(pool,
                                                toTransform.width,
                                                toTransform.height)
        val canvas = Canvas(result)

        canvas.drawBitmap(toTransform, 0f, 0f, paint)

        val height = Math.min(toTransform.height, 3 * actionBarSize)


        gradient.setBounds(0, 0, toTransform.width, height)
        gradient.draw(canvas)

        return result
    }

    /**
     * in order to avoid banding, we use a bitmap in ARGB_8888 mode
     */
    private fun getBitmapForTransformation(pool: BitmapPool,
                                           width: Int,
                                           height: Int): Bitmap {
        val toReuse = pool.get(width, height, Bitmap.Config.ARGB_8888)
        if (toReuse != null) return toReuse
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    }


    override fun getId() = "GradientTr-LostContext"
}
