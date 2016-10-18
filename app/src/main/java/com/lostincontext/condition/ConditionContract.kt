package com.lostincontext.condition

import com.google.android.gms.maps.model.LatLng
import com.lostincontext.commons.BasePresenter
import com.lostincontext.commons.BaseView
import com.lostincontext.commons.list.Section
import com.lostincontext.condition.pick.GridBottomSheetItem
import com.lostincontext.condition.pick.PickerDialogCallback
import com.lostincontext.condition.pick.PlusButtonCallback
import com.lostincontext.ruledetails.items.FenceItem
import com.lostincontext.ruledetails.items.FenceItemCallback


interface ConditionContract {

    interface View : BaseView<Presenter> {
        fun displayFenceChoice()
        fun notifyItemInserted(position: Int)
        fun notifyItemChanged(position: Int, payload: Any)
        fun checkPermissionsAndShowLocationPicker(name: String, item: GridBottomSheetItem)
        fun setItems(items: List<FenceItem>)

    }

    interface Presenter : BasePresenter,
                          PlusButtonCallback,
                          PickerDialogCallback,
                          FenceItemCallback {
        fun onMenuItemClick(itemId: Int): Boolean
        fun provideFenceChoices(): List<Section<*>>
        fun onPlacePicked(placeName: String, item: GridBottomSheetItem, latLng: LatLng)

    }
}
