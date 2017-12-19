package com.robotrader.ebinjoy999.robotrader.service;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.robotrader.ebinjoy999.robotrader.R;

/**
 * Created by macadmin on 12/19/17.
 */

public class CustomNotificationManager {
   public static int NOTIFICATION_ID_SERVICE_RUNNER = 10005;
   Context context;
   CustomNotificationManager(Context context){
       this.context = context;
   }

    private void showNotification() {
        NotificationCompat.Builder mBuilder =  buildNotification();
//        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(this,0,intent,Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        mBuilder.setContentIntent(pi);
        android.app.NotificationManager mNotificationManager =
                (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID_SERVICE_RUNNER, mBuilder.build());
    }

    private NotificationCompat.Builder buildNotification() {
        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher_round) // notification icon
                .setContentTitle("TraderRobo!") // title for notification
                .setContentText("Caution trading is automated, please do a manual check.") // message for notification
                .setPriority(Notification.PRIORITY_MAX)
                .setOngoing(true)
                .setAutoCancel(false); // clear notification after click
    }

}
