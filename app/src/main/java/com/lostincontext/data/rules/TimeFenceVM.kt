package com.lostincontext.data.rules


import com.google.android.gms.awareness.fence.AwarenessFence

class TimeFenceVM(val state: State,
                  val starting: Long,
                  val ending: Long) : FenceVM {


    override fun build(builder: FenceBuilder): AwarenessFence = builder.time(this)

    override fun describe(descriptor: FenceDescriptor): String = descriptor.time(this)

    override fun giveIcon(iconGiver: FenceIconGiver,
                          icons: MutableList<Int>) {
        iconGiver.time(this, icons)
    }

    enum class State {
        IN_INTERVAL,
        IN_DAILY_INTERVAL,
        IN_SUNDAY_INTERVAL,
        IN_MONDAY_INTERVAL,
        IN_TUESDAY_INTERVAL,
        IN_WEDNESDAY_INTERVAL,
        IN_THURSDAY_INTERVAL,
        IN_FRIDAY_INTERVAL,
        IN_SATURDAY_INTERVAL
    }
}
