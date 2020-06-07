package com.alekseyld.collegetimetable.repository.base;

import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.entity.SettingsResponse;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public interface SettingsRepository {

    String SETTINGS_KEY = "SETTINGS";

    String URL_KEY = "Url";
    String GROUP_KEY = "Group";
    String TIME_KEY = "Time";
    String ALARMMODE_KEY = "AlarmMode";
    String NOTIFON_KEY = "NotifOn";
    String FAVORITEGROUPS_KEY = "FavoriteGroups";
    String DARK_MODE_KEY = "DarkMode";

    boolean saveSettings(Settings settings);
    Settings updateSettings(SettingsResponse settings);
    Settings getSettings();

}
