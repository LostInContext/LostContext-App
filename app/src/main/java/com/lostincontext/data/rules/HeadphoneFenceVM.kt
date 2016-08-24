package com.lostincontext.data.rules


import com.google.android.gms.awareness.fence.AwarenessFence

data class HeadphoneFenceVM(val state: State) : FenceVM {

    enum class State {
        PLUGGED_IN,
        PLUGGED_OUT
    }


    override fun build(builder: FenceBuilder): AwarenessFence = builder.headphone(this)

    override fun describe(descriptor: FenceDescriptor): String = descriptor.headphone(this)

    override fun giveIcon(iconGiver: FenceIconGiver,
                          icons: MutableList<Int>): Unit = iconGiver.headphone(this, icons)
}
