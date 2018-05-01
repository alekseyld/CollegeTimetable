package com.alekseyld.collegetimetable.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alekseyld on 04.11.2016.
 */

public class Settings {

    private Set<String> favoriteGroups = new HashSet<>();
    private String notificationGroup;
    private boolean notifOn;
    private boolean alarmMode;
    private boolean changeMode;

    private boolean teacherMode;
    private Set<String> teacherGroups = new HashSet<>();

    public Settings() {}

    public Settings(Set<String> favoriteGroups, String notificationGroup, boolean notifOn, boolean alarmMode) {
        this.favoriteGroups = favoriteGroups;
        this.notificationGroup = notificationGroup;
        this.alarmMode = alarmMode;
        this.notifOn = notifOn;
        this.teacherMode = false;
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

    public Settings setChangeMode(boolean changeMode) {
        this.changeMode = changeMode;
        return this;
    }

    public boolean getChangeMode() {
        return changeMode;
    }

    public boolean getAlarmMode() {
        return alarmMode;
    }

    public Settings setAlarmMode(boolean alarmMode) {
        this.alarmMode = alarmMode;
        return this;
    }

    public boolean getNotifOn() {
        return notifOn;
    }

    public Settings setNotifOn(boolean notifOn) {
        this.notifOn = notifOn;
        return this;
    }

    public boolean getTeacherMode() {
        return teacherMode;
    }

    public Settings setTeacherMode(boolean teacherMode) {
        this.teacherMode = teacherMode;
        return this;
    }

    public Set<String> getTeacherGroups() {
        return teacherGroups;
    }

    public Settings setTeacherGroups(Set<String> teacherGroups) {
        this.teacherGroups = teacherGroups;
        return this;
    }

    public void addTeacherGroup(String favoriteGroup) {
        this.teacherGroups.add(favoriteGroup);
    }

    public void removeTeacherGroup(String favoriteGroup) {
        this.teacherGroups.remove(favoriteGroup);
    }

}
