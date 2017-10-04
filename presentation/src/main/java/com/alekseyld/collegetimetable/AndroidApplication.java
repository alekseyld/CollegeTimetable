package com.alekseyld.collegetimetable;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.internal.di.component.ApplicationComponent;
import com.alekseyld.collegetimetable.internal.di.component.DaggerApplicationComponent;
import com.alekseyld.collegetimetable.internal.di.module.ApplicationModule;
import com.alekseyld.collegetimetable.service.UpdateTimetableService;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashSet;
import java.util.Set;

import io.fabric.sdk.android.Fabric;

import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.ALARMMODE_KEY;
import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.FAVORITEGROUPS_KEY;
import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.GROUP_KEY;
import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.SETTINGS_KEY;
import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.URL_KEY;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.DOC_KEY;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.TIMETABLE_KEY;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.TIMETABLE_NEW_KEY;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class AndroidApplication extends Application {

    private final String NAME_FILE = "DataStorage";
    private final String NOTIFON_KEY = "NotifOn";

    private final String VERSION_KEY = "VERSION";

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        checkAppVersion();
        Fabric.with(this, new Crashlytics());
        initializeInjector();
        initializeDBFlow();
        initializeService();
    }

    private void checkAppVersion() {
        SharedPreferences preferences = this.getSharedPreferences(NAME_FILE, MODE_PRIVATE);

//        if (!preferences.contains(VERSION_KEY)) {
            Gson gson = new Gson();
            //Todo обратная совместимость. Конвертировать старые форматы данных в новые

            if (!preferences.contains(SETTINGS_KEY)){
                Settings settings = new Settings();

                String json = preferences.getString(FAVORITEGROUPS_KEY, "");

                if (!json.equals("")) {
                    settings.setFavoriteGroups((Set<String>) gson.fromJson(json, new TypeToken<HashSet<String>>() {}.getType()));
                } else {
                    settings.setFavoriteGroups(new HashSet<String>());
                }

                settings.setNotificationGroup(
                        preferences.getString(GROUP_KEY, "")
                );

                settings.setNotifOn(
                        preferences.getBoolean(NOTIFON_KEY, false)
                );

                settings.setAlarmMode(
                        preferences.getBoolean(ALARMMODE_KEY, false)
                );

                preferences.edit()
                        .remove(FAVORITEGROUPS_KEY)
                        .remove(GROUP_KEY)
                        .remove(ALARMMODE_KEY)
                        .remove(NOTIFON_KEY)
                        .remove(URL_KEY)
                        .remove(DOC_KEY)
                        .putString(SETTINGS_KEY, gson.toJson(settings))
                        .apply();
            }

            if (!preferences.contains(TIMETABLE_NEW_KEY)){
                for (String key: preferences.getAll().keySet()){
                    if (key.contains(TIMETABLE_KEY)){

                    }
                }
            }

            preferences.edit().putInt(VERSION_KEY, BuildConfig.VERSION_CODE).apply();
//        }
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
