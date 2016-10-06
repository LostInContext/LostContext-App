package com.lostincontext.rulecreate

import android.os.Bundle
import com.lostincontext.commons.BasePresenter
import com.lostincontext.commons.BaseView
import com.lostincontext.ruledetails.pick.PlusButtonCallback

object RuleCreateContract {
    interface View : BaseView<Presenter> {
        fun showRuleDetailsActivity()

    }

    interface Presenter : BasePresenter, PlusButtonCallback {
        fun onMenuItemClick(itemId: Int): Boolean
        fun saveState(outState: Bundle)


    }
}