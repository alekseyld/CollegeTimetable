package com.alekseyld.collegetimetable.utils.repository;

import androidx.annotation.NonNull;

import com.alekseyld.collegetimetable.dto.SettingsDto;
import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.dto.SettingsResponse;
import com.alekseyld.collegetimetable.repository.base.SettingsRepository;

import java.util.HashSet;

public class SettingsRepositoryMock implements SettingsRepository {

    SettingsDto settings = null;

    @Override
    public boolean saveSettings(SettingsDto settings) {
        this.settings = settings;
        return true;
    }

    @NonNull
    @Override
    public SettingsDto getSettings() {
        if (settings != null)
            return settings;

        Settings settings = new Settings()
            .setAlarmMode(false)
            .setChangeMode(false)
            .setNotifOn(false)
            .setTeacherGroups(new HashSet<>())
            .setTeacherMode(false);

        settings.setFavoriteGroups(new HashSet<>());
        settings.setNotificationGroup("");

        return new SettingsDto(settings);
    }

    @NonNull
    @Override
    public SettingsDto updateSettings(SettingsResponse settings) {
        SettingsDto updatedSettings = getSettings();
//        updatedSettings.setAbbreviationMap(settings.getAbbreviationMap());
//        updatedSettings.setNeftGroup(settings.getNeftGroup());
//        updatedSettings.setRootUrl(settings.getRootUrl());
        saveSettings(updatedSettings);
        return updatedSettings;
    }
}
