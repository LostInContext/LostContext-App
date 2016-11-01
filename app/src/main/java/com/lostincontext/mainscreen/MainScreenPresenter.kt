package com.lostincontext.mainscreen


import android.os.Bundle
import com.google.android.gms.awareness.fence.AwarenessFence
import com.google.android.gms.awareness.fence.FenceUpdateRequest
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallbacks
import com.google.android.gms.common.api.Status
import com.lostincontext.awareness.Awareness
import com.lostincontext.data.rules.FenceBuilder
import com.lostincontext.data.rules.repo.RulesRepository
import com.lostincontext.data.rulesV2.Rule
import com.lostincontext.utils.logD
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
        refreshRules()
    }

    private fun refreshRules() {
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
        awareness.init(this,
                       this)
    }


    //region callbacks

    override fun onFabClicked() {
        view.openRuleCreationScreen()
    }

    override fun onRuleInput(rule: com.lostincontext.data.rulesV2.Rule) {
        logD(TAG) { "onRuleInput : $rule" }

        val fenceBuilder = FenceBuilder()
        val builder = FenceUpdateRequest.Builder()

        val buildAwarenessFence: AwarenessFence? = rule.buildAwarenessFence(fenceBuilder)

        if (buildAwarenessFence == null) logD(TAG) { "invalid fence, abort" }

        builder.addFence(rule.key,
                         buildAwarenessFence,
                         view.getPendingIntentFor(rule.playlist))

        awareness.updateFence(builder.build()).setResultCallback(object : ResultCallbacks<Status>() {
            override fun onSuccess(status: Status) {
                logD(TAG) { "updateFence.onSuccess: " + status.statusMessage }
                rulesRepository.saveRule(rule)
                refreshRules()
            }

            override fun onFailure(status: Status) {
                //view.showSnack(EnumSet.of(RuleDetailsContract.RuleErrors.SAVE_ERROR))
            }
        })


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

    companion object {
        private val TAG: String = MainScreenPresenter::class.java.simpleName
    }
}


