package com.lostincontext.data.rules

import android.content.Context
import android.content.res.Resources
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import com.lostincontext.R


class FenceNamer(val context: Context) {

    val resources: Resources = context.resources
    val theme: Resources.Theme = context.theme
    val color = resources.getColor(R.color.fence, theme)

    val notTemplate: String by lazy { resources.getString(R.string.comp_not) }


    fun composite(compositeFenceVM: CompositeFenceVM): CharSequence {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun detectedActivity(detectedActivityFenceVM: DetectedActivityFenceVM): CharSequence = applyStyle("walking")

    fun headphone(headphoneFenceVM: HeadphoneFenceVM): CharSequence = applyStyle("headphones in")

    fun location(locationFenceVM: LocationFenceVM): CharSequence = applyStyle("at work")

    fun not(notFenceVM: NotFenceVM): CharSequence {
        return TextUtils.expandTemplate(notTemplate, notFenceVM.fenceVM.name(this).toString())
    }


    fun time(timeFenceVM: TimeFenceVM): CharSequence {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inline fun applyStyle(text: String): CharSequence {
        val span = SpannableString.valueOf(text)
        span.setSpan(ForegroundColorSpan(color), 0, text.length,
                     Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        return span
    }


}
