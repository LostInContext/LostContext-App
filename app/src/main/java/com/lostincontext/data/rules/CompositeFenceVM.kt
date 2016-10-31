package com.lostincontext.data.rules

import com.google.android.gms.awareness.fence.AwarenessFence
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
data class CompositeFenceVM(val fenceVMs: List<FenceVM>,
                            val operator: Operator) : FenceVM {

    enum class Operator {
        AND,
        OR
    }

    override fun build(builder: FenceBuilder): AwarenessFence = builder.composite(this)

    override fun giveIcon(iconGiver: FenceIconGiver, icons: MutableList<Int>) = iconGiver.composite(this, icons)

    override fun name(fenceNamer: FenceNamer) = fenceNamer.composite(this)

    companion object {
        @JvmField val CREATOR = PaperParcelable.Creator(CompositeFenceVM::class.java)
    }

}
