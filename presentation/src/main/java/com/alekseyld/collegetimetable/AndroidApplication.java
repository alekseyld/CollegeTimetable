package com.alekseyld.collegetimetable;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;

import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.internal.di.component.ApplicationComponent;
import com.alekseyld.collegetimetable.internal.di.component.DaggerApplicationComponent;
import com.alekseyld.collegetimetable.internal.di.module.ApplicationModule;
import com.alekseyld.collegetimetable.service.UpdateTimetableService;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

import io.fabric.sdk.android.Fabric;

import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.SETTINGS_KEY;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.NAME_FILE;

/**
 * Created by Alekseyld on 02.09.2017.
 */

public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        BackwardCompatibility.checkAppVersion(this.getSharedPreferences(NAME_FILE, MODE_PRIVATE));

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Fabric.with(this, new Crashlytics());

        initializeInjector();
        initializeService();

        initializeMockSettings();
    }

    private void initializeMockSettings() {
        SharedPreferences preferences = this.getSharedPreferences(NAME_FILE, MODE_PRIVATE);

        if (!preferences.contains(SETTINGS_KEY)) {

            Set<String> favoriteGroups = new HashSet<>();
            favoriteGroups.add("3 ЭНН-2");
            favoriteGroups.add("2 ПГ-2");
            favoriteGroups.add("2 АПП-1");

            Settings settings = new Settings();
            settings.setFavoriteGroups(favoriteGroups);
            settings.setUrlServer("http://81.30.211.85/wp-content/uploads/colledgett/api/index.php/");

            preferences.edit()
                    .putString(SETTINGS_KEY, new Gson().toJson(settings))
                    .apply();

        }

    }

    private void initializeService() {
        boolean alarmUp = (PendingIntent.getBroadcast(getApplicationContext(), 0,
                    new Intent(this, UpdateTimetableService.class),
                    PendingIntent.FLAG_NO_CREATE) != null);

        if (!alarmUp) {
                Intent ishintent = new Intent(this, UpdateTimetableService.class);
                PendingIntent pintent = PendingIntent.getService(this, 0, ishintent, 0);
                AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarm.cancel(pintent);
                alarm.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), 10 * 60 * 1000, pintent);
        }
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
