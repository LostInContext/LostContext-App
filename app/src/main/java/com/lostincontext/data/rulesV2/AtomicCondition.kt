package com.lostincontext.data.rulesV2

import com.lostincontext.data.rules.FenceVM


data class AtomicCondition(val fence : FenceVM)

// atomic condition == fenceVM ???
// not -> just another FenceVM after all