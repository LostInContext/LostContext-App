package com.lostincontext;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class PlaylistLauncher {
    private static String TAG = PlaylistLauncher.class.getSimpleName();

    public void launchPlaylist(Context context) {
        Log.i(TAG, "i'm launching playlist !");
        Intent intent = new Intent (Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData (Uri.parse("http://www.deezer.com/album/13476357?autoplay=true&utm_campaign=crm-new-lp&utm_source=deezer&utm_medium=email&utm_content=red-hot-chili-peppers&utm_term="));
        context.startActivity(intent);
    }

}
