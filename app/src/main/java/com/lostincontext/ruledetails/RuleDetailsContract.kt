package com.lostincontext.ruledetails

import android.app.PendingIntent
import com.google.android.gms.maps.model.LatLng
import com.lostincontext.commons.BasePresenter
import com.lostincontext.commons.BaseView
import com.lostincontext.commons.list.Section
import com.lostincontext.data.GridBottomSheetItem
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.rules.Rule
import com.lostincontext.ruledetails.items.FenceItem
import com.lostincontext.ruledetails.items.FenceItemCallback
import com.lostincontext.ruledetails.pick.PickerDialogCallback
import com.lostincontext.ruledetails.pick.PlusButtonCallback
import java.util.*


object RuleDetailsContract {

    enum class RuleErrors {
        NO_TITLE,
        NO_CONDITION,
        NO_PLAYLIST,
        SAVE_ERROR
    }

    val LINK_CHANGED = Any()


    interface View : BaseView<Presenter> {

        fun setItems(items: List<FenceItem>)

        fun displayFenceChoice()

        fun notifyItemInserted(position: Int)

        fun pickAPlaylist()

        fun notifyItemChanged(position: Int, payload: Any)

        fun showPlaylist(playlist: Playlist)

        fun checkPermissionsAndShowLocationPicker(name: String, item: GridBottomSheetItem)

        fun setRule(rule: Rule)

        fun getPendingIntentFor(playlist: Playlist): PendingIntent

        fun finishActivity()

        fun showSnack(errors: EnumSet<RuleErrors>)
    }


    interface Presenter : BasePresenter, FenceItemCallback, PlusButtonCallback, PickerDialogCallback {

        fun provideFenceChoices(): List<Section<*>>

        fun onPlaylistPicked(playlist: Playlist)

        fun onPlacePicked(placeName: String, item: GridBottomSheetItem, latLng: LatLng)

        fun onMenuItemClick(itemId: Int): Boolean
    }
}
