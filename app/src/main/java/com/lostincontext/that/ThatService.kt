package com.lostincontext.that

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.gms.awareness.fence.FenceState
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.rules.Rule
import com.lostincontext.data.rules.repo.RulesRepository
import com.lostincontext.utils.Notifications

import java.io.IOException


class ThatService : IntentService(ThatService.TAG) {

    override fun onHandleIntent(intent: Intent) {
        Log.d(TAG, "onHandleIntent : ")
        val fenceState = FenceState.extract(intent)
        Log.i(TAG, "onReceive : fenceKey : " + fenceState.fenceKey)

        val rule = getRule(fenceState)

        if (rule != null) {
            when (fenceState.currentState) {
                FenceState.TRUE -> {
                    val playlist = rule.playlist
                    if (playlist != null) {
                        Notifications.displayNotification(this, rule.name, playlist)
                    }

                    Log.i(TAG, "Rule is verified")
                }

                FenceState.FALSE -> Log.i(TAG, "Rule is NOT verified")

                FenceState.UNKNOWN -> Log.i(TAG, "Rule fence is in an unknown state.")
            }
        }

    }

    private fun getRule(fenceState: FenceState): Rule? {
        val rulesRepository = RulesRepository(getSharedPreferences("rules",
                                                                   Context.MODE_PRIVATE),
                                              ObjectMapper())
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
