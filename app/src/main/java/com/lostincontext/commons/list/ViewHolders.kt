package com.lostincontext.commons.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lostincontext.R

fun createLoadMoreViewHolder(inflater: LayoutInflater,
                             parent: ViewGroup): DummyViewHolder {
    val view = inflater.inflate(R.layout.item_loading_more, parent, false)
    return DummyViewHolder(view)

}

