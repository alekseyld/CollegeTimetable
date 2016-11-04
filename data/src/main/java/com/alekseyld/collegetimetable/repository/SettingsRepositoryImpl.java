package com.alekseyld.collegetimetable.repository;

import android.app.Activity;
import android.content.SharedPreferences;

import com.alekseyld.collegetimetable.SettingsWrapper;
import com.alekseyld.collegetimetable.repository.base.SettingsRepository;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.NAME_FILE;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public class SettingsRepositoryImpl implements SettingsRepository {

    private Activity mActivity;
    private SharedPreferences mPref;

    @Inject
    SettingsRepositoryImpl(Activity activity){
        mActivity = activity;
        mPref = mActivity.getSharedPreferences(NAME_FILE, MODE_PRIVATE);
    }

    @Override
    public String getGroup() {
        return mPref.getString(GROUP_KEY, "2 АПП-1");
    }

    @Override
    public int getTime() {
        return mPref.getInt(TIME_KEY, 10);
    }

    @Override
    public void putGroup(String group) {
        SharedPreferences.Editor ed = mPref.edit();
        ed.putString(GROUP_KEY, group);
        ed.apply();
    }

    @Override
    public void putTime(int time) {
        SharedPreferences.Editor ed = mPref.edit();
        ed.putInt(TIME_KEY, time);
        ed.apply();
    }

    @Override
    public String getUrl() {
        return mPref.getString(URL_KEY, null);
    }

    @Override
    public void putUrl(String url) {
        SharedPreferences.Editor ed = mPref.edit();
        ed.putString(URL_KEY, url);
        ed.apply();
    }

    @Override
    public void put(String group, int time) {
        putGroup(group);
        putTime(time);
    }

    @Override
    public void saveSettings(SettingsWrapper settings) {

    }

    @Override
    public SettingsWrapper getSettings() {
        return null;
    }
}
