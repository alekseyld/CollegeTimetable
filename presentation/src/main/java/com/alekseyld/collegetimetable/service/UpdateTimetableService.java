package com.alekseyld.collegetimetable.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.internal.di.component.DaggerServiceComponent;
import com.alekseyld.collegetimetable.internal.di.module.ServiceModule;
import com.alekseyld.collegetimetable.rx.subscriber.BaseSubscriber;
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
    private int i = 0;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * UpdateTimetableService Used to name the worker thread, important only for debugging.
     */

    @Inject GetSettingsUseCase mGetSettingsUseCase;
    @Inject GetTableFromOnlineUseCase mGetTableFromOnlineUseCase;
    @Inject SaveTableUseCase mSaveTableUseCase;

    private Settings mSettings;

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

        mGetSettingsUseCase.execute(new BaseSubscriber<Settings>(){
            @Override
            public void onNext(Settings settings) {
                mSettings = settings;
            }
            @Override
            public void onCompleted() {
                if(mSettings != null
                        && mSettings.getNotificationGroup() != null
                        && !mSettings.getNotificationGroup().equals("")
                        && mSettings.getNotifOn()) {
                    if(isOnline())
                        getTimeTableOnline();
                }else {
                    stopSelf();
                }
            }
        });
    }

    private void getTimeTableOnline(){
        if (mSettings.getTeacherMode()) {
            mGetTableFromOnlineUseCase.setTeacherGroup(mSettings.getTeacherGroups());
        }
        mGetTableFromOnlineUseCase.setGroup(mSettings.getNotificationGroup());
        mGetTableFromOnlineUseCase.setOnline(isOnline());
        mGetTableFromOnlineUseCase.execute(new BaseSubscriber<TimeTable>(){
            @Override
            public void onNext(TimeTable timeTable) {
                if(timeTable != null
                        && timeTable.getDayList() != null
                        && timeTable.getDayList().size() > 0){

                    saveTimeTable(timeTable);
                    if(!mSettings.getAlarmMode()){
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        v.vibrate(300);
                    }

                    NotificationManager n = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    Notification notification = getNotif("Изменение в расписании");
                    notification.flags |= Notification.FLAG_AUTO_CANCEL;
                    n.notify(UpdateTimetableService.class.getName(), i++, notification);

                }
                stopSelf();
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                stopSelf();
            }
        });
    }

    private void saveTimeTable(TimeTable timeTable){
        mSaveTableUseCase.setTimeTable(timeTable);
        mSaveTableUseCase.setGroup(mSettings.getNotificationGroup());
        mSaveTableUseCase.execute(new BaseSubscriber());
    }

    private Notification getNotif(String s) {
        Drawable myDrawable = getResources().getDrawable(R.mipmap.ic_launcher_square);
        Bitmap bitmap = ((BitmapDrawable) myDrawable).getBitmap();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_square)
                        .setLargeIcon(bitmap)
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
