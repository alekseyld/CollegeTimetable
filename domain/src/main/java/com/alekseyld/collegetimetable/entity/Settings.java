package com.alekseyld.collegetimetable.entity;

import java.util.Set;

/**
 * Created by Alekseyld on 04.11.2016.
 */

public class Settings {

    private Set<String> favoriteGroups;
    private String notificationGroup;
    private boolean notifOn;
    private boolean alarmMode;

    public Settings() {}

    public Settings(Set<String> favoriteGroups, String notificationGroup, boolean notifOn, boolean alarmMode) {
        this.favoriteGroups = favoriteGroups;
        this.notificationGroup = notificationGroup;
        this.alarmMode = alarmMode;
        this.notifOn = notifOn;
    }

    public Set<String> getFavoriteGroups() {
        return favoriteGroups;
    }

    public void setFavoriteGroups(Set<String> favoriteGroups) {
        this.favoriteGroups = favoriteGroups;
    }

    public void addFavoriteGroup(String favoriteGroup) {
        this.favoriteGroups.add(favoriteGroup);
    }

    public void removeFavoriteGroup(String favoriteGroup) {
        this.favoriteGroups.remove(favoriteGroup);
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

    public boolean getNotifOn() {
        return notifOn;
    }

    public void setNotifOn(boolean notifOn) {
        this.notifOn = notifOn;
    }
}
