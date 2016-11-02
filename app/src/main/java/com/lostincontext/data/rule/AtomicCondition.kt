package com.lostincontext.data.rule

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.lostincontext.data.fences.FenceVM
import com.lostincontext.data.fences.NotFenceVM
import com.lostincontext.data.rule.AtomicCondition.Modifier.NONE
import com.lostincontext.data.rule.AtomicCondition.Modifier.NOT
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
@JsonIgnoreProperties(ignoreUnknown = true)
data class AtomicCondition(val fence: FenceVM,
                           var modifier: Modifier = Modifier.NONE) : PaperParcelable {

    enum class Modifier { NONE, NOT }


    fun toggle() {
        modifier = when (modifier) {
            NONE -> NOT
            NOT -> NONE
        }
    }

    fun getModifiedFence(): FenceVM {
        when (modifier) {
            NONE -> return fence
            NOT -> return NotFenceVM(fence)
        }
    }

    companion object {
        @JvmField val CREATOR = PaperParcelable.Creator(AtomicCondition::class.java)
    }


}


