package com.lostincontext.rulecreate

import android.os.Bundle
import com.lostincontext.commons.BasePresenter
import com.lostincontext.commons.BaseView
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.ruledetails.items.PlaylistInEditScreenViewHolder
import com.lostincontext.ruledetails.pick.PlusButtonCallback

object RuleCreateContract {
    interface View : BaseView<Presenter> {
        fun showRuleDetailsActivity()
        fun pickAPlaylist()
        fun setPlaylist(playlist: Playlist)

    }

    interface Presenter : BasePresenter,
                          PlusButtonCallback,
                          PlaylistInEditScreenViewHolder.Callback,
                          ConditionItem.Callback {

        fun onMenuItemClick(itemId: Int): Boolean
        fun saveState(outState: Bundle)
        fun onPlaylistPicked(playlist: Playlist)

    }
}