package com.alekseyld.collegetimetable.entity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alekseyld on 04.11.2016.
 */

public class Settings {

    @NotNull
    private Set<String> favoriteGroups = new HashSet<>();

    @NotNull
    private Map<String, String> abbreviationMap = new HashMap<>();

    @NotNull
    private List<String> neftGroup = new ArrayList<>();

    @NotNull
    private String rootUrl = "";

    @NotNull
    private String notificationGroup = "";

    private boolean notifOn;

    private boolean alarmMode;

    private boolean changeMode;

    private boolean darkMode;

    private boolean teacherMode;

    @NotNull
    private Set<String> teacherGroups = new HashSet<>();

    public Settings() {
    }

    public Settings(
        @NotNull Set<String> favoriteGroups,
        @NotNull String notificationGroup,
        boolean notifOn,
        boolean alarmMode
    ) {
        this.favoriteGroups = favoriteGroups;
        this.notificationGroup = notificationGroup;
        this.alarmMode = alarmMode;
        this.notifOn = notifOn;
        this.teacherMode = false;
    }

    @NotNull
    public Set<String> getFavoriteGroups() {
        return favoriteGroups;
    }

    public Settings setFavoriteGroups(@NotNull Set<String> favoriteGroups) {
        this.favoriteGroups = favoriteGroups;
        return this;
    }

    public void addFavoriteGroup(@NotNull String favoriteGroup) {
        this.favoriteGroups.add(favoriteGroup);
    }

    public void removeFavoriteGroup(@NotNull String favoriteGroup) {
        this.favoriteGroups.remove(favoriteGroup);
    }

    @NotNull
    public String getNotificationGroup() {
        return notificationGroup;
    }

    public Settings setNotificationGroup(@NotNull String notificationGroup) {
        this.notificationGroup = notificationGroup;
        return this;
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

    @NotNull
    public Set<String> getTeacherGroups() {
        return teacherGroups;
    }

    public Settings setTeacherGroups(@NotNull Set<String> teacherGroups) {
        this.teacherGroups = teacherGroups;
        return this;
    }

    public void addTeacherGroup(@NotNull String favoriteGroup) {
        this.teacherGroups.add(favoriteGroup);
    }

    public void removeTeacherGroup(@NotNull String favoriteGroup) {
        this.teacherGroups.remove(favoriteGroup);
    }

    @NotNull
    public Map<String, String> getAbbreviationMap() {
        return abbreviationMap;
    }

    public Settings setAbbreviationMap(@NotNull Map<String, String> abbreviationMap) {
        this.abbreviationMap = abbreviationMap;
        return this;
    }

    @NotNull
    public String getRootUrl() {
        return rootUrl;
    }

    public Settings setRootUrl(@NotNull String rootUrl) {
        this.rootUrl = rootUrl;
        return this;
    }

    @NotNull
    public List<String> getNeftGroup() {
        return neftGroup;
    }

    public Settings setNeftGroup(@NotNull List<String> neftGroup) {
        this.neftGroup = neftGroup;
        return this;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public Settings setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
        return this;
    }

    public boolean hasExternalSettings() {
        return !(rootUrl.isEmpty() || abbreviationMap.isEmpty() || neftGroup.isEmpty());
    }
}
