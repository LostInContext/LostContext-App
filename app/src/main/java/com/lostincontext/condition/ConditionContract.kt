package com.lostincontext.condition

import com.google.android.gms.maps.model.LatLng
import com.lostincontext.commons.BasePresenter
import com.lostincontext.commons.BaseView
import com.lostincontext.commons.list.Section
import com.lostincontext.condition.pick.GridBottomSheetItem
import com.lostincontext.condition.pick.PickerDialogCallback
import com.lostincontext.condition.pick.PlusButtonCallback
import com.lostincontext.rulecreate.ConditionItem
import com.lostincontext.ruledetails.items.FenceItemCallback
import java.util.*


interface ConditionContract {

    interface View : BaseView<Presenter> {
        fun displayFenceChoice()
        fun notifyItemInserted(item: ConditionItem, position: Int)
        fun notifyItemChanged(item: ConditionItem, position: Int, payload: Any)
        fun checkPermissionsAndShowLocationPicker(name: String, item: GridBottomSheetItem)
        fun setItems(items: ArrayList<ConditionItem>)

    }

    interface Presenter : BasePresenter,
                          PlusButtonCallback,
                          PickerDialogCallback,
                          FenceItemCallback,
                          ConditionItem.Callback {

        fun onMenuItemClick(itemId: Int): Boolean
        fun provideFenceChoices(): List<Section<*>>
        fun onPlacePicked(placeName: String, item: GridBottomSheetItem, latLng: LatLng)

    }
}
