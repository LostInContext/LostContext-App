package com.lostincontext.mainscreen

import android.app.PendingIntent
import com.lostincontext.commons.BasePresenter
import com.lostincontext.commons.BaseView
import com.lostincontext.commons.list.EmptyListCallback
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.rulesV2.Rule


interface MainScreenContract {

    interface View : BaseView<Presenter> {

        fun getPendingIntent(playlist: Playlist): PendingIntent
        fun openRuleCreationScreen()
        fun setRules(rules: List<Rule>)
        fun getPendingIntentFor(playlist: Playlist): PendingIntent
        fun goToPlaylist(playlist: Playlist)
    }

    interface Presenter : BasePresenter,
                          MainScreenViewHolder.Callback,
                          EmptyListCallback {

        fun onFabClicked()
        fun onRuleInput(rule: Rule)
    }
}

const val CREATE_RULE_CODE = 9005
