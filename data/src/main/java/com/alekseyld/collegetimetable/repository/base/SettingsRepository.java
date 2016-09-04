package com.alekseyld.collegetimetable.repository.base;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public interface SettingsRepository {

    String getGroup();
    int getTime();

    void putGroup(String group);
    void putTime(int time);
    void put(String group, int time);

}
