package com.lostincontext.data.rulesV2

import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
data class Condition(val atomics: MutableList<AtomicCondition>) : PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelable.Creator(Condition::class.java)
    }
}