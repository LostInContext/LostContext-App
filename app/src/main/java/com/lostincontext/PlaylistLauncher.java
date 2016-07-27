package com.lostincontext;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import com.lostincontext.data.playlist.Playlist;

import java.util.List;

public class PlaylistLauncher {
    private static String TAG = PlaylistLauncher.class.getSimpleName();

    public void launchPlaylist(Context context, Playlist playlist, boolean autoplay) {
        Log.i(TAG, "i'm launching playlist !");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        String uri = "deezer://www.deezer.com/playlist/" + playlist.getId() + "?autoplay=" + autoplay;
        intent.setData(Uri.parse(uri));

        PackageManager manager = context.getPackageManager();
        List<ResolveInfo> info = manager.queryIntentActivities(intent, 0);
        if (info.size() == 0) {
            //fallback on the web :
            uri = "http://www.deezer.com/playlist/" + playlist.getId();
            intent.setData(Uri.parse(uri));
        }
        context.startActivity(intent);
    }

}
