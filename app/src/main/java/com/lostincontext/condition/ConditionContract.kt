package com.lostincontext.condition

import android.os.Bundle
import com.google.android.gms.maps.model.LatLng
import com.lostincontext.commons.BasePresenter
import com.lostincontext.commons.BaseView
import com.lostincontext.commons.list.Section
import com.lostincontext.condition.pick.GridBottomSheetItem
import com.lostincontext.condition.pick.PickerDialogCallback
import com.lostincontext.condition.pick.PlusButtonCallback
import com.lostincontext.data.rulesV2.AtomicCondition
import com.lostincontext.data.rulesV2.Condition
import com.lostincontext.condition.AtomicConditionItem


interface ConditionContract {


    interface View : BaseView<Presenter> {

        fun displayFenceChoice()
        fun checkPermissionsAndShowLocationPicker(name: String, item: GridBottomSheetItem)
        fun add(atomicCondition: AtomicCondition, isFirst: Boolean)
        fun remove(atomic: AtomicCondition)
        fun notifyChange(atomic: AtomicCondition)
        fun setupCondition(condition: Condition)
    }

    interface Presenter : BasePresenter,
                          PlusButtonCallback,
                          PickerDialogCallback,
                          AtomicConditionItem.Callback {

        fun onMenuItemClick(itemId: Int): Boolean
        fun saveState(outState: Bundle)
        fun provideFenceChoices(): List<Section<*>>
        fun onPlacePicked(placeName: String, item: GridBottomSheetItem, latLng: LatLng)
    }

}

const val EXTRA_CONDITION = "condition"
const val PLAYLIST_PICKER_REQUEST_CODE = 9001
const val CONDITION_REQUEST_CODE = 9002
