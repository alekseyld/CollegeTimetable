package com.alekseyld.collegetimetable.service;

import com.alekseyld.collegetimetable.SettingsWrapper;

import rx.Observable;

/**
 * Created by Alekseyld on 04.11.2016.
 */

public interface SettingsService {

    Observable<Boolean> saveSettings(SettingsWrapper settings);
    Observable<SettingsWrapper> getSettings();

}
