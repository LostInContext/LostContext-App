package com.lostincontext.rulecreate

import android.os.Bundle
import com.lostincontext.commons.BasePresenter
import com.lostincontext.commons.BaseView
import com.lostincontext.condition.pick.PlusButtonCallback
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.rule.Condition
import com.lostincontext.data.rule.Rule

object RuleCreateContract {
    interface View : BaseView<Presenter> {
        fun pickACondition(index: Int)
        fun pickAPlaylist()
        fun setPlaylist(playlist: Playlist?)
        fun setConditions(conditions: List<Condition>)
        fun remove(condition: Condition)
        fun setup(rule: Rule)

    }

    interface Presenter : BasePresenter,
                          PlusButtonCallback,
                          PlaylistItem.Callback,
                          ConditionItem.Callback {

        fun onMenuItemClick(itemId: Int): Boolean
        fun saveState(outState: Bundle)
        fun onPlaylistPicked(playlist: Playlist)
        fun onConditionAdded(condition: Condition)
    }


    const val EXTRA_INDEX = "index"
    const val EXTRA_RULE = "rule"

}