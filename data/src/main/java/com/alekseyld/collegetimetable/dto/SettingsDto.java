package com.alekseyld.collegetimetable.dto;

import com.alekseyld.collegetimetable.entity.Settings;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alekseyld on 15.06.2024.
 */
public class SettingsDto {

    @SerializedName("favoriteGroups")
    private final Set<String> favoriteGroups;

    @SerializedName("abbreviationMap")
    private Map<String, String> abbreviationMap;

    @SerializedName("neftGroup")
    private List<String> neftGroup;

    @SerializedName("rootUrl")
    private String rootUrl;

    @SerializedName("notificationGroup")
    @Nullable
    private final String notificationGroup;

    @SerializedName("notifOn")
    private final boolean notifOn;

    @SerializedName("alarmMode")
    private final boolean alarmMode;

    @SerializedName("changeMode")
    private final boolean changeMode;

    @SerializedName("darkMode")
    private final boolean darkMode;

    @SerializedName("teacherMode")
    private final boolean teacherMode;
    
    @SerializedName("teacherGroups")
    private final Set<String> teacherGroups;

    public SettingsDto(Settings settings) {
        this.favoriteGroups = settings.getFavoriteGroups();
        this.abbreviationMap = settings.getAbbreviationMap();
        this.neftGroup = settings.getNeftGroup();
        this.rootUrl = settings.getRootUrl();
        this.notificationGroup = settings.getNotificationGroup();
        this.notifOn = settings.getNotifOn();
        this.alarmMode = settings.getAlarmMode();
        this.changeMode = settings.getChangeMode();
        this.darkMode = settings.isDarkMode();
        this.teacherMode = settings.getTeacherMode();
        this.teacherGroups = settings.getTeacherGroups();
    }

    public Settings toEntity() {
        return new Settings()
            .setFavoriteGroups(favoriteGroups)
            .setAbbreviationMap(abbreviationMap)
            .setNeftGroup(neftGroup)
            .setRootUrl(rootUrl)
            .setNotificationGroup(notificationGroup != null ? notificationGroup : "")
            .setNotifOn(notifOn)
            .setAlarmMode(alarmMode)
            .setChangeMode(changeMode)
            .setDarkMode(darkMode)
            .setTeacherMode(teacherMode)
            .setTeacherGroups(teacherGroups);
    }

    public boolean getNotifOn() {
        return notifOn;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setAbbreviationMap(Map<String, String> abbreviationMap) {
        this.abbreviationMap = abbreviationMap;
    }

    public void setNeftGroup(List<String> neftGroup) {
        this.neftGroup = neftGroup;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public String getRootUrl() {
        return rootUrl;
    }
}
