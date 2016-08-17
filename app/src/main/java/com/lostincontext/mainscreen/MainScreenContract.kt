package com.lostincontext.mainscreen

import android.app.PendingIntent

import com.lostincontext.commons.BasePresenter
import com.lostincontext.commons.BaseView
import com.lostincontext.commons.list.EmptyListCallback
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.rules.Rule


interface MainScreenContract {

    interface View : BaseView<Presenter> {

        fun getPendingIntent(playlist: Playlist): PendingIntent

        fun openRuleCreationScreen()

        fun setRules(rules: List<Rule>)
    }

    interface Presenter : BasePresenter, EmptyListCallback {

        fun onFabClicked()
    }
}
