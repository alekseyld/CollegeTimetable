package com.alekseyld.collegetimetable;

import java.util.Set;

/**
 * Created by Alekseyld on 04.11.2016.
 */

public class SettingsWrapper {

    private Set<String> favoriteGroups;
    private String notificationGroup;
    private boolean alarmMode;

    public SettingsWrapper() {}

    public SettingsWrapper(Set<String> favoriteGroups, String notificationGroup, boolean alarmMode) {
        this.favoriteGroups = favoriteGroups;
        this.notificationGroup = notificationGroup;
        this.alarmMode = alarmMode;
    }

    public Set<String> getFavoriteGroups() {
        return favoriteGroups;
    }

    public void setFavoriteGroups(Set<String> favoriteGroups) {
        this.favoriteGroups = favoriteGroups;
    }

    public String getNotificationGroup() {
        return notificationGroup;
    }

    public void setNotificationGroup(String notificationGroup) {
        this.notificationGroup = notificationGroup;
    }

    public boolean getAlarmMode() {
        return alarmMode;
    }

    public void setAlarmMode(boolean alarmMode) {
        this.alarmMode = alarmMode;
    }
}
