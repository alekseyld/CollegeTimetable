package com.alekseyld.collegetimetable.repository;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;

import com.alekseyld.collegetimetable.TableWrapper;
import com.alekseyld.collegetimetable.repository.base.SettingsRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public class SettingsRepositoryImpl implements SettingsRepository {

    private Activity mActivity;
    private SharedPreferences mPref;

    @Inject
    SettingsRepositoryImpl(Activity activity){
        mActivity = activity;
        mPref = mActivity.getSharedPreferences("DataStorage", MODE_PRIVATE);
    }

    @Override
    public String getGroup() {
        return mPref.getString("Group", "2 АПП-1");
    }

    @Override
    public int getTime() {
        return mPref.getInt("Time", 10);
    }

    @Override
    public void putGroup(String group) {
        SharedPreferences.Editor ed = mPref.edit();
        ed.putString("Group", group);
        ed.apply();
    }

    @Override
    public void putTime(int time) {
        SharedPreferences.Editor ed = mPref.edit();
        ed.putInt("Time", time);
        ed.apply();
    }

    @Override
    public String getUrl() {
        return mPref.getString("Url", null);
    }

    @Override
    public void putUrl(String url) {
        SharedPreferences.Editor ed = mPref.edit();
        ed.putString("Url", url);
        ed.apply();
    }

    @Override
    public void put(String group, int time) {
        putGroup(group);
        putTime(time);
    }
}
