package com.lostincontext.data.rules


import com.google.android.gms.awareness.fence.AwarenessFence

data class DetectedActivityFenceVM(val type: Type,
                                   val state: State) : FenceVM {

    enum class Type {
        RUNNING,
        WALKING,
        IN_VEHICLE,
        ON_BICYCLE,
        ON_FOOT
    }

    enum class State {
        STARTING,
        DURING,
        STOPPING
    }

    override fun build(builder: FenceBuilder): AwarenessFence = builder.detectedActivity(this)

    override fun describe(descriptor: FenceDescriptor): String = descriptor.detectedActivity(this)

    override fun giveIcon(iconGiver: FenceIconGiver): List<Int> = iconGiver.detectedActivity(this)


}
