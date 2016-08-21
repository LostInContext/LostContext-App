package com.lostincontext.commons.list


import android.support.v7.widget.RecyclerView

abstract class Adapter<VH : ViewHolder> : RecyclerView.Adapter<VH>() {

    override fun onFailedToRecycleView(holder: VH?): Boolean {
        return holder!!.onFailedToRecycleView()
    }

    override final fun onBindViewHolder(holder: VH, position: Int) {
        // no use
    }
}
