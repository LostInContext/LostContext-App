package com.lostincontext.rulecreate


import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.util.TypedValue
import android.view.WindowInsets
import com.lostincontext.R
import com.lostincontext.utils.FAST_OUT_LINEAR_IN_INTERPOLATOR
import com.lostincontext.utils.LINEAR_OUT_SLOW_IN_INTERPOLATOR

/**
 * Extension of CoordinatorLayout tasked with painting a scrim over the statusBar, passing insets
 * to a RecyclerView & to update the background of a toolbar.
 * Due to the way WindowInsets are consumed, this is the easiest way to achieve this effect.
 * I don't want to talk about it.
 */
class ScrimCoordinatorLayout : CoordinatorLayout,
                               HeaderScrollListener {


    var systemBarHeight = 0

    var hasBeenApplied = false

    var recyclerView: RecyclerView? = null
    var toolbar: Toolbar? = null

    val scrim: ColorDrawable
    val toolbarBackground: ColorDrawable
    val scrimAnimPivot: Int

    val scrimAnimator = ValueAnimator()
    val toolbarAnimator = ValueAnimator()

    var isScrimmed = false

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
                                               intArrayOf(R.attr.colorAccent,
                                                          R.attr.colorPrimaryDark))
        val colorAccent = a.getColor(0, 0)
        toolbarBackground = ColorDrawable(colorAccent)
        val colorPrimaryDark = a.getColor(1, 0)
        scrim = ColorDrawable(colorPrimaryDark)
        a.recycle()

        toolbarBackground.alpha = 0
        scrim.alpha = 100

        scrimAnimPivot = resources.getDimensionPixelSize(R.dimen.scrim_anim_pivot)

        val duration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
        scrimAnimator.duration = duration
        toolbarAnimator.duration = duration

        scrimAnimator.addUpdateListener {
            scrim.alpha = it.animatedValue as Int
            invalidate(scrim.bounds)
        }

        toolbarAnimator.addUpdateListener { toolbarBackground.alpha = it.animatedValue as Int }


    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //if (changed) {
        for (i in 0..childCount - 1) {
            val v = getChildAt(i)
            when (v.id) {
                R.id.recyclerView -> recyclerView = v as RecyclerView
                R.id.toolbar -> toolbar = v as Toolbar
            }
        }

        if (!hasBeenApplied) {
            hasBeenApplied = true
            val layoutParams: CoordinatorLayout.LayoutParams = recyclerView!!.layoutParams as LayoutParams
            layoutParams.topMargin = -systemBarHeight
            scrim.setBounds(l,
                            0,
                            r,
                            systemBarHeight)

            toolbar!!.background = toolbarBackground

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

    override fun dispatchDraw(c: Canvas) {
        super.dispatchDraw(c)
        scrim.draw(c)
    }

    override fun onScrolled(headerScrollY: Int) {

        if (!isScrimmed && headerScrollY > scrimAnimPivot) {
            isScrimmed = true
            scrimAnimator.cancel()
            toolbarAnimator.cancel()

            scrimAnimator.interpolator = FAST_OUT_LINEAR_IN_INTERPOLATOR
            toolbarAnimator.interpolator = FAST_OUT_LINEAR_IN_INTERPOLATOR

            scrimAnimator.setIntValues(scrim.alpha, 255)
            toolbarAnimator.setIntValues(toolbarBackground.alpha, 255)

            scrimAnimator.start()
            toolbarAnimator.start()

        } else if (isScrimmed && headerScrollY < scrimAnimPivot) {
            isScrimmed = false
            scrimAnimator.cancel()
            toolbarAnimator.cancel()

            scrimAnimator.interpolator = LINEAR_OUT_SLOW_IN_INTERPOLATOR
            toolbarAnimator.interpolator = LINEAR_OUT_SLOW_IN_INTERPOLATOR

            scrimAnimator.setIntValues(scrim.alpha, 100)
            toolbarAnimator.setIntValues(toolbarBackground.alpha, 0)

            scrimAnimator.start()
            toolbarAnimator.start()
        }

    }


}
