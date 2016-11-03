package com.lostincontext.data.fences

import com.lostincontext.R

class FenceIconGiver {


    fun composite(compositeFence: CompositeFenceVM, icons: MutableList<Int>): Unit {
        val fenceVMs = compositeFence.fenceVMs
        fenceVMs.forEach { fenceVM -> fenceVM.giveIcon(this, icons) }
    }

    fun location(fence: LocationFenceVM, icons: MutableList<Int>): Unit {
        when (fence.name) {
            LocationFenceVM.HOME -> icons.add(R.drawable.ic_home_24)
            LocationFenceVM.WORK -> icons.add(R.drawable.ic_work_24)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun not(fence: NotFenceVM, icons: MutableList<Int>): Unit {
    }

    @Suppress("UNUSED_PARAMETER")
    fun headphone(fence: HeadphoneFenceVM, icons: MutableList<Int>): Unit {
        icons.add(R.drawable.ic_headset_24)
    }


    fun detectedActivity(fence: DetectedActivityFenceVM, icons: MutableList<Int>): Unit {
        when (fence.type) {
            DetectedActivityFenceVM.Type.IN_VEHICLE -> icons.add(R.drawable.ic_car_24)
            DetectedActivityFenceVM.Type.RUNNING -> icons.add(R.drawable.ic_run_24)
            DetectedActivityFenceVM.Type.ON_BICYCLE -> icons.add(R.drawable.ic_bike_24)
            DetectedActivityFenceVM.Type.WALKING -> icons.add(R.drawable.ic_walk_24)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun time(timeFenceVM: TimeFenceVM, icons: MutableList<Int>) {
    }

}
