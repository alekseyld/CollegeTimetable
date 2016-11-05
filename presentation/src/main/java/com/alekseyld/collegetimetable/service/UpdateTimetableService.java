package com.alekseyld.collegetimetable.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.SettingsWrapper;
import com.alekseyld.collegetimetable.TableWrapper;
import com.alekseyld.collegetimetable.internal.di.component.DaggerServiceComponent;
import com.alekseyld.collegetimetable.internal.di.module.ServiceModule;
import com.alekseyld.collegetimetable.subscriber.BaseSubscriber;
import com.alekseyld.collegetimetable.usecase.GetSettingsUseCase;
import com.alekseyld.collegetimetable.usecase.GetTableFromOnlineUseCase;
import com.alekseyld.collegetimetable.usecase.SaveTableUseCase;
import com.alekseyld.collegetimetable.view.activity.MainActivity;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public class UpdateTimetableService extends IntentService {
    private final String LOG_TAG = "ServiceLog";
    public static boolean isRunning = false;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * UpdateTimetableService Used to name the worker thread, important only for debugging.
     */

    @Inject GetSettingsUseCase mGetSettingsUseCase;
    @Inject GetTableFromOnlineUseCase mGetTableFromOnlineUseCase;
    @Inject SaveTableUseCase mSaveTableUseCase;

    private SettingsWrapper mSettings;

    public UpdateTimetableService() {
        super("DataService");
    }

    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        Log.d(LOG_TAG, "onCreate");
        isRunning = true;
    }

    private void initializeInjector(){
        DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule(this))
                .build()
                .inject(this);
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

        mGetSettingsUseCase.execute(new BaseSubscriber<SettingsWrapper>(){
            @Override
            public void onNext(SettingsWrapper settingsWrapper) {
                mSettings = settingsWrapper;
            }
            @Override
            public void onCompleted() {
                if(mSettings != null
                        && mSettings.getNotificationGroup() != null
                        && !mSettings.getNotificationGroup().equals("")) {
                    if(isOnline())
                        getTimeTableOnline();
                }else {
                    stopSelf();
                }
            }
        });
    }

    private void getTimeTableOnline(){
        mGetTableFromOnlineUseCase.setGroup(mSettings.getNotificationGroup());
        mGetTableFromOnlineUseCase.setOnline(isOnline());
        mGetTableFromOnlineUseCase.execute(new BaseSubscriber<TableWrapper>(){
            @Override
            public void onNext(TableWrapper tableWrapper) {
                if(tableWrapper != null
                        && tableWrapper.getmTimeTable() != null
                        && tableWrapper.getmTimeTable().size() > 0
                        && tableWrapper.getChanges() != null
                        && tableWrapper.isChanges()){

                    saveTimeTable(tableWrapper);
                    if(!mSettings.getAlarmMode()){
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        v.vibrate(300);
                    }
                    NotificationManager n = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    Notification notification = getNotif("Изменение в расписании");
                    notification.flags |= Notification.FLAG_AUTO_CANCEL;
                    n.notify("com.alekseyld.collegetimetable", 5, notification);

                }
                stopSelf();
            }
            @Override
            public void onError(Throwable e) {
                stopSelf();
            }
        });
    }

    private void saveTimeTable(TableWrapper tableWrapper){
        mSaveTableUseCase.setTimeTable(tableWrapper);
        mSaveTableUseCase.setGroup(mSettings.getNotificationGroup());
        mSaveTableUseCase.execute(new BaseSubscriber());
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
