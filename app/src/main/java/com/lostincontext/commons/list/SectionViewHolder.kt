package com.lostincontext.commons.list


import android.view.LayoutInflater
import android.view.ViewGroup

import com.lostincontext.databinding.ItemSectionHeaderBinding

class SectionViewHolder(private val binding: ItemSectionHeaderBinding) : ViewHolder(binding.root) {


    fun bindTo(section: Section<*>) {
        binding.title = section.title
    }

    companion object {

        fun create(layoutInflater: LayoutInflater,
                   parent: ViewGroup): SectionViewHolder {
            val headerBinding = ItemSectionHeaderBinding.inflate(layoutInflater,
                                                                 parent,
                                                                 false)
            return SectionViewHolder(headerBinding)
        }
    }

}
