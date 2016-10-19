package com.lostincontext.data.rulesV2

import com.lostincontext.data.rules.FenceVM
import com.lostincontext.data.rules.NotFenceVM


class AtomicCondition {

    enum class Modifier { NONE, NOT }

    constructor(fence: FenceVM, modifier: Modifier = Modifier.NONE) {
        this.fence = fence
        this.modifier = modifier
    }


    val fence: FenceVM
        get() {
            when (modifier) {
                Modifier.NONE -> return field
                Modifier.NOT -> return NotFenceVM(field)
            }
        }

    var modifier: Modifier


    fun toggle() {
        when (modifier) {
            Modifier.NONE -> modifier = Modifier.NOT
            Modifier.NOT -> modifier = Modifier.NONE
        }
    }
}


