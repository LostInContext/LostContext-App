package com.lostincontext.data.fences


import com.google.android.gms.awareness.fence.AwarenessFence
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
data class HeadphoneFenceVM(val state: State) : FenceVM {

    enum class State {
        PLUGGED_IN,
        PLUGGED_OUT
    }


    override fun build(builder: FenceBuilder): AwarenessFence = builder.headphone(this)

    override fun giveIcon(iconGiver: FenceIconGiver,
                          icons: MutableList<Int>): Unit = iconGiver.headphone(this, icons)

    override fun name(fenceNamer: FenceNamer) = fenceNamer.headphone(this)

    companion object {
        @JvmField val CREATOR = PaperParcelable.Creator(HeadphoneFenceVM::class.java)
    }
}
