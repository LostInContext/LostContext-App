package com.lostincontext.that


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.lostincontext.PlaylistLauncher
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.utils.cancelNotification

class PlayReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        cancelNotification(context)
        val extras = intent.extras
        if (extras != null) {
            val playlist = extras.getParcelable<Playlist>("playlist")
            if (playlist != null) {
                PlaylistLauncher().launchPlaylist(context, playlist, true)
            }
        }
    }
}
