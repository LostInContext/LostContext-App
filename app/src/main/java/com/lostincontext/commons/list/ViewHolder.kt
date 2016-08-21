package com.lostincontext.commons.list

import android.support.v7.widget.RecyclerView
import android.view.View
import com.lostincontext.BuildConfig
import com.lostincontext.utils.logW

abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    /**
     * @return `true` if you have been able to rid the View of its transient animations, false otherwise.
     * * It is strongly encouraged to clear the view of its animations, this way it can be reused.
     * Otherwise, RecyclerView has to create a new child view.
     * *
     * @see RecyclerView.Adapter.onFailedToRecycleView
     */
    open fun onFailedToRecycleView(): Boolean {
        logW(TAG) { "onFailedToRecycleItem" + toString() }
        if (BuildConfig.DEBUG) {
            throw RuntimeException("you need to rid the ViewHolder of its transient animations")
        }
        return false
    }

    companion object {

        private val TAG = ViewHolder::class.java.simpleName
    }


}
