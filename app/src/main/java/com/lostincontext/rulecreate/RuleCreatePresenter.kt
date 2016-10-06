package com.lostincontext.rulecreate

import android.os.Bundle
import javax.inject.Inject

class RuleCreatePresenter : RuleCreateContract.Presenter {
    override fun saveState(outState: Bundle) {
    }


    private val view: RuleCreateContract.View

    @Inject internal constructor(view: RuleCreateContract.View,
                                 icicle: Bundle?) {

        this.view = view

        if (icicle != null) {
            // todo fetch saved state here
        }

    }

    override fun start() {
    }

    override fun onPlusButtonClick() {
        view.showRuleDetailsActivity()
    }

    override fun onMenuItemClick(itemId: Int): Boolean {

        return false
    }
}