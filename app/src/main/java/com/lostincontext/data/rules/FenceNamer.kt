package com.lostincontext.data.rules

import android.content.Context
import android.content.res.Resources
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils.expandTemplate
import android.text.style.ForegroundColorSpan
import com.lostincontext.R
import com.lostincontext.utils.getColorSafe


class FenceNamer(val context: Context) {

    val resources: Resources = context.resources
    val theme: Resources.Theme = context.theme
    val color = resources.getColorSafe(R.color.fence, theme)

    val notTemplate: String by lazy { resources.getString(R.string.not_x) }


    @Suppress("UNUSED_PARAMETER")
    fun composite(compositeFenceVM: CompositeFenceVM): CharSequence {
        throw UnsupportedOperationException("not supported")
        // composites should not use named directly but call the scribe instead
    }

    fun detectedActivity(detectedActivityFenceVM: DetectedActivityFenceVM): CharSequence {
        val text = when (detectedActivityFenceVM.type) {
            DetectedActivityFenceVM.Type.RUNNING -> resources.getString(R.string.running)
            DetectedActivityFenceVM.Type.WALKING -> resources.getString(R.string.walking)
            DetectedActivityFenceVM.Type.IN_VEHICLE -> resources.getString(R.string.in_vehicle)
            DetectedActivityFenceVM.Type.ON_BICYCLE -> resources.getString(R.string.on_bike)
            DetectedActivityFenceVM.Type.ON_FOOT -> resources.getString(R.string.on_foot)
        }

        return applyStyle(text)
    }

    fun headphone(headphoneFenceVM: HeadphoneFenceVM): CharSequence {
        val text = when (headphoneFenceVM.state) {
            HeadphoneFenceVM.State.PLUGGED_IN -> resources.getString(R.string.headphones_in)
            HeadphoneFenceVM.State.PLUGGED_OUT -> resources.getString(R.string.headphones_out)
        }

        return applyStyle(text)
    }

    fun location(locationFenceVM: LocationFenceVM): CharSequence {
        return applyStyle(locationFenceVM.name)
    }

    fun not(notFenceVM: NotFenceVM): CharSequence {
        return applyStyle(expandTemplate(notTemplate,
                                         notFenceVM.fenceVM.name(this)).toString())
    }


    @Suppress("UNUSED_PARAMETER")
    fun time(timeFenceVM: TimeFenceVM): CharSequence {
        throw UnsupportedOperationException("not implemented yet")
    }

    @Suppress("NOTHING_TO_INLINE")
    inline fun applyStyle(text: String): CharSequence {
        val span = SpannableString.valueOf(text)
        span.setSpan(ForegroundColorSpan(color), 0, text.length,
                     Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        return span
    }


}
