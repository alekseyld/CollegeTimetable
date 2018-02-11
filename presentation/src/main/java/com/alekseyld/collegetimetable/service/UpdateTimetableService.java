package com.alekseyld.collegetimetable.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.component.DaggerServiceComponent;
import com.alekseyld.collegetimetable.internal.di.module.ServiceModule;
import com.alekseyld.collegetimetable.rx.subscriber.BaseSubscriber;
import com.alekseyld.collegetimetable.usecase.CheckAndUpdateChangesUseCase;
import com.alekseyld.collegetimetable.view.activity.MainActivity;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 04.09.2017.
 */

public class UpdateTimetableService extends IntentService {

    private static boolean isRunning = false;

    @Inject
    CheckAndUpdateChangesUseCase mCheckAndUpdateChangesUseCase;

    public UpdateTimetableService() {
        super(UpdateTimetableService.class.getName());
    }

    public static boolean isRunning() {
        return isRunning;
    }

    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
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
        isRunning = false;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mCheckAndUpdateChangesUseCase.execute(new BaseSubscriber<String>() {
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(String title) {
                super.onNext(title);

//                if(!mSettings.getAlarmMode()){
//                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//                    v.vibrate(300);
//                }

                if (title.equals("")) {
                    stopSelf();
                    return;
                }

                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = getAndroidNotification(title);
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                manager.notify("com.alekseyld.collegetimetable", 5, notification);

                stopSelf();
            }
        });

    }

    private Notification getAndroidNotification(String title) {
        Drawable myDrawable = getResources().getDrawable(R.mipmap.android_logo);
        Bitmap bitmap = ((BitmapDrawable) myDrawable).getBitmap();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.android_logo)
                        .setLargeIcon(bitmap)
                        .setContentTitle(title)
                        .setContentText(title);
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
}
