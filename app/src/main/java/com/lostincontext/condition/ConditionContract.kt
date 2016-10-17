package com.lostincontext.condition

import com.lostincontext.commons.BasePresenter
import com.lostincontext.commons.BaseView
import com.lostincontext.commons.list.Section
import com.lostincontext.condition.pick.PickerDialogCallback
import com.lostincontext.condition.pick.PlusButtonCallback


interface ConditionContract {

    interface View : BaseView<Presenter> {
        fun displayFenceChoice()

    }

    interface Presenter : BasePresenter,
                          PlusButtonCallback,
                          PickerDialogCallback {
        fun onMenuItemClick(itemId: Int): Boolean
        fun provideFenceChoices(): List<Section<*>>

    }
}
