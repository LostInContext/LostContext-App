package com.lostincontext

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.utils.logI

class PlaylistLauncher {

    fun launchPlaylist(context: Context, playlist: Playlist, autoplay: Boolean = true) {
        logI(TAG) { "i'm launching playlist !" }
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        var uri = "deezer://www.deezer.com/playlist/${playlist.id}?autoplay=$autoplay"
        intent.data = Uri.parse(uri)
        val manager = context.packageManager
        val info = manager.queryIntentActivities(intent, 0)
        if (info.size == 0) {
            //fallback on the web :
            uri = "http://www.deezer.com/playlist/${playlist.id}"
            intent.data = Uri.parse(uri)
        }
        context.startActivity(intent)
    }

    companion object {
        private val TAG = PlaylistLauncher::class.java.simpleName
    }

}
