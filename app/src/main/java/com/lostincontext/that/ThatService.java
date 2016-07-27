package com.lostincontext.that;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.awareness.fence.FenceState;
import com.lostincontext.PlaylistLauncher;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.data.rules.Rule;
import com.lostincontext.data.rules.repo.RulesRepository;

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

        if (rule != null && TextUtils.equals(fenceState.getFenceKey(), rule.getName())) {
            switch (fenceState.getCurrentState()) {
                case FenceState.TRUE:
                    final Playlist playlist = rule.getPlaylist();
                    if (playlist != null) {
                        new PlaylistLauncher().launchPlaylist(this, playlist, true);
                    }

                    Log.i(TAG, "Headphones are plugged in.");
                    break;

                case FenceState.FALSE:
                    Log.i(TAG, "Headphones are NOT plugged in.");
                    break;

                case FenceState.UNKNOWN:
                    Log.i(TAG, "The headphone fence is in an unknown state.");
                    break;
            }
        }

    }

    @Nullable private Rule getRule(FenceState fenceState) {
        final RulesRepository rulesRepository = new RulesRepository(getSharedPreferences(getPackageName(),
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
