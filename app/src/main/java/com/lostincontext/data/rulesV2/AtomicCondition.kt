package com.lostincontext.data.rulesV2

import com.lostincontext.data.rules.FenceVM
import com.lostincontext.data.rules.NotFenceVM


class AtomicCondition {

    enum class Modifier { NONE, NOT }

    constructor(fence: FenceVM, modifier: Modifier) {
        this.fence = fence
        this.modifier = modifier
    }


    val fence: FenceVM
    var modifier: Modifier


    fun toggle() {
        when (modifier) {
            Modifier.NONE -> modifier = Modifier.NOT
            Modifier.NOT -> modifier = Modifier.NONE
        }
    }

    fun getAtomic(): FenceVM {
        when (modifier) {

            AtomicCondition.Modifier.NONE -> return fence
            AtomicCondition.Modifier.NOT -> return NotFenceVM(fence)
        }
    }

}


