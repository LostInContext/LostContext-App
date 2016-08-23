package com.lostincontext.that

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.android.gms.awareness.fence.FenceState
import com.lostincontext.data.rules.Rule
import com.lostincontext.data.rules.repo.RulesRepository
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
                    val playlist = rule.playlist
                    if (playlist != null) {
                        displayNotification(this, rule.name!!, playlist)
                    }

                }

                FenceState.FALSE -> logI(TAG) { "Rule NOT verified" }

                FenceState.UNKNOWN -> logI(TAG) { "Rule fence is in an unknown state" }
            }
        }

    }

    private fun getRule(fenceState: FenceState): Rule? {
        val rulesRepository = RulesRepository(getSharedPreferences("rules",
                                                                   Context.MODE_PRIVATE),
                                              jacksonObjectMapper())
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
