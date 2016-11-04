package com.alekseyld.collegetimetable.service;

import com.alekseyld.collegetimetable.SettingsWrapper;
import com.alekseyld.collegetimetable.repository.base.SettingsRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Alekseyld on 04.11.2016.
 */

public class SettingsServiceImpl implements SettingsService {

    private SettingsRepository mSettingsRepository;

    @Inject
    public SettingsServiceImpl(SettingsRepository settingsRepository) {
        mSettingsRepository = settingsRepository;
    }

    @Override
    public Observable<Boolean> saveSettings(SettingsWrapper settings) {
        return Observable.just(
                mSettingsRepository.saveSettings(settings)
        );
    }

    @Override
    public Observable<SettingsWrapper> getSettings() {
        return Observable.just(
                mSettingsRepository.getSettings()
        );
    }
}
