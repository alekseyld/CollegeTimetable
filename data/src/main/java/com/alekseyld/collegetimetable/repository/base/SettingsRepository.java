package com.alekseyld.collegetimetable.repository.base;

import androidx.annotation.NonNull;

import com.alekseyld.collegetimetable.dto.SettingsDto;
import com.alekseyld.collegetimetable.dto.SettingsResponse;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public interface SettingsRepository {

    String SETTINGS_KEY = "SETTINGS";

    String URL_KEY = "Url";
    String GROUP_KEY = "Group";
    String ALARMMODE_KEY = "AlarmMode";
    String NOTIFON_KEY = "NotifOn";
    String FAVORITEGROUPS_KEY = "FavoriteGroups";
    String DARK_MODE_KEY = "DarkMode";

    boolean saveSettings(SettingsDto settings);

    @NonNull
    SettingsDto updateSettings(SettingsResponse settings);

    @NonNull
    SettingsDto getSettings();
}
