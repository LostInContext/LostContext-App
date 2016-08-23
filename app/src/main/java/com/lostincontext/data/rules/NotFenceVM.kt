package com.lostincontext.data.rules


import com.google.android.gms.awareness.fence.AwarenessFence

data class NotFenceVM(val fenceVM: FenceVM) : FenceVM {


    override fun build(builder: FenceBuilder): AwarenessFence = builder.not(this)

    override fun describe(descriptor: FenceDescriptor): String = descriptor.not(this)

    override fun giveIcon(iconGiver: FenceIconGiver): List<Int> = iconGiver.not(this)
}
