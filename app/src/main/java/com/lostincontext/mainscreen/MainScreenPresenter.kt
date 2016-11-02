package com.lostincontext.mainscreen


import android.os.Bundle
import com.google.android.gms.awareness.fence.AwarenessFence
import com.google.android.gms.awareness.fence.FenceUpdateRequest
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallbacks
import com.google.android.gms.common.api.Status
import com.lostincontext.awareness.Awareness
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.fences.FenceBuilder
import com.lostincontext.data.rule.repo.RulesRepository
import com.lostincontext.data.rule.Rule
import com.lostincontext.utils.logD
import com.lostincontext.utils.logE
import com.lostincontext.utils.logW
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
                logD(TAG) { "tasks loaded : $rules" }
                view.setRules(rules)
            }

            override fun onTasksLoadFailure() {
                logE(TAG) { "tasks load failure" }
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

    override fun onRuleInput(rule: Rule) {
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


    override fun onPlaylistCoverClick(playlist: Playlist) = view.goToPlaylist(playlist)


    override fun onRuleLongClick(rule: Rule): Boolean {

        val builder = FenceUpdateRequest.Builder()
        builder.removeFence(rule.key)
        val fenceUpdateRequest = builder.build()

        awareness.updateFence(fenceUpdateRequest).setResultCallback(object : ResultCallbacks<Status>() {


            override fun onSuccess(status: Status) {
                logD(TAG) { "FenceDeletion.onSuccess: " + status.statusMessage }
                rulesRepository.deleteRule(rule)
                refreshRules()
            }

            override fun onFailure(status: Status) {
                logW(TAG) { "failure to delete : $status" }
            }
        })

        return true
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


