package com.lostincontext.data.rules

import android.support.annotation.StringDef
import com.google.android.gms.awareness.fence.AwarenessFence
import com.google.android.gms.maps.model.LatLng
import kotlin.annotation.AnnotationRetention.SOURCE

data class LocationFenceVM(val state: State,
                           val latitude: Double,
                           val longitude: Double,
                           @LocationName val name: String,
                           val radius: Double = 10.0) : FenceVM {


    constructor(@LocationName locationName: String, latLng: LatLng) : this(State.IN,
                                                                           latLng.latitude,
                                                                           latLng.longitude,
                                                                           locationName)


    enum class State {
        IN,
        ENTERING,
        EXITING
    }

    @Retention(SOURCE)
    @StringDef(HOME, WORK)
    annotation class LocationName

    val dwellTimeMillis: Long = 10000 //10 seconds


    override fun build(builder: FenceBuilder): AwarenessFence {
        return builder.location(this)
    }

    override fun describe(descriptor: FenceDescriptor): String {
        return descriptor.location(this)
    }

    override fun giveIcon(iconGiver: FenceIconGiver,
                          icons: MutableList<Int>) {
        return iconGiver.location(this, icons)
    }

    companion object {
        const val HOME = "HOME"
        const val WORK = "WORK"

    }
}
