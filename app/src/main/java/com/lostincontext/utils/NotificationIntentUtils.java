package com.lostincontext.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.lostincontext.R;
import com.lostincontext.that.PlayReceiver;
import com.lostincontext.that.ThatService;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationIntentUtils {
    public static String ACTION_PLAY = "action_play";
    private static int KEY_PLAY = 9877;


    public static void displayNotification(Context context, String fenceName) {
        Intent intent = new Intent(context, PlayReceiver.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

        Notification n = new Notification.Builder(context)
                .setContentTitle("LostContext : " + fenceName + " is verified")
                .setContentText("Launch playlist?")
                .setSmallIcon(R.drawable.ic_deezer_logo_24)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .addAction(getPlayAction(context))
                .build();


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }

    private static Notification.Action getPlayAction(Context context) {
        Intent playReceive = new Intent();
        playReceive.setAction(ACTION_PLAY);
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, KEY_PLAY, playReceive, PendingIntent.FLAG_UPDATE_CURRENT);

        return new Notification.Action.Builder(R.drawable.ic_tick_16,
                                               "action play",
                                               pendingIntentPlay)
                .build();
    }


}
