package com.alekseyld.collegetimetable.service;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.view.activity.MainActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.EOFException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public class UpdateTimetableService extends IntentService {
    private final String LOG_TAG = "ServiceLog";
    public static boolean isRunning = false;

    SharedPreferences mPref;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * UpdateTimetableService Used to name the worker thread, important only for debugging.
     */
    public UpdateTimetableService() {
        super("DataService");
    }

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
        isRunning = true;
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
        isRunning = false;
    }

    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind");
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(LOG_TAG, "onHandleIntent");

        mPref = getSharedPreferences("DataStorage", MODE_PRIVATE);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        int time = mPref.getInt("Time", 5);
        NotificationManager n = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = getNotif("Изменение в расписании");
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        if(isOnline()){
            Document document = null;
            try {
                document = Jsoup.connect(mPref.getString("Url", "")).get();
                if(!document.text().equals(mPref.getString("Doc", ""))){
                    n.notify("com.alekseyld.collegetimetable", 5, notification);
                    v.vibrate(300);
                }
            } catch (Exception e) {
                stopSelf();
            }
        }
    }


    //do a pause
    private void pause(int p){
        try {
            TimeUnit.MINUTES.sleep(p);
        } catch (InterruptedException e) {e.printStackTrace();}
    }

    private Notification getNotif(String s) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.android_logo)
                        .setContentTitle(s)
                        .setContentText("Изменение в расписании");
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

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
