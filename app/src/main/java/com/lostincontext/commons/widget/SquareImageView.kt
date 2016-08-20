package com.lostincontext.commons.widget


import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.lostincontext.R

class SquareImageView @JvmOverloads constructor(context: Context,
                                                attrs: AttributeSet? = null,
                                                defStyle: Int = 0)
: AppCompatImageView(context,
                     attrs,
                     defStyle) {

    private var widthFixed = true

    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs,
                                                   R.styleable.SquareImageView,
                                                   defStyle,
                                                   0)
            widthFixed = a.getInt(R.styleable.SquareImageView_fixedSize, 0) == 0
            a.recycle()
        }
    }


    // warning suppressed since we do want to pass the same argument twice
    @SuppressWarnings("SuspiciousNameCombination")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = if (widthFixed) widthMeasureSpec else heightMeasureSpec
        super.onMeasure(size, size)
    }
}