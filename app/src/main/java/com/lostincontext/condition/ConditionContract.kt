package com.lostincontext.condition

import com.lostincontext.commons.BasePresenter
import com.lostincontext.commons.BaseView
import com.lostincontext.ruledetails.pick.PlusButtonCallback


interface ConditionContract {

    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter,
                          PlusButtonCallback {
        fun onMenuItemClick(itemId: Int): Boolean

    }
}
