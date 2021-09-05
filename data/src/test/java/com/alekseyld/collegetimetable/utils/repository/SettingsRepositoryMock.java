package com.alekseyld.collegetimetable.utils.repository;

import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.entity.SettingsResponse;
import com.alekseyld.collegetimetable.repository.base.SettingsRepository;

import java.util.HashSet;

public class SettingsRepositoryMock implements SettingsRepository {

    Settings settings = null;

    @Override
    public boolean saveSettings(Settings settings) {
        this.settings = settings;
        return true;
    }

    @Override
    public Settings getSettings() {
        if (settings != null)
            return settings;

        settings = new Settings()
                .setAlarmMode(false)
                .setChangeMode(false)
                .setNotifOn(false)
                .setTeacherGroups(new HashSet<>())
                .setTeacherMode(false);

         settings.setFavoriteGroups(new HashSet<>());
         settings.setNotificationGroup("");
        return settings;
    }

    @Override
    public Settings updateSettings(SettingsResponse settings) {
        Settings updatedSettings = getSettings();
//        updatedSettings.setAbbreviationMap(settings.getAbbreviationMap());
//        updatedSettings.setNeftGroup(settings.getNeftGroup());
//        updatedSettings.setRootUrl(settings.getRootUrl());
        saveSettings(updatedSettings);
        return updatedSettings;
    }
}
