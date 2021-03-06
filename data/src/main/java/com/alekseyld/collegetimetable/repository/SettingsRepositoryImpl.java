package com.alekseyld.collegetimetable.repository;

import android.content.SharedPreferences;

import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.entity.SettingsResponse;
import com.alekseyld.collegetimetable.repository.base.SettingsRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public class SettingsRepositoryImpl implements SettingsRepository {

    private SharedPreferences mPref;

    private Gson mGson;
    private Type mSettingType;

    @Inject
    SettingsRepositoryImpl(SharedPreferences sharedPreferences){
        mPref = sharedPreferences;

        mGson = new Gson();
        mSettingType = new TypeToken<Settings>(){}.getType();
    }

    @Override
    public boolean saveSettings(Settings settings) {
        String json = mGson.toJson(settings);
        SharedPreferences.Editor ed = mPref.edit();
        ed.putString(SETTINGS_KEY, json);
        ed.putBoolean(NOTIFON_KEY, settings.getNotifOn());
        ed.putBoolean(DARK_MODE_KEY, settings.isDarkMode());
        ed.apply();
        return true;
    }

    @Override
    public Settings updateSettings(SettingsResponse settings) {
        Settings updatedSettings = getSettings();
        updatedSettings.setAbbreviationMap(settings.getAbbreviationMap());
        updatedSettings.setNeftGroup(settings.getNeftGroup());
        updatedSettings.setRootUrl(settings.getRootUrl());
        saveSettings(updatedSettings);
        return updatedSettings;
    }

    @Override
    public Settings getSettings() {
        String json = mPref.getString(SETTINGS_KEY, "");
        return mGson.fromJson(json, mSettingType);
    }

}
