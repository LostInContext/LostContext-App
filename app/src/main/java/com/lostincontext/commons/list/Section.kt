package com.lostincontext.commons.list


import android.support.v7.widget.RecyclerView
import com.lostincontext.R

abstract class Section<out Model>(val title: String, private val models: List<Model>, val itemViewType: Int) {

    fun getHeaderViewType(): Int = R.id.view_type_section_header

    fun size(): Int = models.size + 1

    operator fun get(i: Int): Model = models[i]


    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var finalPosition = position
        if (finalPosition == 0) {
            (holder as SectionViewHolder).bindTo(this)
            return
        }
        finalPosition--
        onBindItemViewHolder(holder, finalPosition)

    }

    protected abstract fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int)
}
