package com.alekseyld.collegetimetable.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.app.Notification;
import android.app.TaskStackBuilder;
import android.support.v4.app.NotificationCompat;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.utils.Utils;
import com.alekseyld.collegetimetable.view.activity.MainActivity;
import com.alekseyld.collegetimetable.view.fragment.WebViewFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alekseyld on 13.12.2017.
 */

public class MusicService extends IntentService {

    private final IBinder mBinder = new MusicBinder();

    private WebViewFragment fragment;
    private MediaPlayer mediaPlayer;

    private Map<String, String> cacheAudio;

    public class MusicBinder extends Binder {
        public MusicService getService(WebViewFragment fragment){
            return MusicService.this.setFragment(fragment);
        }
    }

    public MusicService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        startForeground(25565, getNotification("Music Player"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        fragment = null;
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // get cache music  list
        cacheAudio = Utils.getCacheMusicList();
        mediaPlayer = new MediaPlayer();
    }

    //process finish audio playlists offline or online
    // if device offline or offline playlist -> start next audio from directory
    // if device online and ! offline playlist -> fragment.playNext -> "javascript: getAudioPlayer().playNext(); JAVA.processAudio(..."

    public void processAudio(String urlString, String audioId, String audioTitle, String audioArtist){
        //todo process audio offline and online

        boolean isOnline = Utils.isNetworkAvailable(getApplicationContext());

        //if audio.isDownload -> mediaPlayer.startAudio

        //if !audio.isDownload and device.online -> fragment.downloadAudio(
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

    public MusicService setFragment(WebViewFragment fragment) {
        this.fragment = fragment;
        return this;
    }
}
