package com.lostincontext.data.fences


import com.google.android.gms.awareness.fence.AwarenessFence
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
data class NotFenceVM(val fenceVM: FenceVM) : FenceVM {


    override fun build(builder: FenceBuilder): AwarenessFence = builder.not(this)

    override fun giveIcon(iconGiver: FenceIconGiver,
                          icons: MutableList<Int>) = iconGiver.not(this, icons)

    override fun name(fenceNamer: FenceNamer) = fenceNamer.not(this)

    companion object {
        @JvmField val CREATOR = PaperParcelable.Creator(NotFenceVM::class.java)
    }
}
