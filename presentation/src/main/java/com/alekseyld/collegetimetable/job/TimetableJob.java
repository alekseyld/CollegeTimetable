package com.alekseyld.collegetimetable.job;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
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
import com.alekseyld.collegetimetable.utils.Utils;
import com.alekseyld.collegetimetable.view.activity.MainActivity;
import com.evernote.android.job.Job;

import javax.inject.Inject;

import static com.alekseyld.collegetimetable.utils.Utils.TIMETABLE_NOTIFICATION_ID;

public class TimetableJob extends Job {
    public static final String TAG = "TimetableJob_Tag";

    private static final int WIFI_TIMING = 10 * 60 * 1000;//10 * 60 * 1000
    private static final int ERROR_TIMING = 60 * 1000;
    private static final int DEFAULT_TIMING = 25 * 60 * 1000;

    @Inject
    GetSettingsUseCase mGetSettingsUseCase;
    @Inject
    SharedPreferences mSharedPreferences;
    @Inject
    GetTableFromOnlineUseCase mGetTableFromOnlineUseCase;
    @Inject
    GetTableFromOfflineUseCase mGetTableFromOfflineUseCase;
    @Inject
    SaveTableUseCase mSaveTableUseCase;

    private Settings mSettings;

    private int connectionState;
    private boolean isFinish = false;

    private int timing = DEFAULT_TIMING;

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        Log.d(TAG, "onRunJob");

        connectionState = isOnline();

        if (connectionState == 0) {
//            planRunning(5 * 60 * 1000);
            return Result.SUCCESS;
        }

        initializeInjector();

        SharedPreferences.Editor ed = mSharedPreferences.edit();
        ed.putLong(TAG, System.currentTimeMillis());
        ed.apply();

        mGetSettingsUseCase.execute(new BaseSubscriber<Settings>() {
            @Override
            public void onNext(Settings settings) {
                mSettings = settings;
            }

            @Override
            public void onCompleted() {

                if (mSettings != null
                        && mSettings.getNotificationGroup() != null
                        && !mSettings.getNotificationGroup().equals("")
                        && mSettings.getNotifOn()) {

                    getTimeTableOnline();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                isFinish = true;
            }
        });

        while (!isFinish) {
            SystemClock.sleep(50);
        }

        Log.d(TAG, "SUCCESS job");

        planRunning(timing);

        return Result.SUCCESS;
    }

    private void planRunning(int timeing) {
        Log.d(TAG, "planRunning = " + timeing);
//
//        boolean notifOn = !mSettings.getNotifOn();
//        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(TimetableJob.TAG);
//        if (jobRequests.size() > 1) {
//            for (JobRequest j: jobRequests) {
//                j.cancelAndEdit();
//            }
//        }
//
//        if (!notifOn) {
//            isFinish = true;
//            return;
//        }
//
        Utils.getTimeTableJob(timing).schedule();
//
        isFinish = true;
    }

    private void getTimeTableOnline() {
        mGetTableFromOfflineUseCase.setGroup(mSettings.getNotificationGroup());
        mGetTableFromOfflineUseCase.execute(new BaseSubscriber<TimeTable>() {
            @Override
            public void onNext(final TimeTable oldTimeTable) {
                if (DataUtils.fioPattern.matcher(mSettings.getNotificationGroup()).find()) {
                    mGetTableFromOnlineUseCase.setTeacherGroup(mSettings.getTeacherGroups());
                }
                mGetTableFromOnlineUseCase.setGroup(mSettings.getNotificationGroup());
                mGetTableFromOnlineUseCase.setOnline(isOnline() > 0);
                mGetTableFromOnlineUseCase.execute(new BaseSubscriber<TimeTable>() {

                    @Override
                    public void onNext(TimeTable timeTable) {
                        super.onNext(timeTable);

                        if (timeTable != null
                                && timeTable.getDayList() != null
                                && timeTable.getDayList().size() > 0
                                && isTimeTableChanged(oldTimeTable, timeTable)) {

                            Log.d(TAG, "Timetable change");

                            saveTimeTable(timeTable);

                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                NotificationChannel channel = new NotificationChannel("notify_001",
                                        "Channel timetable app",
                                        NotificationManager.IMPORTANCE_DEFAULT);
                                NotificationManager mNotificationManager =
                                        (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                                if (mNotificationManager != null)
                                    mNotificationManager.createNotificationChannel(channel);
                            }
                            notificationManager.cancelAll();
                            notificationManager.notify(TIMETABLE_NOTIFICATION_ID, getChangeNotification("Изменение в расписании", mSettings.getNotificationGroup()));
                        }

                        if (connectionState == 1) {
//                            planRunning(WIFI_TIMING);
                            timing = WIFI_TIMING;
                        } else {
                            timing = DEFAULT_TIMING;
                        }
                        isFinish = true;
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        timing = ERROR_TIMING;
                        isFinish = true;
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                timing = DEFAULT_TIMING;
                isFinish = true;
            }
        });
    }

    private boolean isTimeTableChanged(TimeTable oldTimeTable, TimeTable timeTable) {
        if (oldTimeTable == null
                || oldTimeTable.getDayList().size() == 0) {
            return true;
        }

        for (int day = 0; day < timeTable.getDayList().size(); day++) {
            for (int lesson = 0; lesson < 7; lesson++) {
                if (!oldTimeTable.getDayList().get(day).getDayLessons().get(lesson).getDoubleName()
                        .equals(timeTable.getDayList().get(day).getDayLessons().get(lesson).getDoubleName())) {
                    return true;
                }
            }
        }

        return false;
    }

    private void saveTimeTable(TimeTable timeTable) {
        mSaveTableUseCase.setTimeTable(timeTable);
        mSaveTableUseCase.setGroup(mSettings.getNotificationGroup());
        mSaveTableUseCase.execute(new BaseSubscriber());
    }

    private Notification getChangeNotification(String s, String group) {

        String text = "";
        if (DataUtils.fioPattern.matcher(group).find()) {
            text = "Для преподавателя " + group;
        } else {
            text = "Для группы " + group;
        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getContext(), "notify_001")
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(s)
                        .setContentText(text)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//                        .setContentText("name = "+intent.getStringExtra("name")+"; "+
//                                        "number = "+intent.getIntExtra("number", 0));
        Intent resultIntent = new Intent(getContext(), MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
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

    private int isOnline() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI")) {
                if (ni.isConnected()) {
                    haveConnectedWifi = true;
                }
            }
            if (ni.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (ni.isConnected()) {
                    haveConnectedMobile = true;
                }
            }
        }
        return (haveConnectedWifi ? 1 : 0) + (haveConnectedMobile ? 2 : 0);
    }

    private void initializeInjector() {
        DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule(getContext()))
                .build()
                .inject(this);

    }
}
