package com.lostincontext.data.rules

import android.support.annotation.StringDef
import com.google.android.gms.awareness.fence.AwarenessFence
import com.google.android.gms.maps.model.LatLng
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable
import kotlin.annotation.AnnotationRetention.SOURCE

@PaperParcel
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

    @Transient
    val dwellTimeMillis: Long = 10000 //10 seconds


    override fun build(builder: FenceBuilder): AwarenessFence = builder.location(this)

    override fun giveIcon(iconGiver: FenceIconGiver,
                          icons: MutableList<Int>) = iconGiver.location(this, icons)

    override fun name(fenceNamer: FenceNamer) = fenceNamer.location(this)

    companion object {
        const val HOME = "home"
        const val WORK = "work"
        @JvmField val CREATOR = PaperParcelable.Creator(LocationFenceVM::class.java)
    }

}
