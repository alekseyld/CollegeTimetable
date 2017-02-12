package com.alekseyld.collegetimetable.repository.base;

import android.content.SharedPreferences;

import com.alekseyld.collegetimetable.SettingsWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import static com.alekseyld.collegetimetable.repository.base.TableRepository.DOC_KEY;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.TIMETABLE_KEY;

/**
 * Created by Alekseyld on 05.11.2016.
 */

public abstract class BaseSettingsRepository implements SettingsRepository {

    private SharedPreferences mPref;

    private Gson mGson;
    private Type mSetType;

    public BaseSettingsRepository(SharedPreferences sharedPreferences) {
        mPref = sharedPreferences;

        mGson = new Gson();
        mSetType = new TypeToken<HashSet<String>>(){}.getType();
    }

    @Override
    public String getGroup() {
        return mPref.getString(GROUP_KEY, "");
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

    private void saveFavorite(Set<String> groups){
        String json = mGson.toJson(groups);

        if(groups != null && groups.size() > 0){
            SharedPreferences.Editor ed = mPref.edit();
            ed.remove(FAVORITEGROUPS_KEY);
            ed.putString(FAVORITEGROUPS_KEY, json);
            ed.apply();
        }
    }

    private void saveNotification(String group){
        if(group != null && !group.equals("")){
            SharedPreferences.Editor ed = mPref.edit();
            ed.putString(GROUP_KEY, group.toUpperCase());
            ed.remove(DOC_KEY);
            ed.remove(TIMETABLE_KEY);
            ed.remove(URL_KEY);
            ed.apply();
        }
    }

    private void saveAlarmMode(boolean alarmMode){
        SharedPreferences.Editor ed = mPref.edit();
        ed.putBoolean(ALARMMODE_KEY, alarmMode);
        ed.apply();
    }

    private void saveNotifOn(boolean notifOn){
        SharedPreferences.Editor ed = mPref.edit();
        ed.putBoolean(NOTIFON_KEY, notifOn);
        ed.apply();
    }

    private Set<String> getFavorite(){
        String json = mPref.getString(FAVORITEGROUPS_KEY, "");
        if(!json.equals("")){
            return mGson.fromJson(json, mSetType);
        }else {
            return new HashSet<>();
        }
    }

    private String getNotification(){
        return mPref.getString(GROUP_KEY, "");
    }

    private boolean getAlarmMode(){
        return mPref.getBoolean(ALARMMODE_KEY, true);
    }

    private boolean getNotifOn(){
        return mPref.getBoolean(NOTIFON_KEY, false);
    }

    @Override
    public boolean saveSettings(SettingsWrapper settings) {
        saveFavorite(settings.getFavoriteGroups());
        saveNotification(settings.getNotificationGroup());
        saveAlarmMode(settings.getAlarmMode());
        saveNotifOn(settings.getNotifOn());
        return true;
    }

    @Override
    public SettingsWrapper getSettings() {
        return new SettingsWrapper(
                getFavorite(),
                getNotification(),
                getNotifOn(),
                getAlarmMode()
        );
    }

}
