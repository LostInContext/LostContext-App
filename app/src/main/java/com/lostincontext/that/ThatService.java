package com.lostincontext.that;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.awareness.fence.FenceState;
import com.lostincontext.PlaylistLauncher;
import com.lostincontext.data.playlist.Playlist;


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

        if (TextUtils.equals(fenceState.getFenceKey(), "test")) {
            switch (fenceState.getCurrentState()) {
                case FenceState.TRUE:
                    final Bundle extras = intent.getExtras();
                    if (extras != null) {
                        final Playlist playlist = extras.getParcelable("playlist");
                        if (playlist != null) {
                            new PlaylistLauncher().launchPlaylist(this, playlist, true);
                        }
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
}
