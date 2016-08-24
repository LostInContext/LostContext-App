package com.lostincontext.data.rules

import com.google.android.gms.awareness.fence.AwarenessFence

data class CompositeFenceVM(val fenceVMs: List<FenceVM>,
                            val operator: Operator) : FenceVM {

    enum class Operator {
        AND,
        OR
    }

    override fun build(builder: FenceBuilder): AwarenessFence = builder.composite(this)

    override fun describe(descriptor: FenceDescriptor): String = descriptor.composite(this)

    override fun giveIcon(iconGiver: FenceIconGiver, icons: MutableList<Int>) = iconGiver.composite(this, icons)
}
