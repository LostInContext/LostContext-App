package com.lostincontext.that;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lostincontext.PlaylistLauncher;
import com.lostincontext.utils.NotificationIntentUtils;

public class PlayReceiver extends BroadcastReceiver{
    @Override public void onReceive(Context context, Intent intent) {
        if (NotificationIntentUtils.ACTION_PLAY.equals(intent.getAction())) {
            Log.v("shuffTest", "Pressed YES");
//            if (playlist != null) {
//                new PlaylistLauncher().launchPlaylist(ThatService.this, playlist, true);
//            }

        }
    }
}
