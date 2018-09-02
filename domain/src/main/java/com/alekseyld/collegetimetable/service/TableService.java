package com.alekseyld.collegetimetable.service;

import com.alekseyld.collegetimetable.entity.TimeTable;

import java.util.Set;

import rx.Observable;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public interface TableService {

    Observable<TimeTable> getTimetableFromOnline(boolean online, String group);
    Observable<TimeTable> getTimetableFromOnlineAssociativity(boolean online, Set<String> groups);
    Observable<TimeTable> getTimetableFromOffline(String group);

    Observable<Boolean> saveTimetable(TimeTable tableTable, String group);

    Observable<Boolean> update(String minute, String group);

    Observable<TimeTable> getTeacherTimeTable(boolean online, String teacherFio, Set<String> teacherGroup);

}
