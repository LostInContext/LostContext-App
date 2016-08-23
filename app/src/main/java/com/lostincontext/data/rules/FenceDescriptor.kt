package com.lostincontext.data.rules

import android.text.TextUtils

class FenceDescriptor {

    fun not(notFence: NotFenceVM): String {
        val description = notFence.fenceVM.describe(this)
        return "not { $description } "
    }

    fun composite(compositeFence: CompositeFenceVM): String {
        val fenceVMs = compositeFence.fenceVMs
        var description = ""
        for (fenceVM in fenceVMs) {
            if (!TextUtils.isEmpty(description)) description += getSeparator(compositeFence)
            description += fenceVM.describe(this)
        }
        return description
    }

    private fun getSeparator(compositeFence: CompositeFenceVM): String {
        when (compositeFence.operator) {
            CompositeFenceVM.Operator.AND -> return " and "
            CompositeFenceVM.Operator.OR -> return " or "
        }
    }

    fun location(fence: LocationFenceVM): String {
        return "at " + fence.name
    }


    fun time(fence: TimeFenceVM): String {
        return fence.state.name
    }

    fun headphone(fence: HeadphoneFenceVM): String {
        when (fence.state) {
            HeadphoneFenceVM.State.PLUGGED_IN -> return " plugging in the headphones"
            HeadphoneFenceVM.State.PLUGGED_OUT -> return " unplugging the headphones"
        }


    }

    fun detectedActivity(fence: DetectedActivityFenceVM): String {

        val name = fence.type.name

        when (fence.state) {
            DetectedActivityFenceVM.State.STARTING -> return " starting $name"
            DetectedActivityFenceVM.State.DURING -> return " during $name"
            DetectedActivityFenceVM.State.STOPPING -> return " stopping $name"
        }
    }
}
