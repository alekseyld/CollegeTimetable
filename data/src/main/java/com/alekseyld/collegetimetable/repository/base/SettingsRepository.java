package com.alekseyld.collegetimetable.repository.base;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public interface SettingsRepository {

    String getGroup();
    int getTime();
    String getUrl();

    void putGroup(String group);
    void putTime(int time);
    void putUrl(String url);

    void put(String group, int time);

}
