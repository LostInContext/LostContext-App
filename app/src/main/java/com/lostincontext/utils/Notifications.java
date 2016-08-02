package com.lostincontext.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.support.v4.app.NotificationCompat;

import com.lostincontext.R;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.that.PlayReceiver;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Notifications {
    public static String ACTION_PLAY = "action_play";
    private static final int NOTIFICATION_ID = 0;


    public static void displayNotification(Context context, String fenceName, Playlist playlist) {
        Intent intent = new Intent(context, PlayReceiver.class);
        intent.putExtra("playlist", playlist);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), intent, 0);

        Notification n = new Notification.Builder(context)
                .setContentTitle("LostContext : " + fenceName + " is verified")
                .setContentText("Launch playlist?")
                .setSmallIcon(R.drawable.ic_music_note_24)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .addAction(getPlayAction(context, playlist))
                .build();


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID, n);
    }

    private static NotificationCompat.Action getPlayAction(Context context, Playlist playlist) {
        Intent playReceive = new Intent();
        playReceive.putExtra("playlist", playlist);
        playReceive.setAction(ACTION_PLAY);
        int KEY_PLAY = 9877;
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, KEY_PLAY, playReceive, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Action.Builder(R.drawable.ic_tick_16,
                                               ACTION_PLAY,
                                               pendingIntentPlay).build();
    }

    public static void cancelNotification(Context context) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(ns);
        notificationManager.cancel(NOTIFICATION_ID);
        Intent closeIntent = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(closeIntent);
    }
}
