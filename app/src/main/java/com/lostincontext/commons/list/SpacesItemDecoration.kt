package com.lostincontext.commons.list


import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class SpacesItemDecoration(space: Int,
                           private val span: Int) : RecyclerView.ItemDecoration() {

    private val space: Int = space / 2


    override fun getItemOffsets(outRect: Rect,
                                view: View,
                                parent: RecyclerView,
                                state: RecyclerView.State?) {


        val position = parent.getChildAdapterPosition(view)
        val column = position % span

        if (position >= span) {
            outRect.top = space
        }

        outRect.left = column * space / span
        outRect.right = space - (column + 1) * space / span

    }

}