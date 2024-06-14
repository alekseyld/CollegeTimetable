package com.alekseyld.collegetimetable.repository;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.alekseyld.collegetimetable.dto.SettingsDto;
import com.alekseyld.collegetimetable.dto.SettingsResponse;
import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.repository.base.SettingsRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashSet;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public class SettingsRepositoryImpl implements SettingsRepository {

    private final SharedPreferences mPref;

    private final Gson mGson;
    private final Type mSettingType;

    @Inject
    SettingsRepositoryImpl(SharedPreferences sharedPreferences) {
        mPref = sharedPreferences;

        mGson = new Gson();
        mSettingType = new TypeToken<SettingsDto>() {}.getType();
    }

    @Override
    public boolean saveSettings(SettingsDto settings) {
        String json = mGson.toJson(settings);
        SharedPreferences.Editor ed = mPref.edit();
        ed.putString(SETTINGS_KEY, json);
        ed.putBoolean(NOTIFON_KEY, settings.getNotifOn());
        ed.putBoolean(DARK_MODE_KEY, settings.isDarkMode());
        ed.apply();
        return true;
    }

    @NonNull
    @Override
    public SettingsDto updateSettings(SettingsResponse settings) {
        SettingsDto updatedSettings = getSettings();

        updatedSettings.setAbbreviationMap(settings.getAbbreviationMap());
        updatedSettings.setNeftGroup(settings.getNeftGroup());
        updatedSettings.setRootUrl(settings.getRootUrl());

        saveSettings(updatedSettings);

        return updatedSettings;
    }

    @NonNull
    @Override
    public SettingsDto getSettings() {
        String json = mPref.getString(SETTINGS_KEY, "");
        SettingsDto dto = mGson.fromJson(json, mSettingType);

        if (dto != null) return dto;

        Settings defaultSettings = new Settings()
            .setAlarmMode(false)
            .setChangeMode(false)
            .setNotifOn(false)
            .setTeacherGroups(new HashSet<>())
            .setTeacherMode(false);
        return new SettingsDto(defaultSettings);
    }

}
