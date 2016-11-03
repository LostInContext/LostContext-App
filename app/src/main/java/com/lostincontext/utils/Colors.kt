package com.lostincontext.utils


import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.view.View
import android.view.animation.Interpolator
import android.widget.ImageView
import android.widget.TextView
import com.lostincontext.commons.animation.GammaEvaluator


fun Resources.getColorSafe(@ColorRes colorRes: Int,
                           theme: Resources.Theme): Int {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) return getColor(colorRes, theme)
    @Suppress("DEPRECATION")
    return getColor(colorRes)
}


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
    val valueAnimator = ValueAnimator()
    valueAnimator.setIntValues(from, to)
    valueAnimator.setEvaluator(GammaEvaluator.getInstance())
    valueAnimator.addUpdateListener { valueAnimator -> callback.invoke(valueAnimator) }
    if (interpolator != null) valueAnimator.interpolator = interpolator
    valueAnimator.setDuration(duration)
            .start()
}
