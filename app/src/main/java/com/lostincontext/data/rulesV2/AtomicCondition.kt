package com.lostincontext.data.rulesV2

import com.lostincontext.data.rules.FenceVM
import com.lostincontext.data.rules.NotFenceVM


data class AtomicCondition(private val fence: FenceVM, private val modifier: Modifier) {

    enum class Modifier { NONE, NOT }

    fun getAtomic(): FenceVM {
        when (modifier) {

            Modifier.NONE -> return fence
            Modifier.NOT -> return NotFenceVM(fence)
        }

    }


}


