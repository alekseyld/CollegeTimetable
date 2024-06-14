package com.alekseyld.collegetimetable;

import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.ALARMMODE_KEY;
import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.FAVORITEGROUPS_KEY;
import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.GROUP_KEY;
import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.NOTIFON_KEY;
import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.SETTINGS_KEY;
import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.URL_KEY;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.DAYS_KEY;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.DOC_KEY;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.TIMETABLE_KEY;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.TIMETABLE_NEW_KEY;

import android.content.SharedPreferences;

import com.alekseyld.collegetimetable.entity.Day;
import com.alekseyld.collegetimetable.entity.Lesson;
import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by Alekseyld on 04.10.2017.
 */

class BackwardCompatibility {

    private final static String VERSION_KEY = "VERSION";

    static void checkAppVersion(SharedPreferences preferences) {

        if (!preferences.contains(VERSION_KEY)) {
            Gson gson = new Gson();

            if (!preferences.contains(SETTINGS_KEY)) {
                settingToNewFormat(preferences, gson);
            }

            if (!preferences.contains(TIMETABLE_NEW_KEY)) {
                timeTableToNewFormat(preferences, gson);
            }

            preferences.edit().putInt(VERSION_KEY, BuildConfig.VERSION_CODE).apply();
        }
    }

    private static void settingToNewFormat(SharedPreferences preferences, Gson gson) {
        Settings settings = new Settings();

        String json = preferences.getString(FAVORITEGROUPS_KEY, "");

        if (!json.isEmpty()) {
            settings.setFavoriteGroups(gson.fromJson(json, new TypeToken<HashSet<String>>() {}.getType()));
        } else {
            settings.setFavoriteGroups(new HashSet<>());
        }

        settings.setNotificationGroup(
            preferences.getString(GROUP_KEY, "")
        );

        settings.setNotifOn(
            preferences.getBoolean(NOTIFON_KEY, false)
        );

        settings.setAlarmMode(
            preferences.getBoolean(ALARMMODE_KEY, false)
        );

        preferences.edit()
            .remove(FAVORITEGROUPS_KEY)
            .remove(GROUP_KEY)
            .remove(ALARMMODE_KEY)
            .remove(NOTIFON_KEY)
            .remove(URL_KEY)
            .remove(DOC_KEY)
            .putString(SETTINGS_KEY, gson.toJson(settings))
            .apply();
    }

    private static void timeTableToNewFormat(SharedPreferences preferences, Gson gson) {
        Map<String, ?> preferencesMap = preferences.getAll();
        for (String key : preferencesMap.keySet()) {
            if (key.contains(TIMETABLE_KEY)) {
                String group = key.split(TIMETABLE_KEY)[1];

                String jsonTimetable = (String) preferencesMap.get(key);
                String jsonDays = (String) preferencesMap.get(DAYS_KEY + group);

                TimeTable newTimetable = new TimeTable();
                newTimetable.setDayList(Arrays.asList(new Day[7]));

                HashMap<String, Map<String, String>> timetable = gson.fromJson(jsonTimetable, new TypeToken<>(){}.getType());
                HashMap<String, String> days = gson.fromJson(jsonDays, new TypeToken<>(){}.getType());

                for (String timeKey : timetable.keySet()) {
                    int index = getDayIndex(timeKey);

                    Day day = new Day()
                        .setId(index)
                        .setDate(days.get(timeKey))
                        .setDayLessons(getLessonListFromMap(timetable.get(timeKey)));

                    newTimetable.getDayList().set(index, day);
                }

                preferences.edit()
                    .putString(TIMETABLE_NEW_KEY + group, gson.toJson(newTimetable))
                    .remove(TIMETABLE_KEY + group)
                    .remove(DAYS_KEY + group)
                    .apply();
            }
        }
    }

    private static int getDayIndex(String timeKey) {
        switch (timeKey) {
            case "Mon":
                return 0;
            case "Tue":
                return 1;
            case "Wed":
                return 2;
            case "Thu":
                return 3;
            case "Friday":
                return 4;
            case "Saturday":
                return 5;
            case "Mon2":
                return 6;
            default:
                return 0;
        }
    }

    private static List<Lesson> getLessonListFromMap(Map<String, String> lessonMap) {
        List<Lesson> lessons = Arrays.asList(new Lesson[7]);

        for (String key : lessonMap.keySet()) {
            int index = getLessonIndexFromKey(key);

            Lesson lesson = new Lesson()
                .setNumber(index)
                .setName(lessonMap.get(key));

            lessons.set(index, lesson);
        }

        return lessons;
    }

    private static int getLessonIndexFromKey(String key) {
        return Integer.parseInt(key.split("lesson")[1]);
    }

}
