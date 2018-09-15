package com.alekseyld.collegetimetable.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.internal.di.component.DaggerServiceComponent;
import com.alekseyld.collegetimetable.internal.di.module.ServiceModule;
import com.alekseyld.collegetimetable.rx.subscriber.BaseSubscriber;
import com.alekseyld.collegetimetable.usecase.GetSettingsUseCase;
import com.alekseyld.collegetimetable.usecase.GetTableFromOfflineUseCase;
import com.alekseyld.collegetimetable.usecase.GetTableFromOnlineUseCase;
import com.alekseyld.collegetimetable.usecase.SaveTableUseCase;
import com.alekseyld.collegetimetable.utils.DataUtils;
import com.alekseyld.collegetimetable.utils.NotificationID;
import com.alekseyld.collegetimetable.utils.Utils;
import com.alekseyld.collegetimetable.view.activity.MainActivity;

import javax.inject.Inject;

import static com.alekseyld.collegetimetable.repository.base.TableRepository.NAME_FILE;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public class UpdateTimetableService extends IntentService {
    private final String LOG_TAG = "ServiceLog";
    public final static String SERVICE_NAME = "UpdateTimetableService";
    public static boolean isRunning = false;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * UpdateTimetableService Used to name the worker thread, important only for debugging.
     */

    @Inject GetSettingsUseCase mGetSettingsUseCase;
    @Inject GetTableFromOnlineUseCase mGetTableFromOnlineUseCase;
    @Inject GetTableFromOfflineUseCase mGetTableFromOfflineUseCase;
    @Inject SaveTableUseCase mSaveTableUseCase;

    private Settings mSettings;

    public UpdateTimetableService() {
        super(SERVICE_NAME);
    }

    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        Log.d(LOG_TAG, "onCreate");
        isRunning = true;

        getApplicationContext()
                .getSharedPreferences(NAME_FILE, MODE_PRIVATE)
                .edit()
                .putLong(SERVICE_NAME, System.currentTimeMillis())
                .apply();
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
        if (DataUtils.fioPattern.matcher(mSettings.getNotificationGroup()).find()) {
            mGetTableFromOnlineUseCase.setTeacherGroup(mSettings.getTeacherGroups());
        }
        mGetTableFromOnlineUseCase.setGroup(mSettings.getNotificationGroup());
        mGetTableFromOnlineUseCase.setOnline(isOnline());
        mGetTableFromOnlineUseCase.execute(new BaseSubscriber<TimeTable>(){
            @Override
            public void onNext(final TimeTable timeTable) {
                mGetTableFromOfflineUseCase.setGroup(mSettings.getNotificationGroup());
                mGetTableFromOfflineUseCase.execute(new BaseSubscriber<TimeTable>() {

                    @Override
                    public void onNext(TimeTable oldTimeTable) {
                        super.onNext(oldTimeTable);

                        if(timeTable != null
                                && timeTable.getDayList() != null
                                && timeTable.getDayList().size() > 0
                                && isTimeTableChanged(oldTimeTable, timeTable)){

                            Log.d(LOG_TAG, "Timetable change");

                            saveTimeTable(timeTable);
//                            if(!mSettings.getAlarmMode()){
//                                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//                                v.vibrate(300);
//                            }

                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                NotificationChannel channel = new NotificationChannel("notify_001",
                                        "Channel timetable app",
                                        NotificationManager.IMPORTANCE_DEFAULT);
                                NotificationManager mNotificationManager =
                                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                if (mNotificationManager != null)
                                    mNotificationManager.createNotificationChannel(channel);
                            }
                            notificationManager.cancelAll();
                            notificationManager.notify(NotificationID.getID(), getChangeNotification("Изменение в расписании"));
                        }

                        planRunning(20 * 60 * 1000);
                        stopSelf();
                    }
                });
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                planRunning(60 * 1000);
                stopSelf();
            }
        });
    }

    private void planRunning(int timeing) {
        if (!mSettings.getNotifOn()) return;

        Intent ishintent = new Intent(getApplicationContext(), UpdateTimetableService.class);
        PendingIntent pintent = PendingIntent.getService(getApplicationContext(), 0, ishintent, 0);
        AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        if (alarm != null) {
            alarm.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), Utils.SERVICE_TIMER, pintent);
            alarm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + timeing, pintent);
        }
    }

    private boolean isTimeTableChanged(TimeTable oldTimeTable, TimeTable timeTable) {
        if (oldTimeTable == null
                || oldTimeTable.getDayList().size() == 0){
            return true;
        }

        for (int day = 0; day < timeTable.getDayList().size(); day++) {
            for (int lesson = 0; lesson < 7; lesson++) {
                if (!oldTimeTable.getDayList().get(day).getDayLessons().get(lesson).getDoubleName()
                        .equals(timeTable.getDayList().get(day).getDayLessons().get(lesson).getDoubleName())){
                    return true;
                }
            }
        }

        return false;
    }

    private void saveTimeTable(TimeTable timeTable){
        mSaveTableUseCase.setTimeTable(timeTable);
        mSaveTableUseCase.setGroup(mSettings.getNotificationGroup());
        mSaveTableUseCase.execute(new BaseSubscriber());
    }

    private Notification getChangeNotification(String s) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this,"notify_001")
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(s)
                        .setContentText("Изменение в расписании")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
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
        return mBuilder.build();
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
