package com.alekseyld.collegetimetable.service;

import com.alekseyld.collegetimetable.SettingsWrapper;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Alekseyld on 04.11.2016.
 */

public class SettingsServiceImpl implements SettingsService {

    @Inject
    public SettingsServiceImpl() {
    }

    @Override
    public Observable<Boolean> saveSettings(SettingsWrapper settings) {
        return null;
    }

    @Override
    public Observable<SettingsWrapper> getSettings() {
        return null;
    }
}
