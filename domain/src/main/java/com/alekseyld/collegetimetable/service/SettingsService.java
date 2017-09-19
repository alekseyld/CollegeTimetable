package com.alekseyld.collegetimetable.service;

import com.alekseyld.collegetimetable.entity.Settings;

import rx.Observable;

/**
 * Created by Alekseyld on 04.11.2016.
 */

public interface SettingsService {

    Observable<Boolean> saveSettings(Settings settings);
    Observable<Settings> getSettings();

}
