package com.alekseyld.collegetimetable.repository.base;

import com.alekseyld.collegetimetable.entity.Settings;

/**
 * Created by Alekseyld on 04.09.2017.
 */

public interface SettingsRepository {

    String SETTINGS_KEY = "SETTINGS";

    String URL_KEY = "Url";
    String GROUP_KEY = "Group";
    String TIME_KEY = "Time";
    String ALARMMODE_KEY = "AlarmMode";
    String NOTIFON_KEY = "NotifOn";
    String FAVORITEGROUPS_KEY = "FavoriteGroups";

    boolean saveSettings(Settings settings);
    Settings getSettings();

}
