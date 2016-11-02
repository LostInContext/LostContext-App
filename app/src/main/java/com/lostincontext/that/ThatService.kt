package com.lostincontext.that

import android.app.IntentService
import android.content.Intent
import com.google.android.gms.awareness.fence.FenceState
import com.lostincontext.application.LostApplication
import com.lostincontext.data.rule.Rule
import com.lostincontext.utils.displayNotification
import com.lostincontext.utils.logD
import com.lostincontext.utils.logI
import java.io.IOException


class ThatService : IntentService(ThatService.TAG) {

    override fun onHandleIntent(intent: Intent) {
        logD(TAG) { "onHandleIntent" }
        val fenceState = FenceState.extract(intent)
        logD(TAG) { "fenceKey : " + fenceState.fenceKey }

        val rule = getRule(fenceState)

        if (rule != null) {
            when (fenceState.currentState) {
                FenceState.TRUE -> {
                    logI(TAG) { "Rule verified" }
                    displayNotification(this, rule.playlist)
                }

                FenceState.FALSE -> logI(TAG) { "Rule NOT verified" }

                FenceState.UNKNOWN -> logI(TAG) { "Rule fence is in an unknown state" }
            }
        }

    }

    private fun getRule(fenceState: FenceState): Rule? {
        val lostApplication = application as LostApplication
        val rulesRepository = lostApplication.appComponent.rulesRepository
        var rule: Rule? = null
        try {
            rule = rulesRepository.getRule(fenceState.fenceKey)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return rule
    }

    companion object {

        private val TAG = ThatService::class.java.simpleName
    }


}
