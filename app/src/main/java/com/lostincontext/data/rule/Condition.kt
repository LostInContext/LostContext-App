package com.lostincontext.data.rule

import com.google.android.gms.awareness.fence.AwarenessFence
import com.lostincontext.data.fences.FenceBuilder
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
data class Condition(val atomics: MutableList<AtomicCondition>) : PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelable.Creator(Condition::class.java)
    }


    fun buildAwarenessFence(builder: FenceBuilder): AwarenessFence {
        val fences = atomics.map { it.getModifiedFence().build(builder) }
        return AwarenessFence.or(fences)
    }

    fun validate(): Boolean {
        if (atomics.isEmpty()) return false
        return true
    }


}