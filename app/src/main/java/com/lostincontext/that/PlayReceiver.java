package com.lostincontext.that;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lostincontext.PlaylistLauncher;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.utils.Notifications;

public class PlayReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        Notifications.cancelNotification(context);
        final Bundle extras = intent.getExtras();
        if (extras != null) {
            final Playlist playlist = extras.getParcelable("playlist");
            if (playlist != null) {
                new PlaylistLauncher().launchPlaylist(context, playlist, true);
            }
        }
    }
}
