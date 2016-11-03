package com.lostincontext.data.fences


import com.google.android.gms.awareness.fence.AwarenessFence
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
data class DetectedActivityFenceVM(val type: Type,
                                   val state: State) : FenceVM {

    enum class Type {
        RUNNING,
        WALKING,
        IN_VEHICLE,
        ON_BICYCLE,
    }

    enum class State {
        STARTING,
        DURING,
        STOPPING
    }

    override fun build(builder: FenceBuilder): AwarenessFence = builder.detectedActivity(this)

    override fun giveIcon(iconGiver: FenceIconGiver,
                          icons: MutableList<Int>) = iconGiver.detectedActivity(this, icons)

    override fun name(fenceNamer: FenceNamer) = fenceNamer.detectedActivity(this)

    companion object {
      @JvmField val CREATOR = PaperParcelable.Creator(DetectedActivityFenceVM::class.java)
    }

}
