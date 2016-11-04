package com.alekseyld.collegetimetable.repository.base;

import com.alekseyld.collegetimetable.SettingsWrapper;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public interface SettingsRepository {

    String URL_KEY = "Url";
    String GROUP_KEY = "Group";
    String TIME_KEY = "Time";
    String FAVORITEGROUPS_KEY = "FavoriteGroups";

    String getGroup();
    int getTime();
    String getUrl();

    void putGroup(String group);
    void putTime(int time);
    void putUrl(String url);

    void put(String group, int time);

    void saveSettings(SettingsWrapper settings);
    SettingsWrapper getSettings();

}
