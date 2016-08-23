package com.lostincontext.data.rules

import com.lostincontext.R
import java.util.*

class FenceIconGiver {


    fun composite(compositeFence: CompositeFenceVM): List<Int> {
        val fenceVMs = compositeFence.fenceVMs
        val icons = ArrayList<Int>()
        fenceVMs.forEach { fenceVM -> icons.addAll(fenceVM.giveIcon(this)) }
        return icons
    }

    fun location(fence: LocationFenceVM): List<Int> {
        val iconList = ArrayList<Int>()
        when (fence.name) {
            LocationFenceVM.HOME -> iconList.add(R.drawable.ic_home_24)
            LocationFenceVM.WORK -> iconList.add(R.drawable.ic_work_24)
        }
        return iconList
    }

    @Suppress("UNUSED_PARAMETER")
    fun not(fence: NotFenceVM): List<Int> {
        return emptyList()
    }

    @Suppress("UNUSED_PARAMETER")
    fun headphone(fence: HeadphoneFenceVM): List<Int> {
        return listOf(R.drawable.ic_headset_24)
    }


    fun detectedActivity(fence: DetectedActivityFenceVM): List<Int> {
        val iconList = ArrayList<Int>()
        when (fence.type) {
            DetectedActivityFenceVM.Type.IN_VEHICLE -> iconList.add(R.drawable.ic_car_24)
            DetectedActivityFenceVM.Type.RUNNING -> iconList.add(R.drawable.ic_run_24)
            DetectedActivityFenceVM.Type.ON_BICYCLE -> iconList.add(R.drawable.ic_bike_24)
            DetectedActivityFenceVM.Type.WALKING,
            DetectedActivityFenceVM.Type.ON_FOOT -> iconList.add(R.drawable.ic_walk_24)
        }
        return iconList
    }

}
