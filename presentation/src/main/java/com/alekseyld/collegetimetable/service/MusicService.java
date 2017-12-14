package com.alekseyld.collegetimetable.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.app.Notification;
import android.app.TaskStackBuilder;
import android.support.v4.app.NotificationCompat;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.view.activity.MainActivity;

/**
 * Created by Alekseyld on 13.12.2017.
 */

public class MusicService extends IntentService {

    MediaPlayer mediaPlayer;

    public MusicService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        startForeground(25565, getNotification("Music Player"));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setVolume(100,100);

    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY_COMPATIBILITY;
    }

    private Notification getNotification(String s) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_circle)
                        .setContentTitle(s)
                        .setContentText("Data");
//                        .setContentText("name = "+intent.getStringExtra("name")+"; "+
//                                        "number = "+intent.getIntExtra("number", 0));
        Intent resultIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(1, mBuilder.build());
        return mBuilder.build();
    }

}
