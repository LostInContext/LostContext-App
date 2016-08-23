package com.lostincontext.ruledetails.pick


import android.view.LayoutInflater
import android.view.ViewGroup
import com.lostincontext.R
import com.lostincontext.commons.list.Adapter
import com.lostincontext.commons.list.Section
import com.lostincontext.commons.list.SectionViewHolder
import com.lostincontext.commons.list.ViewHolder
import com.lostincontext.utils.logE

class RulePickAdapter(private val callback: PickerDialogCallback) : Adapter<ViewHolder>() {

    private var count: Int = 0

    private var sections: List<Section<*>> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        val layoutInflater = LayoutInflater.from(parent.context)

        when (viewType) {
            R.id.view_type_section_header -> return SectionViewHolder.create(layoutInflater,
                                                                             parent)

            R.id.view_type_grid_bottom_sheet_item ->
                return BottomSheetGridItemViewHolder.create(layoutInflater,
                                                            parent,
                                                            callback)
        }

        return null
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
        var finalPosition = position

        sections.forEachIndexed { i, section ->
            val sectionSize = section.size()
            if (finalPosition >= sectionSize) {
                finalPosition -= sectionSize
                return@forEachIndexed
            }
            section.onBindViewHolder(holder, finalPosition)
            return
        }
        logE(TAG) { "onBindViewHolder: ,  position not found ! " + finalPosition }
    }


    override fun getItemViewType(position: Int): Int {
        var finalPosition = position

        sections.forEachIndexed { i, section ->
            val sectionSize = section.size()
            if (finalPosition >= sectionSize) {
                finalPosition -= sectionSize
                return@forEachIndexed
            }
            if (finalPosition == 0) return section.getHeaderViewType()
            return section.itemViewType
        }

        throw RuntimeException("position not found !")
    }

    override fun getItemCount(): Int = count

    fun setSections(sections: List<Section<*>>) {
        this.sections = sections
        count()
        notifyDataSetChanged()
    }

    private fun count() {
        count = 0
        var i = 0
        val size = sections.size
        while (i < size) {
            count += sections[i].size()
            i++
        }
    }

    companion object {

        private val TAG = RulePickAdapter::class.java.simpleName
    }
}
