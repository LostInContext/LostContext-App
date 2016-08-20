package com.lostincontext.mainscreen


import android.os.Bundle

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.lostincontext.awareness.Awareness
import com.lostincontext.data.rules.Rule
import com.lostincontext.data.rules.repo.RulesRepository

import javax.inject.Inject


class MainScreenPresenter
@Inject
constructor(private val view: MainScreenContract.View,
            private val awareness: Awareness,
            private val rulesRepository: RulesRepository)
: MainScreenContract.Presenter,
  GoogleApiClient.ConnectionCallbacks,
  GoogleApiClient.OnConnectionFailedListener {


    override fun start() {
        rulesRepository.getRules(object : RulesRepository.LoadTasksCallback {
            override fun onTasksLoaded(rules: List<Rule>) {
                view.setRules(rules)
            }

            override fun onTasksLoadFailure() {
            }
        })
    }

    @Inject
    internal fun setup() {
        view.setPresenter(this)
        awareness.init(this,
                       this)
    }


    //region callbacks

    override fun onFabClicked() {
        view.openRuleCreationScreen()
    }


    //endregion


    override fun onConnected(bundle: Bundle?) {
    }

    override fun onConnectionSuspended(i: Int) {
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
    }

    override fun onRefreshButtonClick() {

    }
}


