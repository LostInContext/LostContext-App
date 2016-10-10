package com.lostincontext.rulecreate


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.util.TypedValue
import android.view.WindowInsets
import com.lostincontext.R
import com.lostincontext.utils.logD

/**
 * Extension of CoordinatorLayout tasked with painting a scrim over the statusBar, passing insets
 * to a RecyclerView & to update the background of a toolbar.
 * Due to the way WindowInsets are consumed, this is the easiest way to achieve this effect.
 * I don't want to talk about it.
 */
class ScrimCoordinatorLayout : CoordinatorLayout {


    var systemBarHeight = 0

    var hasBeenApplied = false

    var recyclerView: RecyclerView? = null
    var toolbar: Toolbar? = null

    val scrim = ColorDrawable(Color.DKGRAY)
    val toolbarBackground: ColorDrawable

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context,
                                                                                  attrs,
                                                                                  defStyleAttr) {
    }

    init {

        val a = context.obtainStyledAttributes(TypedValue().data,
                                               intArrayOf(R.attr.colorAccent))
        val accent = a.getColor(0, 0)
        toolbarBackground = ColorDrawable(accent)
        toolbarBackground.alpha = 100
        scrim.alpha = 100
        a.recycle()
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //if (changed) {
        for (i in 0..childCount - 1) {
            val v = getChildAt(i)
            when (v.id) {
                R.id.recyclerView -> recyclerView = v as RecyclerView?
                R.id.toolbar -> toolbar = v as Toolbar
            }
        }

        logD("test") { "l : $l t: $t r: $r b: $b" }

        if (!hasBeenApplied) {
            hasBeenApplied = true
            val layoutParams: CoordinatorLayout.LayoutParams = recyclerView!!.layoutParams as LayoutParams
            layoutParams.topMargin = -systemBarHeight
            scrim.setBounds(l,
                            0,
                            r,
                            systemBarHeight)

            toolbar?.background = toolbarBackground

            requestLayout()
        }


        //}

        super.onLayout(changed, l, t, r, b)
    }


    override fun dispatchApplyWindowInsets(insets: WindowInsets): WindowInsets {
        hasBeenApplied = false
        systemBarHeight = insets.systemWindowInsetTop
        return super.dispatchApplyWindowInsets(insets)
    }


    override fun onDraw(c: Canvas) {
        super.onDraw(c)
    }

    override fun dispatchDraw(c: Canvas) {
        super.dispatchDraw(c)
        scrim.draw(c)
    }


}
