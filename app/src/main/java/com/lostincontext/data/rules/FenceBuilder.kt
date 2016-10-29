package com.lostincontext.data.rules

import com.google.android.gms.awareness.fence.AwarenessFence
import com.google.android.gms.awareness.fence.DetectedActivityFence
import com.google.android.gms.awareness.fence.HeadphoneFence
import com.google.android.gms.awareness.fence.LocationFence.*
import com.google.android.gms.awareness.fence.TimeFence
import com.google.android.gms.location.DetectedActivity
import java.util.*

class FenceBuilder {


    fun not(notFence: NotFenceVM): AwarenessFence {
        val awarenessFence = notFence.fenceVM.build(this)
        return AwarenessFence.not(awarenessFence)
    }

    fun composite(compositeFence: CompositeFenceVM): AwarenessFence {
        val fenceVMs = compositeFence.fenceVMs

        val awarenessFences = arrayOfNulls<AwarenessFence>(fenceVMs.size)

        var i = 0
        val count = fenceVMs.size
        while (i < count) {
            awarenessFences[i] = fenceVMs[i].build(this)
            i++
        }

        when (compositeFence.operator) {
            CompositeFenceVM.Operator.AND -> return AwarenessFence.and(*awarenessFences)
            CompositeFenceVM.Operator.OR -> return AwarenessFence.or(*awarenessFences)
        }
    }

    @SuppressWarnings("MissingPermission")
    fun location(fence: LocationFenceVM): AwarenessFence {
        val latitude = fence.latitude
        val longitude = fence.longitude
        val radius = fence.radius
        val dwellTimeMillis = fence.dwellTimeMillis

        when (fence.state) {
            LocationFenceVM.State.IN -> return `in`(latitude, longitude, radius, dwellTimeMillis)
            LocationFenceVM.State.ENTERING -> return entering(latitude, longitude, radius)
            LocationFenceVM.State.EXITING -> return exiting(latitude, longitude, radius)
        }
    }

    fun time(fence: TimeFenceVM): AwarenessFence {
        val startingTime = fence.starting
        val endingTime = fence.ending
        val state = fence.state

        when (state) {

            TimeFenceVM.State.IN_INTERVAL -> return TimeFence.inInterval(startingTime, endingTime)
            TimeFenceVM.State.IN_DAILY_INTERVAL -> return TimeFence.inDailyInterval(TimeZone.getDefault(),
                                                                                    startingTime,
                                                                                    endingTime)
            TimeFenceVM.State.IN_SUNDAY_INTERVAL -> return TimeFence.inSundayInterval(TimeZone.getDefault(),
                                                                                      startingTime,
                                                                                      endingTime)
            TimeFenceVM.State.IN_MONDAY_INTERVAL -> return TimeFence.inMondayInterval(TimeZone.getDefault(),
                                                                                      startingTime,
                                                                                      endingTime)
            TimeFenceVM.State.IN_TUESDAY_INTERVAL -> return TimeFence.inTuesdayInterval(TimeZone.getDefault(),
                                                                                        startingTime,
                                                                                        endingTime)
            TimeFenceVM.State.IN_WEDNESDAY_INTERVAL -> return TimeFence.inWednesdayInterval(TimeZone.getDefault(),
                                                                                            startingTime,
                                                                                            endingTime)
            TimeFenceVM.State.IN_THURSDAY_INTERVAL -> return TimeFence.inThursdayInterval(TimeZone.getDefault(),
                                                                                          startingTime,
                                                                                          endingTime)
            TimeFenceVM.State.IN_FRIDAY_INTERVAL -> return TimeFence.inFridayInterval(TimeZone.getDefault(),
                                                                                      startingTime,
                                                                                      endingTime)
            TimeFenceVM.State.IN_SATURDAY_INTERVAL -> return TimeFence.inSaturdayInterval(TimeZone.getDefault(),
                                                                                          startingTime,
                                                                                          endingTime)
        }
    }

    fun headphone(fence: HeadphoneFenceVM): AwarenessFence {
        when (fence.state) {

            HeadphoneFenceVM.State.PLUGGED_IN -> return HeadphoneFence.pluggingIn()

            HeadphoneFenceVM.State.PLUGGED_OUT -> return HeadphoneFence.unplugging()
        }


    }

    fun detectedActivity(fence: DetectedActivityFenceVM): AwarenessFence {
        val type: Int
        when (fence.type) {
            DetectedActivityFenceVM.Type.WALKING -> type = DetectedActivity.WALKING
            DetectedActivityFenceVM.Type.IN_VEHICLE -> type = DetectedActivity.IN_VEHICLE
            DetectedActivityFenceVM.Type.ON_BICYCLE -> type = DetectedActivity.ON_BICYCLE
            DetectedActivityFenceVM.Type.RUNNING -> type = DetectedActivity.RUNNING
        }

        when (fence.state) {
            DetectedActivityFenceVM.State.STARTING -> return DetectedActivityFence.starting(type)
            DetectedActivityFenceVM.State.DURING -> return DetectedActivityFence.during(type)
            DetectedActivityFenceVM.State.STOPPING -> return DetectedActivityFence.stopping(type)
        }
    }

}
