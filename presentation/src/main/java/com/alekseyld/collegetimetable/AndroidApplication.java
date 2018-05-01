package com.alekseyld.collegetimetable;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;

import com.alekseyld.collegetimetable.internal.di.component.ApplicationComponent;
import com.alekseyld.collegetimetable.internal.di.component.DaggerApplicationComponent;
import com.alekseyld.collegetimetable.internal.di.module.ApplicationModule;
import com.alekseyld.collegetimetable.service.UpdateTimetableService;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class AndroidApplication extends Application {

    private final String NAME_FILE = "DataStorage";
    private final String NOTIFON_KEY = "NotifOn";

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        BackwardCompatibility.checkAppVersion(this.getSharedPreferences(NAME_FILE, MODE_PRIVATE));

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Fabric.with(this, new Crashlytics());
        initializeInjector();
        initializeDBFlow();
        initializeService();
    }

    private void initializeService() {
        SharedPreferences preferences = this.getSharedPreferences(NAME_FILE, MODE_PRIVATE);

        if (preferences.getBoolean(NOTIFON_KEY, false)) {
            boolean alarmUp = (PendingIntent.getBroadcast(getApplicationContext(), 0,
                    new Intent(this, UpdateTimetableService.class),
                    PendingIntent.FLAG_NO_CREATE) != null);

            if (!alarmUp) {
                Intent ishintent = new Intent(this, UpdateTimetableService.class);
                PendingIntent pintent = PendingIntent.getService(this, 0, ishintent, 0);
                AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarm.cancel(pintent);
                alarm.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), 30 * 60 * 1000, pintent);
            }
        }
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void initializeDBFlow() {
//        FlowManager.init(new FlowConfig.Builder(this).build());
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
