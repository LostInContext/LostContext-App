package com.lostincontext.utils


import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.view.View
import android.view.animation.Interpolator
import android.widget.ImageView
import android.widget.TextView


fun View.animateBackgroundColor(@ColorInt from: Int,
                                @ColorInt to: Int,
                                duration: Long,
                                interpolator: Interpolator? = null) {
    animate(from,
            to,
            duration,
            interpolator) { valueAnimator -> setBackgroundColor(valueAnimator.animatedValue as Int) }
}


fun TextView.animateTextColor(@ColorInt from: Int,
                              @ColorInt to: Int,
                              duration: Long,
                              interpolator: Interpolator? = null) {
    animate(from,
            to,
            duration,
            interpolator) { valueAnimator -> setTextColor(valueAnimator.animatedValue as Int) }
}

fun ImageView.animateImageTint(@ColorInt from: Int,
                               @ColorInt to: Int,
                               duration: Long,
                               interpolator: Interpolator? = null) {
    animate(from,
            to,
            duration,
            interpolator) { valueAnimator ->
        imageTintList = ColorStateList.valueOf(valueAnimator.animatedValue as Int)
    }
}

fun Drawable.animateDrawableTint(@ColorInt from: Int,
                                 @ColorInt to: Int,
                                 duration: Long,
                                 interpolator: Interpolator? = null) {
    setTintMode(PorterDuff.Mode.SRC_ATOP)
    animate(from,
            to,
            duration,
            interpolator) { valueAnimator -> setTint(valueAnimator.animatedValue as Int) }
}

private inline fun animate(@ColorInt from: Int,
                           @ColorInt to: Int,
                           duration: Long,
                           interpolator: Interpolator?,
                           crossinline callback: (valueAnimator: ValueAnimator) -> Unit) {
    val valueAnimator = ValueAnimator.ofArgb(from, to)
    valueAnimator.addUpdateListener { valueAnimator -> callback.invoke(valueAnimator) }
    if (interpolator != null) valueAnimator.interpolator = interpolator
    valueAnimator.setDuration(duration)
            .start()
}

private fun blendColors(@ColorInt from: Int,
                        @ColorInt to: Int,
                        ratio: Float): Int {
    val inverseRatio = 1f - ratio

    val r = (Color.red(to) * ratio + Color.red(from) * inverseRatio).toInt()
    val g = (Color.green(to) * ratio + Color.green(from) * inverseRatio).toInt()
    val b = (Color.blue(to) * ratio + Color.blue(from) * inverseRatio).toInt()

    return Color.rgb(r, g, b)
}
