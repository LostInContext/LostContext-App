package com.lostincontext.data.rulesV2

import com.lostincontext.data.rules.FenceVM
import com.lostincontext.data.rules.NotFenceVM
import com.lostincontext.data.rulesV2.AtomicCondition.Modifier.NONE
import com.lostincontext.data.rulesV2.AtomicCondition.Modifier.NOT


data class AtomicCondition(val fence: FenceVM, var modifier: Modifier = Modifier.NONE) {

    enum class Modifier { NONE, NOT }


    fun toggle() {
        modifier = when (modifier) {
            NONE -> NOT
            NOT -> NONE
        }
    }

    fun getModifiedFence(): FenceVM {
        when (modifier) {
            NONE -> return fence
            NOT -> return NotFenceVM(fence)
        }
    }


}


