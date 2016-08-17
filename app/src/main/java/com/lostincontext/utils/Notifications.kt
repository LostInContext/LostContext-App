package com.lostincontext.utils

import android.app.Notification
import android.app.Notification.DEFAULT_ALL
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import com.lostincontext.R
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.that.PlayReceiver

object Notifications {
    val ACTION_PLAY = "action_play"
    private val NOTIFICATION_ID = 0


    fun displayNotification(context: Context,
                            fenceName: String,
                            playlist: Playlist) {
        val intent = Intent(context,
                            PlayReceiver::class.java)
        intent.putExtra("playlist",
                        playlist)
        val pIntent = PendingIntent.getBroadcast(context,
                                                 System.currentTimeMillis().toInt(),
                                                 intent,
                                                 0)

        val n = NotificationCompat.Builder(context)
                .setContentTitle("LostContext : $fenceName is verified")
                .setContentText("Launch playlist?")
                .setSmallIcon(R.drawable.ic_music_note_24)
                .setContentIntent(pIntent)
                .setColor(ContextCompat.getColor(context,
                                                 R.color.colorAccent))
                .setDefaults(DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .addAction(getPlayAction(context,
                                         playlist))
                .build()


        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(NOTIFICATION_ID,
                                   n)
    }

    private fun getPlayAction(context: Context,
                              playlist: Playlist): NotificationCompat.Action {
        val playReceive = Intent()
        playReceive.putExtra("playlist",
                             playlist)
        playReceive.action = ACTION_PLAY
        val KEY_PLAY = 9877
        val pendingIntentPlay = PendingIntent.getBroadcast(context,
                                                           KEY_PLAY,
                                                           playReceive,
                                                           PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Action.Builder(R.drawable.ic_play_24,
                                                 context.getString(R.string.play),
                                                 pendingIntentPlay).build()
    }

    fun cancelNotification(context: Context) {
        val ns = Context.NOTIFICATION_SERVICE
        val notificationManager = context.getSystemService(ns) as NotificationManager
        notificationManager.cancel(NOTIFICATION_ID)
        val closeIntent = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        context.sendBroadcast(closeIntent)
    }
}
