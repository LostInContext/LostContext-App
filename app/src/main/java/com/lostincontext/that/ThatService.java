package com.lostincontext.that;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.awareness.fence.FenceState;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.data.rules.Rule;
import com.lostincontext.data.rules.repo.RulesRepository;
import com.lostincontext.utils.Notifications;

import java.io.IOException;


public class ThatService extends IntentService {

    private static final String TAG = ThatService.class.getSimpleName();

    public ThatService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent : ");
        FenceState fenceState = FenceState.extract(intent);
        Log.i(TAG, "onReceive : fenceKey : " + fenceState.getFenceKey());

        Rule rule = getRule(fenceState);

        if (rule != null) {
            switch (fenceState.getCurrentState()) {
                case FenceState.TRUE:
                    Playlist playlist = rule.getPlaylist();
                    if (playlist != null) {
                        Notifications.displayNotification(this, rule.getName(), playlist);
                    }

                    Log.i(TAG, "Rule is verified");
                    break;

                case FenceState.FALSE:
                    Log.i(TAG, "Rule is NOT verified");
                    break;

                case FenceState.UNKNOWN:
                    Log.i(TAG, "Rule fence is in an unknown state.");
                    break;
            }
        }

    }

    @Nullable private Rule getRule(FenceState fenceState) {
        final RulesRepository rulesRepository = new RulesRepository(getSharedPreferences("rules",
                                                                                         MODE_PRIVATE), new ObjectMapper());
        Rule rule = null;
        try {
            rule = rulesRepository.getRule(fenceState.getFenceKey());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rule;
    }


}
