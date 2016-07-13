package com.lostincontext;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.lostincontext.data.Playlist;

public class PlaylistLauncher {
    private static String TAG = PlaylistLauncher.class.getSimpleName();

    public void launchPlaylist(Context context, Playlist playlist) {
        Log.i(TAG, "i'm launching playlist !");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String uri = "http://www.deezer.com/playlist/" + playlist.getId();
        intent.setData(Uri.parse(uri));
        context.startActivity(intent);
    }

}
