package com.alekseyld.collegetimetable.service;

import com.alekseyld.collegetimetable.api.SettingsApi;
import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.repository.base.SettingsRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Alekseyld on 04.11.2016.
 */

public class SettingsServiceImpl implements SettingsService {

    private SettingsRepository mSettingsRepository;
    private SettingsApi mSettingsApi;

    @Inject
    public SettingsServiceImpl(SettingsRepository settingsRepository,
                               SettingsApi settingsApi) {

        mSettingsRepository = settingsRepository;
        mSettingsApi = settingsApi;
    }

    @Override
    public Observable<Boolean> saveSettings(Settings settings) {
        return Observable.just(
                mSettingsRepository.saveSettings(settings)
        );
    }

    @Override
    public Observable<Settings> getSettings() {
        return Observable.just(
                mSettingsRepository.getSettings()
        );
    }

    @Override
    public Observable<Settings> updateSettingsOnline() {
        return mSettingsApi.getSettings()
                .map(settingsResponse -> mSettingsRepository.updateSettings(settingsResponse));
    }

}
